import React, { useState } from 'react';
import {Button, Container, Grid, TextField, InputAdornment, useTheme, useMediaQuery, Typography} from '@mui/material';
import { PhotoCamera, CheckCircle } from '@mui/icons-material';
import {UserDemographicsDto} from "../../services/user/UserService.types";
import {RentalDto} from "../../services/rentals/rentalService.types";
import {CarCreationDto} from "../../services/car/carService.types";
import ImageUpload from "../../components/ImageUpload";


type ValidatedFieldsType = {
    odometer: boolean;
    returnDay: boolean;
    front: boolean;
    left: boolean;
    right: boolean;
    back: boolean;
    odometerPic: boolean;
};

const CarRentalConfirmationForm: React.FC<{
    user: UserDemographicsDto | undefined;
    rental: RentalDto | undefined;
    car: CarCreationDto | undefined;
}> = ({ user, rental, car }) => {
    const [odometer, setOdometer] = useState('');
    const [returnDay, setReturnDay] = useState('');
    const [pictures, setPictures] = useState({
        front: null,
        left: null,
        right: null,
        back: null,
        odometerPic: null
    });

    const [validatedFields, setValidatedFields] = useState<ValidatedFieldsType>({
        odometer: false,
        returnDay: false,
        front: false,
        left: false,
        right: false,
        back: false,
        odometerPic: false
    });
    const theme = useTheme();
    // @ts-ignore
    const isMobile = useMediaQuery(theme.breakpoints.down('sm'));
    const isAllFieldsValidated = Object.values(validatedFields).every(Boolean);

    const handleFieldValidation = (name: string, value: any) => {
        if (value) {
            setValidatedFields(prev => ({ ...prev, [name]: true }));
        } else {
            setValidatedFields(prev => ({ ...prev, [name]: false }));
        }
    };

    const handlePictureChange = (file: File) => {
        if (file && file.length > 0) {
            const name = file.name;
            setPictures(prev => ({ ...prev, [name]: file }));
            handleFieldValidation(name, file);
        }
    };

    return (
        <Container maxWidth="md"> {/* Or "lg" or "xl" or "false" */}
            <form onSubmit={e => e.preventDefault()}>
                <Grid container spacing={3} direction="column">
                    <Container sx={{marginTop: "50px"}}>
                        <Grid container direction={isMobile ? "column" : "row"} spacing={3}>
                            <Grid item>
                                <Typography variant="h5">User Details</Typography>
                                <Typography><strong>Name:</strong> {user?.firstName} {user?.middleInitial} {user?.lastName}</Typography>
                                <Typography><strong>Date of Birth:</strong> {user?.dateOfBirth?.toLocaleDateString()}</Typography>
                                <Typography><strong>Gender:</strong> {user?.gender}</Typography>
                                {/* Add more user properties as needed */}
                            </Grid>

                            <Grid item>
                                <Typography variant="h5">Rental Details</Typography>
                                <Typography><strong>Phone Number:</strong> {rental?.renterPhoneNumber}</Typography>
                                <Typography><strong>Status:</strong> {rental?.status}</Typography>
                                {/* Continued Rental Details */}
                                <Typography><strong>Rental Date:</strong> {rental?.rentalDatetime?.toUTCString()}</Typography>
                                {/* Add more rental properties as needed */}
                            </Grid>

                            <Grid item>
                                <Typography variant="h5">Car Details</Typography>
                                <Typography><strong>Make:</strong> {car?.make}</Typography>
                                <Typography><strong>Model:</strong> {car?.model}</Typography>
                                <Typography><strong>Year:</strong> {car?.year}</Typography>
                                <Typography><strong>VIN:</strong> {car?.vin}</Typography>
                                <Typography><strong>License Plate:</strong> {car?.licensePlate}</Typography>
                                <Typography><strong>Color:</strong> {car?.color}</Typography>
                            </Grid>
                        </Grid>

                    </Container>
                    <Grid item>
                        <TextField
                            label="Odometer"
                            variant="outlined"
                            fullWidth
                            type="number"
                            value={odometer}
                            onChange={(e) => {
                                setOdometer(e.target.value);
                                handleFieldValidation('odometer', e.target.value);
                            }}
                        />
                    </Grid>

                    <Grid item>
                        <TextField
                            label="Return Day"
                            variant="outlined"
                            fullWidth
                            type="datetime-local"
                            InputLabelProps={{
                                shrink: true,
                            }}
                            value={returnDay}
                            onChange={(e) => {
                                setReturnDay(e.target.value);
                                handleFieldValidation('returnDay', e.target.value);
                            }}
                        />
                    </Grid>

                    {["front", "left", "back", "right", "odometer"].map((angle) => (
                        <Grid item key={angle}>
                            <ImageUpload label={angle} onImageChange={handlePictureChange} />
                        </Grid>
                    ))}

                </Grid>
            </form>
        </Container>
    );
};

export default CarRentalConfirmationForm;
