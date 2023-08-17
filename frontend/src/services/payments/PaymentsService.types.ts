export interface PaymentMethodSessionBeginDto {
	userId:string;
	successUrl:string;
	cancelUrl:string;
    url:string|undefined;
}

export interface InvoiceCreatePaymentDto {
    invoiceId:string;
	successUrl:string;
	cancelUrl:string;
    url? :string;
}

export interface createInvoiceDto {
	rentalId: string;
	note: string;
	subTotal: number;
	payerId: string;
	invoiceType: string;
}

export interface PaymentsInvoiceDto {
	id: string;
	rentalId: string;
	payerId: string;
	subTotal: number;
	taxRate: number;
	taxTotal: number;
	total: number;
	paidBy: string;
	note: string;
	externalPaymentId: string;
	externalPaymentStatus: string;
	createdAt: string;
	updatedAt: string;
	createdBy: string;
	updatedBy: string;
	active: number;
}