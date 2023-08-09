import React, { useEffect, useState } from 'react';
import {useNavigate, useParams} from "react-router-dom";
import {UserDto} from "../../services/user/UserService.types";
import CustomToolbar from "../../components/CustomToolbar";
import {Button} from "@mui/material";
import CarTable from "../staff/CarTable";
import {useErrorModal} from "../../contexts/ErrorModalContext";

const CarConfirmation: React.FC<{ }>  = () => {
    const { phoneNumber, cardId, rentId } = useParams();
    const [user, setUser] = useState<UserDto | undefined>();
    const navigate = useNavigate();
    const { showError, handleAPIError } = useErrorModal();

    useEffect(() => {
        init();
    }, []);

    async function init() {

    }

    function back() {
        navigate(`/dash/car/select/${phoneNumber}`)
    }

    function pay() {

    }

    return (
        <>
            <CustomToolbar>
                <Button onClick={back} variant={"contained"} color={"secondary"}>Back</Button>
                <Button onClick={pay} variant={"contained"} color={"primary"}>Pay</Button>
            </CustomToolbar>
        </>
    );
}


export default CarConfirmation;