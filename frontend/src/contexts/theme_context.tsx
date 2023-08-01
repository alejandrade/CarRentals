import React, { createContext, useContext, useState, ReactNode } from "react";
import {getThemeByName} from "../Theme";
import { ThemeProvider as MuiThemeProvider } from '@mui/material/styles';

interface ThemeContextType {
    theme: string;
    toggleTheme: () => void;
}

const Theme_context = createContext<ThemeContextType | undefined>(undefined);

interface ThemeProviderProps {
    children: ReactNode;
}

export const ThemeProvider: React.FC<ThemeProviderProps> = ({ children }) => {
    const [theme, setTheme] = useState<string>("light");
    const muiTheme = getThemeByName(theme);  // get MUI theme based on your theme

    const toggleTheme = () => {
        setTheme((prevTheme) => (prevTheme === "light" ? "dark" : "light"));
    };

    return (
        <Theme_context.Provider value={{ theme, toggleTheme }}>
            <MuiThemeProvider theme={muiTheme}>
                {children}
            </MuiThemeProvider>
        </Theme_context.Provider>
    );
};

export const useTheme = (): ThemeContextType => {
    const context = useContext(Theme_context);
    if (!context) {
        throw new Error("useTheme must be used within a ThemeProvider");
    }
    return context;
};
