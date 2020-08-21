package logic.services;

import garage.structure.util.GaragePosition;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.util.Pair;
import movement.EmergencyGarageVehicle;
import movement.PathFinder;
import movement.thread_safe.SynchronizedGarageModel;
import util.PairVehiclePosition;
import vehicles.AmbulanceVehicle;
import vehicles.EmergencyHandler;
import vehicles.FiremenVehicle;
import vehicles.PoliceVehicle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


public final class EmergencyService {
    private static final EmergencyService ourInstance = new EmergencyService();
    private final List<PairVehiclePosition> policeVehicles = new ArrayList<>();
    private final List<PairVehiclePosition> ambulanceVehicles = new ArrayList<>();
    private final List<PairVehiclePosition> firemenVehicles = new ArrayList<>();

    public static ObjectProperty<ArrayList<Integer>> allowedDriving = new SimpleObjectProperty<>(new ArrayList<>());
    private List<EmergencyGarageVehicle> getClosestVehicles( GaragePosition position ) {
        List<EmergencyGarageVehicle> arrayList = new ArrayList<>();
        getClosestVehicle(position, policeVehicles).ifPresent(x -> arrayList.add((EmergencyGarageVehicle)x.getVehicle()));
        getClosestVehicle(position, ambulanceVehicles).ifPresent(x -> arrayList.add((EmergencyGarageVehicle)x.getVehicle()));
        getClosestVehicle(position, firemenVehicles).ifPresent(x -> arrayList.add((EmergencyGarageVehicle)x.getVehicle()));
        return arrayList;
    }

    private Optional<PairVehiclePosition> getClosestVehicle( GaragePosition position, final List<PairVehiclePosition> list ) {
        list.forEach(pairVehiclePosition -> pairVehiclePosition.setPosition(((EmergencyGarageVehicle)pairVehiclePosition.getVehicle()).getVehiclePosition()));
        return list.parallelStream().min(Comparator.comparingInt(pairVehiclePosition -> PathFinder.getPath(pairVehiclePosition.getPosition(), position).size()));
    }

    public void handleEmergency( GaragePosition accidentPosition ) {
        List<EmergencyGarageVehicle> vehicles = getClosestVehicles(accidentPosition);
        allowedDriving.getValue().add(accidentPosition.getLevel());
        for (EmergencyGarageVehicle vehicle : vehicles
        ) {
           // vehicle.getMoveGenerator().setEmergencyAlarm(new Pair<>(Boolean.TRUE, accidentPosition));
        }

    }

    public void registerVehicle( GaragePosition position, EmergencyGarageVehicle vehicle ) {
        PairVehiclePosition newPair = new PairVehiclePosition(vehicle, position);
        if (vehicle.getVehicle() instanceof AmbulanceVehicle)
            ambulanceVehicles.add(newPair);
        else if (vehicle.getVehicle() instanceof PoliceVehicle)
            policeVehicles.add(newPair);
        else if (vehicle.getVehicle() instanceof FiremenVehicle)
            firemenVehicles.add(newPair);
    }

    public void unregisterVehicle( GaragePosition position, EmergencyHandler vehicle ) {
        if (vehicle instanceof AmbulanceVehicle) expel(vehicle, ambulanceVehicles);
        else if (vehicle instanceof PoliceVehicle) expel(vehicle, policeVehicles);
        else if (vehicle instanceof FiremenVehicle) expel(vehicle, firemenVehicles);
    }

    private void expel( EmergencyHandler vehicle, List<PairVehiclePosition> list ) {
        list.removeIf(pairVehiclePosition -> ((EmergencyGarageVehicle)pairVehiclePosition.getVehicle()).getVehicle().equals(vehicle));
    }
    public static EmergencyService getInstance() {
        return ourInstance;
    }

    private EmergencyService() {
    }

}
