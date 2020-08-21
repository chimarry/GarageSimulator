package garage.structure.util;

import garage.structure.model.PlatformField;
import garage.structure.util.configuration.GarageConfig;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;


import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public final class PlatformFactory {

    private PlatformFactory(){}

    public static ConcurrentHashMap<Position,ObjectProperty<PlatformField>> createPlatform(){
        ConcurrentHashMap<Position,ObjectProperty<PlatformField>> map = new ConcurrentHashMap<>(GarageConfig.getTotalPlatformFields());
        int rowNo = GarageConfig.getRowCount();
        int colNo = GarageConfig.getColumnCount();

        for(int i = 0;i< rowNo;++i) {
            for (int j = 0; j < colNo; ++j) {
                Position position = new Position(i,j);
                map.put(position,new SimpleObjectProperty<>(new PlatformField(position)));
            }
        }
        ArrayList<Position> borders = GarageConfig.getParkingFields();
        borders.forEach((pair)-> map.get(pair).getValue().setParkingField(true));
        return map;
    }

}
