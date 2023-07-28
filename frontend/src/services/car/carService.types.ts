// carService.types.ts

export interface CarCreationDto {
    make?: string;
    model?: string;
    year?: number;
    vin?: string;
    color?: string;
    mileage?: number;
    price?: number;
    rentPrice?: number;
    licensePlate?: string;
    status?: string;
    // ... any other fields you might have in the DTO.
}

export interface CarResponseDto extends CarCreationDto {
    id: string;
    shortId: string;
    createdAt: Date;
    updatedAt: Date;
    createdBy: string;
    updatedBy: string;
    // ... any other fields you might expect in the response.
}

export interface PaginatedCarResponse {
    content: CarResponseDto[];
    pageable: {
        pageNumber: number;
        pageSize: number;
        offset: number;
        unpaged: boolean;
        paged: boolean;
    };
    last: boolean;
    totalElements: number;
    totalPages: number;
    first: boolean;
    numberOfElements: number;
    sort: {
        sorted: boolean;
        unsorted: boolean;
        empty: boolean;
    };
    size: number;
    number: number;
    empty: boolean;
}
