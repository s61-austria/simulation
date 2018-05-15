package rest;

import com.kontofahren.datenvertrag.LocationUpdateSerializer;
import domain.Vehicle;
import domain.enums.VehicleType;
import utils.JsonUtil;
import utils.RandomString;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.SecureRandom;

public class RestClient {
    private static final String REST_URI =
            "http://localhost:8080/government/api";
    private Client client = ClientBuilder.newClient();

    public Vehicle createJsonVehicle(Vehicle vehicle) {
        RandomString gen = new RandomString(10, new SecureRandom());
        vehicle.setHardwareSerialNumber(gen.nextString());
        vehicle.setLicensePlate(gen.nextString());
        vehicle.setVehicleType(VehicleType.PKW);

        Response response = client.target(REST_URI).path("vehicles")
                .queryParam("serialNumber",
                        vehicle.getHardwareSerialNumber())
                .queryParam("vehicleType",
                        vehicle.getVehicleType().toString())
                .queryParam("licensePlate",
                        vehicle.getLicensePlate())
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity("", MediaType.APPLICATION_JSON));

        return new JsonUtil().decode(response.readEntity(String.class), Vehicle.class);
    }

    public void pingVehicleLocation(Vehicle vehicle) {
        LocationUpdateSerializer locationUpdateSerializer =
                new LocationUpdateSerializer(vehicle.getUuid(),
                        vehicle.getCurrentLat(),
                        vehicle.getCurrentLon(),
                        null);

        Response response = client.target(REST_URI).path("vehicles")
                .queryParam("serialNumber",
                        vehicle.getHardwareSerialNumber())
                .queryParam("vehicleType",
                        vehicle.getVehicleType().toString())
                .queryParam("licensePlate",
                        vehicle.getLicensePlate())
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity("", MediaType.APPLICATION_JSON));
    }
}
