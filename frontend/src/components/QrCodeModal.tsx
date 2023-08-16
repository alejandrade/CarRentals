import React from 'react';
import Modal from '@mui/material/Modal';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import QRCode from 'qrcode.react';

// Styles for the modal
const modalStyle = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 400,
    bgcolor: 'background.paper',
    boxShadow: 24,
    p: 4,
};

interface QRCodeModalProps {
    open: boolean;
    handleClose: () => void;
    value: string;
}

const QRCodeModal: React.FC<QRCodeModalProps> = ({ open, handleClose, value }) => {
      return (
        <Modal
            open={open}
            onClose={handleClose}
            aria-labelledby="qr-code-title"
            aria-describedby="qr-code-description"
        >
            <Box sx={modalStyle}>
                <Typography id="qr-code-title" variant="h6" component="h2">
                    Scan to Pay
                </Typography>
                <QRCode value={value} size={320} />
                <Typography id="qr-code-description" mt={2}>
                    Ask Renter to scan the QR code to proceed with the payment.
                </Typography>
            </Box>
        </Modal>
    );
};

export default QRCodeModal;
