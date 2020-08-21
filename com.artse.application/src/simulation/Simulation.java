package simulation;

import business_logic.dao.FileSystemGarageDAOFactory;
import controller.AdminGarageController;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.AdminGarageModel;

import java.io.IOException;


public class Simulation extends Application {
    public static ObjectProperty<Integer> minVehiclesCount = new SimpleObjectProperty<>();
    private  ChangeListener<Integer> minVehiclesCountListener = new ChangeListener<Integer>() {
        @Override
        public void changed( ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1 ) {
            UserApplication.load(adminController,minVehiclesCount.getValue());
        }
    };
    private AdminGarageController adminController;

    private ChangeListener<Boolean> appIsRunnedListener = new ChangeListener<Boolean>() {
        @Override
        public void changed( ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue ) {
            if (newValue) {
                adminController.closeStage();
                try{
                FXMLLoader.load(getClass().getResource("/simulation/view/userDialog.fxml"));}
                catch (IOException ex){
                    System.out.println(ex.getMessage());
                }
            }
        }
    };


    public static void main( String[] args ) {
        launch(args);
    }

    @Override
    public void start( Stage stage ) throws Exception {
        ObjectProperty<Boolean> isRunned = new SimpleObjectProperty<>();
        minVehiclesCount.set(-1);
        minVehiclesCount.addListener(minVehiclesCountListener);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminPart.fxml"));
        loader.load();
        adminController = loader.getController();
        adminController.initModel(new AdminGarageModel(new FileSystemGarageDAOFactory()));
        adminController.runIsPressed.addListener(appIsRunnedListener);

    }

}
