import React, { useEffect, useState } from 'react';
import {useNavigate, useParams} from "react-router-dom";
import userService from "../../services/user/UserService";
import {UserDto} from "../../services/user/UserService.types";
import CustomToolbar from "../../components/CustomToolbar";
import UserEdit from "../user/UserEdit";
import {Button} from "@mui/material";

const UserCreate: React.FC<{ }>  = () => {
    const { phoneNumber } = useParams();
    const [user, setUser] = useState<UserDto | undefined>();
    const navigate = useNavigate();

    useEffect(() => {
        init();
    }, []);

    async function init() {
        if (phoneNumber) {
            const client = await userService.getClient(phoneNumber.replace(/\s+/g, ''));
            setUser(client);
        }
    }

    function back() {
        navigate(`/dash/clerk`)
    }

    function next() {
        if (user?.userInsurances && user.userDemographics && user.userLicenses) {
            return navigate(`/dash/car/select/${phoneNumber}`);
        }
    }

    return (
        <>{user && <>
            <CustomToolbar>
                <Button onClick={back} variant={"contained"} color={"secondary"}>Back</Button>
                <Button onClick={next} variant={"contained"}>Next</Button>
            </CustomToolbar>
            <UserEdit dto={user}/>
        </>
        }
        </>
    );
}


export default UserCreate;
