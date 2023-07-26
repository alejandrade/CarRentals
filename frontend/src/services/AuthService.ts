// authService.ts
import { StartVerificationRequest, StartVerificationResponse } from './AuthService.types';

class AuthService {
    private readonly BASE_URL = "YOUR_BASE_URL_HERE";  // Replace with your base URL

    /**
     * Start the verification process with Twilio.
     *
     * @param data - The data containing phoneNumber and channel for the verification.
     * @returns - A promise with the response.
     */
    async startVerification(data: StartVerificationRequest): Promise<StartVerificationResponse> {
        const response = await fetch(`${this.BASE_URL}/auth/v1/twilio/startVerification`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        });

        if (!response.ok) {
            throw new Error(`Failed to start verification: ${response.statusText}`);
        }

        return response.json();
    }
}

export default new AuthService();
