package factories;

import garage.structure.util.GaragePosition;
import garage.structure.util.Position;
import garage.structure.util.configuration.GarageConfig;

import java.security.SecureRandom;
import java.util.Random;

public final class RandomGaragePosition {
    private static final Random random = new Random();
    public static Position getRandomPosition(){
        return new Position(random.nextInt(10),random.nextInt(8));}
        public static GaragePosition getRandomGaragePosition(){
        return new GaragePosition(random.nextInt(GarageConfig.getPlatformCount()),getRandomPosition());
        }
}
