package garage.structure.util;

import javafx.geometry.Pos;

import java.util.Objects;

public final class Position  implements Comparable<Position> {
    public final int row;

    public final int col;

    public Position( int row, int col ) {
        this.row = row;
        this.col = col;
    }
    public boolean isNextTo(Position position){
        return ((Math.abs(row - position.row) == 1 && col == position.col) || (Math.abs(position.col - col) == 1 && row == position.row) || this.equals(position));
    }
    @Override
    public boolean equals( Object o ) {
        if (this == o) {return true;}
        if (!(o instanceof Position)) {return false;}
        Position position = (Position) o;
        return (row == position.row &&
                col == position.col);
    }

    @Override
    public String toString() {
        return "Position{" +
                "Row: " + row +
                "Column: " + col +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }


    @Override
    public int compareTo( Position position ) {
        if(equals(position)){
            return 0;}
        else if(position.row < row)
        { return 1;}
        else if(position.row == row && position.col < col)
        {  return 1;}
        return -1;
    }
}
