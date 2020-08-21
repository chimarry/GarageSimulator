package vehicles;

public interface PoliceVehicle extends EmergencyHandler {

  // PoliceService POLICE_REGISTRY_SERVICE= PoliceService.getInstance();
   default  String getSymbol(){
      if(getRotation() == Rotation.OFF)
         return VehicleSymbol.P.toString();
      return VehicleSymbol.PR.toString();
   }

}
