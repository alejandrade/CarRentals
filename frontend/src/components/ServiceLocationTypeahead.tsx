import React, { useEffect, useState } from "react";
import Autocomplete from "@mui/material/Autocomplete";
import TextField from "@mui/material/TextField";
import { ServiceLocationDto } from "../services/service_location/ServiceLocationService.types";
import serviceLocationService from "../services/service_location/ServiceLocationService";

interface Props {
    onChange?: (selectedServiceLocations: ServiceLocationDto | ServiceLocationDto[] | null) => void;
    value?: Partial<ServiceLocationDto> | Partial<ServiceLocationDto>[] | null;
    error?: boolean;
    helperText?: string;
    multiple?: boolean;
}

const ServiceLocationTypeahead: React.FC<Props> = ({
                                                       value, onChange, error, helperText, multiple = false
                                                   }) => {
    const [options, setOptions] = useState<ServiceLocationDto[]>([]);
    // Set the initial value for defaultValue based on multiple prop
    const [selectedValue, setSelectedValue] = useState<ServiceLocationDto | ServiceLocationDto[] | null>(multiple ? [] : null);

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

                let serviceLocationDtos = response.filter(loc => ids.includes(loc.id));
                if (multiple) {
                    setSelectedValue(serviceLocationDtos);
                } else {
                    setSelectedValue(serviceLocationDtos[0]);
                }

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
            multiple={multiple}
            getOptionLabel={(option: ServiceLocationDto) => option.name}
            value={selectedValue}
            onChange={(event, newValue) => {
                setSelectedValue(newValue);
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
