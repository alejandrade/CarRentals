import React, {useEffect, useState} from 'react';
import { keys } from 'ts-transformer-keys';

import {
    Button,
    Card,
    CardContent,
    CardHeader, Container,
    FormControl, FormHelperText,
    Grid,
    InputLabel,
    MenuItem,
    Select,
    SelectChangeEvent,
    TextField
} from '@mui/material';
import { Gender, UserDemographicsDto } from "../../services/user/UserService.types";
import USStatesDropdown from "../../components/USStatesDropdown";
import userService from "../../services/user/UserService";
import LoadingButton from "@mui/lab/LoadingButton";

type FormErrors = {
    firstName?: string;
    middleInitial?: string;
    lastName?: string;
    dateOfBirth?: string;
    gender?: string;
    address?: string;
    city?: string;
    state?: string;
    postalCode?: string;
    country?: string;
    additionalInfo?: string;
};

type param = {
    dto: Partial<UserDemographicsDto> | undefined;
    userId: string;
    onSave: (data: UserDemographicsDto) => void;
}

const UserDemographicsForm: React.FC<param> = ({ dto, userId, onSave }) => {
    const [formData, setFormData] = useState<Partial<UserDemographicsDto>>(dto || {});
    const [errors, setErrors] = useState<Partial<FormErrors>>({});
    const [lastChanged, setLastChanged] = useState<keyof UserDemographicsDto>();
    const [loading, setLoading] = useState<boolean>(false);


    useEffect(() => {
        if (lastChanged) {
            validateForm(lastChanged);
        }
    }, [formData])

    const handleChange = (name: keyof UserDemographicsDto) => (
        event: React.ChangeEvent<HTMLInputElement | { name?: string | undefined; value: unknown }>
    ) => {
        const value = event.target.value;
        if (name === 'dateOfBirth') {
            // Parse the input value to a Date object
            const dateOfBirth = new Date(value as string);
            setFormData({
                ...formData,
                [name]: dateOfBirth,
            });
        } else {
            setFormData({
                ...formData,
                [name]: value,
            });
        }
        setLastChanged(name);
    };


    const handleSelect = (name: keyof UserDemographicsDto) => (
        event: SelectChangeEvent
    ) => {
        setFormData({
            ...formData,
            [name]: event.target.value as Gender
        });
        validateForm(name);  // Real-time validation for this field
    };

    const validateForm = (field?: keyof UserDemographicsDto): boolean => {
        let valid = true;
        let tempErrors: Partial<FormErrors> = {};

        if (!field || field === 'firstName') {
            tempErrors.firstName = !formData.firstName ? "First name is required" : undefined;
        }

        if (!field || field === 'lastName') {
            tempErrors.lastName = !formData.lastName ? "Last name is required" : undefined;
        }

        if (!field || field === 'gender') {
            tempErrors.gender = !formData.gender ? "Gender is required" : undefined;
        }

        if (!field || field === 'dateOfBirth') {
            tempErrors.dateOfBirth = !formData.dateOfBirth ? "Date of Birth is required" : undefined;
        }

        if (!field || field === 'address') {
            tempErrors.address = !formData.address ? "Address is required" : undefined;
        }

        if (!field || field === 'city') {
            tempErrors.city = !formData.city ? "City is required" : undefined;
        }

        if (!field || field === 'state') {
            tempErrors.state = !formData.state ? "State is required" : undefined;
        }

        if (!field || field === 'postalCode') {
            if (!formData.postalCode) {
                tempErrors.postalCode = "Postal Code is required";
            } else if (!/^\d{5}$/.test(formData.postalCode)) {
                tempErrors.postalCode = "Invalid US ZIP code format";
            } else {
                tempErrors.postalCode = undefined;  // Ensure error is cleared if format is correct
            }
        }

        if (!field || field === 'additionalInfo') {
            if (formData.additionalInfo && !/^[a-zA-Z0-9\s.,!?]*$/.test(formData.additionalInfo)) {
                tempErrors.additionalInfo = "Notes can't have special characters";
            }
        }

        if (!field || field === 'middleInitial') {
            if (formData.middleInitial && formData.middleInitial.length > 1) {
                tempErrors.middleInitial = "Middle Initial can only be 1 character";
            }
        }

        setErrors({
            ...errors,
            ...tempErrors
        });

        valid = !Object.values(tempErrors).some(val => val !== undefined);
        return valid;
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setLoading(true);
        if (validateForm()) {
            formData.country = "US";
            formData.userId = userId;
            await userService.createUserDemographics(formData as UserDemographicsDto);
            onSave(formData as UserDemographicsDto);
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
                                label="First Name"
                                value={formData.firstName || ''}
                                onChange={handleChange('firstName')}
                                error={!!errors.firstName}
                                helperText={errors.firstName}
                            />
                        </Grid>

                        <Grid item>
                            <TextField
                                fullWidth
                                label="Middle Initial"
                                value={formData.middleInitial || ''}
                                onChange={handleChange('middleInitial')}
                                inputProps={{ maxLength: 1 }}
                            />
                        </Grid>

                        <Grid item>
                            <TextField
                                fullWidth
                                label="Last Name"
                                value={formData.lastName || ''}
                                onChange={handleChange('lastName')}
                                error={!!errors.lastName}
                                helperText={errors.lastName}
                            />
                        </Grid>

                        <Grid item>
                            <TextField
                                fullWidth
                                label="Date of Birth"
                                type="date"
                                value={formData.dateOfBirth ? formData.dateOfBirth.toISOString().split('T')[0] : ''}
                                onChange={handleChange('dateOfBirth')}
                                error={!!errors.dateOfBirth}
                                helperText={errors.dateOfBirth}
                                InputLabelProps={{
                                    shrink: true,
                                }}
                            />
                        </Grid>

                        <Grid item>
                            <FormControl fullWidth error={!!errors.gender}>
                                <InputLabel>Gender</InputLabel>
                                <Select
                                    value={formData.gender || ''}
                                    onChange={handleSelect("gender")}
                                >
                                    <MenuItem value="Male">Male</MenuItem>
                                    <MenuItem value="Female">Female</MenuItem>
                                    <MenuItem value="Other">Other</MenuItem>
                                    <MenuItem value="Prefer_Not_To_Say">Prefer Not To Say</MenuItem>
                                </Select>
                                {errors.gender && <FormHelperText>{errors.gender}</FormHelperText>}

                            </FormControl>
                        </Grid>

                        <Grid item>
                            <TextField
                                fullWidth
                                label="Street Address"
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
                            <USStatesDropdown
                                value={formData.state || ''}
                                onChange={handleSelect('state')}
                                error={!!errors.state}
                                helperText={errors.state}
                            />

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
                                label="Notes"
                                multiline
                                rows={4}
                                value={formData.additionalInfo || ''}
                                onChange={handleChange('additionalInfo')}
                                error={!!errors.additionalInfo}
                                helperText={errors.additionalInfo}
                            />
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

export default UserDemographicsForm;
