import React, { useState } from 'react';
import { Card, CardContent, CardHeader, TextField, Button, Grid, SelectChangeEvent, Container } from '@mui/material';
import USStatesDropdown from "../../components/USStatesDropdown";
import {compressImage} from "../../util/ImageFunctions";
import LoadingButton from "@mui/lab/LoadingButton";
import {Gender, UserDemographicsDto, UserInsuranceDto, UserLicenseDto} from "../../services/user/UserService.types";


type Props = {
    dto: Partial<UserLicenseDto>,
    onSave: (dto: UserLicenseDto) => void;
};
const LicenseForm: React.FC<Props> = ({dto, onSave}) => {
    const [formData, setFormData] = useState<Partial<UserLicenseDto>>(dto);
    const [errors, setErrors] = useState<{ [key in keyof UserLicenseDto]?: string }>({});
    const [frontImage, setFrontImage] = useState<File | null>(null);
    const [backImage, setBackImage] = useState<File | null>(null);
    const [loading, setLoading] = useState<boolean>(false);

    const handleImageChange = (setImage: React.Dispatch<React.SetStateAction<File | null>>) => async (
        event: React.ChangeEvent<HTMLInputElement>
    ) => {
        const file = event.target.files?.[0];
        if (file) {
            try {
                const compressedBlob = await compressImage(file);
                const compressedFile = new File([compressedBlob], file.name, {
                    type: compressedBlob.type,
                    lastModified: new Date().getTime()
                });
                setImage(compressedFile);
            } catch (err) {
                console.error('Failed to compress image:', err);
            }
        }
    };
    const validateInput = (name: keyof UserLicenseDto, value: any) => {
        switch (name) {
            case 'licenseNumber':
                return !value.trim() ? 'License number is required' : undefined;
            case 'issuingState':
                return !value.trim() ? 'Issuing state is required' : undefined;
            case 'expirationDate':
                return !value ? 'Expiration date is required' : undefined;
            default:
                return undefined;
        }
    };

    const handleSelect = (name: keyof UserLicenseDto) => (
        event: SelectChangeEvent
    ) => {
        setFormData({
            ...formData,
            [name]: event.target.value as Gender
        });
    };


    const handleChange = (name: keyof UserLicenseDto) => (
        event: React.ChangeEvent<HTMLInputElement>
    ) => {
        const value = name === 'expirationDate' ? new Date(event.target.value) : event.target.value;

        // Set form data
        setFormData({
            ...formData,
            [name]: value
        });

        // Validate and set errors
        const error = validateInput(name, value);
        setErrors({
            ...errors,
            [name]: error
        });
    };

    const handleChangeSelect = () => (
        event: SelectChangeEvent
    ) => {
        const value = event.target.value;

        // Set form data
        setFormData({
            ...formData,
            issuingState: value
        });

        // Validate and set errors
        const error = validateInput('issuingState', value);
        setErrors({
            ...errors,
            'issuingState': error
        });
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setLoading(true);
        onSave(formData as UserLicenseDto);
        setLoading(false);
    };

    return (

                <Container>
                    <form onSubmit={handleSubmit}>
                        <Grid container direction="column" spacing={2}>
                            <Grid item>
                                <TextField
                                    fullWidth
                                    label="License Number"
                                    value={formData.licenseNumber || ''}
                                    onChange={handleChange('licenseNumber')}
                                    error={Boolean(errors.licenseNumber)}
                                    helperText={errors.licenseNumber}
                                />
                            </Grid>
                            <Grid item>
                                <USStatesDropdown
                                    value={formData.issuingState || ''}
                                    onChange={handleSelect('issuingState')}
                                    error={Boolean(errors.issuingState)}
                                    helperText={errors.issuingState}
                                />
                            </Grid>
                            <Grid item>
                                <TextField
                                    fullWidth
                                    label="Expiration Date"
                                    type="date"
                                    value={formData.expirationDate?.toISOString().split('T')[0] || ''}
                                    onChange={handleChange('expirationDate')}
                                    InputLabelProps={{
                                        shrink: true,
                                    }}
                                    error={Boolean(errors.expirationDate)}
                                    helperText={errors.expirationDate}
                                />
                            </Grid>
                            <Grid item>
                                <TextField
                                    fullWidth
                                    label="Front of License"
                                    type="file"
                                    onChange={handleImageChange(setFrontImage)}
                                    InputProps={{ inputProps: { accept: 'image/*' } }}
                                    InputLabelProps={{
                                        shrink: true,
                                    }}
                                />
                            </Grid>
                            <Grid item>
                                <TextField
                                    fullWidth
                                    label="Back of License"
                                    type="file"
                                    onChange={handleImageChange(setBackImage)}
                                    InputProps={{ inputProps: { accept: 'image/*' } }}
                                    InputLabelProps={{
                                        shrink: true,
                                    }}
                                />
                            </Grid>
                            <Grid item>
                                <LoadingButton loading={loading} type="submit" variant="contained" fullWidth color="primary">
                                    Submit
                                </LoadingButton>
                            </Grid>
                        </Grid>
                    </form>
                </Container>
    );
};


export default LicenseForm;
