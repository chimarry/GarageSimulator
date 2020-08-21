package vehicles;

import util.Identification;

public class Van extends Vehicle {

    public static final int STANDARD_DEADWEIGHT_TONNAGE = 2;
    protected int deadweight;

    public Van( Identification identification ){
        this(identification,STANDARD_DEADWEIGHT_TONNAGE);
    }

    public Van( Identification identification, int deadweight ){
        super(identification);
        setDeadweight(deadweight);
    }

    public void setDeadweight(int deadweight) {
        this.deadweight = deadweight;
    }
    public int getDeadweight(){
        return  deadweight;
    }
}
