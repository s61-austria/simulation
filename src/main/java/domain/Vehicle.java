package domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import domain.enums.VehicleType;

import java.io.Serializable;

public class Vehicle implements Serializable {
    private String id;
    private String uuid;
    private String hardwareSerialNumber;
    private String licensePlate;
    private VehicleType vehicleType;

    @JsonIgnore
    private double currentLat;
    @JsonIgnore
    private double currentLon;

    @JsonIgnore
    private String rate;
    @JsonIgnore
    private String currentLocation;
    @JsonIgnore
    private String locations;
    @JsonIgnore
    private String owner;


    public Vehicle(String id, String uuid, double currentLat, double currentLon) {
        this.id = id;
        this.uuid = uuid;
        this.currentLat = currentLat;
        this.currentLon = currentLon;
    }

    public Vehicle() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getHardwareSerialNumber() {
        return hardwareSerialNumber;
    }

    public void setHardwareSerialNumber(String hardwareSerialNumber) {
        this.hardwareSerialNumber = hardwareSerialNumber;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public double getCurrentLat() {
        return currentLat;
    }

    public void setCurrentLat(double currentLat) {
        this.currentLat = currentLat;
    }

    public double getCurrentLon() {
        return currentLon;
    }

    public void setCurrentLon(double currentLon) {
        this.currentLon = currentLon;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
