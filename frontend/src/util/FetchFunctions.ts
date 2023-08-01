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
    json: Promise<T>;
};

export class APIError extends Error {
    details: ErrorDetails;

    constructor(message: string, details: ErrorDetails) {
        super(message);
        this.details = details;
    }
}

async function _fetch(input: RequestInfo, init?: RequestInit, withAuth: boolean = true): Promise<WrappedResponse> {
    if (withAuth) {
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
    }

    const response = await fetch(input, init);
    if (!response.ok) {
        const errorDetails: ErrorDetails = await response.json();
        throw new APIError('API Request failed', errorDetails);
    }

    return {
        ok: response.ok,
        json: response.json()  // Note: This is a promise!
    };
}

// Create a cache key resolver
const resolver = (input: RequestInfo, init?: RequestInit, withAuth?: boolean) => {
    return JSON.stringify({ input, init, withAuth });
};

// Memoize the function
const memoizedFetch = memoize(_fetch, resolver);

export async function authFetch(input: RequestInfo, init?: RequestInit): Promise<WrappedResponse> {
    const result = await memoizedFetch(input, init, true); // withAuth is true

    // Set a timeout to clear the specific cache entry after 500ms
    setTimeout(() => {
        memoizedFetch.cache.delete(resolver(input, init, true));
    }, 500);

    return result;
}

export async function memFetch(input: RequestInfo, init?: RequestInit): Promise<WrappedResponse> {
    const result = await memoizedFetch(input, init, false); // withAuth is false

    // Set a timeout to clear the specific cache entry after 500ms
    setTimeout(() => {
        memoizedFetch.cache.delete(resolver(input, init, false));
    }, 500);

    return result;
}
