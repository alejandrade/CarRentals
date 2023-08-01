import React, { useState } from 'react';
import { Card, CardContent, CardHeader, TextField, Button, Grid, SelectChangeEvent, Container } from '@mui/material';
import USStatesDropdown from "../../components/USStatesDropdown";

export interface UserLicenseDto {
    licenseNumber: string;
    issuingState: string;
    expirationDate: Date;
}

const LicenseForm: React.FC = () => {
    const [formData, setFormData] = useState<Partial<UserLicenseDto>>({});
    const [errors, setErrors] = useState<{ [key in keyof UserLicenseDto]?: string }>({});

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

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        console.log('License Data:', formData);
    };

    return (
        <Card>
            <CardHeader title="License" />
            <CardContent>
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
                                    onChange={handleChangeSelect()}
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
                                <Button fullWidth type="submit" variant="contained" color="primary">
                                    Submit
                                </Button>
                            </Grid>
                        </Grid>
                    </form>
                </Container>
            </CardContent>
        </Card>
    );
};

export default LicenseForm;
