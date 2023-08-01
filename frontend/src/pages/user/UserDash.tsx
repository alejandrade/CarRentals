import React, {useEffect, useState} from 'react';
import UserDemographicsForm from "./UserDemographicsForm";
import {UserDto} from "../../services/user/UserService.types";
import userService from "../../services/user/UserService";
import InsuranceForm from "./InsuranceForm";
import LicenseForm from "./LicenseForm";
import ContactInformationForm from "./ContactInformationForm";
import LoadingOverlay from "../../components/LoadingOverlay";
import UserDemographicsCard from "./UserDemographicsCard";
import ContactInformationCard from "./ContactInformationCard";
import UserInsuranceCard from "./UserInsuranceCard";

const UserDash: React.FC = () => {
    const [user, setUser] = useState<UserDto>()

    useEffect(() => {
        var loggedInUser = userService.getLoggedInUser();
        loggedInUser.then((resp)=> {
            setUser(resp);
            console.log(resp);
        })
    }, [])


    return(<>
        {user ? <>
        <ContactInformationCard userId={user.id} dto={{email: user.email, phoneNumber: user.phoneNumber}} />
        <UserDemographicsCard userId={user.id} dto={user?.userDemographics}></UserDemographicsCard>
        <UserInsuranceCard dto={user?.userInsurances[0]} />
        <LicenseForm/>
        </> : <LoadingOverlay/>
        }
    </>) ;
}

export default UserDash;
