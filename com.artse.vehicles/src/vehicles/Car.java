package vehicles;


import util.Identification;

public class Car extends Vehicle {

    public static final int DEFAULT_DOOR_COUNT =  4;
    protected int doorNo;

    public Car( Identification identification ){
        this(identification,DEFAULT_DOOR_COUNT);
    }

    public Car( Identification identification, int doorNo ){
        super(identification);
        setDoorNo(doorNo);
    }

    public int getDoorNo() {
        return doorNo;
    }
    public void setDoorNo(int aDoorNo){
        doorNo = aDoorNo;
    }

}
