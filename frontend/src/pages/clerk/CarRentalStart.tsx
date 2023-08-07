import React, { useState } from 'react';
import {TextField, Button, Paper, Typography, Box} from '@mui/material';
import {Navigate, useNavigate} from "react-router-dom";
import PhoneInputComponent from "../../components/PhoneInputComponent";
import {UserDto} from "../../services/user/UserService.types";
import userService from "../../services/user/UserService";
import LoadingButton from "@mui/lab/LoadingButton";
import UserEdit from "../user/UserEdit";
import {APIError} from "../../util/FetchFunctions";
import {useErrorModal} from "../../contexts/ErrorModalContext";
import authService from "../../services/auth/AuthService";
import CodeInput from "../../components/CodeInput";
import clerkService from "../../services/clerk/ClerkService";

const CarRentalStart: React.FC = () => {
    const [phoneNumber, setPhoneNumber] = useState<string>('');
    const [valid, setValid] = useState<boolean>();
    const [user, setUser] = useState<UserDto | undefined>();
    const [loading, setLoading] = useState<boolean>(false);
    const [userFound, setUserFound] = useState<boolean>();
    const [verificationStarted, setVerificationStarted] = useState<boolean>(false);

    const navigate = useNavigate();
    const { showError, handleAPIError } = useErrorModal();


    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        setLoading(true);
        if (valid) {
            const client = await userService.getClient(phoneNumber.replace(/\s+/g, '')).catch((err: APIError) => {
                console.log(err);
                if (err.details.status == 404) {
                    return false;
                } else {
                    handleAPIError(err);
                    return err;
                }
            });

            if (client instanceof APIError) {
                return;
            }

            if (client) {
                console.log("this happened")
                setUserFound(true);
                setUser(client);
                navigate(`userCreate/${client.phoneNumber}`)
            } else {
                setUserFound(false);
            }
            setLoading(false);
        }
    };

    async function onVerificationProcessStart() {
        setLoading(true);
        await authService.startVerification({phoneNumber: phoneNumber.replace(/\s+/g, ''), channel: "SMS"});
        setVerificationStarted(true);
        setLoading(false);
    }

    async function onVerificationCodeSent(code: string) {
        setLoading(true);
        var created = await clerkService.verifyAndCreate({code, phoneNumber});
        if (created.verified) {
            const userCreated = await userService.getClient(phoneNumber);
            console.log("we created the user")
            setUser(userCreated);
            setLoading(false);
            setUserFound(true);
        }
    }

    return (
        <Paper style={{ padding: '20px', maxWidth: '400px', margin: '20px auto' }}>
            <Typography variant="h5" style={{ marginBottom: '20px' }}>
                Client Phone Number
            </Typography>
            <form onSubmit={handleSubmit}>
                <PhoneInputComponent onValidate={setValid} isValid={valid} value={phoneNumber} onChange={(val) => setPhoneNumber(val)}/>
                <LoadingButton loading={loading} variant="contained" color="primary" type="submit" disabled={!valid}
                        style={{ marginTop: '20px' }} // Add this line
                >
                    Search
                </LoadingButton>
            </form>
            {userFound === true &&
                <Navigate to={`userCreate/${phoneNumber}`}/>
            }

            {userFound === false && <>
                <Box display="flex" alignItems="center" mt={2}>
                    <Typography variant={"body1"}>User Not Found. </Typography>
                    <LoadingButton
                        loading={loading}
                        variant="text"
                        color="primary"
                        disableElevation
                        onClick={onVerificationProcessStart}
                        sx={{ textTransform: 'none', textDecoration: 'underline', ml: 1 }}
                        disabled={!valid}
                    >
                        Create
                    </LoadingButton>
                </Box>

            </>}

            {verificationStarted && <>
                <Typography variant={"body1"} >A code has been sent to the clients text messages</Typography>
                <CodeInput onSubmit={onVerificationCodeSent}/>
            </>}
        </Paper>
    );
};

export default CarRentalStart;
