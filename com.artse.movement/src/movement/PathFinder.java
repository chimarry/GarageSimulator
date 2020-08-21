package movement;

import garage.structure.util.GaragePosition;
import garage.structure.util.Position;
import garage.structure.util.configuration.GarageConfig;
import logic.smart.SmartPlatform;

import static logic.smart.SmartPlatform.*;

import util.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public final class PathFinder {

    public static List<GaragePosition> getPath( GaragePosition position, GaragePosition accidentPosition ) {
        List<GaragePosition> emergencyPath = new ArrayList<>();

        if (position.getLevel() != accidentPosition.getLevel()) {
            emergencyPath.addAll(getPathToPlatform(position, accidentPosition));
            position = new GaragePosition(accidentPosition.getLevel(),
                    position.getLevel() > accidentPosition.getLevel() ? GarageConfig.getHigherEntry() : GarageConfig.getLowerEntry());
        }
        emergencyPath.addAll(getPathAtPlatform(accidentPosition.getLevel(), position.getPosition(), accidentPosition.getPosition()));
        return emergencyPath;
    }

    private static List<GaragePosition> getPathAtPlatform( int level, final Position p1, final Position p2 ) {
        List<GaragePosition> emergencyPath = new ArrayList<>();
        Position tempPos = p1;
        if (!(p2.row == ROAD_LEVEL_TO_NEXT_PLATFORM || p2.row == ROAD_LEVEL_TO_PREV_PLATFORM)) {
            while (!tempPos.isNextTo(p2)) {
                tempPos = SmartPlatform.getNextPosition(tempPos, Request.GO_CICRULLAR);
                emergencyPath.add(new GaragePosition(level, tempPos));
            }
        } else {
            while (tempPos.row != ROAD_LEVEL_TO_PREV_PLATFORM && tempPos.row != ROAD_LEVEL_TO_NEXT_PLATFORM) {
                tempPos = SmartPlatform.getNextPosition(tempPos, Request.GO_CICRULLAR);
                emergencyPath.add(new GaragePosition(level, tempPos));
            }
            int wantedRow = tempPos.col < p2.col ? ROAD_LEVEL_TO_NEXT_PLATFORM : ROAD_LEVEL_TO_PREV_PLATFORM;
            while (tempPos.row != wantedRow) {
                tempPos = SmartPlatform.getNextPosition(tempPos, Request.GO_CICRULLAR);
                emergencyPath.add(new GaragePosition(level, tempPos));
            }
            while (!tempPos.isNextTo(p2)) {
                tempPos = SmartPlatform.getNextPosition(tempPos, Request.GO_NEXT);
                emergencyPath.add(new GaragePosition(level, tempPos));
            }
        }
        return emergencyPath;
    }



    public static List<GaragePosition> getPathToPlatform( GaragePosition position1, GaragePosition position2 ) {

        List<GaragePosition> emergencyPath = new ArrayList<>();
        Position currentPosition = position1.getPosition();
        Integer level = position1.getLevel();
        Function<Integer, Integer> iterator = level > position2.getLevel() ? x -> --x : x -> ++x;
        int crossroad = level > position2.getLevel() ? SmartPlatform.ROAD_LEVEL_TO_PREV_PLATFORM : SmartPlatform.ROAD_LEVEL_TO_NEXT_PLATFORM;
        Position exit = level > position2.getLevel() ? GarageConfig.getLowerExit() : GarageConfig.getHigherExit();
        while (currentPosition.row != crossroad) {
            currentPosition = SmartPlatform.getNextPosition(currentPosition, Request.GO_CICRULLAR);
            emergencyPath.add(new GaragePosition(level, currentPosition));
        }
        for (; !level.equals(position2.getLevel()); level = iterator.apply(level)) {

            while (!currentPosition.equals(exit)) {
                currentPosition = SmartPlatform.getNextPosition(currentPosition, Request.GO_NEXT);
                emergencyPath.add(new GaragePosition(level, currentPosition));
            }
            currentPosition = SmartPlatform.getNextPosition(currentPosition, Request.GO_NEXT);
            emergencyPath.add(new GaragePosition(iterator.apply(level), currentPosition));
        }
        return emergencyPath;
    }

}
