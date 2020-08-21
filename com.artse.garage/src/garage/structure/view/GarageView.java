package garage.structure.view;
import garage.structure.controller.GarageControllerInterface;
import garage.structure.model.GarageModelInterface;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.ArrayList;

public class GarageView {
    private final GarageModelInterface model;
    private final GarageControllerInterface controller;

    //fxml components
    private AnchorPane anchorPane;
    private ComboBox<Integer> comboBox = new ComboBox<>();
    private ArrayList<PlatformView> platformViews = new ArrayList<>();
    private GridPane gridPane;

    private static Button freeFieldsButton;

    public static Button getFreeFieldsButton() {
        return freeFieldsButton;
    }

    public static void setFreeFieldsButton( Button freeFieldsButton ) {
        GarageView.freeFieldsButton = freeFieldsButton;
    }

    public static Button getAddVehicleButton() {
        return addVehicleButton;
    }

    public static void setAddVehicleButton( Button addVehicleButton ) {
        GarageView.addVehicleButton = addVehicleButton;
    }

    private static Button addVehicleButton;


    public GarageView( GarageModelInterface model, GarageControllerInterface controller ) {
        this.model = model;
        this.controller = controller;
        for (int i = 0; i < model.getPlatformCount(); ++i) {
            platformViews.add(new PlatformView(model.getPlatform(i)));
        }
    }

    public void registerCurrentPlatformListener( ChangeListener<Integer> listener ) {
        comboBox.valueProperty().addListener(listener);
    }
   public void initButtons(){
       freeFieldsButton = new Button("Free parking" + "\n" + "fields");
       freeFieldsButton.setId("button");
       freeFieldsButton.setLayoutX(450);
       freeFieldsButton.setLayoutY(700);
       freeFieldsButton.setPrefSize(100, 50);

       addVehicleButton = new Button("Add vehicle");
       addVehicleButton.setId("button");
       addVehicleButton.setLayoutX(450);
       addVehicleButton.setLayoutY(600);
       addVehicleButton.setPrefSize(100, 50);
       freeFieldsButton.setDisable(true);
       addVehicleButton.setDisable(true);
   }
    private AnchorPane initializeView() {
        initButtons();
        anchorPane = new AnchorPane();
        anchorPane.setId("anchorPane");
        anchorPane.setPrefSize(600, 800);
        anchorPane.getStylesheets().add("/garage/structure/util/configuration/design/garage.css");
        anchorPane.applyCss();

        comboBox.setPrefWidth(50);
        comboBox.setPrefHeight(20);
        comboBox.setId("comboBox");
        comboBox.getStylesheets().add("/garage/structure/util/configuration/design/garage.css");
        comboBox.applyCss();
        comboBox.setVisibleRowCount(model.getPlatformCount());
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < model.getPlatformCount(); ++i)
            arrayList.add(i);
        comboBox.getItems().addAll(arrayList);

        Label label = new Label();
        label.setId("label");
        label.getStylesheets().add("/garage/structure/util/configuration/design/garage.css");
        label.setLabelFor(comboBox);
        label.setText("Set platform...");

        comboBox.setLayoutX(110);
        comboBox.setOnAction(actionEvent -> label.setText("Change platform..."));
        AnchorPane.getLeftAnchor(comboBox);

        anchorPane.getChildren().addAll(comboBox, label, freeFieldsButton, addVehicleButton);

        return anchorPane;
    }

    public void displayView() {

        Scene scene = new Scene(initializeView());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

    }

    public void displayPlatform( int i ) {

        GridPane pane = platformViews.get(i).getView();
        AnchorPane.setBottomAnchor(pane, 5.0);
        anchorPane.getChildren().remove(anchorPane.lookup("#gridPane"));
        gridPane = pane;
        gridPane.setId("gridPane");
        anchorPane.getChildren().add(gridPane);
        addVehicleButton.setDisable(false);
        freeFieldsButton.setDisable(false);
    }


}
