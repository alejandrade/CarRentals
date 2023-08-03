package com.techisgood.carrentals.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "service_location_clerk", uniqueConstraints = {
        @UniqueConstraint(name = "service_location_clerk_key", columnNames = {"location_id", "clerk_id"})
})
public class ServiceLocationClerk {

    @EmbeddedId
    private ServiceLocationClerkId id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId("clerkId")  // map to the field in the composite key
    @JoinColumn(name = "clerk_id", referencedColumnName = "id", insertable = false, updatable = false, columnDefinition = "char(36)")
    private DbUser clerk;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId("locationId")  // map to the field in the composite key
    @JoinColumn(name = "location_id", referencedColumnName = "id", insertable = false, updatable = false, columnDefinition = "char(36)")
    private ServiceLocation serviceLocation;

    // other fields and methods if needed
}
