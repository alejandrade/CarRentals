import React, { useEffect, useState } from "react";
import Autocomplete from "@mui/material/Autocomplete";
import TextField from "@mui/material/TextField";
import ListItem from "@mui/material/ListItem";
import ListItemText from "@mui/material/ListItemText";
import CircularProgress from "@mui/material/CircularProgress";
import { ServiceLocationDto } from "../services/service_location/ServiceLocationService.types";
import useDebounce from "../util/useDebounce";
import serviceLocationService from "../services/service_location/ServiceLocationService";
import { List } from "@mui/material";

interface Props {
    onChange?: (selectedServiceLocations: ServiceLocationDto[]) => void;
    value: ServiceLocationDto[];
    error?: boolean;
    helperText?: string;
}

type Param = {
    state: string;
} & Props;

const ServiceLocationTypeahead: React.FC<Param> = ({ state, value, onChange, error, helperText }) => {
    const [loading, setLoading] = useState(false);
    const [options, setOptions] = useState<ServiceLocationDto[]>(value || []);
    const [hasMore, setHasMore] = useState(true);
    const [page, setPage] = useState(0);
    const [searchTerm, setSearchTerm] = useState("");

    const debouncedSearchTerm = useDebounce(searchTerm, 500);

    useEffect(() => {
        let active = true;

        const loadMoreOptions = async () => {
            if (!loading && hasMore && active && debouncedSearchTerm) {
                setLoading(true);
                try {
                    const response = await serviceLocationService.getAllServiceLocationName(
                        page,
                        10,
                        state,
                        debouncedSearchTerm
                    );
                    setLoading(false);
                    setHasMore(!response.last);
                    setPage(response.number + 1);
                    setOptions(prevOptions => [...prevOptions, ...response.content]);
                } catch (error) {
                    console.error("Failed to fetch service locations:", error);
                }
            }
        };

        loadMoreOptions();

        return () => {
            active = false;
        };
    }, [loading, hasMore, page, debouncedSearchTerm, options, state]);

    return (
        <Autocomplete
            id="service-locations"
            options={options}
            multiple={true}
            getOptionLabel={(option) => option.name}
            value={value}
            onChange={(event, newValue) => {
                if (onChange) {
                    onChange(newValue || []);
                }
            }}
            onInputChange={(event, inputValue) => {
                setSearchTerm(inputValue);
            }}
            renderInput={(params) => (
                <TextField
                    {...params}
                    label="Service Locations"
                    variant="outlined"
                    error={error}
                    helperText={helperText}
                    InputProps={{
                        ...params.InputProps,
                        endAdornment: (
                            <>
                                {loading ? (
                                    <CircularProgress color="inherit" size={20} />
                                ) : null}
                                {params.InputProps.endAdornment}
                            </>
                        ),
                    }}
                />
            )}
            PopperComponent={({ children }) => (
                <List sx={{ pt: 0 }} dense>
                    {children as React.ReactElement}
                    {loading && (
                        <ListItem>
                            <ListItemText>
                                <CircularProgress color="inherit" size={20} />
                            </ListItemText>
                        </ListItem>
                    )}
                </List>
            )}
        />
    );
};

export default ServiceLocationTypeahead;
