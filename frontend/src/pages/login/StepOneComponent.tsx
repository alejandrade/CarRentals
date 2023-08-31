import React, { useState } from 'react';
import { TextField, Box, Checkbox, FormControlLabel, Button, RadioGroup, Radio, Container, Typography } from '@mui/material';
import PhoneInputComponent from "../../components/PhoneInputComponent";
import EmailInputComponent from "../../components/EmailInputComponent";
import { isValidPhoneNumber } from "react-phone-number-input";
import LoadingButton from '@mui/lab/LoadingButton';
import AuthService from "../../services/auth/AuthService";

type StepOneProps = {
    username: string;
    remember: boolean;
    setUsername: (value: string) => void;
    setRemember: (value: boolean) => void;
    onNext: () => void;
};

const StepOneComponent: React.FC<StepOneProps> = ({ username, remember, setUsername, setRemember, onNext }) => {
    const [inputType, setInputType] = useState<'phone' | 'email'>('phone');
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(false);
    const handleInputTypeChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setInputType(event.target.value as 'phone' | 'email');
        setUsername(''); // Clear the username when switching input type
    };

    const isValidInput = () => {
        if (error) return false;
        if (inputType === 'phone') {
            return isValidPhoneNumber(username);
        } else {
            const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
            return emailRegex.test(username);
        }
    };

    const handleNext = async () => {
        if (isValidInput()) {
            setLoading(true);
            const auth =  await AuthService.startVerification({
                phoneNumber: username.replace(/\s+/g, ''),
                channel: "SMS"
            });
            if (auth) {
                onNext();
            } else {
                console.log("error")
            }
            setLoading(false);

        }
    };

    return (
        <Container maxWidth="sm" style={{ height: 300 }}>
            <Typography variant="caption" gutterBottom>
                Login with phone number.
            </Typography>

            {/*<Box display="flex" justifyContent="center" mb={2}>*/}
            {/*    <RadioGroup row value={inputType} onChange={handleInputTypeChange}>*/}
            {/*        <FormControlLabel value="phone" control={<Radio />} label="Phone Number" />*/}
            {/*        <FormControlLabel value="email" control={<Radio />} label="Email" />*/}
            {/*    </RadioGroup>*/}
            {/*</Box>*/}

            {inputType === 'phone' ? (
                <PhoneInputComponent
                    value={username}
                    onChange={setUsername}
                />
            ) : (
                <EmailInputComponent
                    value={username}
                    onChange={setUsername}
                />
            )}

            {/*<Box mt={2}>*/}
            {/*    <FormControlLabel*/}
            {/*        control={*/}
            {/*            <Checkbox*/}
            {/*                checked={remember}*/}
            {/*                onChange={(e) => setRemember(e.target.checked)}*/}
            {/*                name="remember"*/}
            {/*                color="primary"*/}
            {/*            />*/}
            {/*        }*/}
            {/*        label="Remember me"*/}
            {/*    />*/}
            {/*</Box>*/}

            <Box mt={2}>
                <LoadingButton
                    loading={loading}
                    variant="contained"
                    fullWidth
                    color="primary"
                    onClick={handleNext}
                    disabled={!isValidInput()}  // Disabling the button based on the validity
                >
                    Next
                </LoadingButton>
            </Box>
        </Container>
    );
};

export default StepOneComponent;
