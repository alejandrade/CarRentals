import React, {useState} from 'react';
import {Button, Card, CardContent, CardHeader, Container, Grid, Typography,} from '@mui/material';
import UserDemographicsForm from "./UserDemographicsForm";
import {UserDemographicsDto} from "../../services/user/UserService.types";


interface UserDemographicsCardProps {
    dto?: UserDemographicsDto;
    userId: string
}

const UserDemographicsCard: React.FC<UserDemographicsCardProps> = ({dto, userId}) => {
    const [isEditing, setIsEditing] = useState(false);
    const [data, setData] = useState<Partial<UserDemographicsDto|undefined>>(dto);

    const handleEditClick = (val: boolean) => {
        setIsEditing(val);
        console.log("start editing")
    };

    const handleSaveClick = (result: UserDemographicsDto) => {
        setIsEditing(false);
        setData(result);
    };
    return (
        <Card>
            <CardHeader title="Personal Information" action={isEditing ? <Button onClick={() => setIsEditing(false)}>Cancel</Button> : <Button onClick={() => setIsEditing(true)}>Edit</Button>} />
            <CardContent>
                {data && !isEditing ?
                    <Container>
                        <Grid container direction="column" spacing={2}>
                            <Grid item>
                                <Typography variant="body1">
                                    Name: {data.firstName} {data.middleInitial || ''} {data.lastName}
                                </Typography>
                            </Grid>
                            <Grid item>
                                <Typography variant="body1">Date of
                                    Birth: {data.dateOfBirth?.toLocaleDateString() || ''}</Typography>
                            </Grid>
                            <Grid item>
                                <Typography variant="body1">Gender: {data.gender || ''}</Typography>
                            </Grid>
                            <Grid item>
                                <Typography
                                    variant="body1">Address: {data.address || ''}, {data.city || ''}, {data.state || ''} {data.postalCode || ''}, {data.country || ''}</Typography>
                            </Grid>
                            <Grid item>
                                <Typography variant="body1">Additional Info: {data.additionalInfo || ''}</Typography>
                            </Grid>
                        </Grid>
                    </Container>
                    : <UserDemographicsForm onSave={handleSaveClick} dto={data} userId={userId}/>
                }
            </CardContent>
        </Card>
    );
};

export default UserDemographicsCard;
