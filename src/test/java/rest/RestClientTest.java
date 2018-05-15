package rest;

import domain.Vehicle;
import org.junit.Assert;
import org.junit.Test;
import serializers.ProfileSerializer;

import javax.ws.rs.core.Response;

public class RestClientTest {

    @Test
    public void createVehicleTest() {
        RestClient restClient = new RestClient();
        Vehicle vehicle = new Vehicle("", "",
                0, 0);

        Vehicle vehicle1 = restClient.createJsonVehicle(vehicle);

        Assert.assertNotNull(vehicle1);
    }

    @Test
    public void loginTest() {
        RestClient restClient = new RestClient();
        String token = restClient.login(
                "Jandie Hendriks",
                "password1");

        System.out.println(token);

        Assert.assertNotNull(token);
        Assert.assertTrue(!token.trim().isEmpty());
    }

    @Test
    public void getAllUsersTest() {
        RestClient restClient = new RestClient();

        ProfileSerializer[] users = restClient.getAllUsers();

        Assert.assertEquals(2, users.length);
    }

    @Test
    public void addVehicleToUserTest() {
        RestClient restClient = new RestClient();
        Vehicle vehicle = new Vehicle("", "",
                0, 0);

        vehicle = restClient.createJsonVehicle(vehicle);

        ProfileSerializer user = restClient.getAllUsers()[0];

        System.out.println(user.getKontoUser().getUserName());

        Response response =
                restClient.addVehicleToUser(vehicle, user.getUuid());

        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void pingLocationTest() {
        RestClient restClient = new RestClient();
        String token = restClient.login(
                "Jandie Hendriks",
                "password1");

        Vehicle vehicle = new Vehicle("", "",
                0, 0);

        vehicle = restClient.createJsonVehicle(vehicle);

        ProfileSerializer user = restClient.getAllUsers()[0];

        System.out.println(user.getKontoUser().getUserName());

        Response response =
                restClient.addVehicleToUser(vehicle, user.getUuid());

        Assert.assertEquals(200, response.getStatus());

        vehicle.setCurrentLat(45.66);
        vehicle.setCurrentLon(7.59);

        response = restClient.pingVehicleLocation(vehicle, token);

        Assert.assertEquals(200, response.getStatus());
    }

}
