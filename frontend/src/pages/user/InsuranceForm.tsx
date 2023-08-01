import React, {useEffect, useState} from 'react';
import {Card, CardHeader, CardContent, TextField, Button, Grid, Container} from '@mui/material';
import {compressImage} from "../../util/ImageFunctions";
import LoadingButton from "@mui/lab/LoadingButton";
import {UserInsuranceDto} from "../../services/user/UserService.types";
import userService from "../../services/user/UserService";
import {useErrorModal} from "../../contexts/ErrorModalContext";


export interface keyHolder {
    policyNumber: string;
    provider: string;
    endDate: Date;
    active: boolean;
    frontImage?: string;  // Added these fields
    backImage?: string;
}

type ContactInformationProps = {
    dto: UserInsuranceDto,
    onSave: (dto: UserInsuranceDto) => void;
    userId: string;
};

const InsuranceForm: React.FC<ContactInformationProps> = ({dto, onSave, userId}) => {
    const [formData, setFormData] = useState<Partial<UserInsuranceDto>>(dto);
    const [errors, setErrors] = useState<{ [key in keyof keyHolder]?: string }>({});
    const [frontImage, setFrontImage] = useState<File | null>(null);  // New state for compressed front image
    const [backImage, setBackImage] = useState<File | null>(null);   // New state for compressed back image
    const [loading, setLoading] = useState<boolean>(false);
    const [disabled, setDisabled] = useState<boolean>(true);
    const { showError, handleAPIError } = useErrorModal();

    const isFormValid = () => {
        const requiredFields: (keyof keyHolder)[] = ['policyNumber', 'provider', 'endDate', 'frontImage', 'backImage'];

        // Check if any error exists
        for (let key in errors) {
            // @ts-ignore
            if (errors[key]) return false;
        }

        // Check if all required fields are present
        for (let field of requiredFields) {
            // @ts-ignore
            if (!formData[field] && field !== 'frontImage' && field !== 'backImage') return false;
            if ((field === 'frontImage' && !frontImage) || (field === 'backImage' && !backImage)) return false;
        }

        return true;
    };

    useEffect(() => {
        setDisabled(!isFormValid());
    }, [formData, errors, frontImage, backImage]);



    const validateInput = (name: string, value: any) => {
        switch (name) {
            case 'policyNumber':
                return !value.trim() ? 'Policy number is required' : undefined;
            case 'provider':
                return !value.trim() ? 'Provider is required' : undefined;
            case 'endDate':
                return !value ? 'End date is required' : undefined;
            case 'frontImage':
            case 'backImage':
                return !value ? 'Image is required' : undefined;
            default:
                return undefined;
        }
    };


    const handleImageChange = (setImage: React.Dispatch<React.SetStateAction<File | null>>) => async (
        event: React.ChangeEvent<HTMLInputElement>
    ) => {
        const file = event.target.files?.[0];
        if (file) {
            try {
                const compressedBlob = await compressImage(file);
                const compressedFile = new File([compressedBlob], file.name, {
                    type: compressedBlob.type, // This ensures the file type (MIME) remains the same
                    lastModified: new Date().getTime() // Optionally set the last modified date
                });
                setImage(compressedFile);
            } catch (err) {
                console.error('Failed to compress image:', err);
            }
        }
    };

    const handleChange = (name: keyof UserInsuranceDto) => (
        event: React.ChangeEvent<HTMLInputElement>
    ) => {
        let value: any;

        if (name === 'endDate') {
            value = new Date(event.target.value);
        } else if (name === 'active') {
            value = event.target.checked;
        } else {
            value = event.target.value;
        }

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
        const userInsuranceDto = await userService.saveInsurance(formData as UserInsuranceDto).catch(handleAPIError);
        if (userInsuranceDto && frontImage && backImage) {
            await userService.uploadInsuranceImage(userInsuranceDto.id, "FRONT", frontImage).catch(handleAPIError);
            await userService.uploadInsuranceImage(userInsuranceDto.id, "BACK", backImage).catch(handleAPIError);
        }
        setLoading(false);
        onSave(formData as UserInsuranceDto);
    };

    return (
        <Card>
            <CardHeader title="Insurance" />
            <CardContent>
                <Container>

                <form onSubmit={handleSubmit}>
                    <Grid container direction="column" spacing={2}>
                        <Grid item>
                            <TextField
                                fullWidth
                                label="Policy Number"
                                value={formData.policyNumber || ''}
                                onChange={handleChange('policyNumber')}
                                error={Boolean(errors.policyNumber)}
                                helperText={errors.policyNumber}
                            />
                        </Grid>

                        <Grid item>
                            <TextField
                                fullWidth
                                label="Provider"
                                value={formData.provider || ''}
                                onChange={handleChange('provider')}
                                error={Boolean(errors.provider)}
                                helperText={errors.provider}
                            />
                        </Grid>

                        <Grid item>
                            <TextField
                                fullWidth
                                label="End Date"
                                type="date"
                                value={formData.endDate?.toISOString().split('T')[0] || ''}
                                onChange={handleChange('endDate')}
                                InputLabelProps={{
                                    shrink: true,
                                }}
                                error={Boolean(errors.endDate)}
                                helperText={errors.endDate}
                            />
                        </Grid>

                        <Grid item>
                            <TextField
                                fullWidth
                                label="Front of Card"
                                type="file"
                                onChange={handleImageChange(setFrontImage)}
                                error={Boolean(errors.frontImage)}
                                helperText={errors.frontImage}
                                InputProps={{ inputProps: { accept: 'image/*' } }}
                                InputLabelProps={{
                                    shrink: true,
                                }}
                            />
                        </Grid>

                        <Grid item>
                            <TextField
                                fullWidth
                                label="Back of Card"
                                type="file"
                                onChange={handleImageChange(setBackImage)}
                                error={Boolean(errors.backImage)}
                                helperText={errors.backImage}
                                InputProps={{ inputProps: { accept: 'image/*' } }}
                                InputLabelProps={{
                                    shrink: true,
                                }}
                            />
                        </Grid>

                        <Grid item>
                            <LoadingButton disabled={disabled} loading={loading} type="submit" variant="contained" fullWidth color="primary">
                                Save
                            </LoadingButton>
                        </Grid>
                    </Grid>
                </form>
                </Container>
            </CardContent>
        </Card>
    );
};

export default InsuranceForm;
