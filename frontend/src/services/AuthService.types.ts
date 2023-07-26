// types.ts
export interface StartVerificationRequest {
    phoneNumber: string;
    channel: string;
}

export interface StartVerificationResponse {
    // Define the properties of the expected response here
    message?: string;
    // ... any other fields
}
