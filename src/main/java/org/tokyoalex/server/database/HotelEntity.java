package org.tokyoalex.server.database;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class HotelEntity implements Serializable {
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Integer rooms_available;

    @Getter
    @Setter
    private Integer max_capacity;

    @Getter
    @Setter
    private String location;

    public HotelEntity()    {}
    public HotelEntity(String name, Integer available, Integer capacity, String location)    {
        this.name = name;
        this.rooms_available = available;
        this.max_capacity = capacity;
        this.location = location;
    }
}
