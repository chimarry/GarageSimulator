package logic.smart;

import garage.structure.util.Position;
import util.Request;

import java.util.HashMap;

public class SmartPosition {
    public static final short DEFAULT_REQUEST_COUNT = 3;

    private HashMap<Request, Position> neighbours = new HashMap<>(DEFAULT_REQUEST_COUNT);

    public SmartPosition(){}
    public SmartPosition( Position goCircullar, Position goNextPlatform, Position park ){
        addNeighbour(Request.GO_NEXT,goNextPlatform);
        addNeighbour(Request.GO_CICRULLAR,goCircullar);
        addNeighbour(Request.PARK,park);
    }
    public Position chooseNext(Request request){
        return neighbours.get(request);
    }
    public void addNeighbour(Request request, Position pos){
        neighbours.put(request,pos);
    }
}
