import { debounce } from "@mui/material";
import memoize from 'lodash/memoize';

type ErrorDetails = {
    timestamp: string;
    status: number;
    error: string;
    message: string;
    path: string;
    bindingErrors: string[];
};

export type WrappedResponse<T = any> = {
    ok: boolean;
    json: T;
};

export class APIError extends Error {
    details: ErrorDetails;

    constructor(message: string, details: ErrorDetails) {
        super(message);
        this.details = details;
    }
}

async function _authFetch(input: RequestInfo, init?: RequestInit): Promise<WrappedResponse> {
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

    if (!response.ok) {
        const errorDetails: ErrorDetails = await response.json();
        throw new APIError('API Request failed', errorDetails);
    }

    return {
        ok: response.ok,
        json: response.json()
    };
}

// Create a cache key resolver
const resolver = (input: RequestInfo, init?: RequestInit) => {
    return JSON.stringify({ input, init });
};

// Memoize the function
const memoizedAuthFetch = memoize(_authFetch, resolver);

export async function authFetch(input: RequestInfo, init?: RequestInit): Promise<WrappedResponse> {
    const result = await memoizedAuthFetch(input, init);

    // Set a timeout to clear the specific cache entry after 500ms
    setTimeout(() => {
        memoizedAuthFetch.cache.delete(resolver(input, init));
    }, 500);

    return result;
}
