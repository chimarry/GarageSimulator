package model;

import business_logic.dto.VehicleDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class AdminPlatformModel {
    private ArrayList<VehicleDTO> list;
    public AdminPlatformModel( ArrayList<VehicleDTO> list)
    {
        this.list = list;
    }

    public ArrayList<VehicleDTO> getList() {
        return list;
    }

}
