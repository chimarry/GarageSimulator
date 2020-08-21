package util;

import business_logic.dto.VehicleCategory;
import business_logic.dto.VehicleDTO;
import controller.VehicleFormController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class VehicleFormLoader {

    public static void load( Class c, String fxml, VehicleCategory category, TableView<VehicleDTO> tableView ) {
        try {
            Stage s = new Stage();
            s.initModality(Modality.APPLICATION_MODAL);
            FXMLLoader loader = new FXMLLoader(c.getResource(fxml));
            Pane pane  = loader.load();
            VehicleFormController form = loader.getController();
            form.initView(category,tableView);
            Scene myScene = new Scene(pane);
            s.setScene(myScene);
            s.showAndWait();
        } catch (IOException ex) {
        }
    }
}
