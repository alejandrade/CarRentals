export type Gender = 'Male' | 'Female' | 'Other' | 'Prefer_Not_To_Say';


export type  UserDto = {
    id: string;
    email: string;
    phoneNumber: string;
    isEmailAuth: boolean;
    enabled: boolean;
    accountNonExpired: boolean;
    credentialsNonExpired: boolean;
    accountNonLocked: boolean;
    authorities?: string[];  // It's optional because it's a List which can be null
    userDemographics?: UserDemographicsDto;  // Assuming you've a similar type for UserDemographicsDto
}
export type  UserDemographicsDto = {
    userId: string;
    firstName: string;
    middleInitial?: string;  // fields that can be null or not set should be optional
    lastName?: string;
    dateOfBirth?: Date;  // Using JavaScript's Date type to represent LocalDate
    gender?: Gender;
    address?: string;
    city?: string;
    state?: string;
    postalCode?: string;
    country?: string;
    additionalInfo?: string;
}
