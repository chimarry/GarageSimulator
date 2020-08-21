package vehicles;

import util.Identification;

public class PoliceCar extends Car implements PoliceVehicle {

    public PoliceCar( Identification identification ){
        this(identification,DEFAULT_DOOR_COUNT);
    }

    public PoliceCar( Identification identification, int doorNo ){
        super(identification,doorNo);
        setRotation(EmergencyHandler.Rotation.OFF);
    }
    private EmergencyHandler.Rotation rotation;

    @Override
    public void drive(){
        if(rotation==Rotation.ON)
            accelerate();
        else super.drive();
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

}
