import React, { useState } from 'react';
import { Card, CardContent, Typography, Button, Grid, Box, TextField } from '@mui/material';
import LoadingButton from "@mui/lab/LoadingButton";
import adminPayments from "../../services/admin/AdminPayments";

interface AccountingCardProps {

}

const AccountingCard: React.FC<AccountingCardProps> = () => {
    const [endDate, setEndDate] = useState<Date | null>(null);
    const formattedDate = new Date(Date.now() - 30 * 24 * 60 * 60 * 1000).toISOString().split('T')[0];
    const [startDate, setStartDate] = useState<string>(formattedDate);

    const handleStartDateChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const selectedDate = event.target.valueAsDate;
        if (selectedDate) {
            const newEndDate = new Date(selectedDate);
            setStartDate(newEndDate.toISOString().split('T')[0]);
            newEndDate.setDate(newEndDate.getDate() + 30);
            setEndDate(newEndDate);
        } else {
            setEndDate(null);
        }
    };

    async function download() {
        if (startDate) {
            await adminPayments.getStatement(startDate);
        }
        setStartDate('');
    }

    return (
        <Card variant="outlined">
            <CardContent>
                <Typography variant="h5" component="div">
                    Accounting
                </Typography>
                <Box mt={2}>
                    <Grid container spacing={2}>
                        <Grid item xs={10}>
                            <TextField
                                id="start-date"
                                label="Start Date"
                                type="date"
                                value={startDate}
                                variant="outlined"
                                InputLabelProps={{
                                    shrink: true,
                                }}
                                onChange={handleStartDateChange}
                            />
                            {endDate && (
                                <Typography variant="body1">
                                    End Date: {endDate.toLocaleDateString()}
                                </Typography>
                            )}
                        </Grid>
                    </Grid>
                </Box>
                <Box mt={2}>
                    <LoadingButton onClick={download} variant="contained" color="primary">
                        Download
                        Statement
                    </LoadingButton>
                </Box>
            </CardContent>
        </Card>
    );
};

export default AccountingCard;
