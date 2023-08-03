export interface PaymentMethodSessionBeginDto {
	userId:string;
	successUrl:string;
	cancelUrl:string;
    url:string|undefined;
}