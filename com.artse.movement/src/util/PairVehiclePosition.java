package util;

import garage.structure.util.GaragePosition;
import movement.GarageVehicle;

public class PairVehiclePosition {
    private GarageVehicle vehicle;
    private GaragePosition position;

    public GarageVehicle getVehicle() {
        return vehicle;
    }
   public GaragePosition getPosition(){
        return  position;
   }
    public PairVehiclePosition( GarageVehicle vehicle, GaragePosition position){
        this.vehicle = vehicle;
        this.position = position;
    }

    public void setPosition( GaragePosition position ) {
        this.position = position;
    }


}
