export type Gender = 'Male' | 'Female' | 'Other' | 'Prefer_Not_To_Say';

export type UserDemographicsDto = {
    userId: string;
    firstName: string;
    middleInitial?: string;
    lastName: string;
    dateOfBirth: Date;
    gender: Gender;
    address: string;
    city: string;
    state: string;
    postalCode: string;
    country: string;
    additionalInfo?: string;
};
