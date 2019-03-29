import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.ArrayList;
import javafx.event.ActionEvent;

import java.util.*;

/**
 * This is a separate viewer which gets initialized upon selecting a borough to display the properties in it.
 * Then should the user wish to see the location of a property they need to double-click the cell. What is more each
 * column could be sorted by double clicking on the header.
 */

public class TableViewer extends Application {
    private MapWebView terryMaps;

    public AirbnbDataLoader loader = new AirbnbDataLoader();
    public ArrayList<AirbnbListing> data = new ArrayList<>();

    public String borough;

    private ObservableList<AirbnbListing> tableData =
            FXCollections.observableArrayList();
    private TableView table = new TableView();
    private ArrayList<Double> coordinates = new ArrayList<>();
    private ArrayList<String> statActions = new ArrayList<>();
    private int currentActionIndex = 0;

    private Stage stage;


    private String latitude;
    private String longitude;

    public TableViewer(String borough, ArrayList<AirbnbListing> data) {
        this.data = data;
        this.borough = borough;
    }

    @Override
    public void start(Stage stage) {


        this.stage = stage;
        Stage webMapStage=new Stage();
        terryMaps = new MapWebView();
        try {
            terryMaps.start(webMapStage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        tableData.addAll(filterData(data));



        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setMinSize(500, 500);
        pane.setVgap(10);
        pane.setHgap(10);

        Scene scene = new Scene(new Group());
        stage.setTitle(borough);
        stage.setWidth(500);
        stage.setHeight(500);

        final Label label = new Label(borough);
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);
        table.setItems(tableData);

        table.setRowFactory(p -> {
            TableRow<AirbnbListing> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !(row.isEmpty())) {
                    AirbnbListing rowData = row.getItem();
                    latitude = String.valueOf(rowData.getLatitude());
                    longitude = String.valueOf(rowData.getLongitude());
                    System.out.println(latitude+longitude);
                    terryMaps.show(latitude, longitude, rowData.getName(), rowData.getHost_name(), rowData.getRoom_type(), rowData.getPrice(), rowData.getLastReview(), rowData.getNeighbourhood());

                }
            });

            return row;
        });


        TableColumn hostNameCol = new TableColumn("Host");
        hostNameCol.setMinWidth(100);
        hostNameCol.setCellValueFactory(
                new PropertyValueFactory<>("host_name"));

        TableColumn priceCol = new TableColumn("Price");
        priceCol.setMinWidth(100);
        priceCol.setCellValueFactory(
                new PropertyValueFactory<>("price"));

        TableColumn reviewsCol = new TableColumn("Reviews");
        reviewsCol.setMinWidth(100);
        reviewsCol.setCellValueFactory(
                new PropertyValueFactory<>("numberOfReviews"));

        TableColumn minNightsCol = new TableColumn("Min Nights");
        minNightsCol.setMinWidth(100);
        minNightsCol.setCellValueFactory(
                new PropertyValueFactory<>("minimumNights"));
        table.getColumns().addAll(hostNameCol, priceCol, reviewsCol, minNightsCol);


        VBox vbox = new VBox();
        vbox.setSpacing(20);
        vbox.setPadding(new Insets(5, 5, 5, 5));
        vbox.getChildren().addAll(label, table);
        vbox.setAlignment(Pos.CENTER);
        table.prefHeightProperty().bind(vbox.heightProperty());
        table.prefWidthProperty().bind(vbox.widthProperty());


        HBox statViewer = new HBox();
        statViewer.setAlignment(Pos.CENTER);
        statViewer.prefHeightProperty().bind(stage.heightProperty());
        statViewer.prefWidthProperty().bind(stage.widthProperty());
        statViewer.setSpacing(20);
        statViewer.setPadding(new Insets(5, 5, 5, 5));
        statViewer.getChildren().addAll(vbox);


        ((Group) scene.getRoot()).getChildren().addAll(statViewer);

        stage.setScene(scene);
        stage.show();


    }

        /**
         * A method to filter the data by a borough
         */
        private ArrayList<AirbnbListing> filterData (ArrayList < AirbnbListing > data) {
            ArrayList<AirbnbListing> newList = new ArrayList<>();
            for (AirbnbListing listing : data) {

                if (listing.getNeighbourhood().replaceAll("\\s+", "").equals(borough.replaceAll("\\s+", ""))) {
                    newList.add(listing);
                }

            }
            return newList;
        }
    }





