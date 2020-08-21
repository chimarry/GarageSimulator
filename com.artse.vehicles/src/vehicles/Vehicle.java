package vehicles;


import util.Identification;

import java.util.Objects;

public abstract class Vehicle implements Wheelable {
    protected final static class Speed {
        static final long NO_SPEED = 10000;
        static final long MEDIUM = 500;
        static final long FAST = 500;
        static final long PARKED = 2000;
    }

    private final Identification identification;

    public Identification getIdentification() {
        return identification;
    }

    protected long speed = Speed.NO_SPEED;
    protected String category;

    public Vehicle( final Identification identification ) {
        this.identification = identification;
        setSpeed(Speed.NO_SPEED);
    }

    protected void setSpeed( long aSpeed ) {
        speed = aSpeed;
    }

    @Override
    public void drive() {
        setSpeed(Speed.MEDIUM);
        try {
            Thread.sleep(speed);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void stop() {
        setSpeed(Speed.NO_SPEED);

        try {
            Thread.sleep(speed);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void park() {
        drive();
        setSpeed(Speed.PARKED);
    }

    @Override
    public void accelerate() {
        setSpeed(Speed.FAST);

        try {
            Thread.sleep(speed);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String getSymbol() {
        return VehicleSymbol.V.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(identification.getVin());
    }

    @Override
    public boolean equals( Object o ) {
        if (this == o) return true;
        if (!(o instanceof Vehicle)) return false;
        return identification.equals(((Vehicle) o).identification);
    }

    @Override
    public String toString() {
        return identification.toString();
    }
}
