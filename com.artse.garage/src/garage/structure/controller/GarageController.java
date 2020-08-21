package garage.structure.controller;

import garage.structure.model.GarageModelInterface;
import garage.structure.view.GarageView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class GarageController implements GarageControllerInterface {
    private final GarageModelInterface garageModel;
    private final GarageView garageView;
    public static int currentPlatform  = 0;

    private final ChangeListener<Integer> selectedPlatformListener = new ChangeListener<>(){
        @Override
        public void changed( ObservableValue<? extends Integer> observableValue, Integer old, Integer current ) {
            garageModel.setCurrentPlatform(garageModel.getPlatform(current));
            displayCurrentPlatform(current);
            currentPlatform = current;
        }
    };
    public GarageController(GarageModelInterface aModel){
        garageModel = aModel;
        garageView = new GarageView(garageModel,this);
        garageView.registerCurrentPlatformListener(selectedPlatformListener);
        garageView.displayView();
    }

    @Override
    public void displayCurrentPlatform(int level) {
        garageView.displayPlatform(level);
    }
}
