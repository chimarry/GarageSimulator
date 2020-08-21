package simulation.controller;

import garage.structure.controller.GarageController;
import garage.structure.model.GarageModel;
import garage.structure.util.configuration.GarageConfig;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import simulation.Simulation;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserDialogController implements Initializable {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button buttonOk;
    @FXML
    private TextField textField;
    private Stage stage = new Stage();
    ;

    @Override
    public void initialize( URL url, ResourceBundle resourceBundle ) {
        Scene scene = new Scene(anchorPane);
        buttonOk.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle( ActionEvent actionEvent ) {
               int numberOfVehicles = validInput(textField.getText());
                if (numberOfVehicles < 0) {
                    textField.setText("");
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("Input not valid");
                    errorAlert.setContentText("You must enter valid number.");
                    errorAlert.showAndWait();

                }
                else{
                stage.close();
                Simulation.minVehiclesCount.set(numberOfVehicles);}
            }

        });
        stage.setTitle("USER APPLICATION ENTERING");
        stage.setScene(scene);
        stage.show();
    }

    private int validInput( String string ) {
        Pattern pattern = Pattern.compile("((\\d)(\\d))|(\\d)");
        Matcher matcher = pattern.matcher(string);
        if (matcher.find())
            if (Integer.parseInt(string) <= GarageConfig.getTotalParkingFields())
                return Integer.parseInt(string);
            return -1;
    }
}
