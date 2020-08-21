package garage.structure.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import garage.structure.util.configuration.GarageConfig;

public final class GarageModel implements GarageModelInterface{

    public final static int PLATFORM_COUNT = GarageConfig.getPlatformCount();
    private final static ObservableList<PlatformModel> platformModels = FXCollections.observableArrayList();
    private final static ObjectProperty<PlatformModel> currentPlatform = new SimpleObjectProperty<>();
    private final static GarageModel ourInstance = new GarageModel();


    public static GarageModel getInstance() {
        return ourInstance;
    }

    private GarageModel() {
            for (int i = 0; i < PLATFORM_COUNT; ++i) {
                platformModels.add(new PlatformModel());
            }
    }

    public PlatformModel getPlatform(int level){
        return platformModels.get(level);
    }
    public ObservableList<PlatformModel> getList(){
        return platformModels;
    }
    public ObjectProperty<PlatformModel> currentPlatformProperty(){
        return currentPlatform;
    }
     public int getPlatformCount(){
        return PLATFORM_COUNT;
     }
    public void setCurrentPlatform( PlatformModel currentPlatform ) {
        GarageModel.currentPlatform.set(currentPlatform);
    }
}
