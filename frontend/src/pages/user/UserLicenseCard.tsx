import React, { useState } from 'react';
import {Button, Card, CardContent, CardHeader, Container, Grid, Typography} from "@mui/material";
import {UserLicenseDto} from "../../services/user/UserService.types";
import LicenseForm from "./LicenseForm"; // assuming you have a similar form created

interface UserLicenseCardProps {
    dto: Partial<UserLicenseDto>;
}

const UserLicenseCard: React.FC<UserLicenseCardProps> = ({ dto }) => {
    const [isEditing, setIsEditing] = useState(false);
    const [data, setData] = useState<Partial<UserLicenseDto>>(dto);

    const handleEditClick = () => {
        setIsEditing(true);
    };

    const handleSaveClick = (dto: UserLicenseDto) => {
        setIsEditing(false);
        setData(dto);
    };

    return (
        <Card>
            <CardHeader
                title="License Details"
                action={isEditing ?
                    <Button onClick={() => setIsEditing(false)}>Cancel</Button> :
                    <Button onClick={handleEditClick}>Edit</Button>
                }
            />
            <CardContent>
                {!isEditing && data.licenseNumber && data.issuingState && data.expirationDate ? (
                    <Container>
                        <Grid container direction="column" spacing={2}>
                            <Grid item>
                                <Typography variant="body1">License Number: {data.licenseNumber}</Typography>
                            </Grid>
                            <Grid item>
                                <Typography variant="body1">Issuing State: {data.issuingState}</Typography>
                            </Grid>
                            <Grid item>
                                <Typography variant="body1">Expiration Date: {new Date(data.expirationDate).toLocaleDateString()}</Typography>
                            </Grid>
                        </Grid>
                    </Container>
                ) : (
                    <LicenseForm onSave={handleSaveClick}  dto={data} /> // Assuming a similar structure for the LicenseForm
                )}
            </CardContent>
        </Card>
    );
};

export default UserLicenseCard;
