import React, {useEffect} from 'react';
import { Card, CardContent, Typography, Button, Grid } from '@mui/material';
import {useAuth} from "../../contexts/auth_context";
import {useNavigate} from "react-router-dom";

const AuthoritySelectPage: React.FC = () => {
    const {authorities} = useAuth();
    const navigate = useNavigate();

    useEffect(()=> {
        console.log(authorities);
        if (authorities.length === 1) {
            onSelect(authorities[0].replace("ROLE_", "").toLowerCase())
        }
    }, [])

    function onSelect(path:string) {
        console.log(path);
        navigate(`/dash/${path.replace("ROLE_", "").toLowerCase()}`)
    }

    return (
        <Grid container justifyContent="center" alignItems="center" style={{ height: '100vh' }}>
            <Card style={{ width: '300px' }}>
                <CardContent>
                    <Typography variant="h5" gutterBottom>
                        Select Role
                    </Typography>

                    {authorities.map((role, index) => (
                        <Button
                            key={index}
                            variant="contained"
                            color="primary"
                            fullWidth
                            onClick={() => onSelect(role)}
                        >
                            {role}
                        </Button>
                    ))}
                </CardContent>
            </Card>
        </Grid>
    );
}

export default AuthoritySelectPage;
