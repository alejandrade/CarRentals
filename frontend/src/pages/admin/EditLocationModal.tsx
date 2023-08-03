import React, { useEffect, useState } from "react";
import Modal from "@mui/material/Modal";
import Card from "@mui/material/Card";
import CardHeader from "@mui/material/CardHeader";
import CardContent from "@mui/material/CardContent";
import Button from "@mui/material/Button";
import ServiceLocationForm from "./ServiceLocationForm";
import {ServiceLocationDto} from "../../services/service_location/ServiceLocationService.types";
import serviceLocationService from "../../services/service_location/ServiceLocationService";
import {useErrorModal} from "../../contexts/ErrorModalContext"; // Import your ServiceLocationForm component

type Param = {
    locationId?: string
    onClose: () => void
}

const EditLocationModal: React.FC<Param> = ({ locationId, onClose }) => {
    const [open, setOpen] = useState(false);
    const [editedLocationDto, setEditedLocationDto] = useState<Partial<ServiceLocationDto>>({});
    const { showError, handleAPIError } = useErrorModal();

    useEffect(() => {
        if (locationId) {
            init();
        }
    }, [locationId]);

    async function init() {
        const location = await serviceLocationService.getServiceLocationById(locationId!).catch(handleAPIError);
        if (location)
        setEditedLocationDto(location);
    }

    const handleOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const handleSubmit = async (data: ServiceLocationDto) => {
        // Do something with the location data (e.g., submit to the server)
        console.log("Submitted value:", data);
        await serviceLocationService.save(data).catch(handleAPIError);
        handleClose();
        onClose();
    };

    return (
        <div>
            <Button variant="contained" onClick={handleOpen}>
                {locationId ? "Edit" : "Create"}
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
                    <CardHeader title={locationId ? "Edit Location" : "Create Location"} />
                    <CardContent>
                        <ServiceLocationForm dto={editedLocationDto} onSave={handleSubmit} />
                    </CardContent>
                </Card>
            </Modal>
        </div>
    );
};

export default EditLocationModal;
