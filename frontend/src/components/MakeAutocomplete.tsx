import React, { useState } from 'react';
import { TextField, Autocomplete, FormHelperText } from '@mui/material';

interface MakeAutocompleteProps {
    value: string | undefined;
    onChange: (make: string | undefined) => void;
}

const MakeAutocomplete: React.FC<MakeAutocompleteProps> = ({ value, onChange }) => {
    const [error, setError] = useState<string | undefined>(undefined);

    const availableMakes = [
        'Acura',
        'Audi',
        'BMW',
        'Chevrolet',
        'Ford',
        'Honda',
        'Hyundai',
        'Kia',
        'Lexus',
        'Mazda',
        'Mercedes-Benz',
        'Nissan',
        'Subaru',
        'Tesla',
        'Toyota',
        'Volkswagen',
        // Add more car makes as needed
    ];

    const handleChange = (_event: any, newValue: string | null) => {
        if (newValue === null) {
            setError('Please select a valid car make');
            onChange('');
        } else {
            setError(undefined);
            onChange(newValue);
        }
    };

    return (
        <>
            <Autocomplete
                options={availableMakes}
                value={value}
                onChange={handleChange}
                renderInput={(params) => (
                    <TextField
                        {...params}
                        name="make"
                        label="Make"
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

export default MakeAutocomplete;
