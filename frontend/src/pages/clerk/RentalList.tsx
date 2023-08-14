import React, { useState, useEffect } from 'react';
import {
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Paper,
    Button,
    Card,
    CardContent,
    Typography,
    Checkbox, // Import Checkbox
} from '@mui/material';
import { Paginated } from '../../services/user/UserService.types';
import { RentalDto } from '../../services/rentals/rentalService.types';
import rentalService from '../../services/rentals/RentalService';

interface PaginatedTableProps {
    title: string;
    status: string;
    onSelect: (selectedRow: string | null) => void
    reloadData: boolean
}

function PaginatedTable({ title, status, onSelect, reloadData }: PaginatedTableProps) {
    const [rentals, setRentals] = useState<RentalDto[]>([]);
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const [selectedRowId, setSelectedRowId] = useState<string | null>(null); // Track selected row ID

    useEffect(() => {
        loadRentalsByStatus(status, page);
    }, [status, page, reloadData]);

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

    const handleRowSelect = (id: string) => {
        setSelectedRowId(selectedRowId === id ? null : id); // Toggle selection
        onSelect(selectedRowId === id ? null : id);
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
                                <TableCell>Select</TableCell>
                                <TableCell>Renter Phone Number</TableCell>
                                <TableCell>Status</TableCell>
                                <TableCell>Initial Odometer Reading</TableCell>
                                <TableCell>Ending Odometer Reading</TableCell>
                                <TableCell>Rental Date</TableCell>
                                <TableCell>Return Date</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {rentals.map((rental) => (
                                <TableRow key={rental.id} selected={selectedRowId === rental.id}>
                                    <TableCell>
                                        <Checkbox
                                            checked={selectedRowId === rental.id}
                                            onChange={() => handleRowSelect(rental.id)}
                                        />
                                    </TableCell>
                                    <TableCell>{rental.renterPhoneNumber}</TableCell>
                                    <TableCell>{rental.status}</TableCell>
                                    <TableCell>{rental.initialOdometerReading}</TableCell>
                                    <TableCell>{rental.endingOdometerReading}</TableCell>
                                    <TableCell>{rental.rentalDatetime.toLocaleString()}</TableCell>
                                    <TableCell>
                                        {rental.returnDatetime ? rental.returnDatetime.toLocaleString() : '-'}
                                    </TableCell>
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
