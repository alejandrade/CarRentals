import React, { useState } from 'react';
import {Card, CardHeader, CardContent, TextField, Button, Grid, Container} from '@mui/material';

export interface UserInsuranceDto {
    policyNumber: string;
    provider: string;
    endDate: Date;
    active: boolean;
}

const InsuranceForm: React.FC = () => {
    const [formData, setFormData] = useState<Partial<UserInsuranceDto>>({ active: false });
    const [errors, setErrors] = useState<{ [key in keyof UserInsuranceDto]?: string }>({});

    const validateInput = (name: keyof UserInsuranceDto, value: any) => {
        switch (name) {
            case 'policyNumber':
                return !value.trim() ? 'Policy number is required' : undefined;
            case 'provider':
                return !value.trim() ? 'Provider is required' : undefined;
            case 'endDate':
                return !value ? 'End date is required' : undefined;
            default:
                return undefined;
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

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        // Process formData
        console.log('Insurance Data:', formData);
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
                            <Button type="submit" variant="contained" fullWidth color="primary">
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

export default InsuranceForm;
