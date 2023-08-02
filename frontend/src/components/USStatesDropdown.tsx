import React from 'react';
import {Select, MenuItem, FormControl, InputLabel, SelectChangeEvent, FormHelperText} from '@mui/material';
interface Props {
    onChange?: (event: SelectChangeEvent) => void;
    value: string;
    error?: boolean;
    helperText?: string;
}

const US_STATES = [
    { State: "Alabama", abrv: "AL" },
    { State: "Alaska", abrv: "AK" },
    { State: "Arizona", abrv: "AZ" },
    { State: "Arkansas", abrv: "AR" },
    { State: "California", abrv: "CA" },
    { State: "Colorado", abrv: "CO" },
    { State: "Connecticut", abrv: "CT" },
    { State: "Delaware", abrv: "DE" },
    { State: "Florida", abrv: "FL" },
    { State: "Georgia", abrv: "GA" },
    { State: "Hawaii", abrv: "HI" },
    { State: "Idaho", abrv: "ID" },
    { State: "Illinois", abrv: "IL" },
    { State: "Indiana", abrv: "IN" },
    { State: "Iowa", abrv: "IA" },
    { State: "Kansas", abrv: "KS" },
    { State: "Kentucky", abrv: "KY" },
    { State: "Louisiana", abrv: "LA" },
    { State: "Maine", abrv: "ME" },
    { State: "Maryland", abrv: "MD" },
    { State: "Massachusetts", abrv: "MA" },
    { State: "Michigan", abrv: "MI" },
    { State: "Minnesota", abrv: "MN" },
    { State: "Mississippi", abrv: "MS" },
    { State: "Missouri", abrv: "MO" },
    { State: "Montana", abrv: "MT" },
    { State: "Nebraska", abrv: "NE" },
    { State: "Nevada", abrv: "NV" },
    { State: "New Hampshire", abrv: "NH" },
    { State: "New Jersey", abrv: "NJ" },
    { State: "New Mexico", abrv: "NM" },
    { State: "New York", abrv: "NY" },
    { State: "North Carolina", abrv: "NC" },
    { State: "North Dakota", abrv: "ND" },
    { State: "Ohio", abrv: "OH" },
    { State: "Oklahoma", abrv: "OK" },
    { State: "Oregon", abrv: "OR" },
    { State: "Pennsylvania", abrv: "PA" },
    { State: "Rhode Island", abrv: "RI" },
    { State: "South Carolina", abrv: "SC" },
    { State: "South Dakota", abrv: "SD" },
    { State: "Tennessee", abrv: "TN" },
    { State: "Texas", abrv: "TX" },
    { State: "Utah", abrv: "UT" },
    { State: "Vermont", abrv: "VT" },
    { State: "Virginia", abrv: "VA" },
    { State: "Washington", abrv: "WA" },
    { State: "West Virginia", abrv: "WV" },
    { State: "Wisconsin", abrv: "WI" },
    { State: "Wyoming", abrv: "WY" },
];

const USStatesDropdown: React.FC<Props> = ({ onChange, value, error, helperText }) => {
    return (
        <FormControl fullWidth variant="outlined" error={error}>
            <InputLabel>State</InputLabel>
            <Select
                value={value}
                onChange={onChange}
                label="State"
            >
                {US_STATES.map((state) => (
                    <MenuItem key={state.State} value={state.abrv}>
                        {state.State}
                    </MenuItem>
                ))}
            </Select>
            {helperText && <FormHelperText>{helperText}</FormHelperText>}
        </FormControl>
    );
}

export default USStatesDropdown;