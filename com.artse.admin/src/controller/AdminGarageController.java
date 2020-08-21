package controller;

import business_logic.dto.VehicleCategory;
import business_logic.dto.VehicleDTO;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import model.AdminGarageModel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.Identification;
import util.Photo;
import util.VehicleFormLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminGarageController implements Initializable {
    @FXML
    private MenuBar menuBar;
    @FXML
    private ImageView imageView;
    @FXML
    private TableView<VehicleDTO> tableView;
    @FXML
    private TableColumn<VehicleDTO, String> name;
    @FXML
    private TableColumn<VehicleDTO, String> regNum;
    @FXML
    private TableColumn<VehicleDTO, String> vin;
    @FXML
    private TableColumn<VehicleDTO, String> photo;
   // @FXML
    private ComboBox<String> comboBox;
    @FXML
    private MenuItem menuOptionsShowPhoto;
    @FXML
    private Button runButton;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private MenuItem menuOptionsDelete;
    @FXML
    private MenuItem menuOptionsEdit;
    @FXML
    private MenuItem menuEditChoosePlatform;
    @FXML
    private MenuItem menuOptionsAddVehicle;
    private int minVehicleCount = 0;
    private Stage vehicleStage;
    private Stage stage;
    public static ObjectProperty<Integer> selectedPlatform = new SimpleObjectProperty<>();
    public ObjectProperty<Boolean> runIsPressed = new SimpleObjectProperty<>(false);
    private AdminGarageModel model;
    public static ObjectProperty<ArrayList<VehicleDTO>> platform = new SimpleObjectProperty<>();

    private final ChangeListener<Integer> selectedPlatformListener = new ChangeListener<>() {
        @Override
        public void changed( ObservableValue<? extends Integer> observableValue, Integer old, Integer current ) {
            model.currentPlatform.setValue(model.getPlatform(current).getList());
            platform.bindBidirectional(model.currentPlatform);
            model.currentPlatform.bindBidirectional(platform);
            tableView.getItems().clear();
            tableView.getItems().addAll(platform.get());
        }
    };

    private final ChangeListener<String> selectedVehicle = new ChangeListener<>() {
        @Override
        public void changed( ObservableValue<? extends String> observableValue, String old, String current ) {
            try {
                VehicleFormController.vehicle = null;
                    vehicleStage.close();
                VehicleFormLoader.load(this.getClass(), "/view/vehicleForm.fxml", extractClass(current), tableView);
            } catch (Exception ex) {
            }
        }
    };

    @Override
    public void initialize( URL url, ResourceBundle resourceBundle ) {
        runIsPressed.setValue(false);
        selectedPlatform.addListener(selectedPlatformListener);
        Stage stage = new Stage();
        Scene scene = new Scene(anchorPane);
        initTableView();

        menuBar.getStylesheets().add("/design/Menubar.css");
        menuBar.applyCss();
        imageView.setImage(new Image("/design/default_image.png"));
        menuOptionsDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle( ActionEvent actionEvent ) {
                VehicleDTO dto = tableView.getSelectionModel().getSelectedItem();
                model.currentPlatform.get().remove(dto);
                tableView.getItems().remove(tableView.getSelectionModel().getSelectedItem());
                imageView.setImage(new Image("/design/default_image.png"));
            }
        });
        menuOptionsShowPhoto.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle( ActionEvent actionEvent ) {
                if (tableView.getSelectionModel().getSelectedItem() != null) {
                    FileInputStream stream = null;
                    try {
                        String string = tableView.getSelectionModel().getSelectedItem().getPhotoPath();
                        stream = new FileInputStream(new File(string));
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                    Image image = new Image(stream);
                    imageView.setImage(image);
                }
            }
        });
        menuOptionsAddVehicle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle( ActionEvent actionEvent ) {
                Pane pane  =new Pane();
                initVehicleComboBox();
                pane.setPrefSize(150,100);
                pane.getChildren().add(comboBox);
                 vehicleStage = new Stage();
                vehicleStage.initModality(Modality.APPLICATION_MODAL);
                vehicleStage.setX(1020);
                vehicleStage.setY(90);
                Scene vehicleScene = new Scene(pane);
                vehicleStage.setTitle("Vehicle chooser");
                vehicleStage.setScene(vehicleScene);
                vehicleStage.show();
            }
        });
        menuOptionsEdit.setOnAction(new EventHandler<>() {
            @Override
            public void handle( ActionEvent actionEvent ) {
                if (tableView.getSelectionModel().getSelectedItem() != null) {
                    try {
                        VehicleFormController.vehicle = tableView.getSelectionModel().getSelectedItem();
                        VehicleFormLoader.load(this.getClass(), "/view/vehicleForm.fxml", tableView.getSelectionModel().getSelectedItem().getCategory(), tableView);
                    } catch (Exception ignored) {
                    }
                    //  FxmlLoader.loadDialog(this.getClass(), "/javafx2/view/StudentForm.fxml");
                }

            }
        });
        menuEditChoosePlatform.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle( ActionEvent t ) {
                try {
                    FXMLLoader.load(this.getClass().getResource("/view/selectPlatform.fxml"));
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
        runButton.setOnAction(new EventHandler<ActionEvent>()  {
            @Override
            public void handle( ActionEvent actionEvent ) {
                runIsPressed.setValue(true);

                model.saveData();

            }

        });

        stage.setScene(scene);
        this.stage =stage;
        stage.show();

    }
    public void closeStage(){
        stage.close();
    }
    public void initModel( AdminGarageModel model ) {
        this.model = model;
        displayDefaultPlatform();
    }

    public static VehicleCategory extractClass( String string ) {
        switch (string) {
            case VehicleClass.AMBULANCE_CAR:
                return VehicleCategory.AC;
            case VehicleClass.AMBULANCE_VAN:
                return VehicleCategory.AV;
            case VehicleClass.CAR:
                return VehicleCategory.C;
            case VehicleClass.MOTORCYCLE:
                return VehicleCategory.M;
            case VehicleClass.POLICE_CAR:
                return VehicleCategory.PC;
            case VehicleClass.POLICE_MOTORCYCLE:
                return VehicleCategory.PM;
            case VehicleClass.VAN:
                return VehicleCategory.V;
            case VehicleClass.FIREMEN_VAN:
                return VehicleCategory.FV;
        }
        return null;
    }

    public ArrayList<VehicleDTO> getVehicles(int level){
        return model.getPlatform(level).getList();
    }

    private class VehicleClass {
        public static final String CAR = "/design/car.png";
        public static final String VAN = "/design/van.png";
        public static final String MOTORCYCLE = "/design/motorcycle.png";
        public static final String AMBULANCE_CAR = "/design/AmbulanceCar.png";
        public static final String POLICE_CAR = "/design/PoliceCar.png";
        public static final String POLICE_MOTORCYCLE = "/design/PoliceMotorcycle.png";
        public static final String FIREMEN_VAN = "/design/FiremenVan.png";
        public static final String AMBULANCE_VAN = "/design/AmbulanceVan.png";

    }
    private void displayDefaultPlatform(){
        model.currentPlatform.setValue(model.getPlatform(0).getList());
        platform.bindBidirectional(model.currentPlatform);
        model.currentPlatform.bindBidirectional(platform);
        tableView.getItems().clear();
        tableView.getItems().addAll(platform.get());

    }
    private void initTableView() {
        tableView.setId("tableView");
        tableView.getStylesheets().add("/design/TableView.css");
        tableView.applyCss();
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        regNum.setCellValueFactory(new PropertyValueFactory<>("registrationNumber"));
        vin.setCellValueFactory(new PropertyValueFactory<>("vin"));
        photo.setCellValueFactory(new PropertyValueFactory<>("photo"));
    }

    private void initVehicleComboBox() {
        comboBox = new ComboBox<>();
        comboBox.setPrefSize(150,100);
        ObservableList<String> options = FXCollections.observableArrayList();
        options.addAll(VehicleClass.CAR, VehicleClass.VAN, VehicleClass.MOTORCYCLE, VehicleClass.AMBULANCE_CAR, VehicleClass.POLICE_CAR
                , VehicleClass.POLICE_MOTORCYCLE, VehicleClass.FIREMEN_VAN, VehicleClass.AMBULANCE_VAN);
        comboBox.setPromptText("Add vehicle");
        comboBox.getStylesheets().add("/design/ComboBox.css");
        comboBox.setId("comboBox");
        comboBox.applyCss();
        comboBox.setVisibleRowCount(4);
        comboBox.getItems().addAll(options);
        comboBox.setButtonCell(new StatusListCell());
        comboBox.setCellFactory(c -> new StatusListCell());
        comboBox.valueProperty().addListener(selectedVehicle);
    }

    public int getMinVehicleCount() {
        return minVehicleCount;
    }
}

class StatusListCell extends ListCell<String> {
    protected void updateItem( String item, boolean empty ) {
        super.updateItem(item, empty);
        setGraphic(null);
        setText(null);
        if (item != null) {
            ImageView imageView = new ImageView(new Image(item));
            imageView.setFitWidth(60);
            imageView.setFitHeight(60);
            setGraphic(imageView);
        }
    }
}
