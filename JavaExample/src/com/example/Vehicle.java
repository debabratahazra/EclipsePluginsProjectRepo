package com.example;

import java.util.ArrayList;
import java.util.List;

import jdk.nashorn.internal.objects.annotations.Getter;

import com.example.annotation.Entity;
import com.example.annotation.Optional;
import com.example.annotation.Persistent;

/**
 * Annotation example
 * @author DEBABRATA
 *
 */
@Entity
public class Vehicle {

    @Persistent
    protected String vehicleName = null;


    @Getter
    public String getVehicleName() {
        return this.vehicleName;
    }

    public void setVehicleName(@Optional String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public List<String> addVehicleNameToList(List<String> names) {

        @Optional
        List<String> localNames = names;

        if(localNames == null) {
            localNames = new ArrayList<>();
        }
        localNames.add(getVehicleName());

        return localNames;
    }

}