import React, { useEffect, useState } from 'react';
import { Accordion, AccordionSummary, AccordionDetails, Typography, useMediaQuery, useTheme } from '@mui/material';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import { UserDto } from "../../services/user/UserService.types";
import LoadingOverlay from "../../components/LoadingOverlay";
import UserDemographicsCard from "./UserDemographicsCard";
import ContactInformationCard from "./ContactInformationCard";
import UserInsuranceCard from "./UserInsuranceCard";
import UserLicenseCard from "./UserLicenseCard";
import paymentsService from "../../services/payments/PaymentsService";
import {PaymentsInvoiceDto} from "../../services/payments/PaymentsService.types";
import WarningAlert from "../../components/WarningAlert";

export type Param = {
    dto: UserDto | undefined;
}
const UserEdit: React.FC<Param> = ({dto}) => {
    const [user, setUser] = useState<UserDto | undefined>(dto);
    const [userInvoices, setUserInvoices] = useState<PaymentsInvoiceDto[]>([]);

    const theme = useTheme();
    const isMobile = useMediaQuery(theme.breakpoints.down('sm'));

    useEffect(() => {
        const urlSearchParams = new URLSearchParams(window.location.search);
        const success = urlSearchParams.get("success");
        if (success === "true") {
            alert("payment successful");
        }
        init().then();
    }, [user])

    async function init() {
        const currentInvoices = await paymentsService.currentInvoices();
        setUserInvoices(currentInvoices);
    }
    const renderCard = (title: string, CardComponent: React.ReactElement, dto: any) => {
        const shouldExpand = !dto;

        if (isMobile) {
            return (
                <Accordion defaultExpanded={shouldExpand}>
                    <AccordionSummary expandIcon={<ExpandMoreIcon />}>
                        <Typography>{title}</Typography>
                    </AccordionSummary>
                    <AccordionDetails>
                        {CardComponent}
                    </AccordionDetails>
                </Accordion>
            );
        }
        return CardComponent;
    }

    async function payInvoice(invoiceId: string) {
        const payment = await paymentsService.invoiceCreatePayment({
            invoiceId: invoiceId,
            cancelUrl: location.href,
            successUrl: location.href
        });

        if (payment.url)
            location.href = payment.url;
    }

    return (
        <>
            {user ? (
                <>
                    {userInvoices.map((invoice) =>
                        <>
                            <WarningAlert warningText={`Active Invoice: ${invoice.total / 100}$`} onButtonClick={() => payInvoice(invoice.id)}/>
                        </>
                    )}
                    {renderCard('Contact Information', <ContactInformationCard userId={user.id} dto={{ email: user.email, phoneNumber: user.phoneNumber }} />, user.email)}
                    {renderCard('Personal Information', <UserDemographicsCard userId={user.id} dto={user?.userDemographics} />, user?.userDemographics)}
                    {renderCard('Insurance Details', <UserInsuranceCard userId={user.id}  dto={user.userInsurances && user.userInsurances.length > 0 ? user.userInsurances[0] : {}} />, user.userInsurances && user.userInsurances.length > 0 ? user.userInsurances[0] : null)}
                    {renderCard('License Details', <UserLicenseCard userId={user.id} dto={user.userLicenses && user.userLicenses.length > 0 ? user.userLicenses[0] : {}} />, user.userLicenses && user.userLicenses.length > 0 ? user.userLicenses[0] : null)}
                </>
            ) : (
               <></>
            )}
        </>
    );
}


export default UserEdit;
