package model;

import business_logic.dao.GarageDAO;
import business_logic.dao.GarageDAOFactory;
import business_logic.dto.VehicleDTO;
import controller.AdminGarageController;
import garage.structure.util.configuration.GarageConfig;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class AdminGarageModel {

    private ObservableList<AdminPlatformModel> list = FXCollections.observableArrayList();


    public ObjectProperty<ArrayList<VehicleDTO>> currentPlatform = new SimpleObjectProperty<>(null);

    private GarageDAOFactory factory;
    private GarageDAO dao;
    public AdminGarageModel(GarageDAOFactory fac){
        factory =fac;
        dao = factory.createGarageDAO();
        loadData();
        for(int i =0;i< GarageConfig.getPlatformCount();++i){
            list.add(new AdminPlatformModel(dao.getVehicleDTOs(i)));
        }

    }
    public ObservableList<AdminPlatformModel> getList() {
        return list;
    }

    public void setList( ObservableList<AdminPlatformModel> list ) {
        this.list = list;
    }

    public AdminPlatformModel getPlatform(int level){
        return list.get(level);
    }
    public void loadData(){
    dao.loadData();
    }
    public void saveData(){
        dao.saveData();
    }
}
