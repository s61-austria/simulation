import de.tudresden.sumo.cmd.Simulation;
import de.tudresden.sumo.cmd.Vehicle;
import de.tudresden.ws.container.SumoPosition2D;
import it.polito.appeal.traci.*;
import logic.VehicleLogic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Run {

    static String sumo_bin = "C:\\Program Files (x86)\\DLR\\Sumo\\bin\\sumo.exe";
    static final String config_file = "C:\\Users\\Jandie\\IdeaProjects\\simulation\\maps\\test\\osm.sumocfg";

    public static void main(String[] args) {
        VehicleLogic vehicleLogic = new VehicleLogic();
        //start Simulation
        SumoTraciConnection conn = new SumoTraciConnection(sumo_bin, config_file);

        //set some options
        conn.addOption("step-length", "0.1"); //timestep 1 second

        try{
            conn.runServer();

            conn.do_timestep();

            for(int i=0; i<36000; i++){
                //add new vehicle
                Collection<String> vehiclesIds = (Collection<String>) conn.do_job_get(Vehicle.getIDList());

                System.out.println(vehiclesIds.stream().collect(Collectors.joining()));

                if (vehiclesIds.size() > 0) {
                    List<domain.Vehicle> vehicles = new ArrayList<>();

                    for (String vehicleId : vehiclesIds) {
                        SumoPosition2D pos =
                                (SumoPosition2D) conn.do_job_get(
                                        Vehicle.getPosition(vehicleId));
                        pos = (SumoPosition2D) conn.do_job_get(Simulation.convertGeo(pos.x, pos.y, false));
                        System.out.println("lat: " + pos.y + " lon: " + pos.x);

                        vehicles.add(new domain.Vehicle(
                                vehicleId, null, pos.y, pos.x
                        ));

                        vehicleLogic.update(vehicles);
                    }
                }

                for (int x=0; x<100; x++) {
                    conn.do_timestep();
                }
            }

            //stop TraCI
            conn.close();

        }catch(Exception ex){ex.printStackTrace();}

    }
}
