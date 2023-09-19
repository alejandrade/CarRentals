import {PaginatedCarResponse} from "../car/carService.types";
import {authFetch} from "../../util/FetchFunctions";

class AdminPayments {
    private readonly BASE_URL = process.env.BASE_URL;  // Replace with your base URL

    async getStatement(startDate: string) {
        const token = localStorage.getItem('token');
        const response = await fetch(`${this.BASE_URL}/payments/v1/statement?startDate=${encodeURIComponent(startDate)}`, {
            headers: {
                'Authorization': `Bearer ${token}`,
            }
        });

        if (response.ok) {
            // Extract filename from the response headers
            const contentDisposition = response.headers.get('Content-Disposition');
            const filenameMatch = contentDisposition && contentDisposition.match(/filename="(.+)"/);
            const filename = filenameMatch && filenameMatch[1] ? filenameMatch[1] : `statement-${startDate}.csv`;

            // Create a blob from the response data
            const blob = await response.blob();

            // Create a URL for the blob
            const blobUrl = window.URL.createObjectURL(blob);

            // Create an anchor element to trigger the download
            const anchor = document.createElement('a');
            anchor.href = blobUrl;
            anchor.download = filename;

            // Trigger the click event on the anchor element to start the download
            anchor.click();

            // Revoke the blob URL to free up resources
            window.URL.revokeObjectURL(blobUrl);

            return true; // Download was successful
        } else {
            const errorResponse = await response.json();
            console.error('Error:', errorResponse);
            return false; // Download failed
        }
    }
}

export default new AdminPayments();
