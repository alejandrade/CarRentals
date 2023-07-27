import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';  // Import the Button component
import {useAuth} from "../contexts/auth_context";

const TopBar: React.FC = () => {
    const { isAuthenticated, logout } = useAuth();  // Assuming useAuth provides a logout method

    return (
        <AppBar position="static">
            <Toolbar>
                <Typography variant="h6" component="div" style={{ flexGrow: 1 }}>
                    CarRental
                </Typography>
                {isAuthenticated && (
                    <Button color="inherit" onClick={logout}>
                        Logout
                    </Button>
                )}
            </Toolbar>
        </AppBar>
    );
}

export default TopBar;
