import React, { useState } from 'react';
import {Button, Card, CardContent, CardHeader, Container, Grid, Typography} from "@mui/material";
import {UserInsuranceDto} from "../../services/user/UserService.types";
import InsuranceForm from "./InsuranceForm";

interface UserInsuranceCardProps {
    dto: UserInsuranceDto;
}

const UserInsuranceCard: React.FC<UserInsuranceCardProps> = ({ dto }) => {
    const [isEditing, setIsEditing] = useState(false);
    const [data, setData] = useState<UserInsuranceDto>(dto);

    const handleEditClick = () => {
        setIsEditing(true);
    };

    const handleSaveClick = (dto: UserInsuranceDto) => {
        setIsEditing(false);
        setData(dto);
    };

    return (
        <Card>
            <CardHeader title="User Insurance" action={isEditing ? <Button onClick={() => setIsEditing(false)}>Cancel</Button> : <Button onClick={handleEditClick}>Edit</Button>} />
            <CardContent>
                {!isEditing && data.policyNumber && data.provider && data.endDate ? (
                    <Container>
                        <Grid container direction="column" spacing={2}>
                            <Grid item>
                                <Typography variant="body1">Policy Number: {data.policyNumber}</Typography>
                            </Grid>
                            <Grid item>
                                <Typography variant="body1">Provider: {data.provider}</Typography>
                            </Grid>
                            <Grid item>
                                <Typography variant="body1">End Date: {new Date(data.endDate).toLocaleDateString()}</Typography>
                            </Grid>
                        </Grid>
                    </Container>
                ) : (
                    <InsuranceForm onSave={handleSaveClick} userId={dto.userId} dto={data} />
                )}
            </CardContent>
        </Card>
    );
};

export default UserInsuranceCard;
