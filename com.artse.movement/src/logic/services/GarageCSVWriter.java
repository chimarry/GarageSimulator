package logic.services;

import garage.structure.util.configuration.GarageConfig;
import util.Identification;

import java.io.*;

public class GarageCSVWriter {
    public static void writeToCSV(String registrationNumber,String enterDate, String exitDate,String interval,String charge){
        File file = new File("C:\\Users\\PWIN\\Desktop\\Projekat\\GarageApplication\\com.artse.application\\GarageChargeRecord.csv");
        try ( FileWriter fileWriter = new FileWriter(file,true)){
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(registrationNumber);
            stringBuilder.append(',');
            stringBuilder.append(enterDate);
            stringBuilder.append(',');
            stringBuilder.append(exitDate);
            stringBuilder.append(',');
            stringBuilder.append(interval);
            stringBuilder.append(',');
            stringBuilder.append(charge+ GarageConfig.getCurrency());
            stringBuilder.append('\n');
            printWriter.print(stringBuilder.toString());
            bufferedWriter.close();
            printWriter.close();
        } catch (IOException ex) {

        }
    }
}
