import React, { useState } from 'react';
import { Button, Box, Typography, Paper, IconButton } from '@mui/material';
import CloudUploadIcon from '@mui/icons-material/CloudUpload';
import DeleteIcon from '@mui/icons-material/Delete';
import { compressImage } from "../util/ImageFunctions";

interface ImageUploadProps {
    label: string;
    onImageChange: (file: File) => void;
}

const ImageUpload: React.FC<ImageUploadProps> = ({ label, onImageChange }) => {
    const [previewImage, setPreviewImage] = useState<string | null>(null);
    const [isDragOver, setIsDragOver] = useState(false);

    const handleImageChange = async (event: React.ChangeEvent<HTMLInputElement>) => {
        const file = event.target.files ? event.target.files[0] : null;
        processFile(file);
    };

    const processFile = async (file: File | null) => {
        if (file) {
            const compressedBlob = await compressImage(file);
            const compressedFile = new File([compressedBlob], file.name, {
                type: compressedBlob.type,
                lastModified: new Date().getTime()
            });
            onImageChange(compressedFile);

            const reader = new FileReader();
            reader.onloadend = () => {
                setPreviewImage(reader.result as string);
            };
            reader.readAsDataURL(compressedFile);
        }
    }

    const handleDrop = (event: React.DragEvent<HTMLDivElement>) => {
        event.preventDefault();
        const file = event.dataTransfer.files[0];
        processFile(file);
        setIsDragOver(false);
    }

    const removeImage = () => {
        setPreviewImage(null);
        onImageChange(new File([], '')); // Pass an empty File object to clear the selected image in the parent state
    }

    return (
        <div>
            <Typography variant="h6" gutterBottom>{label}</Typography>

            <Paper
                elevation={3}
                style={{
                    height: '200px',
                    display: 'flex',
                    justifyContent: 'center',
                    alignItems: 'center',
                    position: 'relative',
                    border: isDragOver ? '3px dashed #3f51b5' : '3px dashed #e0e0e0',
                    borderRadius: '8px',
                    backgroundColor: isDragOver ? '#f5f5f5' : '#ffffff',
                }}
                onDragOver={(e) => {
                    e.preventDefault();
                    setIsDragOver(true);
                }}
                onDragLeave={() => setIsDragOver(false)}
                onDrop={handleDrop}
            >
                {previewImage ? (
                    <>
                        <IconButton
                            style={{ position: 'absolute', top: '10px', right: '10px' }}
                            onClick={removeImage}
                        >
                            <DeleteIcon />
                        </IconButton>
                        <img src={previewImage} alt="Preview" style={{ maxWidth: '100%', maxHeight: '100%', borderRadius: '8px' }} />
                    </>
                ) : (
                    <Box textAlign="center">
                        <CloudUploadIcon color="action" fontSize="large" />
                        <Typography variant="body2" color="textSecondary" style={{ marginBottom: '10px' }}>Drag & drop an image here</Typography>
                        <input
                            style={{ display: 'none' }}
                            id="raised-button-file"
                            type="file"
                            onChange={handleImageChange}
                            accept="image/*"
                        />
                        <label htmlFor="raised-button-file">
                            <Button variant="outlined" component="span" color="primary">
                                Or select a file
                            </Button>
                        </label>
                    </Box>
                )}
            </Paper>
        </div>
    );
};

export default ImageUpload;
