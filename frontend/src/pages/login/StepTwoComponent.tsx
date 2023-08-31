import React, { useState } from 'react';
import { TextField, Box, Button, Typography, Alert } from '@mui/material';
import AuthService from "../../services/auth/AuthService";
import {useAuth} from "../../contexts/auth_context";

type StepTwoProps = {
    username: string;
    validationNumber: string;
    setValidationNumber: (value: string) => void;
    onLogin: () => void;
    onBack: () => void;
};

const StepTwoComponent: React.FC<StepTwoProps> = ({
                                                      username,
                                                      validationNumber,
                                                      setValidationNumber,
                                                      onLogin,
                                                      onBack
                                                  }) => {
    const [errorMessage, setErrorMessage] = useState<string | null>(null);
    const {login} = useAuth();

    const handleLogin =  async () => {
        const validated = await AuthService.verify({
            phoneNumber: username.replace(/\s+/g, ''),
            code: validationNumber
        });
        if (!validated?.verified) {  // Simulated validation for example purposes
            setErrorMessage("Code Doesn't seem to match");
        } else {
            setErrorMessage(null);
            login(validated.token, validated.authorities);
            onLogin();
        }
    }

    return (
        <div>
            <Typography variant="caption" gutterBottom>
                Check your phone or email for a code and enter it here.
            </Typography>

            {errorMessage && (
                <Alert severity="error" style={{ marginBottom: '16px' }}>
                    {errorMessage}
                </Alert>
            )}

            <TextField
                label="Validation Number"
                variant="outlined"
                type="number"
                fullWidth
                value={validationNumber}
                onChange={(e) => setValidationNumber(e.target.value)}
            />

            <Box mt={2} display="flex" justifyContent="space-between">
                <Button variant="outlined" color="secondary" onClick={onBack}>
                    Back
                </Button>
                <Button variant="contained" color="primary" onClick={handleLogin}>
                    Login
                </Button>
            </Box>
        </div>
    );
};

export default StepTwoComponent;
