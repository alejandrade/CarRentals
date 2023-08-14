// UserDto.ts

import {ServiceLocationDto} from "../service_location/ServiceLocationService.types";

export interface UserDto {
    id: string;
    email: string;
    phoneNumber: string;
    isEmailAuth: boolean;
    enabled: boolean;
    accountNonExpired: boolean;
    credentialsNonExpired: boolean;
    accountNonLocked: boolean;
    version?: number;
    serviceLocation?: ServiceLocationDto;
    serviceLocationId?: string;
    authorities?: string[];
    userLicenses?: UserLicenseDto[];
    userInsurances?: UserInsuranceDto[];
    userDemographics?: UserDemographicsDto;
}

// UserLicenseDto.ts

export interface UserLicenseDto {
    id: string;
    userId: string;
    licenseNumber: string;
    issuingState: string;
    dateOfIssue: Date;
    expirationDate: Date;
    licenseClass: string;
    backCardPictureKey: string;
    frontCardPictureKey: string;
    active: boolean;
}

// UserInsuranceDto.ts

export interface UserInsuranceDto {
    id: string;
    userId: string;
    policyNumber: string;
    provider: string;
    frontCardPictureKey: string;
    backCardPictureKey: string;
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

export interface UpdateContactInformation {
    userId: string;
    username: string;
}

export type UserWithDetailsDto = {
    id: string;
    email: string;
    phoneNumber: string;
    firstName: string;
    lastName: string;
    authorities: string[];
    serviceLocationIds: string[];
};

export interface Paginated<T> {
    content: T[];
    pageable: {
        sort: {
            sorted: boolean;
            unsorted: boolean;
            empty: boolean;
        };
        offset: number;
        pageNumber: number;
        pageSize: number;
        paged: boolean;
        unpaged: boolean;
    };
    totalPages: number;
    totalElements: number;
    last: boolean;
    numberOfElements: number;
    first: boolean;
    size: number;
    number: number;
    sort: {
        sorted: boolean;
        unsorted: boolean;
        empty: boolean;
    };
    empty: boolean;
}


export const userDemographicKeys = ["userId", "firstName", "middleInitial", "lastName", "dateOfBirth", "gender", "address", "city", "state", "postalCode", "country", "additionalInfo"];
