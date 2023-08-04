import React, { useState } from 'react';
import { TextField, Autocomplete, FormHelperText } from '@mui/material';

interface YearTextFieldProps {
    value: number | undefined;
    onChange: (year: number | undefined) => void;
}

const YearTextField: React.FC<YearTextFieldProps> = ({value, onChange }) => {
    const [error, setError] = useState<string | undefined>(undefined);
    const currentYear = new Date().getFullYear();
    const validYears: number[] = Array.from({ length: currentYear - 1900 + 1 }, (_, index) => currentYear - index);

    const handleChange = (_event: any, newValue: number | null) => {
        if (newValue === null) {
            setError('Please select a valid car year');
            onChange(undefined);
        } else {
            setError(undefined);
            onChange(newValue);
        }
    };

    return (
        <>
            <Autocomplete
                options={validYears}
                value={value === undefined ? null : value}
                onChange={handleChange}
                getOptionLabel={(option) => option.toString()}
                renderInput={(params) => (
                    <TextField
                        {...params}
                        name="year"
                        label="Year"
                        fullWidth
                        type="number"
                        error={Boolean(error)}
                    />
                )}
            />
            {error && <FormHelperText error>{error}</FormHelperText>}
        </>
    );
};

export default YearTextField;
