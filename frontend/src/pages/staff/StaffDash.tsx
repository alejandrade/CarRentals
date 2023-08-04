import React, { useState } from 'react';
import {
    Accordion,
    AccordionSummary,
    AccordionDetails,
    Typography,
    Button,
    useMediaQuery,
    useTheme,
    Box
} from '@mui/material';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import CarTable from "./CarTable";
import GenericModal from "../../components/GenericModal";
import CarForm from "./CarForm";
import CarEditor from "./CarEditor";

const StaffDash: React.FC = () => {
    const theme = useTheme();

    const isMobile = useMediaQuery(theme.breakpoints.down('sm'));


    if (isMobile) {
        return (
            <Box display="flex" justifyContent="center" alignItems="center" height="100vh">
                <Typography variant="h6">Must use desktop to access this page.</Typography>
            </Box>
        );
    }

    return (
        <>
            <CarEditor/>
        </>
    );
};

export default StaffDash;
