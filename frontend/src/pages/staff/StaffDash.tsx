import * as React from 'react';
import { Accordion, AccordionSummary, AccordionDetails, Typography } from '@mui/material';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import CarTable from "./CarTable";

const StaffDash: React.FC = () => {
    return (
        <div>
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

        </div>
    );
}

export default StaffDash;
