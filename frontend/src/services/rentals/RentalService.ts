import {
    RentalDto,
    RentalActionDto,
    PaginatedRentalResponse,
    RentalPictureDto,
    CreateRentalPictureDto, RentalCreateDto
} from './rentalService.types';
import { authFetch } from "../../util/FetchFunctions";

class RentalService {
    private readonly BASE_URL = process.env.BASE_URL;

    /**
     * Create a new rental.
     *
     * @param data - The data for rental creation.
     * @returns - A promise with the response containing the created rental.
     */
    async createRental(data: RentalCreateDto): Promise<RentalDto> {
        const response = await authFetch(`${this.BASE_URL}/clerk/v1/rentals`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        });

        return response.json;
    }

    /**
     * Start a rental by its ID.
     *
     * @param rentalId - The ID of the rental to start.
     * @param rentalActionDto - The data for rental action, such as odometer reading and version.
     * @returns - A promise with the response containing the started rental data.
     */
    async startRental(rentalId: string, rentalActionDto: RentalActionDto): Promise<RentalDto> {
        const response = await authFetch(`${this.BASE_URL}/clerk/v1/rentals/${rentalId}/start`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(rentalActionDto),
        });

        return response.json;
    }

    /**
     * End a rental by its ID.
     *
     * @param rentalId - The ID of the rental to end.
     * @param rentalActionDto - The data for rental action, such as odometer reading and version.
     * @returns - A promise with the response containing the ended rental data.
     */
    async endRental(rentalId: string, rentalActionDto: RentalActionDto): Promise<RentalDto> {
        const response = await authFetch(`${this.BASE_URL}/clerk/v1/rentals/${rentalId}/end`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(rentalActionDto),
        });

        return response.json;
    }

    /**
     * Fetch all rentals with pagination.
     *
     * @param page - Page number.
     * @param size - Number of records per page.
     * @returns - A promise with the paginated response of rentals.
     */
    async fetchAllRentals(page: number = 0, size: number = 10): Promise<PaginatedRentalResponse> {
        const response = await authFetch(`${this.BASE_URL}/clerk/v1/rentals?page=${page}&size=${size}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        return response.json;
    }

    /**
     * Fetch rentals by clerk ID with pagination.
     *
     * @param clerkId - The ID of the clerk.
     * @param page - Page number.
     * @param size - Number of records per page.
     * @returns - A promise with the paginated response of rentals for the specified clerk.
     */
    async fetchRentalsByClerkId(clerkId: string, page: number = 0, size: number = 10): Promise<PaginatedRentalResponse> {
        const response = await authFetch(`${this.BASE_URL}/clerk/v1/rentals/byClerk/${clerkId}?page=${page}&size=${size}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        return response.json;
    }

    /**
     * Create a rental picture.
     *
     * @param file - The picture file to upload.
     * @param dto - The data for rental picture creation.
     * @returns - A promise with the response containing the created rental picture.
     */
    async createRentalPicture(file: File, dto: CreateRentalPictureDto): Promise<RentalPictureDto> {
        const formData = new FormData();
        formData.append('file', file);
        formData.append('dto', JSON.stringify(dto));

        const response = await authFetch(`${this.BASE_URL}/clerk/v1/rentals/pictures`, {
            method: 'POST',
            body: formData,
        });

        return response.json;
    }


}

export default new RentalService();
