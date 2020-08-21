package garage.structure.model;

import garage.structure.util.Position;
import javafx.beans.property.ObjectProperty;


import java.util.concurrent.ConcurrentHashMap;

public interface PlatformModelInterface {
     ConcurrentHashMap<Position,ObjectProperty<PlatformField>> getPlatform();
     ObjectProperty<PlatformField> fieldProperty( Position position );
     PlatformField getField( final Position position );
     void setField( final Position pos, final String symbol);
}
