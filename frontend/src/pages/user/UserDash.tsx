import React, {useEffect, useState} from 'react';
import UserDemographicsForm from "./UserDemographicsForm";
import {UserDto} from "../../services/user/UserService.types";
import userService from "../../services/user/UserService";
import InsuranceForm from "./InsuranceForm";
import LicenseForm from "./LicenseForm";
import ContactInformationForm from "./ContactInformationForm";
import LoadingOverlay from "../../components/LoadingOverlay";
import UserDemographicsCard from "./UserDemographicsCard";

const UserDash: React.FC = () => {
    const [user, setUser] = useState<UserDto>()

    useEffect(() => {
        var loggedInUser = userService.getLoggedInUser();
        loggedInUser.then((resp)=> {
            setUser(resp);
            console.log(resp);
        })
    }, [])

    function onContactInformationSubmit (phone: string, email: string) {

    }

    return(<>
        {user ? <>
        <ContactInformationForm onSubmit={onContactInformationSubmit} phoneNumber={user?.phoneNumber} email={user?.email}/>
        <UserDemographicsCard userId={user.id} data={user?.userDemographics}></UserDemographicsCard>
        <InsuranceForm/>
        <LicenseForm/>
        </> : <LoadingOverlay/>
        }
    </>) ;
}

export default UserDash;
