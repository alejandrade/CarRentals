import React, {useEffect, useState} from "react";
import Modal from "@mui/material/Modal";
import Card from "@mui/material/Card";
import CardHeader from "@mui/material/CardHeader";
import CardContent from "@mui/material/CardContent";
import CardActions from "@mui/material/CardActions";
import Typography from "@mui/material/Typography";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import {UserDto} from "../../services/user/UserService.types";
import userService from "../../services/user/UserService";
import UserDtoForm from "./AdminUserDtoForm";

type Param = {
    userId: string
}
const EditUserModal: React.FC<Param> = ({userId}) => {
    const [open, setOpen] = useState(false);
    const [textValue, setTextValue] = useState("");
    const [editedUserDto, setEditedUserDto] = useState<UserDto>();

    useEffect(() => {

    }, []);

    async function init() {
        const user = await userService.getUser(userId);
        setEditedUserDto(user);
    }

    const handleOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
        setTextValue(""); // Clear the text field when closing the modal
    };

    const handleSubmit = (data: UserDto) => {
        // Do something with the text value (e.g., submit to the server)
        console.log("Submitted value:", textValue);
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
                        <UserDtoForm onSave={handleSubmit}/>
                    </CardContent>
                </Card>
            </Modal>
        </div>
    );
};

export default EditUserModal;
