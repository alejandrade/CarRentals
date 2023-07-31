import React from 'react';
import "./index.css";
import '@fontsource/roboto/300.css';
import '@fontsource/roboto/400.css';
import '@fontsource/roboto/500.css';
import '@fontsource/roboto/700.css';
import {
    RouterProvider,
} from "react-router-dom";
import router from "./router"
import {ThemeProvider} from "./contexts/theme_context";
import {AuthProvider} from "./contexts/auth_context";
import { createRoot } from 'react-dom/client';
import {ErrorModalProvider} from "./contexts/ErrorModalContext";

const App: React.FC = () => {

     return (
         <React.StrictMode>
            <ThemeProvider>
                <AuthProvider>
                    <ErrorModalProvider>
                        <RouterProvider router={router}/>
                    </ErrorModalProvider>
                </AuthProvider>
            </ThemeProvider>
        </React.StrictMode>
     )
    }
;
const container = document.getElementById('root');
const root = createRoot(container!)
root.render(<App />);