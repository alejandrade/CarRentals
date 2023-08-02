type ImageCompressionOptions = {
    maxWidth?: number;
    quality?: number;
};

/**
 * Compress an image file.
 *
 * @param file - Image file.
 * @param options - Compression options.
 * @returns - Promise resolving with compressed Blob.
 */
export const compressImage = (file: File, options: ImageCompressionOptions = {}): Promise<Blob> => {
    return new Promise((resolve, reject) => {
        const { maxWidth = 512, quality = 0.8 } = options;
        const img = new Image();
        img.src = URL.createObjectURL(file);
        img.onload = () => {
            const canvas = document.createElement('canvas');
            const ctx = canvas.getContext('2d');

            if (img.width > maxWidth) {
                const scaleFactor = maxWidth / img.width;
                canvas.width = maxWidth;
                canvas.height = img.height * scaleFactor;
            } else {
                canvas.width = img.width;
                canvas.height = img.height;
            }

            ctx?.drawImage(img, 0, 0, canvas.width, canvas.height);

            canvas.toBlob((blob) => {
                if (blob) {
                    resolve(blob);
                } else {
                    reject(new Error('Compression failed.'));
                }
            }, 'image/jpeg', quality);
        };
        img.onerror = () => {
            reject(new Error('Failed to load image.'));
        };
    });
};