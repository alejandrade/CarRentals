import React from 'react';
import {Select, MenuItem, FormControl, InputLabel, SelectChangeEvent, FormHelperText} from '@mui/material';
interface Props {
    onChange?: (event: SelectChangeEvent) => void;
    value: string;
    error?: boolean;
    helperText?: string;
}

const US_STATES = [
    "Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut",
    "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa",
    "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan",
    "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire",
    "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio",
    "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota",
    "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia",
    "Wisconsin", "Wyoming"
];

const USStatesDropdown: React.FC<Props> = ({ onChange, value, error, helperText }) => {
    return (
        <FormControl fullWidth variant="outlined" error={error} >
            <InputLabel>State</InputLabel>
            <Select value={value} onChange={onChange} label="State">
                {US_STATES.map((state) => (
                    <MenuItem key={state} value={state}>
                        {state}
                    </MenuItem>
                ))}
            </Select>
            {helperText && <FormHelperText>{helperText}</FormHelperText>}
        </FormControl>
    );
}

export default USStatesDropdown;
