import React, { useState } from 'react';
import { Button, Container, CssBaseline, Typography, IconButton } from '@mui/material';
import { PhotoCamera } from '@mui/icons-material';
import { useParams } from 'react-router-dom';
import {compressImage} from "../../util/ImageFunctions";

type ImageType = 'front' | 'leftSide' | 'rightSide' | 'backSide' | 'odometer';

type ImagesState = {
    [key in ImageType]: File | null;
};

const CarRentalForm: React.FC = () => {
    const { shortId } = useParams();

    const [images, setImages] = useState<ImagesState>({
        front: null,
        leftSide: null,
        rightSide: null,
        backSide: null,
        odometer: null
    });

    const handleImageChange = async (e: React.ChangeEvent<HTMLInputElement>, type: ImageType) => {
        if (e.target.files) {
            const originalFile = e.target.files[0];
            try {
                const compressedBlob = await compressImage(originalFile, { maxWidth: 1024, quality: 0.8 });
                const compressedFile = new File([compressedBlob], originalFile.name, {
                    type: 'image/jpeg',
                });
                setImages(prev => ({ ...prev, [type]: compressedFile }));
            } catch (error) {
                console.error('Failed to compress the image.', error);
            }
        }
    };

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        console.log(images);
        // Further processing like sending to server, etc.
    };

    return (
        <Container component="main" maxWidth="xs">
            <CssBaseline />
            <div>
                <Typography component="h1" variant="h5">
                    Car Rental Form
                </Typography>
                <form noValidate onSubmit={handleSubmit}>
                    {(['front', 'leftSide', 'rightSide', 'backSide', 'odometer'] as ImageType[]).map((type) => (
                        <div key={type} style={{ marginBottom: '16px' }}>
                            <input
                                accept="image/*"
                                style={{ display: 'none' }}
                                id={`icon-button-file-${type}`}
                                type="file"
                                onChange={(e) => handleImageChange(e, type)}
                            />
                            <label htmlFor={`icon-button-file-${type}`}>
                                <IconButton
                                    color="primary"
                                    aria-label={`upload picture for ${type}`}
                                    component="span"
                                >
                                    <PhotoCamera />
                                </IconButton>
                                Upload {type} image
                            </label>
                        </div>
                    ))}
                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        color="primary"
                    >
                        Rent Car
                    </Button>
                </form>
            </div>
        </Container>
    );
};

export default CarRentalForm;
