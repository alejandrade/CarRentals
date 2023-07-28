import React, { useState } from 'react';
import {
    Accordion,
    AccordionSummary,
    AccordionDetails,
    Typography,
    Button,
} from '@mui/material';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import CarTable from "./CarTable";
import GenericModal from "../../components/GenericModal";
import CarForm from "./CarForm";

const StaffDash: React.FC = () => {
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
            <div style={{ display: 'flex', justifyContent: 'flex-end' }}>
                <Button variant={"contained"} onClick={() => setCarFormModalOpen(!carFormModalOpen)}>
                    {carId ? "Edit" : "Create" } Car
                </Button>
            </div>
            <GenericModal onClose={() => {setCarFormModalOpen(false)}} open={carFormModalOpen}>
                <CarForm id={carId ? carId : undefined} refreshTable={refreshTable} />
            </GenericModal>
            <Accordion defaultExpanded={true}>
                <AccordionSummary
                    expandIcon={<ExpandMoreIcon />}
                    aria-controls="panel1a-content"
                    id="panel1a-header"
                >
                    <Typography>Car Details</Typography>
                </AccordionSummary>
                <AccordionDetails>
                    <CarTable refresh={refresh} onSelected={onSelect} />
                </AccordionDetails>
            </Accordion>
        </div>
    );
};

export default StaffDash;
