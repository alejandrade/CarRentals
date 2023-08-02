import React, { useState, useEffect } from 'react';
import {
    Button,
    Container,
    FormControl,
    InputLabel,
    MenuItem,
    Select,
    SelectChangeEvent,
    TextField,
    FormHelperText,
    Grid, Checkbox, FormControlLabel
} from '@mui/material';
import LoadingButton from '@mui/lab/LoadingButton';
import {UserDto} from "../../services/user/UserService.types";
import UserRoleDropdown from "../../components/RoleDropdown";
import ServiceLocationTypeahead from "../../components/ServiceLocationTypeahead";

type FormErrors = {
    email?: string;
    phoneNumber?: string;
    enabled?: string;
    serviceLocations?: string;
    authorities?: string;
};

type UserDtoProps = {
    dto?: Partial<UserDto>;
    onSave: (data: UserDto) => void;
}

const UserDtoForm: React.FC<UserDtoProps> = ({ dto, onSave }) => {
    const [formData, setFormData] = useState<Partial<UserDto>>(dto || {});
    const [errors, setErrors] = useState<FormErrors>({});
    const [loading, setLoading] = useState<boolean>(false);

    useEffect(() => {
        validateForm();
    }, [formData]);

    const handleChange = (name: keyof UserDto) => (event: React.ChangeEvent<HTMLInputElement>) => {
        setFormData({
            ...formData,
            [name]: event.target.value
        });
    };

    const handleSelectChange = (name: keyof UserDto) => (event: SelectChangeEvent<string[]>) => {
        setFormData({
            ...formData,
            [name]: event.target.value as string[]
        });
    };

    const handleCheckboxChange = (name: keyof UserDto) => (event: React.ChangeEvent<HTMLInputElement>) => {
        setFormData({
            ...formData,
            [name]: event.target.checked
        });
    };

    const validateForm = (): boolean => {
        let tempErrors: FormErrors = {};

        if (!formData.email) {
            tempErrors.email = "Email is required.";
        }

        if (!formData.phoneNumber) {
            tempErrors.phoneNumber = "Phone number is required.";
        }

        setErrors(tempErrors);

        return !Object.values(tempErrors).some(error => error !== undefined);
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setLoading(true);

        if (validateForm()) {
            onSave(formData as UserDto);
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
                            label="Email"
                            value={formData.email || ''}
                            onChange={handleChange('email')}
                            error={!!errors.email}
                            helperText={errors.email}
                        />
                    </Grid>
                    <Grid item>
                        <TextField
                            fullWidth
                            label="Phone Number"
                            value={formData.phoneNumber || ''}
                            onChange={handleChange('phoneNumber')}
                            error={!!errors.phoneNumber}
                            helperText={errors.phoneNumber}
                        />
                    </Grid>

                    <Grid item>
                        <ServiceLocationTypeahead
                            state={"OK"}
                        value={formData.serviceLocations || []}
                        onChange={(selectedServiceLocations) => {
                            setFormData({
                                ...formData,
                                serviceLocations: selectedServiceLocations
                            });
                        }}
                        error={!!errors.serviceLocations}
                        helperText={errors.serviceLocations}
                        />
                    </Grid>
                    <Grid item>
                        <UserRoleDropdown value={formData.authorities|| []}
                                          onChange={handleSelectChange('authorities')}
                                          error={!!errors.authorities}
                                          helperText={errors.authorities}
                        />
                    </Grid>
                    <Grid item>
                        <FormControlLabel
                            control={
                                <Checkbox
                                    checked={formData.enabled || false}
                                    onChange={handleCheckboxChange('enabled')}
                                    color="primary"
                                />
                            }
                            label="Enabled"
                        />
                        {errors.enabled && <FormHelperText error>{errors.enabled}</FormHelperText>}
                    </Grid>
                    <Grid item>
                        <LoadingButton loading={loading} fullWidth type="submit" variant="contained" color="primary">
                            Save
                        </LoadingButton>
                    </Grid>
                </Grid>
            </form>
        </Container>
    );
};

export default UserDtoForm;
