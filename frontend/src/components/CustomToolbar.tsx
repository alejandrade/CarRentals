import React, { ReactNode } from "react";
import Toolbar from "@mui/material/Toolbar";
import { styled } from "@mui/material/styles";

const CustomToolbarRoot = styled(Toolbar)(({ theme }) => ({
    display: "flex",
    alignItems: "center",
    justifyContent: "space-between",
    padding: theme.spacing(1),
    backgroundColor: theme.palette.background.default,
}));

interface CustomToolbarProps {
    children: ReactNode;
}

const CustomToolbar: React.FC<CustomToolbarProps> = ({ children }) => {
    return <CustomToolbarRoot>{children}</CustomToolbarRoot>;
};

export default CustomToolbar;
