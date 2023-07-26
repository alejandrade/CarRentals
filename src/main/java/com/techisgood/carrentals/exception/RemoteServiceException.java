package com.techisgood.carrentals.exception;

public class RemoteServiceException extends Exception {
	
	//NOTE(justin): add new services here as we start implementing them in code.
	public enum RemoteService {
		STRIPE("Stripe"),
		TWILIO("Twilio"),
		;
		private String name;
		private RemoteService(String name) {
			this.name = name;
		}
		public String getName() {
			return name;
		}
	}
	
	public RemoteService service;
	
	public RemoteServiceException(RemoteService service, String message) {
		super(message);
		this.service = service;
    }
}
