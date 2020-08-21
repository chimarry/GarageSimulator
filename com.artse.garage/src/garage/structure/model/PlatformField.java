package garage.structure.model;

import garage.structure.util.Position;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Objects;

public final class PlatformField {

    public static final String DEFAULT_SYMBOL = "";
    public static final String DEFAULT_PARKING_FIELD_SYMBOL = "*";

    private boolean isParkingField;
    private ObjectProperty<String> fieldSymbol = new SimpleObjectProperty<>();
    private Position position;

    public void setPosition( Position position ) {
        this.position = position;
    }

    public PlatformField( Position position) {
        setParkingField(false);
        setFieldSymbol(DEFAULT_SYMBOL);
        setPosition(position);
    }
    public ObjectProperty<String> fieldSymbolProperty() {
        return fieldSymbol;
    }

    public void setFieldSymbol( String fieldSymbol ) {
        this.fieldSymbol.set(fieldSymbol);
    }

    public String getFieldSymbol() {

        return fieldSymbol.get();
    }

    public String getDefaultSymbol() {
        if (isParkingField) {
            return DEFAULT_PARKING_FIELD_SYMBOL;
        }
        return DEFAULT_SYMBOL;
    }
    //setters and getters


    public boolean isParkingField() {
        return isParkingField;
    }

    public void setParkingField( boolean parkingField ) {
        isParkingField = parkingField;
        setFieldSymbol(DEFAULT_PARKING_FIELD_SYMBOL);
    }

    public Position getPosition() {
        return position;
    }



    @Override
    public int hashCode() {

        return Objects.hashCode(position);
    }

    @Override
    public boolean equals( Object obj ) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PlatformField other = (PlatformField) obj;

        return Objects.equals(position, other.getPosition());
    }

    @Override
    public String toString() {
        return "Is parking field: " + isParkingField +
                 " ,position: " + position;
    }

}
