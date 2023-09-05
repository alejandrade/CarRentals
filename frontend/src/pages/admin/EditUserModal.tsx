import React, {useEffect, useState} from "react";
import Modal from "@mui/material/Modal";
import Card from "@mui/material/Card";
import CardHeader from "@mui/material/CardHeader";
import CardContent from "@mui/material/CardContent";
import Button from "@mui/material/Button";
import {UserDto} from "../../services/user/UserService.types";
import userService from "../../services/user/UserService";
import UserDtoForm from "./AdminUserDtoForm";
import {useErrorModal} from "../../contexts/ErrorModalContext";

type Param = {
    userId: string,
    onSave: () => void
}
const EditUserModal: React.FC<Param> = ({userId, onSave}) => {
    const [open, setOpen] = useState(false);
    const [textValue, setTextValue] = useState("");
    const [editedUserDto, setEditedUserDto] = useState<UserDto>();
    const { showError, handleAPIError } = useErrorModal();

    useEffect(() => {
        init();
    }, []);

    async function init() {
        const user = await userService.getUser(userId).catch(handleAPIError);
        console.log(user);
        if (user)
        setEditedUserDto(user);
    }

    const handleOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
        setTextValue(""); // Clear the text field when closing the modal
    };

    const handleSubmit = async (data: UserDto) => {
        // Do something with the text value (e.g., submit to the server)
        console.log("Submitted value:", textValue);
        await userService.modifyUser(data.id, data).catch(handleAPIError);
        onSave();
        handleClose();
    };

    return (
        <div>
            <Button variant="contained" onClick={handleOpen}>
                Edit User
            </Button>
            <Modal open={open} onClose={handleClose} aria-labelledby="modal-title">
                <Card sx={{
                    position: 'absolute',
                    top: '50%',
                    left: '50%',
                    transform: 'translate(-50%, -50%)',
                    maxWidth: '500px',
                    width: '80%',  // This can be adjusted based on your preference
                }}>
                    <CardHeader title="Edit User" />
                    <CardContent>
                        <UserDtoForm dto={{id: userId}} onSave={handleSubmit}/>
                    </CardContent>
                </Card>
            </Modal>
        </div>
    );
};

export default EditUserModal;
