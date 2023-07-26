import React, { useState, useEffect } from 'react';
import { MuiTelInput } from 'mui-tel-input';
import { isValidPhoneNumber } from "react-phone-number-input";
import { useTheme } from '@mui/material/styles';

interface Props {
    value: string;
    onChange: (value: string) => void;
    isValid?: boolean;  // The parent can use this prop to check if the phone number is valid.
}

const PhoneInputComponent: React.FC<Props> = ({ value, onChange, isValid }) => {
    const [error, setError] = useState<string | null>(null);
    const theme = useTheme();

    useEffect(() => {
        if (isValid !== undefined) {
            setError(isValid ? null : 'Invalid phone number format.');
        }
    }, [isValid]);

    const handlePhoneChange = (phone: string) => {
        if (phone && !isValidPhoneNumber(phone)) {
            setError('Invalid phone number format.');
        } else {
            setError(null);
        }
        onChange(phone);
    };

    return (
        <MuiTelInput
            label="Phone Number"
            helperText={error || ''}
            error={Boolean(error)}  // this will make the input field have an error style (typically red in MUI)
            style={error ? { color: theme.palette.error.main } : {}}  // make text color red on error
            defaultCountry={"US"}
            value={value}
            fullWidth
            onChange={handlePhoneChange}
        />
    );
};

export default PhoneInputComponent;
