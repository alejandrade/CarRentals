import * as React from 'react';
import { Accordion, AccordionSummary, AccordionDetails, Typography } from '@mui/material';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';

import StandardLayout from "../../components/StandardLayout";
import CarTable from "./CarTable";

const StaffDash: React.FC = () => {
    return (
        <StandardLayout>
            <p>Dash</p>

            <Accordion>
                <AccordionSummary
                    expandIcon={<ExpandMoreIcon />}
                    aria-controls="panel1a-content"
                    id="panel1a-header"
                >
                    <Typography>Car Details</Typography>
                </AccordionSummary>
                <AccordionDetails>
                    <CarTable />
                </AccordionDetails>
            </Accordion>

        </StandardLayout>
    );
}

export default StaffDash;
