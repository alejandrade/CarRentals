// types.ts
export interface StartVerificationRequest {
    phoneNumber: string;
    channel: string;
}

export type TwilioVerificationDto = {
    phoneNumber: string;  // This follows the pattern: ^\+\d{1,15}$
    code: string;         // This should only contain digits based on the pattern.
};

export type TwilioAuthResponse = {
    verified: boolean;
    token: string;
};

