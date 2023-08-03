package com.techisgood.carrentals.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ServiceLocationClerkId implements Serializable {

    @Column(name = "location_id", columnDefinition = "char(36)", nullable = false, length = 36)
    private String locationId;

    @Column(name = "clerk_id", columnDefinition = "char(36)", nullable = false, length = 36)
    private String clerkId;

    // getters, setters, hashCode, and equals methods
}

