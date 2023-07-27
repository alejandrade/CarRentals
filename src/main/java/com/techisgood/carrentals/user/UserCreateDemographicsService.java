package com.techisgood.carrentals.user;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.techisgood.carrentals.exception.RemoteServiceException;
import com.techisgood.carrentals.exception.RemoteServiceException.RemoteService;
import com.techisgood.carrentals.model.DbUser;
import com.techisgood.carrentals.model.DbUserDemographics;
import com.techisgood.carrentals.model.PaymentsCustomer;
import com.techisgood.carrentals.payments.PaymentsService;
import com.techisgood.carrentals.payments.RemotePaymentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserCreateDemographicsService {
	private final UserByIdService userByIdService;
	private final UserDemographicsRepository userDemographicsRepository;
	private final PaymentsService paymentsService;
	private final RemotePaymentsService remotePaymentsService;
	
	@Transactional
	public DbUserDemographics createUserDemographics(
			String userId,
			String first, 
			String middle, 
			String last,
			LocalDate dob,
			DbUserDemographics.Gender gender,
			String address,
			String city,
			String state,
			String zip,
			String country,
			String additional
			) throws RemoteServiceException {
		
		Optional<DbUser> user = userByIdService.getUser(userId);
		DbUserDemographics demo = null;
		if (user.isPresent())  
		{
			demo = new DbUserDemographics();
			demo.setUser(user.get());
			demo.setFirstName(first);
			demo.setMiddleInitial(middle);
			demo.setLastName(last);
			demo.setDateOfBirth(dob);
			demo.setGender(gender);
			demo.setAddress(address);
			demo.setCity(city);
			demo.setState(state);
			demo.setPostalCode(zip);
			demo.setCountry(country);
			demo.setAdditionalInfo(additional);
			 
			userDemographicsRepository.save(demo);
			 
			//TODO(justin): There may be rollback issues here if later transactions cause demographics save to be rolled back, stripe will not rollback.
			PaymentsCustomer customer = paymentsService.getCustomerByUserId(userId);
			Customer createdCustomer = null;
			try {
				if (customer != null) {
					// we have a customer .. but no remote match, so needs to be created in remote, and customer needs to be updated.
					if (remotePaymentsService.getCustomerById(customer.getCustomerId()) == null) {
						createdCustomer = remotePaymentsService.createCustomer(demo);
						paymentsService.updateCustomer(userId, createdCustomer.getId());
					}
					//we have a customer, and the remote match is found, so update remote to match potentially new demographics info
					else {
						remotePaymentsService.updateCustomer(customer.getCustomerId(), demo);
					}
				}
				else {
					//no customer. just create fresh in both our copy and the remote copy.
					createdCustomer = remotePaymentsService.createCustomer(demo);
					paymentsService.createCustomer(user.get(), createdCustomer.getId());
				}
			} catch(StripeException e) {
				throw new RemoteServiceException(RemoteService.STRIPE, e.getMessage());
			} catch(Exception e) {
				if (createdCustomer != null) {
					try {
						createdCustomer.delete();
					} catch (StripeException e1) {
						throw new RemoteServiceException(RemoteService.STRIPE, e1.getMessage());
					}
					throw e;
				}
			}
		}
		return demo;
	}
	
}
