import React from 'react';
import { TextField, Box, Button } from '@mui/material';

type StepTwoProps = {
    validationNumber: string;
    setValidationNumber: (value: string) => void;
    onLogin: () => void;
    onBack: () => void;
};

const StepTwoComponent: React.FC<StepTwoProps> = ({
                                                      validationNumber,
                                                      setValidationNumber,
                                                      onLogin,
                                                      onBack
                                                  }) => {
    return (
        <div>
            <TextField
                label="Validation Number"
                variant="outlined"
                type="number"
                value={validationNumber}
                onChange={(e) => setValidationNumber(e.target.value)}
            />
            <Box mt={2} display="flex" justifyContent="space-between">
                <Button variant="outlined" color="secondary" onClick={onBack}>
                    Back
                </Button>
                <Button variant="contained" color="primary" onClick={onLogin}>
                    Login
                </Button>
            </Box>
        </div>
    );
};

export default StepTwoComponent;
