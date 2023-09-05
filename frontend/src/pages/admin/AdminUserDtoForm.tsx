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
import userService from "../../services/user/UserService";
import PhoneInputComponent from "../../components/PhoneInputComponent";
import {useErrorModal} from "../../contexts/ErrorModalContext";
import Typography from "@mui/material/Typography";
import serviceLocationService from "../../services/service_location/ServiceLocationService";

type FormErrors = {
    email?: string;
    phoneNumber?: string;
    enabled?: string;
    serviceLocations?: string;
    authorities?: string;
};

type UserDtoProps = {
    dto: Partial<UserDto>;
    onSave: (data: UserDto) => void;
}

const UserDtoForm: React.FC<UserDtoProps> = ({ dto, onSave }) => {
    const [formData, setFormData] = useState<Partial<UserDto>>(dto || {});
    const [errors, setErrors] = useState<FormErrors>({});
    const [loading, setLoading] = useState<boolean>(false);
    const { showError, handleAPIError } = useErrorModal();
    const [formLoading, setFormLoading] = useState<boolean>(false);

    useEffect(()=> {
        init();
    }, [])

    async function init() {
        setFormLoading(true);
        setLoading(true);
        if (!dto?.id) {
            throw new Error("illegal state");
        }
        const user = await userService.getUser(dto?.id).catch(handleAPIError);
        if (user) {
            const location = await serviceLocationService.getServiceLocationByUserId(user.id).catch(handleAPIError)

            console.log(user)
            setFormData({
                id: user.id,
                email: user.email,
                phoneNumber: user.phoneNumber,
                serviceLocation: location || undefined,
                serviceLocationId: location?.id || undefined,
                authorities: user.authorities,
                enabled: user.enabled
            })
        }
        setLoading(false);
        setFormLoading(false);
    }

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

    return (<>
        {!formLoading &&
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
                    <PhoneInputComponent value={formData.phoneNumber || ''}
                                         onChange={(val) => {
                                             setFormData({
                                                 ...formData,
                                                 phoneNumber: val
                                             });
                                         }}
                    />

                </Grid>

                <Grid item>
                    <ServiceLocationTypeahead
                        value={formData.serviceLocation}
                        onChange={(selectedServiceLocation) => {
                            setFormData({
                                ...formData,
                                serviceLocation: selectedServiceLocation,
                                serviceLocationId: selectedServiceLocation?.id
                            });
                        }}
                        error={!!errors.serviceLocations}
                        helperText={errors.serviceLocations}
                    />
                </Grid>
                <Grid item>
                    <UserRoleDropdown value={formData.authorities || []}
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
}</>
    );
};

export default UserDtoForm;
