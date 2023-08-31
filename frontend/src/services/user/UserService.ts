import { authFetch } from "../../util/FetchFunctions";
import {
    Paginated,
    UpdateContactInformation,
    UserDemographicsDto,
    UserDto,
    UserInsuranceDto,
    UserLicenseDto, UserWithDetailsDto
} from "./UserService.types";

class UserService {
    private readonly BASE_URL = process.env.BASE_URL;

    /**
     * Save user insurance information.
     *
     * @param data - The insurance data for the user.
     * @returns - A promise with the response containing the saved insurance data.
     */
    async saveInsurance(data: UserInsuranceDto): Promise<UserInsuranceDto> {
        const response = await authFetch(`${this.BASE_URL}/users/v1/user/insurance`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        });

        if (!response.ok) {
            throw new Error('Failed to save insurance information');
        }

        return response.json;
    }

    /**
     * Upload an insurance image.
     *
     * @param userId - the id of person whos insurance you are updating
     * @param insuranceId - The ID of the insurance.
     * @param imageAngle - The angle of the image (FRONT/BACK).
     * @param imageFile - The image file to upload.
     * @returns - A promise with the server response.
     */
    async uploadInsuranceImage(userId: string, insuranceId: string, imageAngle: "FRONT" | "BACK", imageFile: File): Promise<Response> {
        const formData = new FormData();
        formData.append('insuranceId', insuranceId);
        formData.append('imageAngle', imageAngle);
        formData.append('image', imageFile);
        formData.append('userId', userId);

        const response = await authFetch(`${this.BASE_URL}/users/v1/user/insurance/upload`, {
            method: 'POST',
            body: formData,
        });

        if (!response.ok) {
            throw new Error('Failed to upload insurance image');
        }

        return response.json;  // Or simply return response if you don't need the JSON data
    }

    /**
     * Save user license information.
     *
     * @param data - The license data for the user.
     * @returns - A promise with the response containing the saved insurance data.
     */
    async saveLicense(data: UserLicenseDto): Promise<UserLicenseDto> {
        const response = await authFetch(`${this.BASE_URL}/users/v1/user/license`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        });

        if (!response.ok) {
            throw new Error('Failed to save license information');
        }

        return response.json;
    }

    /**
     * Upload an insurance image.
     *
     * @param licenseId - The ID of the license.
     * @param imageAngle - The angle of the image (FRONT/BACK).
     * @param imageFile - The image file to upload.
     * @returns - A promise with the server response.
     */
    async uploadLicenseImage(userId: string, licenseId: string, imageAngle: "FRONT" | "BACK", imageFile: File): Promise<Response> {
        const formData = new FormData();
        formData.append('licenseId', licenseId);
        formData.append('imageAngle', imageAngle);
        formData.append('image', imageFile);
        formData.append("userId", userId)

        const response = await authFetch(`${this.BASE_URL}/users/v1/user/license/upload`, {
            method: 'POST',
            body: formData,
        });

        if (!response.ok) {
            throw new Error('Failed to upload license image');
        }

        return response.json;  // Or simply return response if you don't need the JSON data
    }
    async updateContactInformation(data: UpdateContactInformation): Promise<UserDto>  {
        const response = await authFetch(`${this.BASE_URL}/users/v1/user/contactInformation`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        });
        return response.json;
    }

    async getClient(phoneNumber: string): Promise<UserDto> {
        const response = await authFetch(`${this.BASE_URL}/users/v1/user/client/${phoneNumber}`);
        if (!response.ok) {
            throw new Error('Failed to fetch logged-in user');
        }

        return response.json;
    }

    async authTest(): Promise<boolean> {
        const resp = await authFetch(`${this.BASE_URL}/users/v1/user/authtest`);
        return resp.ok;
    }


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


    /**
     * Fetch paginated user details with admin role.
     *
     * @param page - The page number.
     * @param size - The number of items per page.
     * @returns - A promise with the response containing the paginated user details.
     */
    async getUserWithDetails(page: number = 0, size: number = 10): Promise<Paginated<UserWithDetailsDto>> {
        const response = await authFetch(`${this.BASE_URL}/users/v1/user?page=${page}&size=${size}`);

        if (!response.ok) {
            throw new Error('Failed to fetch user details');
        }

        return response.json;
    }

    /**
     * Modify user information with admin role.
     *
     * @param userId - The ID of the user to modify.
     * @param userDto - The modified user data.
     * @returns - A promise with the response containing the modified user data.
     */
    async modifyUser(userId: string, userDto: UserDto): Promise<UserDto> {
        const response = await authFetch(`${this.BASE_URL}/users/v1/user/admin/${userId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(userDto),
        });

        if (!response.ok) {
            throw new Error('Failed to modify user information');
        }

        return response.json;
    }

    /**
     * Get user information with admin role.
     *
     * @param userId - The ID of the user to retrieve.
     * @returns - A promise with the response containing the user data.
     */
    async getUser(userId: string): Promise<UserDto> {
        const response = await authFetch(`${this.BASE_URL}/users/v1/user/admin/${userId}`, {
            method: 'GET',
        });

        if (!response.ok) {
            throw new Error('Failed to get user information');
        }

        return response.json;
    }
}



export default new UserService();
