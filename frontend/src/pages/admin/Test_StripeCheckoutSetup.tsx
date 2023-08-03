import React, { useEffect, useState } from 'react';
import Typography from '@mui/material/Typography';
import {Container, useMediaQuery, useTheme} from "@mui/material";
import PaymentsService from '../../services/payments/PaymentsService';
import { PaymentMethodSessionBeginDto } from '../../services/payments/PaymentsService.types';
import router from '../../router';

const Test_StripeCheckoutSetup: React.FC = () => {
    const theme = useTheme();
    const isMobile = useMediaQuery(theme.breakpoints.down('sm'));


    
    useEffect(()=>{
        let data:PaymentMethodSessionBeginDto = {
            userId:"123",
            successUrl:"http://localhost:3000/stripe-success",
            cancelUrl:"http://localhost:3000/stripe-cancel",
            url:undefined
        }
        PaymentsService.paymentMethodSessionBeginTest(data).then((response:PaymentMethodSessionBeginDto)=>{
            const url:string = response.url as string;
            window.location.href = url;
        });
    }, []);


    if (isMobile) {
        return <Typography variant="h6">Must use desktop to access this page.</Typography>;
    }



    return (
        <Container>

        </Container>
    );
}

export default Test_StripeCheckoutSetup;