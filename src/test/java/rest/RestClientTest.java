package rest;

import domain.Vehicle;
import org.junit.Assert;
import org.junit.Test;

public class RestClientTest {

    @Test
    public void createVehicleTest() {
        RestClient restClient = new RestClient();
        Vehicle vehicle = new Vehicle("", "",
                0, 0);

        Vehicle vehicle1 = restClient.createJsonVehicle(vehicle);

        Assert.assertNotNull(vehicle1);
    }
}
