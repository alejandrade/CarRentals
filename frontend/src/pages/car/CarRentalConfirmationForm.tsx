import React, {useEffect, useState} from 'react';
import {
    Button,
    Container,
    Grid,
    TextField,
    InputAdornment,
    useTheme,
    useMediaQuery,
    Typography,
    IconButton
} from '@mui/material';
import {PhotoCamera, CheckCircle, Clear} from '@mui/icons-material';
import {UserDemographicsDto} from "../../services/user/UserService.types";
import {RentalDto} from "../../services/rentals/rentalService.types";
import {CarCreationDto} from "../../services/car/carService.types";
import ImageUpload from "../../components/ImageUpload";
import CheckboxWithLabel from "../../components/CheckboxWithLabel";


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
    onChange: (rental: Partial<RentalDto>) => void;
}> = ({ user, rental, car, onChange }) => {
    const [odometer, setOdometer] = useState<number | string>(rental?.initialOdometerReading || '');
    const [returnDay, setReturnDay] = useState<string>('');

    const [gasFeeChecked, setGasFeeChecked] = useState<boolean>(!!rental?.gasFee || false);
    const [damagedFeeCheck, setDamagedFeeCheck] = useState<boolean>(!!rental?.damagedFee || false);
    const [cleaningFeeCheck, setCleaningFeeCheck] = useState<boolean>(rental?.cleaningFee == 3500 || false);
    const [insuranceFeeCheck, setInsuranceFeeCheck] = useState<boolean>(!!rental?.insuranceFee || false);
    const [detailFeeCheck, setDetailFeeCheck] = useState<boolean>((rental?.cleaningFee && rental?.cleaningFee > 3500) || false);

    const [pictures, setPictures] = useState({
        front: null,
        left: null,
        right: null,
        back: null,
        odometerPic: null
    });

    const [validatedFields, setValidatedFields] = useState<ValidatedFieldsType>({
        odometer: false,
        returnDay: true,
        front: false,
        left: false,
        right: false,
        back: false,
        odometerPic: false
    });
    const theme = useTheme();
    // @ts-ignore
    const isMobile = useMediaQuery(theme.breakpoints.down('sm'));

    useEffect(() => {
        let newReturnDatetime;

        if (returnDay && isReturnDayValid(returnDay)) {
            newReturnDatetime = new Date(returnDay);
        } else {
            newReturnDatetime = undefined;
        }


        let cleaningFee;

        if (detailFeeCheck) {
            cleaningFee = 30000;
        } else if (cleaningFeeCheck) {
            cleaningFee = 3500;
        } else {
            cleaningFee = 0;
        }

        onChange({
            initialOdometerReading: odometer as number,
            endingOdometerReading: odometer as number,
            damagedFee: 0,
            cleaningFee,
            insuranceFee: insuranceFeeCheck ? Number(800) : 0,
            gasFee: gasFeeChecked ? 5000 : 0,
            returnDatetime: newReturnDatetime
        });
    }, [odometer, returnDay, damagedFeeCheck, cleaningFeeCheck, gasFeeChecked, detailFeeCheck])



    const isReturnDayValid = (value: string) => {
        const selectedDate = new Date(value);
        const tomorrow = new Date();
        tomorrow.setDate(tomorrow.getDate() + 1);
        tomorrow.setHours(0, 0, 0, 0);

        // Check if the selected date is tomorrow or later
        if (selectedDate < tomorrow) {
            return false;
        }

        // Check if the selected time is between 9am and 4pm
        const selectedTime = selectedDate.getHours();
        if (selectedTime < 9 || selectedTime >= 16) {
            return false;
        }

        return true;
    };

    const handleReturnDayChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const value = e.target.value;

        // Update the state with the new value
        setReturnDay(value);

        // Perform validation
        const isValid = isReturnDayValid(value);

        if (isValid) {
            setValidatedFields(prev => ({ ...prev, returnDay: true }));
        } else {
            setValidatedFields(prev => ({ ...prev, returnDay: false }));
        }
    };

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

    const clearReturnDay = () => {
        setReturnDay('');
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
                                setOdometer(Number(e.target.value));
                                handleFieldValidation('odometer', e.target.value);
                            }}
                        />
                    </Grid>



                    {rental?.status === "RENTED" && <>
                        <Grid item>
                            <CheckboxWithLabel value={cleaningFeeCheck}  onChange={setCleaningFeeCheck} label={"Cleaning Fee"}/>
                        </Grid>
                        <Grid item>
                            <CheckboxWithLabel value={detailFeeCheck}  onChange={setDetailFeeCheck} label={"Detail Fee"}/>
                        </Grid>
                        <Grid item>
                            <CheckboxWithLabel value={gasFeeChecked}  onChange={setGasFeeChecked} label={"Gas Fee"}/>
                        </Grid>
                    </>}

                    <Grid item>
                        {rental?.status !== "RENTED" && <>
                            <TextField
                                label="Return Day"
                                variant="outlined"
                                fullWidth
                                type="datetime-local"
                                InputLabelProps={{
                                    shrink: true,
                                }}
                                disabled={true}
                                value={returnDay}
                                onChange={handleReturnDayChange}
                                error={validatedFields.returnDay === false}
                                helperText={validatedFields.returnDay === false && "Invalid return day."}
                                InputProps={{
                                    endAdornment: (
                                        <InputAdornment position="end">
                                            <IconButton
                                                aria-label="Clear return day"
                                                onClick={clearReturnDay}
                                                onMouseDown={(e) => e.preventDefault()}
                                                edge="end"
                                            >
                                                <Clear />
                                            </IconButton>
                                        </InputAdornment>
                                    ),
                                }}
                            />
                            </>}
                    </Grid>

                    {rental?.status === "RESERVED" && <>
                        <Grid item>
                            <CheckboxWithLabel value={insuranceFeeCheck}  onChange={setInsuranceFeeCheck} label={"Insurance Fee (8$ per day)"}/>
                        </Grid>
                    </>}

                    {/*{["front", "left", "back", "right", "odometer"].map((angle) => (*/}
                    {/*    <Grid item key={angle}>*/}
                    {/*        <ImageUpload label={angle} onImageChange={handlePictureChange} />*/}
                    {/*    </Grid>*/}
                    {/*))}*/}

                </Grid>
            </form>
        </Container>
    );
};

export default CarRentalConfirmationForm;
