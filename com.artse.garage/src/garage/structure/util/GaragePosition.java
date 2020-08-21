package garage.structure.util;

import java.util.Comparator;
import java.util.Objects;

import static java.lang.Math.abs;

public  class GaragePosition implements Comparable<GaragePosition> {
    private  final int level;
    private  final int row;
    private  final int col;
    private final Position position ;

    public GaragePosition( final int xx, final int yy, final int zz){
        level = xx;
        row = yy;
        col = zz;
        position  = new Position(row, col);
    }
    public GaragePosition(final int xx, final Position position){
        level =xx;
        this.position = position;
        row =position.row;
        col =position.col;
    }
    @Override
    public boolean equals( Object o ) {
        if (this == o) {return true;}
        if (!(o instanceof GaragePosition)) {return false;}
        GaragePosition garagePosition = (GaragePosition) o;
        return (level == garagePosition.level &&
               row == garagePosition.row &&
                col == garagePosition.col);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLevel(), getRow(), getCol());
    }
    public Position getPosition(){
        return  position;
    }

    public int getLevel() {
        return level;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    @Override
    public int compareTo( GaragePosition position ) {
      return Comparator.comparing(GaragePosition::getLevel).thenComparing(GaragePosition::getRow).thenComparing(GaragePosition::getCol).compare(this,position);
    }

    @Override
    public String toString() {
        return "("+ level +","+ row +","+ col +")";
    }
}
