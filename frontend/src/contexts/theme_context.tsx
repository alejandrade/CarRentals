import React, { createContext, useContext, useState, ReactNode } from "react";

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

    const toggleTheme = () => {
        setTheme((prevTheme) => (prevTheme === "light" ? "dark" : "light"));
    };

    return (
        <Theme_context.Provider value={{ theme, toggleTheme }}>
            {children}
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
