package garage.structure.model;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;

public interface GarageModelInterface {
    PlatformModel getPlatform( int level );
    ObservableList<PlatformModel> getList();
    ObjectProperty<PlatformModel> currentPlatformProperty();
    int getPlatformCount();
    void setCurrentPlatform( PlatformModel currentPlatform );
 }
