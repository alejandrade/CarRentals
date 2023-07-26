// LoginForm.tsx
import React, { useState } from 'react';
import { Box } from '@mui/material';
import StepOneComponent from './StepOneComponent';
import StepTwoComponent from './StepTwoComponent';

const LoginForm: React.FC = () => {
    const [step, setStep] = useState<number>(0);
    const [username, setUsername] = useState<string>('');
    const [validationNumber, setValidationNumber] = useState<string>('');
    const [remember, setRemember] = useState<boolean>(false);

    const handleNextClick = () => {
        setStep(1);
    };

    const handleLoginClick = () => {
        console.log('Logging in with:', username, validationNumber);
    };

    return (
        <Box
            display="flex"
            flexDirection="column"
            alignItems="center"
            justifyContent="center"
        >
            {step === 0 ? (
                <StepOneComponent
                    username={username}
                    remember={remember}
                    setUsername={setUsername}
                    setRemember={setRemember}
                    onNext={handleNextClick}
                />
            ) : (
                <StepTwoComponent
                    validationNumber={validationNumber}
                    setValidationNumber={setValidationNumber}
                    onLogin={handleLoginClick}
                    onBack={() => setStep(0)}
                />

            )}
        </Box>
    );
}

export default LoginForm;
