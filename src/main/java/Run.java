import it.polito.appeal.traci.*;
import org.osgeo.proj4j.BasicCoordinateTransform;
import org.osgeo.proj4j.CRSFactory;
import org.osgeo.proj4j.CoordinateReferenceSystem;
import org.osgeo.proj4j.ProjCoordinate;

import java.awt.geom.Point2D;
import java.util.Collection;

public class Run {
    public static void main(String[] args) {
        SumoTraciConnection conn = new SumoTraciConnection(
                "./maps/test/osm.sumocfg",  // config file
                12345                                  // random seed
        );
        try {
            conn.runServer(true);

            System.out.println("Map bounds are: " + conn.queryBounds());

            for (int i = 0; i < 1000000; i++) {
                //Thread.sleep(1000);
                int time = conn.getCurrentSimTime() / 1000;
                Collection<Vehicle> vehicles = conn.getVehicleRepository().getAll().values();

                System.out.println("At time step " + time + ", there are "
                        + vehicles.size() + " vehicles: " + vehicles);

                if (vehicles.size() > 0) {
                    //TODO: Location conversion from cartesian to lat lon still needs to be implemented correctly.
                    Vehicle aVehicle = vehicles.iterator().next();
                    double posX = aVehicle.getPosition().getX();
                    double posY = aVehicle.getPosition().getY();
                    double netOffSetX = -353027.49;
                    double netOffSetY = -5295485.24;
                    posX -= netOffSetX;
                    posY -= netOffSetY;
                    CRSFactory cf = new CRSFactory();
                    CoordinateReferenceSystem crsSrc = cf.createFromName("EPSG:25832");
                    CoordinateReferenceSystem crsDest = cf.createFromName("EPSG:4326");
                    BasicCoordinateTransform basicTransform = new BasicCoordinateTransform(
                            crsSrc, crsDest);
                    ProjCoordinate pcSrc = new ProjCoordinate(posX, posY);
                    ProjCoordinate pcDest = new ProjCoordinate();
                    pcDest = basicTransform.transform(pcSrc, pcDest);
                    Point2D newPosition = new Point2D.Double(pcDest.x, pcDest.y);

                    System.out.println("Vehicle " + aVehicle
                            + " x: "
                            + newPosition.getX()
                            + " x: "
                            + newPosition.getY());
                }

                conn.nextSimStep();
            }

            conn.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
