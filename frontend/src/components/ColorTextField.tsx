import React from 'react';
import { Autocomplete, TextField } from '@mui/material';

interface ColorTextFieldProps {
    value: string | undefined;
    onChange: (value: string) => void;
}

const ColorTextField: React.FC<ColorTextFieldProps> = ({ value, onChange }) => {
    // Array of common car color names for the typeahead dropdown
    const allowedColors = [
        'White',
        'Black',
        'Gray',
        'Silver',
        'Gold',
        'Red',
        'Blue',
        'Green',
        'Yellow',
        'Orange',
        'Purple',
        'Brown',
    ];

    return (
        <Autocomplete
            options={allowedColors}
            value={value}
            onChange={(_, newValue) => {
                if (typeof newValue === 'string') {
                    onChange(newValue);
                }
            }}
            renderInput={(params) => (
                <TextField {...params} name="color" label="Color" fullWidth margin="normal" />
            )}
        />
    );
};

export default ColorTextField;
