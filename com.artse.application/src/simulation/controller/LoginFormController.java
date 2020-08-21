package simulation.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {
    public static Properties properties;
    static {

        properties = new Properties();
        try {
            String currentDirectory = new File(".").getCanonicalPath();
            FileInputStream input = new FileInputStream(currentDirectory + java.nio.file.FileSystems.getDefault().getSeparator() + "src" + java.nio.file.FileSystems.getDefault().getSeparator() +
                    "files" + java.nio.file.FileSystems.getDefault().getSeparator()+"AdminLogin.properties");
            properties.load(input);
        } catch (
                IOException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    TextField username;
    @FXML
    TextField password;
    @FXML
    Button logInButton;
    @Override
    public void initialize( URL url, ResourceBundle resourceBundle ) {
        password.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed( ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue ) {
                if (!newValue) {
                    if (!password.getText().equals(properties.getProperty("PASSWORD"))) {
                        alert("VIN is not valid.\nYou must enter 17 characters.\nOnly digit and word characters are valid.\nTry again.");
                        password.setText("");
                    }
                }
            }
        });
        username.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed( ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue ) {
                if (!newValue) {
                    if (!username.getText().equals(properties.getProperty("ADMIN_NAME"))) {
                        alert("Registration number is not valid.\nForm must be: XXX-Y-XXX, where X is number, and Y is word character.\nTry again.");
                        username.setText("");
                    }
                }
            }
        });


    }
    public void alert( String message ) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Input not valid");
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }

}
