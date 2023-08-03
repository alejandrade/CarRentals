import React from 'react';
import Typography from '@mui/material/Typography';
import {Container, useMediaQuery, useTheme} from "@mui/material";
import UserDetailsTable from "./UserDetailsTable";

const AdminDash: React.FC = () => {
    const theme = useTheme();
    const isMobile = useMediaQuery(theme.breakpoints.down('sm'));

    if (isMobile) {
        return <Typography variant="h6">Must use desktop to access this page.</Typography>;
    }

    return (
        <Container>
            <UserDetailsTable/>
        </Container>
    );
}

export default AdminDash;
