package business_logic.dto;

import business_logic.dao.InvalidDataActionException;
import garage.structure.util.configuration.GarageConfig;

import java.io.Serializable;
import java.util.ArrayList;

public final class GarageDTO implements Serializable {
    private static final long serialVersionUID = 21L;
    private final int dim1 = GarageConfig.getPlatformCount();
    private final int dim2  = GarageConfig.getTotalPlatformFields();
    private PlatformDTO[] platforms;


    public GarageDTO() {

        platforms = new PlatformDTO[dim1];
        for (int i = 0; i < dim1; ++i)
            platforms[i] = new PlatformDTO();
    }

    public ArrayList<VehicleDTO> getList( int level ) {
        return platforms[level].vehicles;
    }

    public void addVehicleDTO( int level, VehicleDTO dto ) throws InvalidDataActionException {
        platforms[level].addVehicleDTO(dto);
    }

    public void setVehicleDTO( int level, int position, VehicleDTO dto ) throws InvalidDataActionException {
        platforms[level].setVehicleDTO(dto, position);
    }

    public void deleteVehicleDTO( int level, int position ) throws InvalidDataActionException {
        platforms[level].deleteVehicleDTO(position);
    }

    public VehicleDTO getVehicleDTO( int level, int position ) throws InvalidDataActionException {
        return platforms[level].getVehicleDTO(position);
    }

    public int getNumberOfVehicleDTOs( int level ) {
        return platforms[level].currentNumberOfData;
    }

    private class PlatformDTO implements Serializable {
        private static final long serialVersionUID = 12L;
        private int currentNumberOfData = 0;
        private ArrayList<VehicleDTO> vehicles = new ArrayList<>(dim2);

        private boolean isFull() {
            return currentNumberOfData == dim2;
        }

        public void setVehicleDTO( VehicleDTO vehicleDTO, int position ) {
            vehicles.set(position, vehicleDTO);
        }

        public void addVehicleDTO( VehicleDTO vehicleDTO ) throws InvalidDataActionException {
            if (!isFull()) {
                vehicles.add(vehicleDTO);
                currentNumberOfData++;
            } else
                throw new InvalidDataActionException("You cannot add more data, before you delete one.");
        }

        public void deleteVehicleDTO( int position ) throws InvalidDataActionException {
            if (position >= currentNumberOfData || position < 0)
                throw new InvalidDataActionException("Cannot delete from current position is invalid");
            else
                vehicles.remove(position);
        }

        public VehicleDTO getVehicleDTO( int position ) throws InvalidDataActionException {
            if (position >= currentNumberOfData || position < 0)
                throw new InvalidDataActionException("There is element at such position.");
            return vehicles.get(position);
        }

    }

}
