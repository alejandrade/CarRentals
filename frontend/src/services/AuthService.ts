// authService.ts
import {
    StartVerificationRequest,
    TwilioAuthResponse,
    TwilioVerificationDto
} from './AuthService.types';

class AuthService {
    private readonly BASE_URL = process.env.BASE_URL;  // Replace with your base URL

    /**
     * Start the verification process with Twilio.
     *
     * @param data - The data containing phoneNumber and channel for the verification.
     * @returns - A promise with the response.
     */
    async startVerification(data: StartVerificationRequest): Promise<boolean> {
        const response = await fetch(`${this.BASE_URL}/auth/v1/twilio/startVerification`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        });

        if (response.status === 200) {
            return true;
        } else {
            console.error(`Unexpected response from startVerification: ${response.status}`);
            return false;
        }

    }

    async verify(data: TwilioVerificationDto): Promise<TwilioAuthResponse> {
        const response = await fetch(`${this.BASE_URL}/auth/v1/twilio/verify`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        });

        return response.json();
    }
}

export default new AuthService();
