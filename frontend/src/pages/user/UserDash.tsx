import React, { useEffect, useState } from 'react';
import { UserDto } from "../../services/user/UserService.types";
import userService from "../../services/user/UserService";
import UserEdit from "./UserEdit";

const UserDash: React.FC = () => {
    const [user, setUser] = useState<UserDto | undefined>();

    useEffect(() => {
        const loggedInUser = userService.getLoggedInUser();
        loggedInUser.then((resp) => {
            setUser(resp);
        });
    }, []);

    return (
        <>
            <UserEdit dto={user} />
        </>
    );
}


export default UserDash;
