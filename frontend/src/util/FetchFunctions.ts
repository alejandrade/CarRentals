export async function authFetch(input: RequestInfo, init?: RequestInit): Promise<Response> {
    const token = localStorage.getItem('token'); // Adjust this according to where you store the JWT

    if (token) {
        init = {
            ...init,
            headers: {
                ...init?.headers,
                'Authorization': `Bearer ${token}`,
            },
        };
    }

    return fetch(input, init);
}
