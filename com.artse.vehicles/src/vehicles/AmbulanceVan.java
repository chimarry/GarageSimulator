package vehicles;

import util.Identification;

public class AmbulanceVan extends Van implements AmbulanceVehicle {

    private EmergencyHandler.Rotation rotation;
    public AmbulanceVan( Identification identification ){
        this(identification, Van.STANDARD_DEADWEIGHT_TONNAGE);
    }

    public AmbulanceVan( Identification identification, int deadweight){
        super(identification,deadweight);
        setRotation(EmergencyHandler.Rotation.OFF);
    }
    @Override
    public void setRotation( EmergencyHandler.Rotation aRotation) {
        rotation =aRotation;
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
