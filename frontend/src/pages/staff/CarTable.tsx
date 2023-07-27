import * as React from 'react';
import { Paper, Table, TableBody, TableCell, TableHead, TableRow, TableContainer, TablePagination } from '@mui/material';
import {CarResponseDto, PaginatedCarResponse} from "../../services/car/carService.types";
import CarService from "../../services/car/carService";

interface CarTableState {
    cars: CarResponseDto[];
    page: number;
    rowsPerPage: number;
    total: number;
}

class CarTable extends React.Component<{}, CarTableState> {
    constructor(props: {}) {
        super(props);
        this.state = {
            cars: [],
            page: 0,
            rowsPerPage: 10,
            total: 0
        };
    }

    componentDidMount() {
        this.loadCars();
    }

    async loadCars() {
        const data: PaginatedCarResponse = await CarService.fetchAllCars(this.state.page, this.state.rowsPerPage);
        this.setState({
            cars: data.content,
            total: data.totalElements
        });
    }

    handleChangePage = (event: unknown, newPage: number) => {
        this.setState({ page: newPage }, () => {
            this.loadCars();
        });
    };

    handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>) => {
        this.setState({
            rowsPerPage: parseInt(event.target.value, 10),
            page: 0
        }, () => {
            this.loadCars();
        });
    };

    render() {
        return (
            <Paper>
                <TableContainer>
                    <Table>
                        <TableHead>
                            <TableRow>
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
                            {this.state.cars.map(car => (
                                <TableRow key={car.id}>
                                    <TableCell>{car.make}</TableCell>
                                    <TableCell>{car.model}</TableCell>
                                    <TableCell>{car.year}</TableCell>
                                    <TableCell>{car.vin}</TableCell>
                                    <TableCell>{car.color}</TableCell>
                                    <TableCell>{car.createdAt.toLocaleString()}</TableCell>
                                    <TableCell>{car.updatedAt.toLocaleString()}</TableCell>
                                    {/* Add more cells as needed */}
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
                <TablePagination
                    rowsPerPageOptions={[5, 10, 25]}
                    component="div"
                    count={this.state.total}
                    rowsPerPage={this.state.rowsPerPage}
                    page={this.state.page}
                    onPageChange={this.handleChangePage}
                    onRowsPerPageChange={this.handleChangeRowsPerPage}
                />
            </Paper>
        );
    }
}

export default CarTable;
