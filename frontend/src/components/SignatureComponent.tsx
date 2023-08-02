import React, { useState, useRef } from "react";
import SignatureCanvas from 'react-signature-canvas';
import { Button, Dialog, DialogActions, DialogContent, Box, Typography, Paper } from "@mui/material";

const SignatureComponent: React.FC = () => {
    const [signatureImage, setSignatureImage] = useState<string | null>(null);
    const [isOpen, setIsOpen] = useState(false);
    const sigCanvas = useRef<any>(null);

    const saveSignature = () => {
        if (sigCanvas.current) {
            const image = sigCanvas.current.getTrimmedCanvas().toDataURL("image/png");
            setSignatureImage(image);
            setIsOpen(false);
        }
    };

    const clearSignature = () => {
        if (sigCanvas.current) {
            sigCanvas.current.clear();
        }
    };

    return (
        <Box>
            <Button variant="contained" onClick={() => setIsOpen(true)}>Sign Here</Button>

            <Dialog open={isOpen} onClose={() => setIsOpen(false)}>
                <DialogContent>
                    <SignatureCanvas
                        ref={sigCanvas}
                        canvasProps={{ width: 500, height: 200, className: 'signature-canvas' }}
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={clearSignature} color="secondary">Clear</Button>
                    <Button onClick={saveSignature} color="primary">Save</Button>
                </DialogActions>
            </Dialog>

            {signatureImage && (
                <Box mt={3}>
                    <Typography align="center">Renter's Signature:</Typography>
                    <img src={signatureImage} alt="Signature" style={{ display: 'block', margin: '0 auto', border: '1px solid black', marginTop: '10px' }} />
                </Box>
            )}
        </Box>
    );
};

export default SignatureComponent;
