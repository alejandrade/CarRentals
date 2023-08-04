import React, { useState, useEffect } from 'react';
import {
    Paper,
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableRow,
    TableContainer,
    TablePagination,
    Checkbox, Typography,
} from '@mui/material';
import { CarResponseDto, PaginatedCarResponse } from "../../services/car/carService.types";
import CarService from "../../services/car/carService";

interface CarTableState {
    cars: CarResponseDto[];
    selected: string | null; // Use null to represent no selection or the ID of the selected row
    page: number;
    rowsPerPage: number;
    total: number;
}

interface CarProps {
    onSelected: (carId: string|null) => void;
    refresh: boolean; // Prop to trigger a table refresh
}

const CarTable: React.FC<CarProps> = ({ onSelected, refresh }) => {
    const [state, setState] = useState<CarTableState>({
        cars: [],
        selected: null, // Initialize with null to represent no selection initially
        page: 0,
        rowsPerPage: 10,
        total: 0,
    });

    const { cars, selected, page, rowsPerPage, total } = state;

    useEffect(() => {
        loadCars();
    }, [page, rowsPerPage, refresh]); // Include 'refresh' in the dependency array

    useEffect(() => {
        // Additional logic to reset selected when 'refresh' prop changes
        if (refresh) {
            setState((prevState) => ({
                ...prevState,
                selected: null,
            }));
        }
    }, [refresh]);

    const loadCars = async () => {
        const data: PaginatedCarResponse = await CarService.fetchAllCars(page, rowsPerPage);
        setState((prevState) => ({
            ...prevState,
            cars: data.content,
            total: data.totalElements,
        }));
    };

    const handleChangePage = (event: unknown, newPage: number) => {
        setState((prevState) => ({
            ...prevState,
            page: newPage,
        }));
    };

    const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>) => {
        setState((prevState) => ({
            ...prevState,
            rowsPerPage: parseInt(event.target.value, 10),
            page: 0,
        }));
    };

    // Function to handle row selection
    const handleRowSelect = (id: string) => () => {
        if (id === state.selected) {
            onSelected(null);
            setState((prevState) => ({
                ...prevState,
                selected: null,
            }));
        } else {

            onSelected(id);
            setState((prevState) => ({
                ...prevState,
                selected: id,
            }));
        }
    };

    return (
        <Paper>

            <TableContainer>
                <Table>
                    <TableHead>
                        <TableRow>
                            {/* Add a column for selection */}
                            <TableCell>Select</TableCell>
                            <TableCell>Make</TableCell>
                            <TableCell>Model</TableCell>
                            <TableCell>Year</TableCell>
                            <TableCell>VIN</TableCell>
                            <TableCell>Color</TableCell>
                            <TableCell>Created At</TableCell>
                            <TableCell>Updated At</TableCell>
                            {/* Add more headers as needed */}
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {cars.map((car) => (
                            <TableRow key={car.id}>
                                {/* Add a cell with Checkbox for each row */}
                                <TableCell>
                                    <Checkbox
                                        checked={selected === car.id}
                                        onChange={handleRowSelect(car.id)}
                                    />
                                </TableCell>
                                <TableCell>{car.make}</TableCell>
                                <TableCell>{car.model}</TableCell>
                                <TableCell>{car.year}</TableCell>
                                <TableCell>{car.vin}</TableCell>
                                <TableCell>{car.color}</TableCell>
                                <TableCell>{car.createdAt?.toLocaleString()}</TableCell>
                                <TableCell>{car.updatedAt?.toLocaleString()}</TableCell>
                                {/* Add more cells as needed */}
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
            <TablePagination
                rowsPerPageOptions={[5, 10, 25]}
                component="div"
                count={total}
                rowsPerPage={rowsPerPage}
                page={page}
                onPageChange={handleChangePage}
                onRowsPerPageChange={handleChangeRowsPerPage}
            />
        </Paper>
    );
};

export default CarTable;
