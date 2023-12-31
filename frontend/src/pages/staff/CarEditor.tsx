import React, { useState } from 'react';
import {
    Accordion,
    AccordionSummary,
    AccordionDetails,
    Typography,
    Button,
    useMediaQuery,
    useTheme,
    Box, Dialog, DialogContent
} from '@mui/material';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import CarTable from "./CarTable";
import GenericModal from "../../components/GenericModal";
import CarForm from "./CarForm";
import CustomToolbar from "../../components/CustomToolbar";

const CarEditor: React.FC = () => {
    const [refresh, setRefresh] = useState<boolean>(false);
    const [carFormModalOpen, setCarFormModalOpen] = useState<boolean>(false);
    const [carId, setCarId] = useState<string | null>();

    function onSelect(id: string | null) {
        setCarId(id);
    }

    function refreshTable() {
        setRefresh(true);
    }


    return (
        <div>
            <Typography variant={"h4"}>Car Details</Typography>
            <CustomToolbar>
                <Button variant={"contained"} onClick={() => setCarFormModalOpen(!carFormModalOpen)}>
                    {carId ? "Edit" : "Create" }
                </Button>
            </CustomToolbar>
            <Dialog open={carFormModalOpen} onClose={() => {setCarFormModalOpen(false)}}>
                <DialogContent>
                    <CarForm onSave={() => setCarFormModalOpen(false)} id={carId ? carId : undefined} refreshTable={refreshTable} />
                </DialogContent>
            </Dialog>
            <CarTable clerk={false} refresh={refresh} onSelected={onSelect} />
        </div>
    );
};

export default CarEditor;
