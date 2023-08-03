import React, { useEffect, useState } from 'react';
import {
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableRow,
    Paper,
    Checkbox
} from '@mui/material';
import serviceLocationService from "../../services/service_location/ServiceLocationService";
import { ServiceLocationDto } from "../../services/service_location/ServiceLocationService.types";
import Typography from "@mui/material/Typography";
import CustomToolbar from "../../components/CustomToolbar";
import EditUserModal from "./EditUserModal";
import EditLocationModal from "./EditLocationModal";
import {useErrorModal} from "../../contexts/ErrorModalContext";

const LocationsTable: React.FC = () => {
    const [locations, setLocations] = useState<ServiceLocationDto[]>([]);
    const [selectedLocationId, setSelectedLocationId] = useState<string | undefined>(undefined); // to store the selected location name
    const { showError, handleAPIError } = useErrorModal();

    async function fetchLocations() {
        try {
            const locationDetails = await serviceLocationService.getAllServiceLocation().catch(handleAPIError) || [];
            setLocations(locationDetails);
        } catch (error) {
            console.error("Error fetching service locations:", error);
        }
    }

    useEffect(() => {
        fetchLocations();
    }, []);

    const handleRowSelection = (locationName: string) => {
        if (selectedLocationId === locationName) {
            setSelectedLocationId(undefined);
        } else {
            setSelectedLocationId(locationName);
        }
    }


    return (
        <Paper>
            <Typography variant={"h4"}>Locations</Typography>
            <CustomToolbar>
                <EditLocationModal onClose={fetchLocations} locationId={selectedLocationId}/>
            </CustomToolbar>

            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell>Select</TableCell>
                        <TableCell>Name</TableCell>
                        <TableCell>City</TableCell>
                        <TableCell>State</TableCell>
                        <TableCell>Postal Code</TableCell>
                        <TableCell>Country</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {locations.map((location) => (
                        <TableRow key={location.name} onClick={() => handleRowSelection(location.name)}>
                            <TableCell>
                                <Checkbox
                                    checked={selectedLocationId === location.id}
                                    onChange={() => handleRowSelection(location.id)}
                                />
                            </TableCell>
                            <TableCell>{location.name}</TableCell>
                            <TableCell>{location.city}</TableCell>
                            <TableCell>{location.state}</TableCell>
                            <TableCell>{location.postalCode}</TableCell>
                            <TableCell>{location.country}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </Paper>
    );
}

export default LocationsTable;
