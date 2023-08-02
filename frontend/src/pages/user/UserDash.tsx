import React, { useEffect, useState } from 'react';
import UserDemographicsForm from "./UserDemographicsForm";
import { UserDto } from "../../services/user/UserService.types";
import userService from "../../services/user/UserService";
import InsuranceForm from "./InsuranceForm";
import LicenseForm from "./LicenseForm";
import ContactInformationForm from "./ContactInformationForm";
import LoadingOverlay from "../../components/LoadingOverlay";
import UserDemographicsCard from "./UserDemographicsCard";
import ContactInformationCard from "./ContactInformationCard";
import UserInsuranceCard from "./UserInsuranceCard";
import UserLicenseCard from "./UserLicenseCard";

const UserDash: React.FC = () => {
    const [user, setUser] = useState<UserDto | undefined>();

    useEffect(() => {
        const loggedInUser = userService.getLoggedInUser();
        loggedInUser.then((resp) => {
            setUser(resp);
            console.log(resp);
        });
    }, []);

    return (
        <>
            {user ? (
                <>
                    <ContactInformationCard userId={user.id} dto={{ email: user.email, phoneNumber: user.phoneNumber }} />
                    <UserDemographicsCard userId={user.id} dto={user?.userDemographics} />

                    {/* Always render UserInsuranceCard, but conditionally pass in DTO */}
                    <UserInsuranceCard dto={user.userInsurances && user.userInsurances.length > 0 ? user.userInsurances[0] : {}} />

                    {/* Always render UserLicenseCard, but conditionally pass in DTO */}
                    <UserLicenseCard dto={user.userLicenses && user.userLicenses.length > 0 ? user.userLicenses[0] : {}} />
                </>
            ) : (
                <LoadingOverlay />
            )}
        </>
    );
}

export default UserDash;
