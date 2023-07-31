import React, { useState } from 'react';
import { Button, Container, CssBaseline, Typography, IconButton, Grid, TextField, InputAdornment } from '@mui/material';
import { PhotoCamera, CheckCircle } from '@mui/icons-material';
import { useParams } from 'react-router-dom';
import { compressImage } from "../../util/ImageFunctions";
import PhoneInputComponent from "../../components/PhoneInputComponent";
type ValidatedFieldsType = {
    renterPhoneNumber: boolean;
    initialOdometerReading: boolean;
    front: boolean;
    left: boolean;
    right: boolean;
    back: boolean;
    odometer: boolean;
};

const CarRentalForm: React.FC = () => {
    const { shortId } = useParams();

    const [renterPhoneNumber, setRenterPhoneNumber] = useState('');
    const [initialOdometerReading, setInitialOdometerReading] = useState('');

    const [pictures, setPictures] = useState({
        front: null,
        left: null,
        right: null,
        back: null,
        odometer: null
    });

    const [validatedFields, setValidatedFields] = useState<ValidatedFieldsType>({
        renterPhoneNumber: false,
        initialOdometerReading: false,
        front: false,
        left: false,
        right: false,
        back: false,
        odometer: false
    });


    const isAllFieldsValidated = Object.values(validatedFields).every(Boolean);

    const handleFieldValidation = (name: string, value: any) => {
        if (value) {
            setValidatedFields(prev => ({ ...prev, [name]: true }));
        } else {
            setValidatedFields(prev => ({ ...prev, [name]: false }));
        }
    };

    const handlePictureChange = async (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, files } = e.target;
        if (files && files.length > 0) {
            try {
                const compressed = await compressImage(files[0], { maxWidth: 1024, quality: 0.8 });
                setPictures(prev => ({ ...prev, [name]: compressed }));
                handleFieldValidation(name, compressed);
            } catch (error) {
                console.error("Error while compressing image", error);
            }
        }
    };

    // @ts-ignore
    return (
        <Container maxWidth="sm">
            <form onSubmit={e => e.preventDefault()}>
                <Grid container spacing={3} direction="column">

                    <Grid item>
                        <TextField
                            label="Short ID"
                            variant="outlined"
                            fullWidth
                            value={shortId}
                            InputProps={{
                                readOnly: true,
                            }}
                        />
                    </Grid>

                    <Grid item>
                        <PhoneInputComponent
                            value={renterPhoneNumber}
                            onChange={(v) => {
                                setRenterPhoneNumber(v);
                                handleFieldValidation('renterPhoneNumber', v);
                            }}
                        />
                    </Grid>

                    <Grid item>
                        <TextField
                            label="Initial Odometer Reading"
                            variant="outlined"
                            fullWidth
                            type="number"
                            value={initialOdometerReading}
                            onChange={(e) => {
                                setInitialOdometerReading(e.target.value);
                                handleFieldValidation('initialOdometerReading', e.target.value);
                            }}
                            InputProps={{
                                endAdornment: validatedFields.initialOdometerReading && (
                                    <InputAdornment position="end">
                                        <CheckCircle color="success" />
                                    </InputAdornment>
                                ),
                            }}
                        />
                    </Grid>

                    {["front", "left", "back", "right", "odometer"].map((angle) => (
                        <Grid item key={angle}>
                            <TextField
                                variant="outlined"
                                label={angle}
                                fullWidth
                                type="file"
                                name={angle}
                                InputProps={{
                                    startAdornment: (
                                        <InputAdornment position="start">
                                            <PhotoCamera />
                                        </InputAdornment>
                                    ),
                                    endAdornment: (validatedFields as any)[angle] && <InputAdornment position="end">
                                            <CheckCircle color="success" />
                                        </InputAdornment>,
                                }}
                                onChange={handlePictureChange}
                            />
                        </Grid>
                    ))}

                    <Grid item>
                        <Button variant="contained" color="primary" type="submit" disabled={!isAllFieldsValidated}>
                            Submit
                        </Button>
                    </Grid>
                </Grid>
            </form>
        </Container>
    );
};

export default CarRentalForm;
