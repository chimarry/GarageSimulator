package factories;

import business_logic.dto.VehicleCategory;
import business_logic.dto.VehicleDTO;
import garage.structure.util.GaragePosition;
import garage.structure.util.configuration.GarageConfig;
import movement.EmergencyGarageVehicle;
import movement.GarageVehicle;
import movement.OrdinaryGarageVehicle;

public class GarageVehicleFactory {
    public static GarageVehicle createVehicle( VehicleDTO vehicleDTO ) {
        VehicleCategory category = vehicleDTO.getCategory();
        if (isEmergencyVehicle(category))
            return new EmergencyGarageVehicle(VehicleFactory.createVehicle(vehicleDTO));
        return new OrdinaryGarageVehicle(VehicleFactory.createVehicle(vehicleDTO));

    }

    public static GarageVehicle createVehicleAt( VehicleDTO vehicleDTO, int level ) {
        VehicleCategory category = vehicleDTO.getCategory();
        if (isEmergencyVehicle(category))
            return new EmergencyGarageVehicle(VehicleFactory.createVehicle(vehicleDTO), new GaragePosition(level, RandomGaragePosition.getRandomPosition()));
        return new OrdinaryGarageVehicle(VehicleFactory.createVehicle(vehicleDTO), new GaragePosition(level, RandomGaragePosition.getRandomPosition()));
    }

    private static boolean isEmergencyVehicle( VehicleCategory category ) {
        return category != VehicleCategory.C && category != VehicleCategory.V && category != VehicleCategory.M;
    }
}
