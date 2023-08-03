import { authFetch } from "../../util/FetchFunctions";
import {ServiceLocationDto} from "./ServiceLocationService.types";
import {Page} from "../user/UserService.types";

class ServiceLocationService {
    private readonly BASE_URL = process.env.BASE_URL;

    /**
     * Fetch paginated service locations with staff or admin role.
     *
     * @param page - The page number.
     * @param size - The number of items per page.
     * @param state - The state to filter by.
     * @param name - The state to filter by.
     * @returns - A promise with the response containing the paginated service locations.
     */
    async getAllServiceLocation(): Promise<ServiceLocationDto[]> {
        let url = `${this.BASE_URL}/staff/v1/serviceLocations/all`;

        const response = await authFetch(url);

        if (!response.ok) {
            throw new Error('Failed to fetch service locations');
        }

        return response.json;
    }

    /**
     * Fetch paginated service locations with staff or admin role.
     *
     * @param page - The page number.
     * @param size - The number of items per page.
     * @param state - The state to filter by.
     * @returns - A promise with the response containing the paginated service locations.
     */
    async getAllServiceLocations(page: number = 0, size: number = 10, state?: string): Promise<Page<ServiceLocationDto>> {
        let url = `${this.BASE_URL}/staff/v1/serviceLocations?page=${page}&size=${size}`;
        if (state) {
            url += `&state=${encodeURIComponent(state)}`;
        }

        const response = await authFetch(url);

        if (!response.ok) {
            throw new Error('Failed to fetch service locations');
        }

        return response.json;
    }

    /**
     * Fetch a single service location by its ID.
     *
     * @param id - The ID of the service location.
     * @returns - A promise with the response containing the service location data.
     */
    async getServiceLocationById(id: string): Promise<ServiceLocationDto> {
        const url = `${this.BASE_URL}/staff/v1/serviceLocations/${id}`;

        const response = await authFetch(url);

        if (!response.ok) {
            throw new Error('Failed to fetch service location by ID');
        }

        return response.json;
    }

    /**
     * Save or create a new service location.
     *
     * @param dto - The ServiceLocationDto data.
     * @returns - A promise with the response containing the saved service location data.
     */
    async save(dto: ServiceLocationDto): Promise<ServiceLocationDto> {
        const url = `${this.BASE_URL}/staff/v1/serviceLocations`;

        const response = await authFetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(dto)
        });

        if (!response.ok) {
            throw new Error('Failed to save service location');
        }

        return response.json;
    }

}

export default new ServiceLocationService();
