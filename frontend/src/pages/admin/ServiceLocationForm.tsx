import React, {useEffect, useState} from 'react';
import {
    Container,
    TextField,
    Grid,
} from '@mui/material';
import LoadingButton from '@mui/lab/LoadingButton';
import {ServiceLocationDto} from "../../services/service_location/ServiceLocationService.types";
import USStatesDropdown from "../../components/USStatesDropdown";

type FormErrors = {
    name?: string;
    address?: string;
    city?: string;
    state?: string;
    postalCode?: string;
    country?: string;
    additionalInfo?: string;
};

type ServiceLocationProps = {
    dto: Partial<ServiceLocationDto>;
    onSave: (data: ServiceLocationDto) => void;
}

const ServiceLocationForm: React.FC<ServiceLocationProps> = ({ dto, onSave }) => {
    const [formData, setFormData] = useState<Partial<ServiceLocationDto>>(dto || {});
    const [errors, setErrors] = useState<FormErrors>({});
    const [loading, setLoading] = useState<boolean>(false);
    const [isFormValid, setIsFormValid] = useState<boolean>(false);
    useEffect(() => {
        setIsFormValid(checkFormValid());
    }, [formData]);
    const handleChange = (name: keyof ServiceLocationDto) => (event: React.ChangeEvent<HTMLInputElement>) => {
        setFormData({
            ...formData,
            [name]: event.target.value
        });
    };

    const validateForm = (): boolean => {
        let tempErrors: FormErrors = {};

        if (!formData.name) {
            tempErrors.name = "Name is required.";
        }
        if (!formData.address) {
            tempErrors.address = "Address is required.";
        }
        if (!formData.city) {
            tempErrors.city = "City is required.";
        }
        if (!formData.state) {
            tempErrors.state = "State is required.";
        }
        if (!formData.postalCode) {
            tempErrors.postalCode = "Postal code is required.";
        } else if (!/^\d{5}$/.test(formData.postalCode)) {
            tempErrors.postalCode = "Invalid US ZIP code format";
        }

        if (!formData.country) {
            tempErrors.country = "Country is required.";
        }
        // ... and so on for other fields if needed.

        setErrors(tempErrors);

        return !Object.values(tempErrors).some(error => error !== undefined);
    };
    const checkFormValid = (): boolean => {
        return !!formData.name && !!formData.address && !!formData.city && !!formData.postalCode;
    };


    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setLoading(true);
        if (validateForm()) {
            onSave(formData as ServiceLocationDto);
            setLoading(false);
        }
    };

    return (
        <Container>
            <form onSubmit={handleSubmit}>
                <Grid container direction="column" spacing={3}>
                    <Grid item>
                        <TextField
                            fullWidth
                            label="Name"
                            value={formData.name || ''}
                            onChange={handleChange('name')}
                            error={!!errors.name}
                            helperText={errors.name}
                        />
                    </Grid>
                    <Grid item>
                        <TextField
                            fullWidth
                            label="Address"
                            value={formData.address || ''}
                            onChange={handleChange('address')}
                            error={!!errors.address}
                            helperText={errors.address}
                        />
                    </Grid>
                    <Grid item>
                        <TextField
                            fullWidth
                            label="City"
                            value={formData.city || ''}
                            onChange={handleChange('city')}
                            error={!!errors.city}
                            helperText={errors.city}
                        />
                    </Grid>
                    <Grid item>
                        <USStatesDropdown value={formData.state || "OK"} onChange={(val) => {
                            setFormData({
                                ...formData,
                                state: val.target.value
                            });
                        }}/>
                    </Grid>
                    <Grid item>
                        <TextField
                            fullWidth
                            label="Postal Code"
                            value={formData.postalCode || ''}
                            onChange={handleChange('postalCode')}
                            error={!!errors.postalCode}
                            helperText={errors.postalCode}
                        />
                    </Grid>
                    <Grid item>
                        <TextField
                            fullWidth
                            label="Additional Info"
                            value={formData.additionalInfo || ''}
                            onChange={handleChange('additionalInfo')}
                            error={!!errors.additionalInfo}
                            helperText={errors.additionalInfo}
                        />
                    </Grid>
                    <Grid item>
                        <LoadingButton disabled={!isFormValid} loading={loading} fullWidth type="submit" variant="contained" color="primary">
                            Save
                        </LoadingButton>
                    </Grid>
                </Grid>
            </form>
        </Container>
    );
};

export default ServiceLocationForm;
