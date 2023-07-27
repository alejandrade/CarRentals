package com.techisgood.carrentals.model.audit;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class Versioned {

    @Version
    @Column(name = "version", nullable = false, columnDefinition = "int default 0")
    private int version = 0;
}
