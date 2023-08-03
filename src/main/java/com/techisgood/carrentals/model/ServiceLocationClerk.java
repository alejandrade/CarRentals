package com.techisgood.carrentals.model;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "service_location_clerk", uniqueConstraints = {
        @UniqueConstraint(name = "service_location_clerk_key", columnNames = {"location_id", "clerk_id"})
})
public class ServiceLocationClerk {

    @EmbeddedId
    private ServiceLocationClerkId id = new ServiceLocationClerkId();

    @MapsId("clerkId")  // map to the field in the composite key
    @ManyToOne
    @JoinColumn(name = "clerk_id", columnDefinition = "char(36) default (uuid())", referencedColumnName = "id", insertable = false, updatable = false)
    private DbUser clerk;

    @MapsId("locationId")  // map to the field in the composite key
    @ManyToOne
    @JoinColumn(name = "location_id", columnDefinition = "char(36) default (uuid())", referencedColumnName = "id", insertable = false, updatable = false)
    private ServiceLocation serviceLocation;

    public void setClerk(DbUser clerk) {
        this.clerk = clerk;
        if(clerk != null) {
            this.id.setClerkId(clerk.getId());
        }
    }

    public void setServiceLocation(ServiceLocation serviceLocation) {
        this.serviceLocation = serviceLocation;
        if(serviceLocation != null) {
            this.id.setServiceLocationId(serviceLocation.getId());
        }
    }

    public String getServiceLocationId() {
        return this.serviceLocation.getId();
    }

    // other fields and methods if needed
}
