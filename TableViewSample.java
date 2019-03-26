import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;
import javafx.scene.layout.BorderPane;
import java.util.*;

public class TableViewSample extends Application {



    private Calculator calculator = new Calculator();

    public AirbnbDataLoader loader = new AirbnbDataLoader();
    public ArrayList<AirbnbListing> data = loader.load();

    private final ObservableList<AirbnbListing> tableData =
            FXCollections.observableArrayList();
    private TableView table = new TableView();
    private Label statsLabel;
    private Label statsInfoLabel;
    private ArrayList<String> statActions = new ArrayList<>();
    private int currentActionIndex = 0;


    @Override
    public void start(Stage stage) {
        //data=loader.load();
        //System.out.println(data.toString());
        int lowerLimit = 500;
        int upperLimit = 1000;
        tableData.addAll(filterData(data, lowerLimit, upperLimit));

        statActions.add("Average reviews");
        statActions.add("Available properties");
        statActions.add("Homes and apartments");
        statActions.add("Most expensive borough");

        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setMinSize(500, 500);
        pane.setVgap(10);
        pane.setHgap(10);

        Scene scene = new Scene(new Group());
        stage.setTitle("Table");
        stage.setWidth(500);
        stage.setHeight(500);

        final Label label = new Label("Name");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);
        table.setItems(tableData);

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
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(label, table);

        Button myLeftButton = new Button("<");
        statsLabel = new Label();
        statsLabel.setText(statActions.get(currentActionIndex));
        Button myRightButton = new Button(">");

        statsInfoLabel = new Label("default");
        HBox statViewer = new HBox();
        VBox rightPane = new VBox();
        //rightPane.getChildren().addAll(myLeftButton, myRightButton,statsLabel);
        statViewer.setSpacing(20);
        statViewer.setPadding(new Insets(50, 50, 120, 120));
        statViewer.getChildren().addAll(vbox, myLeftButton, statsLabel, myRightButton);

        rightPane.setSpacing(120);
        rightPane.setPadding(new Insets(150,150,550,550));
        rightPane.getChildren().addAll(statsInfoLabel);
        myLeftButton.setOnAction(this::leftButtonClick);
        myRightButton.setOnAction(this::rightButtonClick);

        BorderPane statPane=new BorderPane();


        ((Group) scene.getRoot()).getChildren().addAll(statViewer,rightPane);

        stage.setScene(scene);
        stage.show();
    }

    private Label getTopLabel() {
        Label lbl = new Label("Top");

        lbl.setStyle("-fx-border-style: dotted; -fx-border-width: 0 0 1 0;"
                + "-fx-border-color: gray; -fx-font-weight: bold");

        return lbl;
    }
    /**
     * This method returns the index of the current 
     */
    /**
     * This will be executed when the button is clicked
     * It increments the count by 1
     */
    private void leftButtonClick(ActionEvent event) {
        // Counts number of button clicks and shows the result on a label
        currentActionIndex++;
        if (currentActionIndex > statActions.size() - 1) {
            currentActionIndex = 0;
        }
        statsLabel.setText(statActions.get(currentActionIndex));
        doStatistic();
        currentActionIndex = currentActionIndex;

    }

    /**
     * This will be executed when the button is clicked
     * It increments the count by 1
     */
    private void doStatistic() {
        if (statsLabel.getText().equals("Average reviews")) {
            double average = calculator.calculateAverageViews(tableData);
            {
                statsInfoLabel.setText(Double.toString(Math.round(average)));
            }
        } else if (statsLabel.getText().equals("Available properties")) {
            int total = calculator.calculateAvailability(tableData);
            {
                statsInfoLabel.setText(Integer.toString(total));
            }
        } else if (statsLabel.getText().equals("Homes and apartments")) {
            int totalProperties = calculator.calculateRoomType(tableData);
            {
                statsInfoLabel.setText(Integer.toString(totalProperties));
            }
        } else if (statsLabel.getText().equals("Most expensive borough")) {
            String mostExpensiveBorough = "";
            HashMap<String, Integer> filteredData = calculator.calculateMostExpensiveBorough(tableData);
            System.out.println(filteredData);
            ArrayList<Integer> pricesToFilter = new ArrayList();
            int large = 0;
            for (String borough : filteredData.keySet()) {
                String key = borough.toString();
                int price = filteredData.get(borough);
                pricesToFilter.add(price);
                large = pricesToFilter.get(0);
                for (int i = 0; i < pricesToFilter.size(); i++) {
                    if (large < pricesToFilter.get(i)) {
                        large = pricesToFilter.get(i);
                    }
                }
            }

            for (String currKey : filteredData.keySet()) {
                if (large == filteredData.get(currKey)) {
                    mostExpensiveBorough = currKey;
                    break;
                }
            }
            statsInfoLabel.setText(mostExpensiveBorough);
        }
    }

    /**
     * aked
     * It increments the count by 1
     */
    private ArrayList<AirbnbListing> filterData(ArrayList<AirbnbListing> data, int lowerLimit, int upperLimit) {
        // Counts number of button clicks and shows the result on a label
        ArrayList<AirbnbListing> newList = new ArrayList<>();
        for (AirbnbListing listing : data) {
            /*if((listing.getNeighbourhood().equals("Westminster") || listing.getNeighbourhood().equals("Croydon"))&& listing.getPrice()>=lowerLimit && listing.getPrice()<=upperLimit){

                newList.add(listing);
            }*/
            if (listing.getPrice() >= lowerLimit && listing.getPrice() <= upperLimit) {

                newList.add(listing);
            }
        }
        return newList;
    }

    /**
     * This will be executed when the button is clicked
     * It increments the count by 1
     */
    private void rightButtonClick(ActionEvent event) {
        currentActionIndex--;
        if (currentActionIndex < 0) {
            currentActionIndex = statActions.size() - 1;
        }
        statsLabel.setText(statActions.get(currentActionIndex));
        doStatistic();
        currentActionIndex = currentActionIndex;

    }

}
