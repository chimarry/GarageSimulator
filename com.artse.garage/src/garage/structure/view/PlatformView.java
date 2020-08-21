package garage.structure.view;

import garage.structure.model.PlatformModelInterface;
import garage.structure.util.Position;
import garage.structure.util.configuration.GarageConfig;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.ArrayList;

public class PlatformView {
    private final PlatformModelInterface model;

    private GridPane gridPane;
    private TextArea[][] areas;
    public PlatformView( PlatformModelInterface aModel ) {
        model = aModel;
        intializeView();
    }

    public void intializeView() {
        int rowNo = GarageConfig.getRowCount();
        int colNo = GarageConfig.getColumnCount();
        ArrayList<Position> parkingFields = GarageConfig.getParkingFields();

        gridPane = new GridPane();
        gridPane.setPrefWidth(400);
        gridPane.setPrefHeight(600);
        areas = new TextArea[rowNo][colNo];
        for (int i = 0; i < rowNo; ++i) {
            areas[i] = new TextArea[colNo];
            for (int j = 0; j < colNo; ++j) {
                areas[i][j] = new TextArea();
                areas[i][j].setId("text");
                areas[i][j].getStylesheets().add("/garage/structure/util/configuration/design/platform.css");
                areas[i][j].textProperty().bind(model.fieldProperty(new Position(i, j)).getValue().fieldSymbolProperty());
            }
            gridPane.addRow(i, areas[i]);
            gridPane.getRowConstraints().add(new RowConstraints(60));
            gridPane.getColumnConstraints().add(new ColumnConstraints(50));
        }
        parkingFields.forEach(pair -> areas[pair.row][pair.col].setId("parkingSpace"));

        for (int i = 0; i < rowNo; ++i)
            for (int j = 0; j < colNo; ++j)
                areas[i][j].applyCss();
    }

    public GridPane getView() {

        return gridPane;
    }

}
