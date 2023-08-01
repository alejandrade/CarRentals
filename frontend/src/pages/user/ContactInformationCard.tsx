import React, { useState } from 'react';
import {Button, Card, CardContent, CardHeader, Container, Grid, Typography} from "@mui/material";
import ContactInformationForm from "./ContactInformationForm";
import {UserDemographicsDto} from "../../services/user/UserService.types";

interface ContactInformationDto {
    phoneNumber?: string;
    email?: string;
}


interface ContactInformationCardProps {
    dto: ContactInformationDto;
    userId: string;
}

const ContactInformationCard: React.FC<ContactInformationCardProps> = ({ dto, userId }) => {
    const [isEditing, setIsEditing] = useState(false);
    const [data, setData] = useState<ContactInformationDto>(dto);
    const handleEditClick = () => {
        setIsEditing(true);
    };

    const handleSaveClick = (email: string, phoneNumber: string) => {
        setIsEditing(false);
        setData({
            email,
            phoneNumber
        });
    };

    return (
        <Card>
            <CardHeader title="Contact Information"/>
            <CardContent>
                {!isEditing && data.email && data.phoneNumber ? (
                    <Container>
                        <Grid container direction="column" spacing={2}>
                            <Grid item>
                                <Typography variant="body1">Phone Number: {data.phoneNumber || ''}</Typography>
                            </Grid>
                            <Grid item>
                                <Typography variant="body1">Email: {data.email || ''}</Typography>
                            </Grid>
                        </Grid>
                    </Container>
                ) : (
                    <ContactInformationForm onSave={handleSaveClick} phoneNumber={data.phoneNumber} email={data.email} userId={userId} />
                )}
            </CardContent>
        </Card>
    );
};

export default ContactInformationCard;