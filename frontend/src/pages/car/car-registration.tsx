import React, { useEffect, useState } from 'react';
import {useNavigate, useParams} from "react-router-dom";
import userService from "../../services/user/UserService";
import {UserDto} from "../../services/user/UserService.types";
import CustomToolbar from "../../components/CustomToolbar";
import {Button} from "@mui/material";
import CarTable from "../staff/CarTable";
import {CarResponseDto} from "../../services/car/carService.types";
import rentalService from "../../services/rentals/RentalService";
import {useErrorModal} from "../../contexts/ErrorModalContext";

const CarSelect: React.FC<{ }>  = () => {
    const { phoneNumber } = useParams();
    const [user, setUser] = useState<UserDto | undefined>();
    const [carId, setCarId] = useState<string| null>();
    const navigate = useNavigate();
    const { showError, handleAPIError } = useErrorModal();

    useEffect(() => {
        init();
    }, []);

    async function init() {

    }

    function back() {
        navigate(`/dash/clerk/userCreate/${phoneNumber}`)
    }

    async function next() {
        if (carId && phoneNumber) {
            console.log("happened here")
            var rental = await rentalService.createRental({
                carId,
                renterPhoneNumber: phoneNumber,
                status: "RESERVED",
                rentalDatetime: new Date().toISOString()
            }).catch(handleAPIError);

            rental && navigate(`/dash/clerk/${phoneNumber}/cars/${carId}/${rental.id}`)
        }
    }

    function carSelected(carId: string | null) {
        setCarId(carId);
    }

    return (
        <>
            <CustomToolbar>
                <Button onClick={back} variant={"contained"} color={"secondary"}>Back</Button>
                <Button disabled={!carId} onClick={next} variant={"contained"}>Next</Button>
            </CustomToolbar>
            <CarTable onSelected={carSelected} refresh={false} />
        </>
    );
}


export default CarSelect;
