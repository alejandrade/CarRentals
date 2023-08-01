import React, { useState } from 'react';
import {
    TextField,
    Button,
    FormControl,
    InputLabel,
    Select,
    MenuItem, SelectChangeEvent, Grid
} from '@mui/material';
import {Gender, UserDemographicsDto} from "../../services/user/UserService.types";
import Typography from "@mui/material/Typography";
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
}

const UserDemographicsForm: React.FC<param> = ({dto}) => {
    const [formData, setFormData] = useState<Partial<UserDemographicsDto>>(dto || {});
    const [errors, setErrors] = useState<Partial<FormErrors>>({});

    const handleChange = (name: keyof UserDemographicsDto) => (
        event: React.ChangeEvent<HTMLInputElement | { name?: string | undefined; value: unknown }>
    ) => {
        setFormData({
            ...formData,
            [name]: event.target.value
        });
    };

    const handleChangeGender = () => (
        event: SelectChangeEvent<Gender>
    ) => {
        setFormData({
            gender: event.target.value as Gender
        });
    };

    const validateForm = (): boolean => {
        let valid = true;
        let tempErrors: Partial<FormErrors> = {};

        if (!formData.firstName) {
            tempErrors.firstName = "First name is required";
            valid = false;
        }

        if (!formData.lastName) {
            tempErrors.lastName = "Last name is required";
            valid = false;
        }

        if (!formData.gender) {
            tempErrors.gender = "Gender is required";
            valid = false;
        }

        if (!formData.dateOfBirth) {
            tempErrors.dateOfBirth = "Date of Birth is required";
            valid = false;
        }

        setErrors(tempErrors);
        return valid;
    };

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        if (validateForm()) {
            // Process formData
            console.log('Valid Form Data:', formData);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <Typography variant="h3">User</Typography>

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
                        value={formData.dateOfBirth?.toISOString().split('T')[0] || ''}
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
                            onChange={handleChangeGender()}
                        >
                            <MenuItem value="Male">Male</MenuItem>
                            <MenuItem value="Female">Female</MenuItem>
                            <MenuItem value="Other">Other</MenuItem>
                            <MenuItem value="Prefer_Not_To_Say">Prefer Not To Say</MenuItem>
                        </Select>
                    </FormControl>
                </Grid>

                <Grid item>
                    <TextField
                        fullWidth
                        label="Street Address"
                        value={formData.address || ''}
                        onChange={handleChange('address')}
                    />
                </Grid>

                <Grid item>
                    <TextField
                        fullWidth
                        label="City"
                        value={formData.city || ''}
                        onChange={handleChange('city')}
                    />
                </Grid>

                <Grid item>
                    <TextField
                        fullWidth
                        label="State"
                        value={formData.state || ''}
                        onChange={handleChange('state')}
                    />
                </Grid>

                <Grid item>
                    <TextField
                        fullWidth
                        label="Postal Code"
                        value={formData.postalCode || ''}
                        onChange={handleChange('postalCode')}
                    />
                </Grid>

                <Grid item>
                    <TextField
                        fullWidth
                        label="Country"
                        value={formData.country || ''}
                        onChange={handleChange('country')}
                    />
                </Grid>

                <Grid item>
                    <TextField
                        fullWidth
                        label="Additional Info"
                        multiline
                        rows={4}
                        value={formData.additionalInfo || ''}
                        onChange={handleChange('additionalInfo')}
                    />
                </Grid>

                <Grid item>
                    <Button type="submit" variant="contained" color="primary">
                        Submit
                    </Button>
                </Grid>
            </Grid>
        </form>
    );

};

export default UserDemographicsForm;
