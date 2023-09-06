import React, {useEffect, useState} from 'react';
import {
    TextField, Box, Card, CardContent, Typography, Container,
} from '@mui/material';
import PhoneInputComponent from '../../components/PhoneInputComponent';
import { isValidPhoneNumber } from 'react-phone-number-input';
import LoadingButton from '@mui/lab/LoadingButton';
import authService from "../../services/auth/AuthService";
import userService from "../../services/user/UserService";
import {useErrorModal} from "../../contexts/ErrorModalContext";

type ContactInformationProps = {
    phoneNumber: string | undefined,
    email: string | undefined,
    onSave: (email: string, phoneNumber: string) => void;
    userId: string;
};

const ContactInformationForm: React.FC<ContactInformationProps> = ({ phoneNumber, onSave, email, userId }) => {
    const [phone, setPhone] = useState<string>(phoneNumber || '');
    const [innerEmail, setInnerEmail] = useState<string>(email || '');
    const [loading, setLoading] = useState<boolean>(false);
    const isPhoneDisabled = !!phoneNumber;
    const { showError, handleAPIError } = useErrorModal();


    const isValidInput = () => {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;  // Improved regex for email validation

        if (!isPhoneDisabled && !isValidPhoneNumber(phone)) {
            return false; // If phone is not disabled and the phone number is invalid, return false
        }

        if ((!emailRegex.test(innerEmail) || innerEmail === '')) {
            return false; // If email is not disabled and the email is invalid or empty, return false
        }

        return true; // If none of the above conditions were met, return true
    };

    const handleSubmit = async () => {
        setLoading(true);
        if (isValidInput()) {
            const isEmail = isPhoneDisabled;

            const user = await userService.updateContactInformation({username: isEmail ? innerEmail:phone, userId: userId}).catch(handleAPIError);

            user && onSave(user.email, user.phoneNumber);
        }
        setLoading(false);
    };

    return (
                <Container>
                    <Box mb={2}>
                        <PhoneInputComponent
                            value={phone}
                            onChange={setPhone}
                            disabled={isPhoneDisabled}
                        />
                    </Box>

                    <Box mb={2}>
                        <TextField
                            fullWidth
                            label="Email"
                            value={innerEmail}
                            onChange={(e) => setInnerEmail(e.target.value)}
                            error={!/^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/.test(innerEmail) && innerEmail !== ''}
                            helperText={!/^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/.test(innerEmail) && innerEmail !== '' ? "Invalid email format" : ""}
                        />
                    </Box>
                    <Box display="flex" justifyContent="space-between" mt={2}>

                        <LoadingButton
                            loading={loading}
                            variant="contained"
                            color="primary"
                            onClick={handleSubmit}
                            style={{ flex: 1, marginLeft: '8px' }}  // Added this
                        >
                            Save
                        </LoadingButton>
                    </Box>
                </Container>
    );
};

export default ContactInformationForm;
