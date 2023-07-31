type ErrorDetails = {
    timestamp: string;
    status: number;
    error: string;
    message: string;
    path: string;
    bindingErrors: string[];
};

export class APIError extends Error {
    details: ErrorDetails;

    constructor(message: string, details: ErrorDetails) {
        super(message);
        this.details = details;
    }
}

export async function authFetch(input: RequestInfo, init?: RequestInit): Promise<Response> {
    const token = localStorage.getItem('token');

    if (token) {
        init = {
            ...init,
            headers: {
                ...init?.headers,
                'Authorization': `Bearer ${token}`,
            },
        };
    }

    const response = await fetch(input, init);

    // Checking for non-successful HTTP responses.
    if (!response.ok) {
        const errorDetails: ErrorDetails = await response.json();
        throw new APIError('API Request failed', errorDetails);
    }

    return response;
}
