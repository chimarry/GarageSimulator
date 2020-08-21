package vehicles;
import util.Identification;

public class FiremenVan extends Van implements FiremenVehicle {

    public FiremenVan( Identification identification ){
        this(identification, Van.STANDARD_DEADWEIGHT_TONNAGE);
    }

    public FiremenVan( Identification identification, int deadweight){
        super(identification,deadweight);
        setRotation(EmergencyHandler.Rotation.OFF);
    }
    private EmergencyHandler.Rotation rotation;
    @Override
    public void setRotation( EmergencyHandler.Rotation aRotation ) {
        rotation = aRotation;
    }

    @Override
    public String getSymbol() {
        return FiremenVehicle.super.getSymbol();
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
