package garage.structure.util.configuration;


import garage.structure.util.Position;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import java.util.Properties;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public final class GarageConfig {

    public enum Key {
         COLUMN_COUNT, HIGHER_ENTRY, HIGHER_EXIT, LOWER_ENTRY,
        LOWER_EXIT, PARKING_FIELDS, PARKING_FIELDS_COUNT, PLATFORM_COUNT, PLATFORM_FIELDS_COUNT, ROW_COUNT,CURRENCY
    }

    private static GarageConfig ourInstance = new GarageConfig();
    private static Properties properties;
    public static GarageConfig getInstance() {
        return ourInstance;
    }

    public static String getCurrency(){
        return getValue(Key.CURRENCY);
    }
    private GarageConfig() {
        properties = new Properties();
        try {
            String currentDirectory = new File( "." ).getCanonicalPath();
            FileInputStream input = new FileInputStream(currentDirectory+ java.nio.file.FileSystems.getDefault().getSeparator()+"src"+java.nio.file.FileSystems.getDefault().getSeparator()+"files"+java.nio.file.FileSystems.getDefault().getSeparator()+"platform_config.properties");
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
   //getters
    public static int getColumnCount() {

        return Parser.getKey(Parser.getInteger, getValue(Key.COLUMN_COUNT));
    }

    public static int getRowCount() {

        return Parser.getKey(Parser.getInteger, getValue(Key.ROW_COUNT));
    }

    public static int getTotalParkingFields() {

        return Parser.getKey(Parser.getInteger, getValue(Key.PARKING_FIELDS_COUNT));
    }

    public static int getTotalPlatformFields() {

        return Parser.getKey(Parser.getInteger, getValue(Key.PLATFORM_FIELDS_COUNT));
    }

    public static int getPlatformCount() {

        return Parser.getKey(Parser.getInteger, getValue(Key.PLATFORM_COUNT));
    }
    public static ArrayList<Position> getParkingFields(){
        return Parser.getKey(Parser.getArrayList,getValue(Key.PARKING_FIELDS));
    }

    public static Position getLowerExit(){

        return Parser.getKey(Parser.getPair,getValue(Key.LOWER_EXIT));
    }
    public static Position getLowerEntry(){

        return Parser.getKey(Parser.getPair,getValue(Key.LOWER_ENTRY));
    }
    public static Position getHigherExit(){

        return Parser.getKey(Parser.getPair,getValue(Key.HIGHER_EXIT));
    }
    public static Position getHigherEntry(){

        return Parser.getKey(Parser.getPair,getValue(Key.HIGHER_ENTRY));
    }
    public static Properties getProperties() {
        return (Properties) properties.clone();
    }

    private static String getValue(final Key key){
        return properties.getProperty(key.toString());
    }

    private static final class Parser {

        private Parser(){}

          static final Function<String, ArrayList<Position>> getArrayList=( key)->{
            ArrayList<Position> numbers = new ArrayList<>();

            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(key);
            while(m.find()){
                int n1 = Integer.parseInt(m.group());
                m.find();
                int n2 = Integer.parseInt(m.group());
                numbers.add(new Position(n1,n2));
            }
            return numbers;
        };
        static final Function<String,Integer> getInteger= Integer::parseInt;

        static final Function<String, Position> getPair = ( key)->getArrayList.apply(key).remove(0);

        static <T> T getKey( Function<String,T> function, String key ) {
            return function.apply(key);
        }
    }
}
