import React from 'react';
import Alert from '@mui/material/Alert';
import IconButton from '@mui/material/IconButton';
import Button from '@mui/material/Button';
import CloseIcon from '@mui/icons-material/Close';

interface WarningAlertProps {
    warningText: string;
    onButtonClick: () => void;
}

const WarningAlert: React.FC<WarningAlertProps> = ({ warningText, onButtonClick }) => {
    const [open, setOpen] = React.useState(true);

    const handleClose = () => {
        setOpen(false);
    };

    return (
        open && (
            <Alert
                severity="warning"
                action={
                    <IconButton
                        aria-label="close"
                        color="inherit"
                        size="small"
                        onClick={handleClose}
                    >
                        <CloseIcon fontSize="inherit" />
                    </IconButton>
                }
            >
                {warningText}
                <Button
                    variant="outlined"
                    color="inherit"
                    size="small"
                    style={{ marginLeft: '10px' }}
                    onClick={onButtonClick}
                >
                    Pay
                </Button>
            </Alert>
        )
    );
};

export default WarningAlert;
