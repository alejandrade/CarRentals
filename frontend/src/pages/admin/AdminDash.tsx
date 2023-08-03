import React from 'react';
import Typography from '@mui/material/Typography';
import {Box, Container, Paper, useMediaQuery, useTheme} from "@mui/material";
import UserDetailsTable from "./UserDetailsTable";
import LocationsTable from "./LocationsTable";

const AdminDash: React.FC = () => {
    const theme = useTheme();
    const isMobile = useMediaQuery(theme.breakpoints.down('sm'));

    if (isMobile) {
        return <Typography variant="h6">Must use desktop to access this page.</Typography>;
    }

    return (
        <Box>
            <Container component={Paper} sx={{ mb: 3, p: 3 }}>  {/* Add spacing and padding for aesthetic appeal */}
                <UserDetailsTable/>
            </Container>
            <Container component={Paper} sx={{ mb: 3, p: 3 }}>  {/* Add spacing and padding for aesthetic appeal */}
                <LocationsTable/>
            </Container>
        </Box>
    );
}

export default AdminDash;
