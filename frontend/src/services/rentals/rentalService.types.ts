// rentalService.types.ts

export interface RentalActionDto {
    odometer: number;
    version: number;
}
export interface RentalDto {
    id: string;
    clerkId: string;
    renterPhoneNumber: string;
    status: string;
    initialOdometerReading: number;
    endingOdometerReading: number;
    rentalDatetime: Date; // You may want to use a string representation of the datetime
    returnDatetime: Date; // You may want to use a string representation of the datetime

    // Audit properties
    createdAt: string;
    updatedAt: string;
    createdBy: string;
    updatedBy: string;
}
export interface RentalCreateDto {
    carId: string;
    renterPhoneNumber: string
    status: string;
    rentalDatetime: string;
}
// rentalService.types.ts

export interface RentalPictureDto {
    id: string;
    rentalId: string;
    angle: string; // Assuming RentalPictureAngle is a string representation of an angle
    s3Key: string;
    isInitialPicture: boolean;
    takenById: string;
    takenAt: string; // You may want to use a string representation of the datetime

    // Audit properties
    createdAt: string;
    updatedAt: string;
    createdBy: string;
    updatedBy: string;
}

// Assuming PaginatedRentalResponse is returned by Spring pagination

export interface PaginatedRentalResponse {
    content: RentalDto[];
    pageable: {
        sort: {
            sorted: boolean;
            unsorted: boolean;
            empty: boolean;
        };
        offset: number;
        pageNumber: number;
        pageSize: number;
        paged: boolean;
        unpaged: boolean;
    };
    totalElements: number;
    totalPages: number;
    last: boolean;
    size: number;
    number: number;
    sort: {
        sorted: boolean;
        unsorted: boolean;
        empty: boolean;
    };
    first: boolean;
    numberOfElements: number;
    empty: boolean;
}

export interface CreateRentalPictureDto {
    rentalId: string;
    angle: string;
    isInitialPicture: boolean;
    takenById: string;
    takenAt: string; // Use string or Date type based on your preference and data format
}
