import { authFetch } from "../../util/FetchFunctions";
import {
    createInvoiceDto,
    InvoiceCreatePaymentDto,
    PaymentMethodSessionBeginDto,
    PaymentsInvoiceDto
} from "./PaymentsService.types";

class PaymentsService {
    private readonly BASE_URL = process.env.BASE_URL;

    /**
     * Get a url to redirect the user to enter payment information
     */
    async paymentMethodSessionBegin(data: PaymentMethodSessionBeginDto): Promise<PaymentMethodSessionBeginDto> {
        const response = await authFetch(`${this.BASE_URL}/payments/v1/paymentMethod/sessionBegin`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        });

        if (!response.ok) {
            throw new Error('Failed to save insurance information');
        }

        return response.json;
    }

    
    /**
     * Get a url to redirect the user to enter payment information
     */
    async paymentMethodSessionBeginTest(data: PaymentMethodSessionBeginDto): Promise<PaymentMethodSessionBeginDto> {
        const response = await authFetch(`${this.BASE_URL}/payments/v1/paymentMethod/sessionBegin/TestWithLoggedInUser`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        });

        if (!response.ok) {
            throw new Error('Failed to save insurance information');
        }

        return response.json;
    }


    /**
     * Get a url to redirect the user to enter payment information
     */
    async invoiceCreatePayment(data: InvoiceCreatePaymentDto): Promise<InvoiceCreatePaymentDto> {
        const response = await authFetch(`${this.BASE_URL}/payments/v1/invoices/createPayment`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        });

        if (!response.ok) {
            throw new Error('Failed to save insurance information');
        }

        return response.json;
    }

    /**
     * Get a url to redirect the user to enter payment information
     */
    async invoiceCreatePaymentTest(data: InvoiceCreatePaymentDto): Promise<InvoiceCreatePaymentDto> {
        const response = await authFetch(`${this.BASE_URL}/payments/v1/invoices/createPayment/TestWithLoggedInUser`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        });

        if (!response.ok) {
            throw new Error('Failed to save insurance information');
        }

        return response.json;
    }


    async createInvoice(data: createInvoiceDto): Promise<PaymentsInvoiceDto> {
        const response = await authFetch(`${this.BASE_URL}/payments/v1/invoices`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        });

        if (!response.ok) {
            throw new Error('Failed to save insurance information');
        }

        return response.json;
    }

}

export default new PaymentsService();