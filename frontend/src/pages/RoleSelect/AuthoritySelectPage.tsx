import React, { useEffect } from 'react';
import { Typography, Tabs, Tab, Box, Grid } from '@mui/material';
import { useAuth } from "../../contexts/auth_context";
import { useNavigate, useLocation } from "react-router-dom";
import { Outlet } from 'react-router-dom';

const AuthoritySelectPage: React.FC = () => {

    //roles to show for admin
    const roles = ["user", "staff", "clerk", "admin"];

    const { authorities } = useAuth();
    const navigate = useNavigate();
    const location = useLocation();
    const [selectedTab, setSelectedTab] = React.useState(0);


    useEffect(() => {
        const tab = roles.filter(x=> location.pathname.includes(x))[0];
        if (tab) {
            setSelectedTab(roles.indexOf(tab));
        } else {
            if (authorities[0]) {
                const newTab = authorities[0].replace("ROLE_", "").toLowerCase();
                onSelect(newTab);
            }
        }
    }, [])

    function onSelect(path: string) {
        navigate(`/dash/${path}`);
    }

    const handleChange = (event: React.SyntheticEvent, newValue: number) => {
        setSelectedTab(newValue);
        onSelect(getAuthorities()[newValue]);
    };

    function getAuthorities() {
        if (authorities.includes("ROLE_ADMIN")) {
            return roles;
        } else {
            return authorities.map(x => x.replace("ROLE_", "").toLowerCase());
        }
    }

    return (
        <Grid container direction="column">
            <Grid item xs={12}>
                <Box bgcolor="background.paper">
                    <Tabs
                        value={selectedTab}
                        onChange={handleChange}
                        variant="scrollable"
                        scrollButtons="auto"
                        indicatorColor="primary"
                        textColor="primary"
                    >
                        {getAuthorities().map((role, index) => (
                            <Tab key={index} label={role} />
                        ))}
                    </Tabs>
                </Box>
            </Grid>

            <Grid item xs={12}>
                <Outlet />
            </Grid>
        </Grid>
    );
}

export default AuthoritySelectPage;
