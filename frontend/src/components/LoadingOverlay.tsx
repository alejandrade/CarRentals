import React from 'react';
import { Backdrop, CircularProgress } from '@mui/material';



const LoadingOverlay: React.FC = () => {
    return (
        <Backdrop
            sx={{
                zIndex: (theme) => theme.zIndex.drawer + 1,
                color: 'white'
            }}
            open={true}
        >
            <CircularProgress color="inherit" />
        </Backdrop>
    );
}

export default LoadingOverlay;
