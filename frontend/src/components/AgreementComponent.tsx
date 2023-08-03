import React, { useState, useRef } from "react";
import { Button, Box, Typography, Paper } from "@mui/material";
import SignatureComponent from "./SignatureComponent";

interface AgreementProps {
    startDate: string;
    endDate: string;
    renterName: string;
    rentalAgencyName: string;
    termDays: number;
    dailyRate: number;
    totalRate: number;
}

const AgreementComponent: React.FC<AgreementProps> = ({
                                                          startDate,
                                                          endDate,
                                                          renterName,
                                                          rentalAgencyName,
                                                          termDays,
                                                          dailyRate,
                                                          totalRate
                                                      }) => {
    const [isAcceptEnabled, setIsAcceptEnabled] = useState(false);
    const agreementRef = useRef<HTMLDivElement | null>(null);

    const handleScroll = () => {
        if (agreementRef.current) {
            const container = agreementRef.current;
            if (container.scrollHeight - container.scrollTop === container.clientHeight) {
                setIsAcceptEnabled(true);
            }
        }
    };

    return (
        <Box>
            <Paper
                variant="outlined"
                style={{ height: "400px", overflowY: "scroll", padding: "16px" }}
                onScroll={handleScroll}
                ref={agreementRef}
            >
                <Typography variant="body1">
                    <h2>Car Rental Agreement</h2>
                    <p>
                        THIS AGREEMENT is made and entered into this day of {startDate}, by and between {renterName},
                        hereinafter referred to as "Renter", and {rentalAgencyName}, hereinafter referred to as "Rental Agency".
                    </p>

                    <ol>
                        <li>
                            <strong>Term:</strong> This Agreement is for a term of {termDays} days, commencing on {startDate}, and ending on {endDate}.
                        </li>

                        <li>
                            <strong>Rental Vehicle:</strong> Rental Agency shall provide to Renter a passenger vehicle (the "Vehicle"). The Renter shall return the Vehicle to the agreed rental location no later than the end of the term.
                        </li>

                        <li>
                            <strong>Rental Fee:</strong> Renter will pay to Rental Agency a rental fee of ${dailyRate} per day for a total rental fee of ${totalRate}.
                        </li>

                        {/* ... other parts of the agreement ... */}
                    </ol>

                    <p>
                        By signing below, Renter acknowledges and agrees to the terms and conditions of this Agreement:
                    </p>

                    <div>
                        <div style={{ padding: '50px' }}>
                            <SignatureComponent />
                        </div>
                        Renter's Signature
                    </div>

                    <div>
                        <u>______________________</u><br/>
                        Date
                    </div>
                </Typography>
            </Paper>

            <Box mt={2}>
                <Button variant="contained" color="primary" disabled={!isAcceptEnabled}>
                    Accept
                </Button>
            </Box>
        </Box>
    );
};

export default AgreementComponent;
