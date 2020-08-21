package business_logic.dao;

import business_logic.dto.VehicleDTO;

import java.util.ArrayList;
import java.util.List;

public interface GarageDAO {
    VehicleDTO getVehicleDTO( int level, int position);
    void deleteVehicleDTO(int level, int position);
    void setVehicleDTO(int level,int position,VehicleDTO newDTO);
    ArrayList<VehicleDTO> getVehicleDTOs( int level);
    void loadData();
    void saveData();
}
