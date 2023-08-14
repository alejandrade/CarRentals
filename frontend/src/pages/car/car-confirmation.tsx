import React, { useEffect, useState } from 'react';
import {useNavigate, useParams} from "react-router-dom";
import {UserDemographicsDto, UserDto} from "../../services/user/UserService.types";
import CustomToolbar from "../../components/CustomToolbar";
import {Button} from "@mui/material";
import {useErrorModal} from "../../contexts/ErrorModalContext";
import CarRentalConfirmationForm from "./CarRentalConfirmationForm";
import rentalService from "../../services/rentals/RentalService";
import {RentalActionDto, RentalDto} from "../../services/rentals/rentalService.types";
import carService from "../../services/car/carService";
import {CarResponseDto} from "../../services/car/carService.types";
import userService from "../../services/user/UserService";

const CarConfirmation: React.FC<{ }>  = () => {
    const { phoneNumber, cardId, rentId } = useParams();
    const [userDemographics, setUserDemographics] = useState<UserDemographicsDto>();
    const [rentDto, setRentDto] = useState<RentalDto>();
    const [carDto, setCarDto] = useState<CarResponseDto>();
    const [rentalActionDto, setRentalActionDto] = useState<RentalActionDto>();

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

    async function startRental() {
        rentalActionDto && rentDto?.id && await rentalService.startRental(rentDto?.id, rentalActionDto)
    }
    async function endRental() {
        rentalActionDto && rentDto?.id && await rentalService.endRental(rentDto?.id, rentalActionDto);
    }

    function onChange(rentDto: Partial<RentalDto>){
        if (rentDto) {
            setRentalActionDto({
                initialOdometerReading: rentDto.initialOdometerReading || 0,
                version: rentDto.version || 0,
                returnDatetime: rentDto?.returnDatetime,
                endingOdometerReading: rentDto.endingOdometerReading || 0,
                cleaningFee: rentDto.cleaningFee || false,
                damagedFee: rentDto.damagedFee || false
            });
        }
    }

    return (
        <>
            <CustomToolbar>
                <Button onClick={back} variant={"contained"} color={"secondary"}>Back</Button>
                {rentDto?.status === "RESERVED" &&
                    <Button onClick={startRental} variant={"contained"} color={"primary"}>Start Rental</Button>
                }

                {rentDto?.status === "RENTED" &&
                    <Button onClick={endRental} variant={"contained"} color={"primary"}>End Rental</Button>
                }
            </CustomToolbar>
            <CarRentalConfirmationForm onChange={onChange} user={userDemographics} rental={rentDto} car={carDto} />
        </>
    );
}


export default CarConfirmation;
