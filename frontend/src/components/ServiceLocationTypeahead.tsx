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

const ServiceLocationTypeahead: React.FC<Props> = ({
                                                       value, onChange, error, helperText
                                                   }) => {
    const [options, setOptions] = useState<ServiceLocationDto[]>([]);
    // Set the initial value for defaultValue based on multiple prop
    const [selectedValue, setSelectedValue] = useState<ServiceLocationDto | undefined>();

    useEffect(() => {
        const fetchServiceLocations = async () => {
            try {
                const response = await serviceLocationService.getAllServiceLocation();
                setOptions(response);
                let ids: string[] = [];

                if (Array.isArray(value)) {
                    // @ts-ignore
                    ids = value.map(x => x.id).filter(x => x !== undefined);
                } else {
                    if (value?.id) {
                        ids = [value.id];
                    }
                }

                if (ids.length === 0) {
                    return;
                }

                let serviceLocationDto = response.find(loc => ids.includes(loc.id));
                setSelectedValue(serviceLocationDto);


            } catch (error) {
                console.error("Failed to fetch service locations:", error);
            }
        };

        fetchServiceLocations();
    }, [value]);  // adding value as a dependency so that the effect re-runs when value changes

    return (
        <Autocomplete
            id="service-locations"
            options={options}
            getOptionLabel={(option: ServiceLocationDto) => option.name}
            value={selectedValue}
            onChange={(event, newValue) => {
                setSelectedValue(newValue || undefined);
                if (onChange) {
                    onChange(newValue || undefined);
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
