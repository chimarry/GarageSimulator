package movement.thread_safe;

import garage.structure.model.GarageModel;
import garage.structure.util.GaragePosition;
import garage.structure.util.configuration.GarageConfig;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import logic.services.EmergencyService;
import movement.GarageVehicle;

import java.util.ArrayList;
import java.util.HashMap;

public class SynchronizedGarageModel {
    private static SynchronizedGarageModel ourInstance = new SynchronizedGarageModel();

    public static SynchronizedGarageModel getOurInstance() {
        return ourInstance;
    }

    private HashMap<GaragePosition, SynchronizedPlatformField> fields = new HashMap<>();
    private final GarageModel model = GarageModel.getInstance();


    private SynchronizedGarageModel() {
        for (int k = 0; k < GarageConfig.getPlatformCount(); ++k) {
            for (int i = 0; i < GarageConfig.getRowCount(); ++i)
                for (int j = 0; j < GarageConfig.getColumnCount(); ++j)
                    fields.put(new GaragePosition(k, i, j), new SynchronizedPlatformField());
        }
    }

    public GaragePosition setFields( GaragePosition prev, GaragePosition next, final GarageVehicle vehicle ) {
        if (fields.get(next).getVehicle() != null) {
            synchronized (fields.get(next).fieldMonitor) {
                while (fields.get(next).getVehicle() != null) {
                    try {
                        fields.get(next).fieldMonitor.wait();
                    } catch (InterruptedException ex) {
                    }
                }
            }
        }
        setPrevField(prev);
        fields.get(next).setVehicle(vehicle);
        model.getPlatform(next.getLevel()).setField(next.getPosition(), vehicle.getSymbol());
        return next;
    }
    public void setPrevField( GaragePosition position ) {
        synchronized (fields.get(position).fieldMonitor) {
            fields.get(position).setVehicle(null);
            model.getPlatform(position.getLevel()).setField(position.getPosition(), model.getPlatform(position.getLevel()).getDefaultSymbol(position.getPosition()));
            fields.get(position).fieldMonitor.notify();
        }
    }

    private GaragePosition setNextField( GaragePosition position, final GarageVehicle vehicle ) {
        synchronized (fields.get(position).fieldMonitor) {
            while (fields.get(position).getVehicle() != null)
                try {
                    fields.get(position).fieldMonitor.wait();
                } catch (InterruptedException ex) {
                }
            fields.get(position).setVehicle(vehicle);
            model.getPlatform(position.getLevel()).setField(position.getPosition(), vehicle.getSymbol());
            return position;
        }

    }
}
