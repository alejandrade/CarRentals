import React, {useState} from 'react';
import CarRentalStart from "./CarRentalStart";
import {Box, Button, Container} from "@mui/material";
import RentalList from "./RentalList";
import CustomToolbar from "../../components/CustomToolbar";
import rentalService from "../../services/rentals/RentalService";
import {useNavigate} from "react-router-dom";

const ClerkDash: React.FC = () => {
    const navigate = useNavigate();

    const [selectedReturnedId, setSelectedReturnedId] = useState<string | null>(null);
    const [selectedRentedId, setSelectedRentedId] = useState<string | null>(null); // Track selected row ID
    const [selectedReservedId, setSelectedReservedId] = useState<string | null>(null); // Track selected row ID
    const [reload, setReload] = useState(false);
    async function cancelReserved() {
        selectedReservedId && await rentalService.cancel(selectedReservedId);
        setReload(!reload);
    }

    async function editReserved() {
        if (!selectedReservedId) {
            return;
        }
        const rental = await rentalService.get(selectedReservedId);
        navigate(`/dash/clerk/${rental.renterPhoneNumber}/cars/${rental.carId}/${rental.id}`)
    }

    async function editRented() {
        if (!selectedRentedId) {
            return;
        }
        const rental = await rentalService.get(selectedRentedId);
        navigate(`/dash/clerk/${rental.renterPhoneNumber}/cars/${rental.carId}/${rental.id}`)
    }
    async function pay() {
        setReload(!reload);
    }

    return <>
        <CarRentalStart/>
        <Container>
            <CustomToolbar>
                <Box>
                    <Button onClick={editReserved} disabled={!selectedReservedId} sx={{marginRight: "5px"}} variant={"contained"}>Edit</Button>
                    <Button onClick={cancelReserved} disabled={!selectedReservedId} color={"error"} variant={"contained"}>Cancel</Button>
                </Box>
            </CustomToolbar>
            <RentalList reloadData={reload} onSelect={setSelectedReservedId} title={"Reserved"} status={"RESERVED"} />

            <CustomToolbar>
                <Box>
                    <Button onClick={editRented} disabled={!selectedRentedId} sx={{marginRight: "5px"}} variant={"contained"}>Edit</Button>
                </Box>
            </CustomToolbar>
            <RentalList reloadData={reload} onSelect={setSelectedRentedId} title={"Rented"} status={"RENTED"} />
            <CustomToolbar>
                <Box>
                    <Button onClick={pay} disabled={!selectedReturnedId} sx={{marginRight: "5px"}} variant={"contained"}>Pay</Button>
                </Box>
            </CustomToolbar>
            <RentalList reloadData={reload} onSelect={setSelectedReturnedId}  title={"Returned"} status={"RETURNED"} />
        </Container>
    </>;
}

export default ClerkDash;
