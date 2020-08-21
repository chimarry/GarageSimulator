package vehicles;

import util.Identification;

public class AmbulanceCar extends Car implements AmbulanceVehicle {

    private EmergencyHandler.Rotation rotation;

    public AmbulanceCar( Identification identification ) {
        this(identification, Car.DEFAULT_DOOR_COUNT);
    }

    public AmbulanceCar( Identification identification, int doorNo ) {
        super(identification, doorNo);
        setRotation(EmergencyHandler.Rotation.OFF);
    }

    @Override
    public void setRotation( EmergencyHandler.Rotation aRotation ) {
        rotation = aRotation;
    }

    @Override
    public String getSymbol() {
        return AmbulanceVehicle.super.getSymbol();
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
