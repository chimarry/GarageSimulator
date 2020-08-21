package logic.smart;

import garage.structure.util.Position;
import garage.structure.util.configuration.GarageConfig;
import util.Request;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmartPlatform {

    private static HashMap<Position, SmartPosition> graph = new HashMap<>();
    public static final short ROAD_LEVEL_TO_NEXT_PLATFORM = 1;
    public static final short ROAD_LEVEL_TO_PREV_PLATFORM = 0;
    public static final Position CROSSROAD_LOWER_IN = new Position(1,1);
    public static final Position CROSSROAD_LOWER_OUT = new Position(1,2);
    public static final Position CROSSROAD_HIGHER_IN_1 = new Position(0,5);
    public static final Position CROSSROAD_HIGHER_IN_2 = new Position(1,5);
    public static final Position CROSSROAD_HIGHER_OUT = new Position(1,6);
    private static SmartPlatform ourInstance = new SmartPlatform();
    private SmartPlatform() {
        ConfigurationHelper.readProperties();
    }

    public static Position getNextPosition( final Position pos, final Request request ) {
        SmartPosition position = graph.get(pos);
        return position.chooseNext(request);
    }

    private static class ConfigurationHelper {
        private static Properties properties;

        static {
            properties = new Properties();
            try {
                String currentDirectory = new File(".").getCanonicalPath();
                FileInputStream input = new FileInputStream(currentDirectory + java.nio.file.FileSystems.getDefault().getSeparator() + "src" + java.nio.file.FileSystems.getDefault().getSeparator() +
                        "files" + java.nio.file.FileSystems.getDefault().getSeparator()+"smart_config.properties");
                properties.load(input);
            } catch (
                    IOException ex) {
                ex.printStackTrace();
            }
        }

        private static void readProperties() {
            for (int i = 0; i < GarageConfig.getRowCount(); ++i)
                for (int j = 0, k = 0; j < GarageConfig.getColumnCount(); ++j, k = 0) {

                    String key = "(" + i + "," + j + ")";
                    String line = properties.getProperty(key);
                    SmartPosition smartPosition = new SmartPosition();
                    Pattern p1 = Pattern.compile("(-*\\d+)");
                    Matcher m1 = p1.matcher(line);
                    while (m1.find()) {

                        Position position;
                        int n1 = Integer.parseInt(m1.group());
                        m1.find();
                        int n2 = Integer.parseInt(m1.group());
                        if (n1 == -1)
                            position = null;
                        else position = new Position(n1, n2);
                        smartPosition.addNeighbour(Request.values()[k], position);
                        k++;
                    }
                    graph.put(new Position(i, j), smartPosition);
                }
        }
    }
}
