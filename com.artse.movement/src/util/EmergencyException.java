package util;

import garage.structure.util.GaragePosition;

public class EmergencyException extends RuntimeException {
    private GaragePosition garagePosition;
    Integer numOfVehicles = 0;


    public EmergencyException( GaragePosition accidentPosition){
        garagePosition = accidentPosition;
    }

    public GaragePosition getGaragePosition() {
        return garagePosition;
    }

    @Override
    public String getMessage() {
        return "Have an services case to solve.";
    }
}
