import * as React from 'react';
import { TextField, Button, Paper, Typography } from '@mui/material';
import {CarCreationDto} from "../../services/car/carService.types";
import CarService from "../../services/car/carService";

interface CarFormProps {
    id?: string;
}

interface CarFormState extends CarCreationDto {}

class CarForm extends React.Component<CarFormProps, CarFormState> {
    constructor(props: CarFormProps) {
        super(props);
        this.state = {
            make: '',
            model: '',
            year: undefined,
            vin: '',
            color: '',
            mileage: undefined,
            price: undefined,
            rentPrice: undefined,
            licensePlate: '',
            status: '',
            // ... other initial state properties
        };
    }

    componentDidMount() {
        if (this.props.id) {
            this.loadCarData();
        }
    }

    async loadCarData() {
        // Assuming CarService has a method to fetch individual car details by ID
        const car: CarCreationDto = await CarService.getCarById(this.props.id!);
        this.setState(car);
    }

    handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        const response = await CarService.createOrUpdateCar(this.state);
        if (response.id) {
            console.log("Operation successful:", response);
        } else {
            console.error("Error in operation:", response);
        }
    };

    handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        this.setState({
            [name]: value
        } as Pick<CarFormState, keyof CarFormState>);
    };

    render() {
        const title = this.props.id ? 'Edit Car' : 'Create Car';

        return (
            <Paper style={{ padding: '1rem' }}>
                <Typography variant="h6">{title}</Typography>
                <form onSubmit={this.handleSubmit}>
                    <TextField name="make" label="Make" value={this.state.make} onChange={this.handleChange} fullWidth margin="normal" />
                    <TextField name="model" label="Model" value={this.state.model} onChange={this.handleChange} fullWidth margin="normal" />
                    <TextField name="year" label="Year" value={this.state.year} onChange={this.handleChange} fullWidth margin="normal" type="number" />
                    <TextField name="vin" label="VIN" value={this.state.vin} onChange={this.handleChange} fullWidth margin="normal" />
                    <TextField name="color" label="Color" value={this.state.color} onChange={this.handleChange} fullWidth margin="normal" />
                    <TextField name="mileage" label="Mileage" value={this.state.mileage} onChange={this.handleChange} fullWidth margin="normal" type="number" />
                    <TextField name="price" label="Price" value={this.state.price} onChange={this.handleChange} fullWidth margin="normal" type="number" />
                    <TextField name="rentPrice" label="Rent Price" value={this.state.rentPrice} onChange={this.handleChange} fullWidth margin="normal" type="number" />
                    <TextField name="licensePlate" label="License Plate" value={this.state.licensePlate} onChange={this.handleChange} fullWidth margin="normal" />
                    <TextField name="status" label="Status" value={this.state.status} onChange={this.handleChange} fullWidth margin="normal" />

                    {/* Add any other fields as needed */}

                    <Button variant="contained" color="primary" type="submit" style={{ marginTop: '1rem' }}>
                        Submit
                    </Button>
                </form>
            </Paper>
        );
    }
}

export default CarForm;
