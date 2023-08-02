import React from 'react';
import { Select, MenuItem, FormControl, InputLabel, SelectChangeEvent, FormHelperText } from '@mui/material';

interface Props {
    onChange?: (event: SelectChangeEvent<string[]>) => void;
    value: string[];
    error?: boolean;
    helperText?: string;
}

const ROLES = [
    "ROLE_ANON",
    "ROLE_USER",
    "ROLE_CLERK",
    "ROLE_ADMIN",
    "ROLE_STAFF"
];

const UserRoleDropdown: React.FC<Props> = ({ onChange, value, error, helperText }) => {
    return (
        <FormControl fullWidth variant="outlined" error={error}>
            <InputLabel>Roles</InputLabel>
            <Select
                multiple
                value={value}
                onChange={onChange}
                label="Roles"
                renderValue={(selected) => (selected as string[]).join(', ')}
            >
                {ROLES.map((role) => (
                    <MenuItem key={role} value={role}>
                        {role}
                    </MenuItem>
                ))}
            </Select>
            {helperText && <FormHelperText>{helperText}</FormHelperText>}
        </FormControl>
    );
}

export default UserRoleDropdown;
