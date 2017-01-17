package java.Immutable_Object;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jamesmsw on 17-1-17.
 */
public class VehicleTracker {
    private Map<String,SafeLocation> locMap=new ConcurrentHashMap<String,SafeLocation>();

    public void updateLocation(String vehicleId,SafeLocation newLocation){
        locMap.put(vehicleId,newLocation);
    }
}
