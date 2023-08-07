import React, { useEffect, useState } from 'react';
import { Accordion, AccordionSummary, AccordionDetails, Typography, useMediaQuery, useTheme } from '@mui/material';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import { UserDto } from "../../services/user/UserService.types";
import LoadingOverlay from "../../components/LoadingOverlay";
import UserDemographicsCard from "./UserDemographicsCard";
import ContactInformationCard from "./ContactInformationCard";
import UserInsuranceCard from "./UserInsuranceCard";
import UserLicenseCard from "./UserLicenseCard";

export type Param = {
    dto: UserDto | undefined;
}
const UserEdit: React.FC<Param> = ({dto}) => {
    const [user, setUser] = useState<UserDto | undefined>(dto);
    const theme = useTheme();
    const isMobile = useMediaQuery(theme.breakpoints.down('sm'));


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

    return (
        <>
            {user ? (
                <>
                    {renderCard('Contact Information', <ContactInformationCard userId={user.id} dto={{ email: user.email, phoneNumber: user.phoneNumber }} />, user.email)}
                    {renderCard('Personal Information', <UserDemographicsCard userId={user.id} dto={user?.userDemographics} />, user?.userDemographics)}
                    {renderCard('Insurance Details', <UserInsuranceCard dto={user.userInsurances && user.userInsurances.length > 0 ? user.userInsurances[0] : {}} />, user.userInsurances && user.userInsurances.length > 0 ? user.userInsurances[0] : null)}
                    {renderCard('License Details', <UserLicenseCard dto={user.userLicenses && user.userLicenses.length > 0 ? user.userLicenses[0] : {}} />, user.userLicenses && user.userLicenses.length > 0 ? user.userLicenses[0] : null)}
                </>
            ) : (
               <></>
            )}
        </>
    );
}


export default UserEdit;
