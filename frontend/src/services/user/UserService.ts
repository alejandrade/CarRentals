import { authFetch } from "../../util/FetchFunctions";
import {UserDemographicsDto, UserDto} from "./UserService.types";

class UserService {
    private readonly BASE_URL = process.env.BASE_URL;

    async getLoggedInUser(): Promise<UserDto> {
        const response = await authFetch(`${this.BASE_URL}/users/v1/user/current`);
        if (!response.ok) {
            throw new Error('Failed to fetch logged-in user');
        }

        return response.json;
    }

    /**
     * Create a user's demographic data.
     *
     * @param data - The demographic data for the user.
     * @returns - A promise with the response containing the created demographic data.
     */
    async createUserDemographics(data: UserDemographicsDto): Promise<UserDemographicsDto> {
        const response = await authFetch(`${this.BASE_URL}/users/v1/user/demographic`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        });

        return response.json;
    }
}

export default new UserService();
