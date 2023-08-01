import React, { useState } from 'react';
import {
    TextField, Box, Card, CardContent, Typography, Container
} from '@mui/material';
import PhoneInputComponent from "../../components/PhoneInputComponent";
import { isValidPhoneNumber } from "react-phone-number-input";
import LoadingButton from '@mui/lab/LoadingButton';

type ContactInformationProps = {
    onSubmit: (phone: string, email: string) => void;
    phoneNumber: string | undefined,
    email: string| undefined
};

const ContactInformationForm: React.FC<ContactInformationProps> = ({ onSubmit, phoneNumber, email }) => {
    const [phone, setPhone] = useState<string>(phoneNumber || '');
    const [innerEmail, setInnerEmail] = useState<string>(email || '');
    const [loading, setLoading] = useState<boolean>(false);

    const isValidInput = () => {
        const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
        return isValidPhoneNumber(phone) && emailRegex.test(innerEmail);
    };

    const handleSubmit = async () => {
        if (isValidInput()) {
            onSubmit(phone, innerEmail);
        }
    };

    return (
        <Card>
            <CardContent>
                <Container>
                    <Typography variant="h6" gutterBottom>
                        Contact Information
                    </Typography>

                    <Box mb={2}>
                        <PhoneInputComponent
                            value={phone}
                            onChange={setPhone}
                        />
                    </Box>

                    <Box mb={2}>
                        <TextField
                            fullWidth
                            label="Email"
                            value={innerEmail}
                            onChange={(e) => setInnerEmail(e.target.value)}
                            error={!/^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/.test(innerEmail) && innerEmail !== ''}
                            helperText={!/^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/.test(innerEmail) && innerEmail !== '' ? "Invalid email format" : ""}
                        />
                    </Box>

                    <Box mt={2}>
                        <LoadingButton
                            loading={loading}
                            variant="contained"
                            fullWidth
                            color="primary"
                            onClick={handleSubmit}
                            disabled={!isValidInput()}  // Disabling the button based on the validity
                        >
                            Submit
                        </LoadingButton>
                    </Box>
                </Container>
            </CardContent>
        </Card>
    );
};

export default ContactInformationForm;
