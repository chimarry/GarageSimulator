package movement;

import garage.structure.util.GaragePosition;
import vehicles.Wheelable;

public class PoliceGarageVehicle extends EmergencyGarageVehicle {
    public PoliceGarageVehicle( Wheelable wheelable, GaragePosition startPosition) {
        super(wheelable, startPosition);
    }

    @Override
    public void handleEmergency( GaragePosition position ) {
        super.handleEmergency(position);

    }
}
