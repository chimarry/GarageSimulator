package controller;

import garage.structure.util.configuration.GarageConfig;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SelectPlatformController implements Initializable {
    @FXML
    private ListView<Integer> listView;
    @FXML
    private Button okButton;
    @FXML
    private AnchorPane anchorPane;
    private int platformCount = GarageConfig.getPlatformCount();
    private ObservableList<Integer> observableList = FXCollections.observableArrayList();

    @Override
    public void initialize( URL url, ResourceBundle resourceBundle ) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < platformCount; ++i)
            arrayList.add(i);
        observableList.addAll(arrayList);
        listView.setItems(observableList);
        Stage stage  = new Stage();
        Scene scene = new Scene(anchorPane);
        stage.setTitle("PLATFORM CHOOSER");
        stage.setScene(scene);
        stage.show();
        okButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {

                Node source = (Node) t.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                AdminGarageController.selectedPlatform.setValue(  listView.getSelectionModel().getSelectedItem());
                stage.close();
            }
        });
    }
}
