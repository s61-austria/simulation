package logic;

import domain.Vehicle;
import rest.RestClient;
import serializers.ProfileSerializer;

import java.util.HashMap;
import java.util.List;

public class VehicleLogic {
    private HashMap<String, Vehicle> vehicles = new HashMap<>();
    private RestClient restClient = new RestClient();
    private ProfileSerializer user = restClient.getAllUsers()[0];

    public void update(List<Vehicle> vehicles) {
        checkForNewVehciles(vehicles);

        for (Vehicle vehicle: vehicles) {
            Vehicle vehicleToUpdate = this.vehicles.get(vehicle.getId());

            vehicleToUpdate.setCurrentLat(vehicle.getCurrentLat());
            vehicleToUpdate.setCurrentLon(vehicle.getCurrentLon());
        }
    }

    private void checkForNewVehciles(List<Vehicle> vehicles) {
        for (Vehicle vehicle: vehicles) {
            if (!this.vehicles.containsKey(vehicle.getId())) {
                Vehicle newVehicle = restClient.createJsonVehicle(vehicle);
                newVehicle.setCurrentLat(vehicle.getCurrentLat());
                newVehicle.setCurrentLon(vehicle.getCurrentLon());
                newVehicle.setId(vehicle.getId());

                this.vehicles.put(newVehicle.getId(), newVehicle);
            }
        }
    }
}
