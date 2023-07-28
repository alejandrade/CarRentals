import React, { ChangeEvent } from 'react';
import { TextField } from '@mui/material';

interface VinTextFieldProps {
    value: string | undefined;
    onChange: (val: string) => void;
}

const VinTextField: React.FC<VinTextFieldProps> = ({ value, onChange }) => {
    // Regular expression for VIN validation
    const vinRegex = /^[A-HJ-NPR-Z\d]{17}$/i;

    // Function to handle changes and perform validation
    const handleChange = (event: ChangeEvent<HTMLInputElement>) => {
        const { value } = event.target;
        // Check if the new value matches the VIN regex
            onChange(value);
    };

    return (
        <TextField
            name="vin"
            label="VIN"
            value={value}
            onChange={handleChange}
            fullWidth
            margin="normal"
            error={value ? !vinRegex.test(value) : false}
            helperText={value ? (!vinRegex.test(value) ? 'Invalid VIN' : '') : ""}
        />
    );
};

export default VinTextField;
