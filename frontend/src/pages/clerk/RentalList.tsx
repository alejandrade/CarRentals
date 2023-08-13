import React, { useState, useEffect } from 'react';
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Button, Card, CardContent, Typography } from '@mui/material';
import {Paginated} from "../../services/user/UserService.types";
import {RentalDto} from "../../services/rentals/rentalService.types";
import rentalService from "../../services/rentals/RentalService";

interface PaginatedTableProps {
    title: string;
    status: string;
}

function PaginatedTable({ title, status }: PaginatedTableProps) {
    const [rentals, setRentals] = useState<RentalDto[]>([]);
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);

    useEffect(() => {
        loadRentalsByStatus(status, page);
    }, [status, page]);

    const loadRentalsByStatus = async (status: string, pageNumber: number) => {
        try {
            const response: Paginated<RentalDto> = await rentalService.getRentals(status);
            if (response.content) {
                setRentals(response.content);
            }
            setTotalPages(response.totalPages);
        } catch (error) {
            console.error('Error fetching rentals:', error);
        }
    };

    const handlePageChange = (newPage: number) => {
        setPage(newPage);
    };

    return (
        <Card>
            <CardContent>
                <Typography variant="h6" gutterBottom>
                    {title}
                </Typography>
                <TableContainer component={Paper}>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell>Renter Phone Number</TableCell>
                                <TableCell>Status</TableCell>
                                <TableCell>Initial Odometer Reading</TableCell>
                                <TableCell>Ending Odometer Reading</TableCell>
                                <TableCell>Rental Date</TableCell>
                                <TableCell>Return Date</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {rentals.map(rental => (
                                <TableRow key={rental.id}>
                                    <TableCell>{rental.renterPhoneNumber}</TableCell>
                                    <TableCell>{rental.status}</TableCell>
                                    <TableCell>{rental.initialOdometerReading}</TableCell>
                                    <TableCell>{rental.endingOdometerReading}</TableCell>
                                    <TableCell>{rental.rentalDatetime.toLocaleString()}</TableCell>
                                    <TableCell>{rental.returnDatetime ? rental.returnDatetime.toLocaleString() : '-'}</TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
                <div style={{ display: 'flex', justifyContent: 'center', marginTop: '1rem' }}>
                    <Button disabled={page === 0} onClick={() => handlePageChange(page - 1)}>
                        Previous
                    </Button>
                    <Button disabled={page === totalPages - 1} onClick={() => handlePageChange(page + 1)}>
                        Next
                    </Button>
                </div>
            </CardContent>
        </Card>
    );
}

export default PaginatedTable;
