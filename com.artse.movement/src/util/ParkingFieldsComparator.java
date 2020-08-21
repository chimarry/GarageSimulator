package util;

import garage.structure.util.GaragePosition;
import garage.structure.util.Position;
import garage.structure.util.configuration.GarageConfig;

import java.util.Comparator;

public class ParkingFieldsComparator {
    private ParkingFieldsComparator() {
    }

    public static Comparator<GaragePosition> comparator = ( GaragePosition position1, GaragePosition position2 ) -> {
        if ((position1.getCol() < 4 && position2.getCol() < 4) || (position1.getCol() >= 4 && position2.getCol() >= 4)) {
            return Comparator.comparing(GaragePosition::getRow).compare(position1, position2);
        } else
            return Comparator.comparing(GaragePosition::getCol).compare(position1, position2);

    };
    public static Boolean isParkingField( Position position ){
        return GarageConfig.getParkingFields().parallelStream().anyMatch(position1 -> position.equals(position1));
    }
}

