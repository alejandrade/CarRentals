import React, { ChangeEvent } from 'react';
import { TextField, Button, Paper, Typography } from '@mui/material';
import { CarCreationDto } from '../../services/car/carService.types';
import CarService from '../../services/car/carService';
import VinTextField from '../../components/VinTextField';
import ColorTextField from '../../components/ColorTextField';
import YearTextField from "../../components/YearTextField";
import MakeAutocomplete from "../../components/MakeAutocomplete";
import StatusAutocomplete from "../../components/StatusAutocomplete";
import {ErrorModalContext} from "../../contexts/ErrorModalContext";

interface CarFormProps {
    id?: string;
    refreshTable: () => void;
}

interface CarFormState extends CarCreationDto {}

class CarForm extends React.Component<CarFormProps, CarFormState> {

    static contextType = ErrorModalContext;
    context!: React.ContextType<typeof ErrorModalContext>;

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
        const car = await CarService.getCarById(this.props.id!).catch(this.context.handleAPIError);
        if (car)
        this.setState(car);
    }

    handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        const response = await CarService.createOrUpdateCar(this.state).catch(this.context.handleAPIError);
        if (response && response.id) {
            this.props.refreshTable();
            console.log('Operation successful:', response);
        } else {
            console.error('Error in operation:', response);
        }
    };

    handleChange = (name: keyof CarFormState, value: string | number | undefined) => {
        this.setState({ [name]: value } as Pick<CarFormState, keyof CarFormState>);
    };

    render() {
        const title = this.props.id ? 'Edit Car' : 'Create Car';

        return (
            <Paper style={{ padding: '1rem' }}>
                <Typography variant="h6">{title}</Typography>
                <form onSubmit={this.handleSubmit}>
                    <MakeAutocomplete value={this.state.make} onChange={(val) => this.handleChange("make", val)} />
                    <TextField
                        name="model"
                        label="Model"
                        value={this.state.model}
                        onChange={(e) => this.handleChange('model', e.target.value)}
                        fullWidth
                        margin="normal"
                    />
                    <YearTextField value={this.state.year} onChange={(val) => this.handleChange('year', val)}/>
                    <VinTextField value={this.state.vin} onChange={(value) => this.handleChange('vin', value)} />
                    <ColorTextField value={this.state.color} onChange={(value) => this.handleChange('color', value)} />
                    <TextField
                        name="mileage"
                        label="Mileage"
                        value={this.state.mileage || ''}
                        onChange={(e) => this.handleChange('mileage', parseInt(e.target.value, 10))}
                        fullWidth
                        margin="normal"
                        type="number"
                    />
                    <TextField
                        name="price"
                        label="Price"
                        value={this.state.price || ''}
                        onChange={(e) => this.handleChange('price', parseFloat(e.target.value))}
                        fullWidth
                        margin="normal"
                        type="number"
                    />
                    <TextField
                        name="rentPrice"
                        label="Rent Price"
                        value={this.state.rentPrice || ''}
                        onChange={(e) => this.handleChange('rentPrice', parseFloat(e.target.value))}
                        fullWidth
                        margin="normal"
                        type="number"
                    />
                    <TextField
                        name="licensePlate"
                        label="License Plate"
                        value={this.state.licensePlate}
                        onChange={(e) => this.handleChange('licensePlate', e.target.value)}
                        fullWidth
                        margin="normal"
                    />

                    <StatusAutocomplete value={this.state.status} onChange={(val) => this.handleChange("status", val)} />
                    <Button variant="contained" color="primary" type="submit" style={{ marginTop: '1rem' }}>
                        Submit
                    </Button>
                </form>
            </Paper>
        );
    }
}

export default CarForm
