import React, { useEffect, useState } from 'react';
import {useNavigate, useParams} from "react-router-dom";
import userService from "../../services/user/UserService";
import {UserDto} from "../../services/user/UserService.types";
import CustomToolbar from "../../components/CustomToolbar";
import {Button} from "@mui/material";
import CarTable from "../staff/CarTable";
import {CarResponseDto} from "../../services/car/carService.types";

const CarSelect: React.FC<{ }>  = () => {
    const { phoneNumber } = useParams();
    const [user, setUser] = useState<UserDto | undefined>();
    const [cardId, setCarId] = useState<string| null>();
    const navigate = useNavigate();

    useEffect(() => {
        init();
    }, []);

    async function init() {

    }

    function back() {
        navigate(`/dash/clerk/userCreate/${phoneNumber}`)
    }

    function next() {
        if (cardId) {
            navigate(`/dash/clerk/${phoneNumber}/cars/${cardId}`)
        }
    }

    function carSelected(carId: string | null) {
        setCarId(carId);
    }

    return (
        <>
            <CustomToolbar>
                <Button onClick={back} variant={"contained"} color={"secondary"}>Back</Button>
                <Button disabled={!cardId} onClick={next} variant={"contained"}>Next</Button>
            </CustomToolbar>
            <CarTable onSelected={carSelected} refresh={false} />
        </>
    );
}


export default CarSelect;
