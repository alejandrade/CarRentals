import { UserDemographicsDto } from './UserDemographicsService.types';  // Adjust the path accordingly
import { authFetch } from "../../util/FetchFunctions";

class UserDemographicsService {
    private readonly BASE_URL = process.env.BASE_URL;

    /**
     * Create a user's demographic data.
     *
     * @param data - The demographic data for the user.
     * @returns - A promise with the response containing the created demographic data.
     */
    async createUserDemographics(data: UserDemographicsDto): Promise<UserDemographicsDto> {
        const response = await authFetch(`${this.BASE_URL}/users/v1/demographics`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        });

        return response.json();
    }
}

export default new UserDemographicsService();
