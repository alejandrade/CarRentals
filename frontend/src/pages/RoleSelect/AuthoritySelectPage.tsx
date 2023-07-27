import React, { useEffect } from 'react';
import { Typography, Tabs, Tab, Box, Grid } from '@mui/material';
import { useAuth } from "../../contexts/auth_context";
import { useNavigate } from "react-router-dom";
import { Outlet } from 'react-router-dom';

const AuthoritySelectPage: React.FC = () => {
    const { authorities } = useAuth();
    const navigate = useNavigate();
    const [selectedTab, setSelectedTab] = React.useState(0);

    useEffect(() => {
        console.log(authorities);
        if (authorities.length === 1) {
            onSelect(authorities[0].replace("ROLE_", "").toLowerCase());
        }
    }, [authorities]);  // added authorities to dependency array

    function onSelect(path: string) {
        console.log(path);
        navigate(`/dash/${path.replace("ROLE_", "").toLowerCase()}`);
    }

    const handleChange = (event: React.SyntheticEvent, newValue: number) => {
        setSelectedTab(newValue);
        onSelect(authorities[newValue]);
    };

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
                        {authorities.map((role, index) => (
                            <Tab key={index} label={role.replace("ROLE_", "").toLowerCase()} />
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
