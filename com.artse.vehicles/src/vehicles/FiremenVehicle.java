package vehicles;

public interface FiremenVehicle extends EmergencyHandler {
    default  public String getSymbol(){
        if(getRotation() == Rotation.OFF)
            return VehicleSymbol.F.toString();
        return VehicleSymbol.FR.toString();
    }
}
