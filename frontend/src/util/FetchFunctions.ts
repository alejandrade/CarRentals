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
    let keyParts = [];

    // Use the input URL as part of the key
    if (typeof input === "string") {
        keyParts.push(input);
    } else if (input instanceof Request) {
        keyParts.push(input.url);
    }

    // Handle FormData for caching
    if (init && init.body instanceof FormData) {
        const formDataEntries: any[] = [];
        for (let pair of init.body.entries()) {
            formDataEntries.push(pair);
        }
        // Sorting to ensure order consistency
        formDataEntries.sort((a, b) => a[0].localeCompare(b[0]));
        keyParts.push(JSON.stringify(formDataEntries));
    } else if (init && init.body) {
        keyParts.push(JSON.stringify(init.body));
    }

    // If withAuth matters for your caching, include it
    if (withAuth !== undefined) {
        keyParts.push(String(withAuth));
    }

    return keyParts.join("::");
};
// Memoize with expiration function
function memoizeWithExpiration(fn: Function, resolver?: Function, expirationTime: number = 5000) {
    const cacheExpirations = new Map();


    const result = memoize(
        function(...args: any[]) {
            const key = resolver ? resolver(...args) : args[0];
            setTimeout(() => {
                result.cache.delete(key);
                cacheExpirations.delete(key);
            }, expirationTime);
            return fn(...args);
        },
        // @ts-ignore
        resolver
    );

    return result;
}

// Memoize the function
const memoizedFetch = memoizeWithExpiration(_fetch, resolver, 500);
export async function authFetch(input: RequestInfo, init?: RequestInit): Promise<WrappedResponse> {
     // withAuth is true
    return await memoizedFetch(input, init, true);
}

export async function memFetch(input: RequestInfo, init?: RequestInit): Promise<WrappedResponse> {
    const result = await memoizedFetch(input, init, false); // withAuth is false

    // Set a timeout to clear the specific cache entry after 500ms
    setTimeout(() => {
        memoizedFetch.cache.delete(resolver(input, init, false));
    }, 500);

    return result;
}

