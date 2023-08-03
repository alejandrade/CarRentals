package com.techisgood.carrentals.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServiceLocationClerkId implements Serializable {

    @Column(name = "location_id", columnDefinition = "char(36)", nullable = false, length = 36)
    private String serviceLocationId;

    @Column(name = "clerk_id", columnDefinition = "char(36)", nullable = false, length = 36)
    private String clerkId;

    // getters, setters, hashCode, and equals methods
}

