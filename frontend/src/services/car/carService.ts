// carService.ts
import { CarCreationDto, CarResponseDto, PaginatedCarResponse } from './carService.types';
import {authFetch} from "../../util/FetchFunctions";

class CarService {
    private readonly BASE_URL = process.env.BASE_URL;

    /**
     * Create or update a car.
     *
     * @param data - The data for car creation or update.
     * @returns - A promise with the response containing the created or updated car.
     */
    async createOrUpdateCar(data: CarCreationDto): Promise<CarResponseDto> {
        const response = await authFetch(`${this.BASE_URL}/staff/v1/car`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        });

        return response.json;
    }

    /**
     * Fetch all cars with pagination.
     *
     * @param page - Page number.
     * @param size - Number of records per page.
     * @returns - A promise with the paginated response of cars.
     */
    async fetchAllCars(page: number = 0, size: number = 10): Promise<PaginatedCarResponse> {
        const response = await authFetch(`${this.BASE_URL}/staff/v1/car?page=${page}&size=${size}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        return response.json;
    }

    /**
     * Fetch a car by its ID.
     *
     * @param id - The ID of the car to retrieve.
     * @returns - A promise with the response containing the car data.
     */
    async getCarById(id: string): Promise<CarResponseDto> {
        const response = await authFetch(`${this.BASE_URL}/staff/v1/car/${id}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        return response.json;
    }
}

export default new CarService();
