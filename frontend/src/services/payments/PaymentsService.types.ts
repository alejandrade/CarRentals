export interface PaymentMethodSessionBeginDto {
	userId:string;
	successUrl:string;
	cancelUrl:string;
    url:string|undefined;
}

export interface InvoiceCreatePaymentDto {
    userId:string;
    invoiceId:string;
    
	successUrl:string;
	cancelUrl:string;
    url:string|undefined;
}