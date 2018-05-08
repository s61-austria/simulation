import de.tudresden.sumo.cmd.Edge;
import de.tudresden.sumo.cmd.Simulation;
import de.tudresden.sumo.cmd.Vehicle;
import it.polito.appeal.traci.*;

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
                //get the CO2 emission for a specific edge

                double co2 = (double) conn.do_job_get(Edge.getCO2Emission("gneE0"));
                System.out.println("timestep: " + i + " " + co2 + " g/s");

                //current simulation time
                int simtime = (int) conn.do_job_get(Simulation.getCurrentTime());

                //add new vehicle
                conn.do_job_set(Vehicle.add("veh"+i, "car", "s1", simtime, 0, 13.8, (byte) 1));
                conn.do_timestep();
            }

            //stop TraCI
            conn.close();

        }catch(Exception ex){ex.printStackTrace();}

    }
}
