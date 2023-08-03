import React, { useEffect, useState } from "react";
import Autocomplete from "@mui/material/Autocomplete";
import TextField from "@mui/material/TextField";
import { ServiceLocationDto } from "../services/service_location/ServiceLocationService.types";
import serviceLocationService from "../services/service_location/ServiceLocationService";

interface Props {
    onChange?: (selectedServiceLocations: ServiceLocationDto[]) => void;
    value?: ServiceLocationDto[];
    error?: boolean;
    helperText?: string;
}

const ServiceLocationTypeahead: React.FC<Props> = ({ value, onChange, error, helperText }) => {
    const [options, setOptions] = useState<ServiceLocationDto[]>([]);

    useEffect(() => {
        const fetchServiceLocations = async () => {
            try {
                const response = await serviceLocationService.getAllServiceLocation();
                setOptions(response);
            } catch (error) {
                console.error("Failed to fetch service locations:", error);
            }
        };

        fetchServiceLocations();
    }, []);

    return (
        <Autocomplete
            id="service-locations"
            options={options}
            multiple={true}
            getOptionLabel={(option) => option.name}
            value={value}
            onChange={(event, newValue) => {
                if (onChange) {
                    onChange(newValue);
                }
            }}
            renderInput={(params) => (
                <TextField
                    {...params}
                    label="Service Locations"
                    variant="outlined"
                    error={error}
                    helperText={helperText}
                />
            )}
        />
    );
};

export default ServiceLocationTypeahead;
