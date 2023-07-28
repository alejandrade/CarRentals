import React, { useState } from 'react';
import { TextField, Button, Paper, Typography } from '@mui/material';
import { useNavigate } from "react-router-dom";

const CarRentalStart: React.FC = () => {
    const [carCode, setCarCode] = useState<string>('');
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate();

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const value = e.target.value;
        setCarCode(value);

        if (value.length !== 8 || !/^[A-Za-z0-9]{8}$/.test(value)) {
            setError('Input should be exactly 8 characters and alphanumeric');
        } else {
            setError(null);
        }
    };

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        if (!error) {
            console.log(carCode); // Or handle the validated data as required.
            navigate(`/dash/clerk/rent/${carCode.toLowerCase()}`);
        }
    };

    return (
        <Paper style={{ padding: '20px', maxWidth: '400px', margin: '20px auto' }}>
            <Typography variant="h5" style={{ marginBottom: '20px' }}>
                Car Rental Start
            </Typography>
            <form onSubmit={handleSubmit}>
                <TextField
                    fullWidth
                    label="Car Code"
                    variant="outlined"
                    value={carCode}
                    onChange={handleInputChange}
                    error={Boolean(error)}
                    helperText={error}
                    style={{ marginBottom: '20px' }}
                />
                <Button variant="contained" color="primary" type="submit" disabled={Boolean(error)}>
                    Start Rental
                </Button>
            </form>
        </Paper>
    );
};

export default CarRentalStart;
