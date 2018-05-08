import de.tudresden.sumo.cmd.Edge;
import de.tudresden.sumo.cmd.Simulation;
import de.tudresden.sumo.cmd.Vehicle;
import de.tudresden.ws.container.SumoPosition2D;
import it.polito.appeal.traci.*;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.Collection;

public class Run {

    static String sumo_bin = "C:\\Program Files (x86)\\DLR\\Sumo\\bin\\sumo-gui.exe";
    static final String config_file = "C:\\Users\\Jandie\\Desktop\\simulation\\maps\\test\\osm.sumocfg";

    public static void main(String[] args) {

        //start Simulation
        SumoTraciConnection conn = new SumoTraciConnection(sumo_bin, config_file);

        //set some options
        conn.addOption("step-length", "0.1"); //timestep 1 second

        try{

            //start TraCI
            conn.runServer();

            //load routes and initialize the simulation
            conn.do_timestep();

            for(int i=0; i<3600; i++){

                //add new vehicle
                Collection<String> vehicles = (Collection<String>) conn.do_job_get(Vehicle.getIDList());
                if (vehicles.size() > 0) {
                    System.out.println(Arrays.toString(vehicles.toArray()));

                    SumoPosition2D pos =
                            (SumoPosition2D) conn.do_job_get(
                                    Vehicle.getPosition(vehicles.toArray()[0].toString()));

                    pos = (SumoPosition2D) conn.do_job_get(Simulation.convertGeo(pos.x, pos.y, false));
                    System.out.println("x: " + pos.x + " y: " + pos.y);
                }

                conn.do_timestep();
            }

            //stop TraCI
            conn.close();

        }catch(Exception ex){ex.printStackTrace();}

    }
}
