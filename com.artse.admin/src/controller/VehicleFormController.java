package controller;

import business_logic.dto.VehicleCategory;
import business_logic.dto.VehicleDTO;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import util.Identification;
import util.Photo;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static controller.AdminGarageController.platform;

public class VehicleFormController implements Initializable {
    @FXML
    private TextField name;
    @FXML
    private TextField regNum;
    @FXML
    private TextField vin;
    @FXML
    private TextField photo;
    @FXML
    private TextField doorNo;
    @FXML
    private TextField deadweight;
    @FXML
    private Button buttonBrowse;
    @FXML
    private Button buttonSave;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button buttonCancel;
    private TableView<VehicleDTO> tableView;
    public static VehicleDTO vehicle = null;
    public  VehicleCategory category = null;

    public void initView( VehicleCategory category, TableView<VehicleDTO> tableView ) {
        if (category == VehicleCategory.V || category == VehicleCategory.AV || category == VehicleCategory.FV)
            deadweight.setDisable(false);
        if (category == VehicleCategory.C || category == VehicleCategory.AC || category == VehicleCategory.PC)
            doorNo.setDisable(false);
        this.category = category;
        this.tableView = tableView;
    }

    @Override
    public void initialize( URL url, ResourceBundle resourceBundle ) {
        vin.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed( ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue ) {
                if (!newValue) {
                    if (notValidInput(vin.getText(), "[A-HJ-NPR-Z0-9]{17}")) {
                        alert("VIN is not valid.\nYou must enter 17 characters.\nOnly digit and word characters are valid.\nTry again.");
                        vin.setText("");
                    }
                }
            }
        });
        regNum.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed( ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue ) {
                if (!newValue) {
                    if (notValidInput(regNum.getText(), "[(\\d)]{3}-[\\w]-[(\\d)]{3}")) {
                        alert("Registration number is not valid.\nForm must be: XXX-Y-XXX, where X is number, and Y is word character.\nTry again.");
                        regNum.setText("");
                    }
                }
            }
        });

        Stage stage = new Stage();
        Scene scene = new Scene(anchorPane);
        stage.setTitle("Vehicle");
        if (vehicle != null) {
            name.setText(vehicle.getName());
            regNum.setText(vehicle.getRegistrationNumber());
            vin.setText(vehicle.getVin());
            photo.setText(vehicle.getPhotoPath());
            doorNo.setText(String.valueOf(vehicle.getDoorNo()));
            deadweight.setText(String.valueOf(vehicle.getDeadweight()));
        }
        stage.setScene(scene);
        stage.show();

        buttonCancel.setOnAction(actionEvent -> {
            Node source = (Node) actionEvent.getSource();
            Stage stage12 = (Stage) source.getScene().getWindow();
            stage12.close();
        });
        buttonSave.setOnAction(actionEvent -> {
            if (vehicle != null) {
                vehicle.setVin(vin.getText());
                vehicle.setRegistrationNumber(regNum.getText());
                vehicle.setName(name.getText());
                vehicle.setCategory(vehicle.getCategory());
                vehicle.setDoorNo(Integer.parseInt(doorNo.getText()));
                vehicle.setDeadweight(Integer.parseInt(deadweight.getText()));
                vehicle.setPhotoPath(photo.getText());
                final int pos = platform.get().indexOf(vehicle);
                platform.get().remove(vehicle);
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> platform.get().add(pos, vehicle));
                        tableView.getItems().clear();
                        tableView.getItems().addAll(platform.get());
                    }
                }, 50);
            } else {

                platform.get().add(new VehicleDTO(new Identification(name.getText(), regNum.getText(), vin.getText(), new Photo(photo.getText())),
                        Integer.parseInt(doorNo.getText().equals("") ? "0" : doorNo.getText()), Integer.parseInt(deadweight.getText().equals("") ? "0" : deadweight.getText()), category));
                tableView.getItems().clear();
                tableView.getItems().addAll(platform.get());
            }
            Node source = (Node) actionEvent.getSource();
            Stage stage1 = (Stage) source.getScene().getWindow();
            stage1.close();
        });
        buttonBrowse.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            File file = fileChooser.showOpenDialog(stage);
            photo.setText(file.getPath());
            System.out.println(file.getPath());
        });
    }

    public void alert( String message ) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Input not valid");
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }

    private boolean notValidInput( String string, String regex ) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        return !matcher.find();
    }
}
