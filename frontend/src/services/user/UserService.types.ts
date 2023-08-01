// UserDto.ts

export interface UserDto {
    id: string;
    email: string;
    phoneNumber: string;
    isEmailAuth: boolean;
    enabled: boolean;
    accountNonExpired: boolean;
    credentialsNonExpired: boolean;
    accountNonLocked: boolean;
    authorities: string[];
    userLicenses: UserLicenseDto[];
    userInsurances: UserInsuranceDto[];
    userDemographics: UserDemographicsDto;
}

// UserLicenseDto.ts

export interface UserLicenseDto {
    userId: string;
    licenseNumber: string;
    issuingState: string;
    dateOfIssue: Date;
    expirationDate: Date;
    licenseClass: string;
    backCardPicture: string;
    frontCardPicture: string;
    active: boolean;
}

// UserInsuranceDto.ts

export interface UserInsuranceDto {
    userId: string;
    policyNumber: string;
    provider: string;
    frontCardPicture: string;
    backCardPicture: string;
    endDate: Date;
    active: boolean;
}

// UserDemographicsDto.ts

export enum Gender {
    MALE = 'MALE',
    FEMALE = 'FEMALE',
    OTHER = 'OTHER',
}

export interface UserDemographicsDto {
    userId: string;
    firstName: string;
    middleInitial?: string;
    lastName: string;
    dateOfBirth?: Date;
    gender?: Gender;
    address?: string;
    city?: string;
    state?: string;
    postalCode?: string;
    country?: string;
    additionalInfo?: string;
}
