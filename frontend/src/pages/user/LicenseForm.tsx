import React, {useEffect, useState} from 'react';
import { Card, CardContent, CardHeader, TextField, Button, Grid, SelectChangeEvent, Container } from '@mui/material';
import USStatesDropdown from "../../components/USStatesDropdown";
import {compressImage} from "../../util/ImageFunctions";
import LoadingButton from "@mui/lab/LoadingButton";
import {Gender, UserDemographicsDto, UserInsuranceDto, UserLicenseDto} from "../../services/user/UserService.types";
import ImageUpload from "../../components/ImageUpload";
import userService from "../../services/user/UserService";
import {useErrorModal} from "../../contexts/ErrorModalContext";


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
    const { showError, handleAPIError } = useErrorModal();

    const validateInput = (name: keyof UserLicenseDto, value: any) => {
        switch (name) {
            case 'licenseNumber':
                return !value.trim() ? 'License number is required' : undefined;
            case 'issuingState':
                return !value.trim() ? 'Issuing state is required' : undefined;
            case 'expirationDate':
                return !value ? 'Expiration date is required' : undefined;
            case 'dateOfIssue':
                return !value ? "Issued date is required" : undefined;
            default:
                return undefined;
        }
    };

    const handleSelect = (name: keyof UserLicenseDto) => (
        event: SelectChangeEvent
    ) => {
        setFormData({
            ...formData,
            [name]: event.target.value
        });
    };


    const handleChange = (name: keyof UserLicenseDto) => (
        event: React.ChangeEvent<HTMLInputElement>
    ) => {
        const value = name === 'expirationDate' || name === "dateOfIssue" ? new Date(event.target.value) : event.target.value;

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

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setLoading(true);
        console.log(formData);
        const userLicenseDto = await userService.saveLicense(formData as UserLicenseDto).catch(handleAPIError);
        console.log(userLicenseDto);
        if (userLicenseDto && frontImage && backImage) {
            await userService.uploadLicenseImage(userLicenseDto.id, "FRONT", frontImage).catch(handleAPIError);
            await userService.uploadLicenseImage(userLicenseDto.id, "BACK", backImage).catch(handleAPIError);
        }

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
                                    label="Issued Date"
                                    type="date"
                                    value={formData.dateOfIssue?.toISOString().split('T')[0] || ''}
                                    onChange={handleChange('dateOfIssue')}
                                    InputLabelProps={{
                                        shrink: true,
                                    }}
                                    error={Boolean(errors.dateOfIssue)}
                                    helperText={errors.dateOfIssue}
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
                            <Grid item >
                                <ImageUpload label="Front of License" onImageChange={setFrontImage} />
                            </Grid>
                            <Grid item>
                                <ImageUpload label="Back of License" onImageChange={setBackImage} />
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
