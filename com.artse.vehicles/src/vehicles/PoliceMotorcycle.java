package vehicles;

import util.Identification;

public class PoliceMotorcycle extends Motorcycle implements PoliceVehicle {

   private EmergencyHandler.Rotation rotation;
     public PoliceMotorcycle( Identification identification ){
         super(identification);
         setRotation(EmergencyHandler.Rotation.OFF);
     }
    @Override
    public void setRotation( EmergencyHandler.Rotation aRotation ) {
        rotation = aRotation;
    }


    @Override
    public String getSymbol() {
        return PoliceVehicle.super.getSymbol();
    }

    @Override
    public Rotation getRotation() {
        return rotation;
    }
    @Override
    public void drive(){
        if(rotation==Rotation.ON)
            accelerate();
        else super.drive();
    }
}
