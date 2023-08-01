import React, {createContext, ReactNode, useContext, useState} from 'react';
import Modal from '@mui/material/Modal';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import Box from '@mui/material/Box';
import {APIError} from "../util/FetchFunctions";

type ErrorDetails = {
    timestamp: string;
    status: number;
    error: string;
    message: string;
    path: string;
    bindingErrors: string[];
};

type ErrorModalContextType = {
    showError: (details: ErrorDetails) => void;
    hideError: () => void;
    handleAPIError: (error: any) => void;  // <-- Adding the new function here

};
const mockFunction = () => {
    throw new Error("Function not implemented. Ensure context is used within a provider.");
}
export const ErrorModalContext = createContext<ErrorModalContextType >(
    {
        showError: mockFunction,
        hideError: mockFunction,
        handleAPIError: mockFunction
    }
);

type MyComponentProps = {
    children: ReactNode;
};
export const ErrorModalProvider: React.FC<MyComponentProps> = ({ children }) => {
    const [isVisible, setIsVisible] = useState(false);
    const [errorDetails, setErrorDetails] = useState<ErrorDetails | null>(null);

    const showError = (details: ErrorDetails) => {
        setErrorDetails(details);
        setIsVisible(true);
    };

    const hideError = () => {
        setIsVisible(false);
        setErrorDetails(null);
    };

    const handleAPIError = (error: any) => {
        if (error instanceof APIError) {
            showError(error.details);
        } else {
            // Handle other errors as you see fit
            console.error("An unexpected error occurred:", error);
        }
    };

    return (
        <ErrorModalContext.Provider value={{ showError, hideError, handleAPIError }}>
            {children}
            {isVisible && errorDetails && (
                <Modal
                    open={isVisible}
                    onClose={hideError}
                >
                    <Box sx={{ width: 400, padding: 3, position: 'absolute', top: '50%', left: '50%', transform: 'translate(-50%, -50%)', bgcolor: 'background.paper' }}>
                        <Typography variant="h6" component="h2">
                            Error {errorDetails.status}: {errorDetails.error}
                        </Typography>
                        <Typography>Message: {errorDetails.message}</Typography>
                        <Typography>Path: {errorDetails.path}</Typography>
                        <ul>
                            {errorDetails?.bindingErrors?.map(error => <li key={error}>{error}</li>)}
                        </ul>
                        <Button variant="contained" color="primary" onClick={hideError}>Close</Button>
                    </Box>
                </Modal>
            )}

        </ErrorModalContext.Provider>
    );
};

export const useErrorModal = () => {
    const context = useContext(ErrorModalContext);
    if (!context) {
        throw new Error("useErrorModal must be used within an ErrorModalProvider");
    }
    return context;
};
