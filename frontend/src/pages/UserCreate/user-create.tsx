import React, { useEffect, useState } from 'react';
import {useNavigate, useParams} from "react-router-dom";
import userService from "../../services/user/UserService";
import {UserDto} from "../../services/user/UserService.types";
import CustomToolbar from "../../components/CustomToolbar";
import UserEdit from "../user/UserEdit";
import {Button} from "@mui/material";
import LoadingButton from "@mui/lab/LoadingButton";

const UserCreate: React.FC<{ }>  = () => {
    const { phoneNumber } = useParams();
    const [user, setUser] = useState<UserDto | undefined>();
    const navigate = useNavigate();
    const [loading, setLoading] = useState(false);

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

    async function next() {
        setLoading(true);
        if (phoneNumber) {
            const client = await userService.getClient(phoneNumber.replace(/\s+/g, ''));
            if (client?.userInsurances && client.userDemographics && client.userLicenses) {
                return navigate(`/dash/car/select/${phoneNumber}`);
            }
        }

        setLoading(false);
    }

    return (
        <>{user && <>
            <CustomToolbar>
                <Button onClick={back} variant={"contained"} color={"secondary"}>Back</Button>
                <LoadingButton loading={loading} onClick={next} variant={"contained"}>Next</LoadingButton>
            </CustomToolbar>
            <UserEdit dto={user}/>
        </>
        }
        </>
    );
}


export default UserCreate;
