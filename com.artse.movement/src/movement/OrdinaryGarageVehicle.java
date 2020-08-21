package movement;


import garage.structure.util.GaragePosition;
import util.EmergencyException;
import vehicles.Wheelable;


public class OrdinaryGarageVehicle extends GarageVehicle {
    public OrdinaryGarageVehicle( Wheelable wheelable ) {
        this(wheelable, GarageVehicle.GARAGE_ENTRY);
    }

    public OrdinaryGarageVehicle( Wheelable wheelable, GaragePosition startPosition ) {
        super(wheelable, startPosition);
    }

    @Override
    public void run() {
        try {
            enter(startPosition);


        } catch (EmergencyException ex) {
            officer.callEmergencyService(ex.getGaragePosition());
        }

    }

}
