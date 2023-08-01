import React, {useEffect, useState} from 'react';
import {
    TextField, Box, Card, CardContent, Typography, Container,
} from '@mui/material';
import PhoneInputComponent from '../../components/PhoneInputComponent';
import { isValidPhoneNumber } from 'react-phone-number-input';
import LoadingButton from '@mui/lab/LoadingButton';
import authService from "../../services/auth/AuthService";
import userService from "../../services/user/UserService";

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
    const [verifyDisabled, setVerifyDisabled] = useState(false);
    const [verificationCode, setVerificationCode] = useState<string>('');
    const [isCodeValid, setIsCodeValid] = useState<boolean>(false);

    const [verificationLoading, setVerificationLoading] = useState<boolean>(false);
    const [verificationCompleted, setVerificationCompleted] = useState<boolean>(false);

    const isPhoneDisabled = !!phoneNumber;
    const isEmailDisabled = !!email;



    useEffect(()=> {
        if (!isEmailDisabled) {
            setVerifyDisabled(!isValidInput());
        }
    }, [innerEmail]);
    useEffect(()=> {
        if (!isPhoneDisabled) {
            setVerifyDisabled(!isValidInput());
        }
    }, [phone]);

    const isValidInput = () => {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;  // Improved regex for email validation

        if (!isPhoneDisabled && !isValidPhoneNumber(phone)) {
            return false; // If phone is not disabled and the phone number is invalid, return false
        }

        if (!isEmailDisabled && (!emailRegex.test(innerEmail) || innerEmail === '')) {
            return false; // If email is not disabled and the email is invalid or empty, return false
        }

        return true; // If none of the above conditions were met, return true
    };

    const handleVerify = async () => {
        // Perform async verification here, for example, an API call
        try {
            setVerificationLoading(true);
            // Your verification async logic goes here...
            const isEmail = isPhoneDisabled;
            const username = isEmail ? {email: innerEmail} : {phoneNumber: phone};
            const verifyStarted = await authService.startVerification({channel: isEmail ? "EMAIL" : "SMS", ...username})
            setVerificationCompleted(true); // Display the verification code input

        } catch (error) {
            // Handle verification error
        } finally {
            setVerificationLoading(false);
        }
    };

    const handleVerificationCodeChange = async (code: string) => {
        setVerificationCode(code);
        setIsCodeValid(true)
        // Here, add the logic to validate the code asynchronously if needed.
        // After validating, set `isCodeValid` to either true or false.
    };

    const handleSubmit = async () => {
        if (isValidInput() && isCodeValid) {
            const isEmail = isPhoneDisabled;

            const user = await userService.updateContactInformation({username: isEmail ? innerEmail:phone, code: verificationCode})
            onSave(user.email, user.phoneNumber);
        }
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
                            disabled={isEmailDisabled}
                        />
                    </Box>
                    {verificationCompleted && (
                        <Box mb={2}>
                            <TextField
                                fullWidth
                                label="Verification Code"
                                value={verificationCode}
                                onChange={(e) => handleVerificationCodeChange(e.target.value)}
                                error={!isCodeValid && verificationCode !== ''}
                                helperText={!isCodeValid && verificationCode !== '' ? "Invalid code" : ""}
                            />
                        </Box>
                    )}
                    <Box display="flex" justifyContent="space-between" mt={2}>
                        <LoadingButton
                            loading={verificationLoading}
                            variant="contained"
                            color="primary"
                            onClick={handleVerify || verifyDisabled}
                            disabled={verifyDisabled}
                            style={{ flex: 1, marginRight: '8px' }}  // Added this
                        >
                            Verify
                        </LoadingButton>

                        <LoadingButton
                            loading={loading}
                            variant="contained"
                            color="primary"
                            onClick={handleSubmit}
                            disabled={!verificationCompleted || !isCodeValid}
                            style={{ flex: 1, marginLeft: '8px' }}  // Added this
                        >
                            Save
                        </LoadingButton>
                    </Box>
                </Container>
    );
};

export default ContactInformationForm;
