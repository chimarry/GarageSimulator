package movement;

import garage.structure.util.GaragePosition;
import javafx.util.Pair;
import logic.services.EmergencyService;
import util.EmergencyException;
import vehicles.EmergencyHandler;
import vehicles.Wheelable;

import java.util.ArrayList;
import java.util.List;

public class EmergencyGarageVehicle extends GarageVehicle {

    private final EmergencyService emergencyService = EmergencyService.getInstance();

    public EmergencyHandler getVehicle() {

        return (EmergencyHandler) wheelable;
    }

    public EmergencyGarageVehicle( Wheelable wheelable ) {
        this(wheelable, GarageVehicle.GARAGE_ENTRY);
    }

    public EmergencyGarageVehicle( Wheelable wheelable, GaragePosition startPosition ) {
        super(wheelable, startPosition);
        emergencyService.registerVehicle(startPosition, this);
    }

    public void handleEmergency( GaragePosition position ) {
        List<GaragePosition> path = new ArrayList<>(PathFinder.getPath(vehiclePosition, position));
        final int first = 0;
        while (!path.isEmpty()) {
            GaragePosition garagePosition = path.get(first);
            vehiclePosition = model.setFields(vehiclePosition, garagePosition, this);
            int last = path.indexOf(vehiclePosition);
            if (last != -1)
                path.subList(first, last + 1).clear();
            else path.clear();
            wheelable.drive();
        }

        ((EmergencyHandler) wheelable).handleEmergency();
    }

    @Override
    public void run() {
        try {
            enter(startPosition);

        } catch (EmergencyException exception) {
        }
    }
}
