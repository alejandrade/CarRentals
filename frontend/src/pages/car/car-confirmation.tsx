import React, { useEffect, useState } from 'react';
import {useNavigate, useParams} from "react-router-dom";
import {UserDemographicsDto, UserDto} from "../../services/user/UserService.types";
import CustomToolbar from "../../components/CustomToolbar";
import {Button} from "@mui/material";
import CarTable from "../staff/CarTable";
import {useErrorModal} from "../../contexts/ErrorModalContext";
import CarRentalConfirmationForm from "./CarRentalConfirmationForm";
import rentalService from "../../services/rentals/RentalService";
import {RentalDto} from "../../services/rentals/rentalService.types";
import carService from "../../services/car/carService";
import {CarResponseDto} from "../../services/car/carService.types";
import userService from "../../services/user/UserService";

const CarConfirmation: React.FC<{ }>  = () => {
    const { phoneNumber, cardId, rentId } = useParams();
    const [userDemographics, setUserDemographics] = useState<UserDemographicsDto>();
    const [rentDto, setRentDto] = useState<RentalDto>();
    const [carDto, setCarDto] = useState<CarResponseDto>();

    const navigate = useNavigate();
    const { showError, handleAPIError } = useErrorModal();

    useEffect(() => {
        init();
    }, []);

    async function init() {
        if(rentId){
            const rentDto = await rentalService.get(rentId);
            setRentDto(rentDto);
        }

        if (cardId) {
            const carDto = await carService.getCarById(cardId);
            setCarDto(carDto);
        }

        if (phoneNumber) {
            const userDto = await userService.getClient(phoneNumber);
            setUserDemographics(userDto.userDemographics);
        }

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
            <CarRentalConfirmationForm user={userDemographics} rental={rentDto} car={carDto} />
        </>
    );
}


export default CarConfirmation;
