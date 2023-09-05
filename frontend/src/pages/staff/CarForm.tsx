import React, { useState, useEffect, useContext } from 'react';
import { TextField, Button, Paper, Typography, Box, Stack } from '@mui/material';
import { CarCreationDto } from '../../services/car/carService.types';
import CarService from '../../services/car/carService';
import VinTextField from '../../components/VinTextField';
import ColorTextField from '../../components/ColorTextField';
import YearTextField from "../../components/YearTextField";
import MakeAutocomplete from "../../components/MakeAutocomplete";
import StatusAutocomplete from "../../components/StatusAutocomplete";
import { ErrorModalContext } from "../../contexts/ErrorModalContext";
import ServiceLocationTypeahead from "../../components/ServiceLocationTypeahead";
import { ServiceLocationDto } from "../../services/service_location/ServiceLocationService.types";

interface CarFormProps {
    id?: string;
    refreshTable: () => void;
    onSave: () => void;
}

const CarForm: React.FC<CarFormProps> = ({ id, refreshTable, onSave }) => {
    const errorModalContext = useContext(ErrorModalContext);
    const [loading, setLoading] = useState<boolean>(!!id);
    const [formData, setFormData] = useState<CarCreationDto>({
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
        serviceLocationId: ''
    });

    useEffect(() => {
        const loadCarData = async () => {
            try {
                setLoading(true);
                const car = await CarService.getCarById(id!);
                if (car) {
                    setFormData(car);
                }
                setLoading(false);
            } catch (error) {
                errorModalContext.handleAPIError(error);
                setLoading(false);
            }
        };

        if (id) {
            loadCarData();
        }
    }, [id, errorModalContext]);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setLoading(true);
        const response = await CarService.createOrUpdateCar(formData).catch(errorModalContext.handleAPIError);
        if (response && response.id) {
            refreshTable();
            setLoading(false);
            onSave();
            console.log('Operation successful:', response);
        } else {
            console.error('Error in operation:', response);
        }
    };

    const handleChange = (name: keyof CarCreationDto, value: string | number | undefined) => {
        setFormData(prev => ({ ...prev, [name]: value }));
    };

    if (loading) {
        return <p>Loading...</p>;
    }

    const title = id ? 'Edit Car' : 'Create Car';

    return (
        <Box component={Paper} sx={{ p: 2, width: '500px', m: 'auto' }}>
            <Typography variant="h6">{title}</Typography>
            <form onSubmit={handleSubmit}>
                <Stack spacing={2} direction="column">
                    <MakeAutocomplete value={formData.make} onChange={(val) => handleChange("make", val)} />
                    <TextField
                        name="model"
                        label="Model"
                        value={formData.model}
                        onChange={(e) => handleChange('model', e.target.value)}
                        fullWidth
                    />
                    <YearTextField value={formData.year} onChange={(val) => handleChange('year', val)} />
                    <VinTextField value={formData.vin} onChange={(value) => handleChange('vin', value)} />
                    <ColorTextField value={formData.color} onChange={(value) => handleChange('color', value)} />
                    <TextField
                        name="mileage"
                        label="Mileage"
                        value={formData.mileage || ''}
                        onChange={(e) => handleChange('mileage', parseInt(e.target.value, 10))}
                        fullWidth
                        type="number"
                    />
                    <TextField
                        name="price"
                        label="Price"
                        value={formData.price || ''}
                        onChange={(e) => handleChange('price', parseFloat(e.target.value))}
                        fullWidth
                        type="number"
                    />
                    <TextField
                        name="rentPrice"
                        label="Rent Price"
                        value={formData.rentPrice || ''}
                        onChange={(e) => handleChange('rentPrice', parseFloat(e.target.value))}
                        fullWidth
                        type="number"
                    />
                    <TextField
                        name="licensePlate"
                        label="License Plate"
                        value={formData.licensePlate}
                        onChange={(e) => handleChange('licensePlate', e.target.value)}
                        fullWidth
                    />
                    <ServiceLocationTypeahead value={{id: formData.serviceLocationId}}
                                              onChange={(val) => handleChange('serviceLocationId', (val as ServiceLocationDto).id)} />
                    <StatusAutocomplete value={formData.status} onChange={(val) => handleChange("status", val)} />
                    <Button variant="contained" color="primary" type="submit">
                        Submit
                    </Button>
                </Stack>
            </form>
        </Box>
    );
};

export default CarForm;
