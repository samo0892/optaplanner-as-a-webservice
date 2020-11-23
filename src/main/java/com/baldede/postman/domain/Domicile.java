package com.baldede.postman.domain;

import com.baldede.postman.domain.*;
import com.baldede.postman.persistence.AbstractPersistable;

public class Domicile extends AbstractPersistable implements Standstill {

    private Location location;
    private String name;

    @Override
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // ************************************************************************
    // Complex methods
    // ************************************************************************

    /**
     * @param standstill never null
     * @return a positive number, the distance multiplied by 1000 to avoid floating point arithmetic rounding errors
     */
    @Override
    public long getDistanceTo(Standstill standstill) {
        return location.getDistanceTo(standstill.getLocation());
    }

    @Override
    public String toString(){
        return getName();
    }

}
