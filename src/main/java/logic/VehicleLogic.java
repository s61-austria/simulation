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
    private String token = restClient.login(
            "Jandie Hendriks",
            "password1");

    public void update(List<Vehicle> vehicles) {
        checkForNewVehicles(vehicles);

        for (Vehicle vehicle: vehicles) {
            Vehicle vehicleToUpdate = this.vehicles.get(vehicle.getId());

            vehicleToUpdate.setCurrentLat(vehicle.getCurrentLat());
            vehicleToUpdate.setCurrentLon(vehicle.getCurrentLon());

            restClient.pingVehicleLocation(vehicleToUpdate, token);
        }
    }

    private void checkForNewVehicles(List<Vehicle> vehicles) {
        for (Vehicle vehicle: vehicles) {
            if (!this.vehicles.containsKey(vehicle.getId())) {
                boolean success = false;
                do {
                    try {
                        Vehicle newVehicle = restClient.createJsonVehicle(vehicle);

                        newVehicle.setCurrentLat(vehicle.getCurrentLat());
                        newVehicle.setCurrentLon(vehicle.getCurrentLon());
                        newVehicle.setId(vehicle.getId());

                        restClient.addVehicleToUser(newVehicle, user.getUuid());

                        this.vehicles.put(newVehicle.getId(), newVehicle);

                        success = true;
                    } catch (Exception e) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                        success = false;
                    }
                } while (!success);
            }
        }
    }
}
