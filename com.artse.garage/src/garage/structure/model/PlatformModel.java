package garage.structure.model;

import garage.structure.util.PlatformFactory;
import garage.structure.util.Position;
import javafx.beans.property.ObjectProperty;

import java.util.concurrent.ConcurrentHashMap;

public class PlatformModel implements PlatformModelInterface {
    private final ConcurrentHashMap<Position, ObjectProperty<PlatformField>> matrix = PlatformFactory.createPlatform();

    public ConcurrentHashMap<Position, ObjectProperty<PlatformField>> getPlatform() {

        return matrix;
    }

    @Override
    public synchronized ObjectProperty<PlatformField> fieldProperty( Position position ) {
        return matrix.get(position);
    }

    public PlatformField getField( final Position position ) {

        return matrix.get(position).getValue();
    }

    public synchronized void setField( final Position position, final String symbol ) {
        fieldProperty(position).getValue().setFieldSymbol(symbol);
    }
    public String getDefaultSymbol(final Position position){
        return fieldProperty(position).getValue().getDefaultSymbol();
    }
}
