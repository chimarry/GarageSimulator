package simulation;

import business_logic.dto.VehicleDTO;
import controller.AdminGarageController;
import factories.GarageVehicleFactory;
import factories.RandomGarageVehicleFactory;
import garage.structure.controller.GarageController;
import garage.structure.model.GarageModel;
import garage.structure.util.GaragePosition;
import garage.structure.util.configuration.GarageConfig;
import garage.structure.view.GarageView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import logic.services.EmergencyService;
import logic.services.GarageOfficer;
import movement.GarageVehicle;
import movement.thread_safe.SynchronizedGarageModel;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class UserApplication {
    private static EventHandler<ActionEvent> addVehicleEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle( ActionEvent actionEvent ) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    RandomGarageVehicleFactory.createVehicle(GarageController.currentPlatform).start();

                }
            }).start();
        }
    };

    private static EventHandler<ActionEvent> throwOutVehicles = new EventHandler<ActionEvent>() {
        @Override
        public void handle( ActionEvent actionEvent ) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    GarageOfficer.getInstance().throwOutVehicles();
                }
            }).start();
        }
    };

    public static void load( AdminGarageController adminController, Integer minVehiclesCount ) {

        GarageModel model = GarageModel.getInstance();
        GarageController controller = new GarageController(model);
        GarageView.getAddVehicleButton().setOnAction(addVehicleEvent);
        GarageView.getFreeFieldsButton().setOnAction(throwOutVehicles);

        Integer vehiclesNumber;

        ArrayList<ArrayList<VehicleDTO>> vehicles = new ArrayList<>();
        ArrayList<ArrayList<GarageVehicle>> garageVehicles = new ArrayList<>();
        //adding garage vehicles from table and getting random vehicles
        for (int i = 0; i < GarageConfig.getPlatformCount(); ++i) {
            vehicles.add(new ArrayList<>());
            garageVehicles.add(new ArrayList<>());
            vehicles.get(i).addAll(adminController.getVehicles(i));
            final int level = i;
            ArrayList<GarageVehicle> ithGarageVehicles = garageVehicles.get(i);
            ithGarageVehicles.addAll(vehicles.get(level).stream().map(vehicleDTO -> GarageVehicleFactory.createVehicleAt(vehicleDTO, level)).collect(Collectors.toCollection(ArrayList::new)));
            vehiclesNumber = vehicles.get(i).size();
            if (vehiclesNumber < minVehiclesCount) {
                vehiclesNumber = minVehiclesCount - vehicles.get(i).size();
                while (vehiclesNumber != 0) {
                    ithGarageVehicles.add(RandomGarageVehicleFactory.createVehicle(i));
                    vehiclesNumber--;
                }
            }
        }
        garageVehicles.parallelStream().forEach(garageVehicles1 -> garageVehicles1.parallelStream().forEach(Thread::start));

    }

}
