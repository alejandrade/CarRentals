import React, { useEffect } from 'react';
import {Typography, Tabs, Tab, Grid, Box} from '@mui/material';
import { useAuth } from "../../contexts/auth_context";
import { useNavigate, useLocation } from "react-router-dom";
import { Outlet } from 'react-router-dom';
import { styled } from '@mui/system';

const TabsContainer = styled(Box)(({ theme }) => ({
    [theme.breakpoints.down('sm')]: {
        position: 'fixed',
        bottom: 0,
        left: 0,
        right: 0,
        zIndex: 1000,
    },
    [theme.breakpoints.up('md')]: {
        order: 1,
    },
}));

const ContentContainer = styled(Grid)(({ theme }) => ({
    [theme.breakpoints.down('sm')]: {
        paddingBottom: 56, // or whatever the height of your tabs is
    },
    [theme.breakpoints.up('md')]: {
        order: 2,
    },
}));

const AuthoritySelectPage: React.FC = () => {

    const roles = ["user", "staff", "clerk", "admin"];

    const { authorities } = useAuth();
    const navigate = useNavigate();
    const location = useLocation();
    const [selectedTab, setSelectedTab] = React.useState(0);

    useEffect(() => {
        if (authorities.length === 0) {
            return;
        }
        const tab = roles.filter(x => location.pathname.includes(x))[0];
        if (tab) {
            setSelectedTab(authorities.indexOf(`ROLE_${tab.toUpperCase()}`));
        } else {
            if (authorities.includes(`ROLE_ADMIN`)) {
                onSelect("admin");
                setSelectedTab(authorities.indexOf(`ROLE_ADMIN`));
            } else if (authorities.includes("ROLE_CLERK")) {
                onSelect("clerk")
                setSelectedTab(authorities.indexOf(`ROLE_CLERK`));
            } else if (authorities.includes("ROLE_STAFF")) {
                onSelect("staff")
                setSelectedTab(authorities.indexOf(`ROLE_STAFF`));

            } else if (authorities.includes("ROLE_USER")) {
                onSelect("user");
                setSelectedTab(authorities.indexOf(`ROLE_USER`));

            }
        }

    }, [authorities]);

    function onSelect(path: string) {
        navigate(`/dash/${path}`);
    }

    const handleChange = (event: React.SyntheticEvent, newValue: number) => {
        setSelectedTab(newValue);
        onSelect(getAuthorities()[newValue]);
    };

    function getAuthorities() {
        return authorities.map(x => x.replace("ROLE_", "").toLowerCase());
    }

    function triggerClick (path: string) {
        navigate(`/dash/${path}`);
    }

    return (
        <Grid container direction="column">
            <TabsContainer bgcolor="background.paper">
                <Tabs
                    value={selectedTab}
                    onChange={handleChange}
                    variant="scrollable"
                    scrollButtons="auto"
                    indicatorColor="primary"
                    textColor="primary"
                >
                    {getAuthorities().map((role, index) => (
                        <Tab onClick={() => triggerClick(role)} key={index} label={role} />
                    ))}
                </Tabs>
            </TabsContainer>

            <ContentContainer item xs={12}>
                <Outlet />
            </ContentContainer>
        </Grid>
    );
}

export default AuthoritySelectPage;
