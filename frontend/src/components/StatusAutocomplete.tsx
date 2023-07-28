import React, { useState } from 'react';
import { TextField, Autocomplete, FormHelperText } from '@mui/material';

interface StatusAutocompleteProps {
    value: string | undefined;
    onChange: (status: string | undefined) => void;
}

const StatusAutocomplete: React.FC<StatusAutocompleteProps> = ({ value, onChange }) => {
    const [error, setError] = useState<string | undefined>(undefined);

    const statusOptions = ['ACTIVE', 'INACTIVE', 'MAINTENANCE'];

    const handleChange = (_event: any, newValue: string | null) => {
        if (newValue === null) {
            setError('Please select a valid status');
            onChange('');
        } else {
            setError(undefined);
            onChange(newValue);
        }
    };

    return (
        <>
            <Autocomplete
                options={statusOptions}
                value={value}
                onChange={handleChange}
                renderInput={(params) => (
                    <TextField
                        {...params}
                        name="status"
                        label="Status"
                        fullWidth
                        margin="normal"
                        error={Boolean(error)}
                    />
                )}
            />
            {error && <FormHelperText error>{error}</FormHelperText>}
        </>
    );
};

export default StatusAutocomplete;
