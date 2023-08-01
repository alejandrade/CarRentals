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

    const isoDateTimeRegex = /^\d{4}-\d{2}-\d{2}(T\d{2}:\d{2}:\d{2})?$/;

    // Recursive function to convert ISO date strings to Date objects
    const convertDatesToObjects = (data: any): any => {
        if (Array.isArray(data)) {
            return data.map(convertDatesToObjects);
        } else if (data !== null && typeof data === 'object') {
            for (const key in data) {
                if (Object.prototype.hasOwnProperty.call(data, key)) {
                    data[key] = convertDatesToObjects(data[key]);
                    if (typeof data[key] === 'string' && isoDateTimeRegex.test(data[key])) {
                        // Check if the string matches the stricter ISO date format and convert it to a Date object
                        const date = new Date(data[key]);
                        if (!isNaN(date.getTime())) {
                            data[key] = date;
                        }
                    }
                }
            }
            return data;
        } else {
            return data;
        }
    };

    const response = await fetch(input, init);
    if (!response.ok) {
        const errorDetails: ErrorDetails = await response.json();
        console.error(JSON.stringify(errorDetails));
        throw new APIError('API Request failed', errorDetails);
    }

    const jsonData = await response.json();
    const dataWithDatesConverted = convertDatesToObjects(jsonData);

    return {
        ok: response.ok,
        json: Promise.resolve(dataWithDatesConverted) // Resolve with the updated JSON object
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

