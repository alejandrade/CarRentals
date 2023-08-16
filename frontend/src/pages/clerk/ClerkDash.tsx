import React, {useEffect, useState} from 'react';
import CarRentalStart from "./CarRentalStart";
import {Box, Button, Container} from "@mui/material";
import RentalList from "./RentalList";
import CustomToolbar from "../../components/CustomToolbar";
import rentalService from "../../services/rentals/RentalService";
import {useNavigate} from "react-router-dom";
import paymentsService from "../../services/payments/PaymentsService";
import carService from "../../services/car/carService";
import {PaymentsInvoiceDto} from "../../services/payments/PaymentsService.types";
import userService from "../../services/user/UserService";
import QRCodeModal from "../../components/QrCodeModal";

const ClerkDash: React.FC = () => {
    const navigate = useNavigate();

    const [selectedReturnedId, setSelectedReturnedId] = useState<string | null>(null);
    const [selectedRentedId, setSelectedRentedId] = useState<string | null>(null); // Track selected row ID
    const [selectedReservedId, setSelectedReservedId] = useState<string | null>(null); // Track selected row ID
    const [reload, setReload] = useState(false);

    const [qrCodeValue, setQrCodeValue] = useState('');
    const [openQrCode, setOpenQrCode] = useState(false);

    useEffect(() => {
        const urlSearchParams = new URLSearchParams(window.location.search);
        const success = urlSearchParams.get("success");
        if (success === "true") {
            alert("payment successful");
        }

    }, []);

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
    function calculateDaysBetweenDates(date1: Date, date2: Date): number {
        // Calculate the time difference in milliseconds
        const timeDifference = Math.abs(date2.getTime() - date1.getTime());

        // Calculate the number of milliseconds in a day
        const millisecondsInDay = 1000 * 60 * 60 * 24;

        // Calculate the number of days
        const days = Math.ceil(timeDifference / millisecondsInDay);

        // If the dates are the same, return 1
        if (days === 0) {
            return 1;
        }

        return days;
    }
    async function createSelfInvoice() {
        if (!selectedReturnedId) {
            throw new Error("need to select returned car");
        }
        const rental = await rentalService.get(selectedReturnedId);
        const car = await carService.getCarById(rental.carId);
        const subTotal = ((car.rentPrice || 0) * calculateDaysBetweenDates(rental.rentalDatetime, rental.returnDatetime))*100;
        const invoice = await createInvoice(rental.clerkId, subTotal, "Clerk Paid");
        const payment = await paymentsService.invoiceCreatePayment({
            invoiceId: invoice.id,
            cancelUrl: location.href,
            successUrl: location.href
        });

        if (payment.url)
            location.href = payment.url;

    }

    async function chargeClientFees() {
        if (!selectedReturnedId) {
            throw new Error("need to select returned car");
        }
        const rental = await rentalService.get(selectedReturnedId);
        const insuranceFee = (rental.insuranceFee || 0) * calculateDaysBetweenDates(rental.rentalDatetime, rental.returnDatetime);
        const invoice = await createInvoice(rental.renterId, insuranceFee + rental.cleaningFee, `Cleaning Fee: ${rental.cleaningFee} Insurance Fee: ${insuranceFee}`);
        const payment = await paymentsService.invoiceCreatePayment({
            invoiceId: invoice.id,
            cancelUrl: location.href,
            successUrl: location.href
        });

        if (payment.url) {
            setQrCodeValue(payment.url);
            setOpenQrCode(true);
            await paymentsService.billRenter(selectedReturnedId, payment.url);
        }

    }
    async function createInvoice(payer: string, subTotal: number, note: string): Promise<PaymentsInvoiceDto> {
        if (!selectedReturnedId) {
            throw new Error("need to select returned car");
        }

        const rental = await rentalService.get(selectedReturnedId);
        const invoice = await paymentsService.createInvoice({
            rentalId: rental.id,
            subTotal,
            note,
            payerId: payer
        });

        return invoice;
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
                    <Button onClick={createSelfInvoice} disabled={!selectedReturnedId} sx={{marginRight: "5px"}} variant={"contained"}>Pay Rental</Button>
                    <Button onClick={chargeClientFees} disabled={!selectedReturnedId}
                            sx={{marginRight: "5px"}} variant={"contained"}>Bill Renter</Button>
                </Box>
            </CustomToolbar>
            <RentalList reloadData={reload} onSelect={setSelectedReturnedId}  title={"Returned"} status={"RETURNED"} />
            <QRCodeModal handleClose={() => setOpenQrCode(false)} value={qrCodeValue} open={openQrCode} />
        </Container>
    </>;
}

export default ClerkDash;
