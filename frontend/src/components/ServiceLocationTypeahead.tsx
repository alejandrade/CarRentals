import React, { useEffect, useState } from "react";
import Autocomplete from "@mui/material/Autocomplete";
import TextField from "@mui/material/TextField";
import { ServiceLocationDto } from "../services/service_location/ServiceLocationService.types";
import serviceLocationService from "../services/service_location/ServiceLocationService";

interface Props {
    onChange?: (selectedServiceLocation: ServiceLocationDto | undefined) => void;
    value?: Partial<ServiceLocationDto> | undefined;
    error?: boolean;
    helperText?: string;
}

// Define a view object for display
interface ViewObject {
    id: string;
    label: string;
}

const placeholderOption: ViewObject = {
    id: "",
    label: "Select Location",
};

const ServiceLocationTypeahead: React.FC<Props> = ({
                                                       value, onChange, error, helperText
                                                   }) => {
    const [options, setOptions] = useState<ViewObject[]>([]);
    const [realOptions, setRealOptions] = useState<ServiceLocationDto[]>([]);

    const [selectedViewValue, setSelectedViewValue] = useState<ViewObject>(
        placeholderOption
    );

    useEffect(() => {
        const fetchServiceLocations = async () => {
            try {
                const response = await serviceLocationService.getAllServiceLocation();
                // Map ServiceLocationDto to ViewObject for display
                const viewObjects = response.map((location) => ({
                    id: location.id,
                    label: location.name,
                }));
                setOptions(viewObjects);
                setRealOptions(response);
                // Map the selected value back to ServiceLocationDto
                const serviceLocationDto = viewObjects.find((loc) => loc.id === value?.id);
                if (serviceLocationDto)
                    setSelectedViewValue(serviceLocationDto)
            } catch (error) {
                console.error("Failed to fetch service locations:", error);
            }
        };

        fetchServiceLocations();
    }, [value]);

    return (
        <Autocomplete
            id="service-locations"
            options={options}
            getOptionLabel={(option: ViewObject) => option.label}
            value={selectedViewValue}
            onChange={(event, newValue) => {
                // Map the selected choice back to ServiceLocationDto
                const selectedDto: ServiceLocationDto | undefined = newValue?.id !== "" ? realOptions.find(x => x.id === selectedDto?.id) : undefined;
                setSelectedViewValue(newValue || placeholderOption);
                if (onChange) {
                    onChange(selectedDto);
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
