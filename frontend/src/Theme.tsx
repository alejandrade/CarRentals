import { createTheme } from '@mui/material/styles';

export const getThemeByName = (themeName: string) => {
    return createTheme({
        palette: {
            mode: themeName === "dark" ? "dark" : "light",
        },
        components: {
            MuiCard: {
                styleOverrides: {
                    root: {
                        margin: '20px',
                    },
                },
            },
        },
    });
};
