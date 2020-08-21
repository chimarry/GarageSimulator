package logic.services;

import garage.structure.model.GarageModel;
import garage.structure.model.PlatformField;
import garage.structure.util.GaragePosition;
import garage.structure.util.Position;
import garage.structure.util.configuration.GarageConfig;

import javafx.beans.property.ObjectProperty;
import javafx.util.Pair;
import logic.smart.SmartPlatform;
import movement.GarageVehicle;
import movement.PathFinder;
import util.PairVehiclePosition;
import util.ParkingFieldsComparator;
import util.Request;
import vehicles.Vehicle;
import vehicles.Wheelable;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class GarageOfficer {
    private static GarageModel model = GarageModel.getInstance();
    private static GarageOfficer ourInstance = new GarageOfficer();
    private static EmergencyService emergencyService = EmergencyService.getInstance();
    private PriorityBlockingQueue<GaragePosition> parkingFields = new PriorityBlockingQueue<>(120, Comparator.comparing(GaragePosition::getLevel).thenComparing(ParkingFieldsComparator.comparator));
    private ArrayList<ArrayList<PairVehiclePosition>> parkedVehicles = new ArrayList<>();
    private HashMap<String, Pair<LocalDateTime, LocalDateTime>> vehiclesEnteringTime = new HashMap<>();

    public void addTimeOfEnteringGarage( Wheelable wheelable, LocalDateTime date ) {
        try {
            String registrationNumber = ((Vehicle) wheelable).getIdentification().getRegistrationNumber();
            vehiclesEnteringTime.put(registrationNumber, new Pair<>(date, null));
        } catch (NullPointerException ex) {
            System.out.println((Vehicle) wheelable);
        }
    }

    public void addTimeOfLeavingGarage( Wheelable wheelable, LocalDateTime date ) {
        String registrationNumber = ((Vehicle) wheelable).getIdentification().getRegistrationNumber();
        LocalDateTime dat = vehiclesEnteringTime.get(registrationNumber).getKey();
        vehiclesEnteringTime.put(registrationNumber, new Pair<>(dat, date));
        chargeService(registrationNumber);
    }

    private void chargeService( String regNumber ) {
        LocalDateTime enterDate = vehiclesEnteringTime.get(regNumber).getKey();
        LocalDateTime exitDate = vehiclesEnteringTime.get(regNumber).getValue();
        Long hours = ChronoUnit.SECONDS.between(enterDate, exitDate) / 10;
        LocalDateTime imaginaryDate = exitDate.plusHours(hours);
        Integer charge = hours < 1 ? 1 : (hours < 3 ? 3 : 8);
        GarageCSVWriter.writeToCSV(regNumber, enterDate.toString(), imaginaryDate.toString(), hours.toString(), charge.toString());
    }

    public int getFreeParkingFieldsNo() {
        return parkingFields.size();
    }

    public static GarageOfficer getInstance() {
        return ourInstance;
    }

    private GarageOfficer() {
        mapParkingFields();
        for (int i = 0; i < GarageConfig.getPlatformCount(); ++i)
            parkedVehicles.add(new ArrayList<>());
    }

    private void mapParkingFields() {
        ArrayList<GaragePosition> positions;
        for (int i = 0; i < GarageModel.PLATFORM_COUNT; ++i) {
            final int j = i;
            ConcurrentHashMap<Position, ObjectProperty<PlatformField>> plat = model.getPlatform(j).getPlatform();
            Stream<Map.Entry<Position, ObjectProperty<PlatformField>>> stream = plat.entrySet().stream();
            positions = stream.filter(x -> x.getValue().get().isParkingField()).map(platformPosition ->
                    new GaragePosition(j, platformPosition.getKey().row, platformPosition.getKey().col)).collect(Collectors.toCollection(ArrayList::new));
            parkingFields.addAll(positions);
        }
    }

    public synchronized void addParkedVehicle( GaragePosition position, GarageVehicle vehicle ) {
        parkedVehicles.get(position.getLevel()).add(new PairVehiclePosition(vehicle, position));
    }

    public synchronized GaragePosition getFreeParkingField( GaragePosition startPosition ) {
        Optional<GaragePosition> parkField;
        int i = 0;
        do {
            final int j = i;
            parkField = parkingFields.stream().filter(pos -> pos.getLevel() + j == startPosition.getLevel()).collect(Collectors.toCollection(ArrayList::new)).stream().min(Comparator.comparingInt(position-> PathFinder.getPath(startPosition, position).size()));
            i++;
        }
        while (!parkField.isPresent() || i == GarageConfig.getPlatformCount());
        if(i==GarageConfig.getPlatformCount())
            return  null;
        parkingFields.remove(parkField.get());
        return parkField.get();
    }

    private void addParkingSpace( GaragePosition position ) {
        parkingFields.add(position);
    }

    public void throwOutVehicles() {
        for (int i = 0; i < GarageConfig.getPlatformCount(); ++i) {
            Random random = new Random();
            ArrayList<PairVehiclePosition> vehicles = parkedVehicles.get(i);
            int size = vehicles.size();
            int count = 0;
            if (size == 0) return;
            while (count < size * (0.15)) {
                int index = random.nextInt(vehicles.size());
                vehicles.get(index).getVehicle().exit(vehicles.get(index).getPosition());
                addParkingSpace(vehicles.remove(index).getPosition());
                count++;
            }
        }
    }

    public void callEmergencyService( GaragePosition position ) {
        emergencyService.handleEmergency(position);
    }

    public GaragePosition getNeighbourPosition( GaragePosition garagePosition ) {
        return new GaragePosition(garagePosition.getLevel(), SmartPlatform.getNextPosition(garagePosition.getPosition(), Request.GO_CICRULLAR));
    }
}


