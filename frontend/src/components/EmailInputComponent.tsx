import React, { useState, useEffect } from 'react';
import { TextField } from '@mui/material';

interface Props {
    value: string;
    onChange: (value: string) => void;
    isValid?: boolean; // The parent can use this prop to check if the email is valid.
}

const EmailInputComponent: React.FC<Props> = ({ value, onChange, isValid }) => {
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        if (isValid !== undefined) {
            setError(isValid ? null : 'Invalid email format.');
        }
    }, [isValid]);

    const isValidEmail = (email: string): boolean => {
        // Simple regex for email validation. More complex cases may require more detailed regex.
        const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
        return emailRegex.test(email);
    };

    const handleEmailChange = (email: string) => {
        if (email && !isValidEmail(email)) {
            setError('Invalid email format.');
        } else {
            setError(null);
        }
        onChange(email);
    };

    return (
        <TextField
            label="Email"
            variant="outlined"
            helperText={error || ''}
            error={Boolean(error)}
            value={value}
            onChange={(e) => handleEmailChange(e.target.value)}
            fullWidth
        />
    );
};

export default EmailInputComponent;
