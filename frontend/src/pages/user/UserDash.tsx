import React, {useEffect, useState} from 'react';
import UserDemographicsForm from "./UserDemographicsForm";
import {UserDto} from "../../services/user/UserService.types";
import userService from "../../services/user/UserService";

const UserDash: React.FC = () => {
    const [user, setUser] = useState<UserDto>()

    useEffect(() => {
        console.log("definedMultiple Times")
        var loggedInUser = userService.getLoggedInUser();
        loggedInUser.then((resp)=> {
            setUser(resp);
        })
    }, [])

    return <UserDemographicsForm dto={user?.userDemographics}></UserDemographicsForm>;
}

export default UserDash;
