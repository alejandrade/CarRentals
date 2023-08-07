import { authFetch } from "../../util/FetchFunctions";
import {TwilioVerificationDto} from "../auth/AuthService.types";


class ClerkService {
    private readonly BASE_URL = process.env.BASE_URL;

    async verifyAndCreate(data: TwilioVerificationDto): Promise<{ verified: boolean }> {
        const response = await authFetch(`${this.BASE_URL}/clerk/v1/twilio/verify`, {
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

}



export default new ClerkService();
