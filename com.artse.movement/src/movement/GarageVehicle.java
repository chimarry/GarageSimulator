package movement;

import garage.structure.util.GaragePosition;

import logic.services.GarageOfficer;
import logic.smart.SmartPlatform;
import movement.thread_safe.SynchronizedGarageModel;
import movement.thread_safe.VehicleMonitor;
import util.ParkingFieldsComparator;
import util.Request;
import vehicles.Wheelable;

import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class GarageVehicle extends Thread {

    public static final GaragePosition GARAGE_ENTRY = new GaragePosition(0, 1, 0);
    public static final GaragePosition GARAGE_EXIT = new GaragePosition(0, 0, 0);

    protected GaragePosition vehiclePosition;
    protected final Wheelable wheelable;
    protected final GaragePosition startPosition;
    protected final GarageOfficer officer = GarageOfficer.getInstance();

    protected final SynchronizedGarageModel model = SynchronizedGarageModel.getOurInstance();


    public String getSymbol() {
        return wheelable.getSymbol();
    }

    public GaragePosition getVehiclePosition() {
        return vehiclePosition;
    }


    public GarageVehicle( Wheelable wheelable, GaragePosition startPosition ) {
        this.wheelable = wheelable;
        if (ParkingFieldsComparator.isParkingField(startPosition.getPosition()))
            this.startPosition = officer.getNeighbourPosition(startPosition);
        else
            this.startPosition = startPosition;

    }

    public void enter( GaragePosition garagePosition ) {
        GaragePosition position = park(garagePosition);
        if (position != null) {
            officer.addParkedVehicle(position, this);
            wheelable.park();
        }

    }

    protected GaragePosition park( GaragePosition fromPosition ) {
        GaragePosition parkPosition = officer.getFreeParkingField(fromPosition);
        officer.addTimeOfEnteringGarage(wheelable, LocalDateTime.now());
        if (parkPosition == null) {
            return null;
        }

        GaragePosition toPosition = new GaragePosition(parkPosition.getLevel(), SmartPlatform.getNextPosition(parkPosition.getPosition(), Request.GO_CICRULLAR));
        ArrayList<GaragePosition> path = new ArrayList<>(PathFinder.getPath(fromPosition, toPosition));
        GaragePosition position = move(fromPosition, path);
        if (position != toPosition)
            vehiclePosition = toPosition = model.setFields(position, toPosition, this);
        wheelable.drive();
        if (!parkPosition.getPosition().equals(SmartPlatform.getNextPosition(toPosition.getPosition(), Request.PARK))) {
            vehiclePosition = toPosition = model.setFields(toPosition, new GaragePosition(toPosition.getLevel(), SmartPlatform.getNextPosition(toPosition.getPosition(), Request.GO_NEXT)), this);
        }
        vehiclePosition = model.setFields(toPosition, parkPosition, this);
        wheelable.drive();
        return parkPosition;
    }

    protected GaragePosition move( GaragePosition position, ArrayList<GaragePosition> path ) {
        GaragePosition prevPosition;
        GaragePosition currentPosition = position;
        int lastIndex;
        final int first = 0;
        while (!path.isEmpty()) {
            prevPosition = currentPosition;
            currentPosition = path.get(first);
            vehiclePosition = currentPosition = model.setFields(prevPosition, currentPosition, this);
            lastIndex = path.indexOf(currentPosition);
            if (lastIndex != -1)
                path.subList(0, lastIndex + 1).clear();
            else path.clear();
            wheelable.drive();
        }
        return currentPosition;
    }

    public void exit( GaragePosition startPosition ) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                GaragePosition position = move(startPosition, new ArrayList<>(PathFinder.getPath(startPosition, GARAGE_EXIT)));
                model.setFields(position, GARAGE_EXIT, new OrdinaryGarageVehicle(wheelable));
                wheelable.drive();
                model.setPrevField(GARAGE_EXIT);
                officer.addTimeOfLeavingGarage(wheelable, LocalDateTime.now());
            }
        }).start();

    }
}
