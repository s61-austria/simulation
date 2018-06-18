package rest;

import com.kontofahren.datenvertrag.LocationUpdateSerializer;
import domain.Vehicle;
import domain.enums.VehicleType;
import serializers.LoginSerializer;
import serializers.ProfileSerializer;
import serializers.VehicleSerializer;
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
            "http://192.168.24.42:8080/government/api";
    private Client client = ClientBuilder.newClient();

    public String login(String username, String password) {
        LoginSerializer loginSerializer = new LoginSerializer();
        loginSerializer.setUsername(username);
        loginSerializer.setPassword(password);

        System.out.println(new JsonUtil().encode(loginSerializer));

        Response response = client.target(REST_URI).path("auth")
                .path("login")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(
                        new JsonUtil().encode(loginSerializer),
                        MediaType.APPLICATION_JSON));

        System.out.println(response.getStatus());

        return response.readEntity(String.class);
    }

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

    public ProfileSerializer[] getAllUsers() {
        Response response = client.target(REST_URI)
                .path("profiles")
                .request(MediaType.APPLICATION_JSON)
                .get();

        return new JsonUtil().decode(
                response.readEntity(String.class),
                ProfileSerializer[].class);
    }

    public Response addVehicleToUser(Vehicle vehicle, String ownerUuid) {
        VehicleSerializer vehicleSerializer = new VehicleSerializer();
        vehicleSerializer.setUuid(vehicle.getUuid());
        vehicleSerializer.setLicensePlate(vehicle.getLicensePlate());
        vehicleSerializer.setOwner(ownerUuid);

        Response response = client.target(REST_URI)
                .path("vehicles")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(
                        new JsonUtil().encode(vehicleSerializer),
                        MediaType.APPLICATION_JSON));

        return response;
    }

    public void pingVehicleLocation(Vehicle vehicle, String token) {
        try {
            LocationUpdateSerializer locationUpdateSerializer =
                    new LocationUpdateSerializer(vehicle.getUuid(),
                            vehicle.getCurrentLat(),
                            vehicle.getCurrentLon(),
                            null);

            client.target(REST_URI)
                    .path("locations")
                    .request(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer " + token)
                    .post(Entity.entity(
                            new JsonUtil().encode(locationUpdateSerializer),
                            MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
