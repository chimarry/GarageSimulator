package util;

import garage.structure.util.GaragePosition;

public class InvalidPositionException extends Exception {
    public  InvalidPositionException(GaragePosition position ){
        super("Position "+position+" is not valid.");
    }
}
