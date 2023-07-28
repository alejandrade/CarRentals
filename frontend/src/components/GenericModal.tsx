import React from 'react';
import {Dialog, DialogContent, DialogTitle, IconButton} from '@mui/material';
import {Close} from "@mui/icons-material";

interface GenericModalProps {
    open: boolean;
    onClose?: () => void;
    children: React.ReactNode;
}

const GenericModal: React.FC<GenericModalProps> = ({ open, onClose, children }) => {
    return (
        <Dialog open={open} onClose={onClose}>
            <DialogTitle>
                {/* Close button */}
                <IconButton
                    edge="end"
                    color="inherit"
                    onClick={onClose}
                    aria-label="close"
                    style={{ position: 'absolute', right: 0, top: 0 }}
                >
                    <Close />
                </IconButton>
            </DialogTitle>
            <DialogContent>
                {children}
            </DialogContent>
        </Dialog>
    );
};


export default GenericModal;
