package business_logic.dao;

import business_logic.dto.GarageDTO;
import business_logic.dto.VehicleDTO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileSystemGarageDAO implements GarageDAO {
    private   GarageDTO garageDTO = new GarageDTO();
    private  final static File file = new File("C:\\Users\\PWIN\\Desktop\\Projekat\\GarageApplication\\com.artse.admin\\src\\business_logic\\garage.ser");

    public void loadData(){
        try(ObjectInputStream stream = new ObjectInputStream(new FileInputStream(file))){
            try{
                garageDTO = (GarageDTO)stream.readObject();
               }

            catch (ClassNotFoundException ex){
                System.out.println(ex.getMessage());
            }
        }
        catch (IOException exeption){

               System.out.println(exeption.getMessage());
        }
    }
    public void saveData(){
        try(ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file))){
                stream.writeObject(garageDTO);
        }
        catch (IOException exeption){
            System.out.println(exeption.getMessage());
        }
    }
    @Override
    public VehicleDTO getVehicleDTO( int level, int position ) {
        return garageDTO.getVehicleDTO(level,position);
    }

    @Override
    public void deleteVehicleDTO( int level, int position ) {
           garageDTO.deleteVehicleDTO(level,position);
    }

    @Override
    public void setVehicleDTO( int level, int position, VehicleDTO newDTO ) {
           garageDTO.setVehicleDTO(level,position,newDTO);
    }

    @Override
    public ArrayList<VehicleDTO> getVehicleDTOs( int level ) {
      return garageDTO.getList(level);
    }
}
