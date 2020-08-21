package vehicles;

public interface AmbulanceVehicle extends EmergencyHandler {
    default   String getSymbol(){
        if(getRotation() == Rotation.OFF)
            return VehicleSymbol.A.toString();
        return VehicleSymbol.AR.toString();
    }
}
