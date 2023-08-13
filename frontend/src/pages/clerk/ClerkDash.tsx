import React from 'react';
import CarRentalStart from "./CarRentalStart";
import {Container} from "@mui/material";
import RentalList from "./RentalList";

const ClerkDash: React.FC = () => {
    return <>
        <CarRentalStart/>
        <Container>
            <RentalList title={"Reserved"} status={"RESERVED"} />
            <RentalList title={"Rented"} status={"RENTED"} />
            <RentalList title={"Returned"} status={"RETURNED"} />
        </Container>
    </>;
}

export default ClerkDash;
