import React from 'react';
import { Checkbox, FormControlLabel } from '@mui/material';

interface CheckboxWithLabelProps {
    label: string;
    value: boolean;
    onChange: (newValue: boolean) => void;
}

const CheckboxWithLabel: React.FC<CheckboxWithLabelProps> = ({ label, value, onChange }) => {
    const handleCheckboxChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        onChange(event.target.checked);
    };

    return (
        <FormControlLabel
            control={<Checkbox checked={value} onChange={handleCheckboxChange} />}
            label={label}
        />
    );
};

export default CheckboxWithLabel;
