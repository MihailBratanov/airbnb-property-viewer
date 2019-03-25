import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.paint.*;

import java.sql.SQLOutput;
import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

import java.util.*;

public class StatisticsPanel extends Application {



    private Calculator calculator = new Calculator();

    public AirbnbDataLoader loader = new AirbnbDataLoader();
    public ArrayList<AirbnbListing> data = loader.load();

    private final ObservableList<AirbnbListing> tableData =
            FXCollections.observableArrayList();

    private Label statsLabel1;
    private Label statsInfoLabel1;
    private Label statsLabel2;
    private Label statsInfoLabel2;
    private Label statsLabel3;
    private Label statsInfoLabel3;
    private Label statsLabel4;
    private Label statsInfoLabel4;
    private ArrayList<String> statActions = new ArrayList<>();
    private int currentActionIndex = 0;

    private double stageMidX;
    private double stageMidY;

    private double mouseX;
    private double mouseY;


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



        Scene scene = new Scene(new Group());



//---------------------------------------------------------
        Button myLeftButton1 = new Button("<");
        statsLabel1 = new Label();
        statsLabel1.setText(statActions.get(currentActionIndex));
        Button myRightButton1 = new Button(">");

        statsInfoLabel1 = new Label("default");
//-----------------------------------------------------------
        Button myLeftButton2 = new Button("<");
        statsLabel2 = new Label();
        statsLabel2.setText(statActions.get(currentActionIndex));
        Button myRightButton2 = new Button(">");

        statsInfoLabel2 = new Label("default");
//---------------------------------------------------------
        Button myLeftButton3 = new Button("<");
        statsLabel3 = new Label();
        statsLabel3.setText(statActions.get(currentActionIndex));
        Button myRightButton3 = new Button(">");

        statsInfoLabel3 = new Label("default");
//-----------------------------------------------------------
        Button myLeftButton4 = new Button("<");
        statsLabel4 = new Label();
        statsLabel4.setText(statActions.get(currentActionIndex));
        Button myRightButton4 = new Button(">");

        statsInfoLabel4 = new Label("default");


        myLeftButton1.setOnAction(this::leftButtonClick);
        myRightButton1.setOnAction(this::rightButtonClick);

        myLeftButton2.setOnAction(this::leftButtonClick);
        myRightButton2.setOnAction(this::rightButtonClick);

        myLeftButton3.setOnAction(this::leftButtonClick);
        myRightButton3.setOnAction(this::rightButtonClick);

        myLeftButton4.setOnAction(this::leftButtonClick);
        myRightButton4.setOnAction(this::rightButtonClick);

        BorderPane statPane=new BorderPane();
        BorderPane paneToBeCentered=new BorderPane();
        BorderPane paneSeparatorTop=new BorderPane();
        BorderPane paneSeparatorBottom=new BorderPane();
        BorderPane statPane1=new BorderPane();
        BorderPane statPane2=new BorderPane();
        BorderPane statPane3=new BorderPane();
        BorderPane statPane4=new BorderPane();



        VBox box1=new VBox();
        box1.getChildren().addAll(statsLabel1,statsInfoLabel1);
        statPane1.setCenter(box1);
        statPane1.setRight(myRightButton1);
        statPane1.setLeft(myLeftButton1);

        VBox box2=new VBox();
        box2.getChildren().addAll(statsLabel2,statsInfoLabel2);
        statPane2.setCenter(box2);
        statPane2.setRight(myRightButton2);
        statPane2.setLeft(myLeftButton2);

        VBox box3=new VBox();
        box3.getChildren().addAll(statsLabel3,statsInfoLabel3);
        statPane3.setCenter(box3);
        statPane3.setRight(myRightButton3);
        statPane3.setLeft(myLeftButton3);

        VBox box4=new VBox();
        box4.getChildren().addAll(statsLabel4,statsInfoLabel4);
        statPane4.setCenter(box4);
        statPane4.setRight(myRightButton4);
        statPane4.setLeft(myLeftButton4);

        statPane1.prefWidthProperty().bind(stage.widthProperty().divide(2));
        statPane1.prefHeightProperty().bind(stage.heightProperty().divide(2));
        statPane4.prefWidthProperty().bind(stage.widthProperty().divide(2));
        statPane4.prefHeightProperty().bind(stage.heightProperty().divide(2));
        statPane2.prefWidthProperty().bind(stage.widthProperty().divide(2));
        statPane2.prefHeightProperty().bind(stage.heightProperty().divide(2));
        statPane3.prefWidthProperty().bind(stage.widthProperty().divide(2));
        statPane3.prefHeightProperty().bind(stage.heightProperty().divide(2));



        paneSeparatorTop.setLeft(statPane1);
        paneSeparatorTop.setRight(statPane2);
        paneSeparatorBottom.setLeft(statPane3);
        statPane3.setBackground(new Background(new BackgroundFill(Color.YELLOW, null, null)));
        paneSeparatorBottom.setRight(statPane4);
        statPane4.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));

        paneSeparatorTop.setMinHeight(100);
        paneSeparatorTop.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
        paneSeparatorBottom.setMinHeight(100);
        paneSeparatorBottom.setBackground(new Background(new BackgroundFill(Color.BLUE, null, null)));

        paneToBeCentered.setTop(paneSeparatorTop);
        paneToBeCentered.setBottom(paneSeparatorBottom);

        statPane.setCenter(paneToBeCentered);

        ((Group) scene.getRoot()).getChildren().addAll(statPane);

        stage.setScene(scene);
        stage.show();

        statPane.addEventFilter(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        mouseX = event.getX();
                        mouseY = event.getY();
                        //System.out.println(event.getX());
                        //System.out.println(event.getY());
                    }
                });
        stage.heightProperty().addListener((observe, oldVal, newVal) -> {
            stageMidY = stage.getHeight()/2;
            System.out.println(stageMidY);
        });

        stage.widthProperty().addListener((observe, oldVal, newVal) -> {
            stageMidX = stage.getWidth()/2;
            System.out.println(stageMidX);
        });


    }

    private int determinePane(double mouseX,double mouseY){
        int pane = 0;
        if (mouseX <= stageMidX && mouseY <= stageMidY) {
            pane = 1;
        }
        else if (mouseX >= stageMidX && mouseY <= stageMidY) {
            pane = 2;
        }
        else if (mouseX <= stageMidX && mouseY >= stageMidY) {
            pane = 3;
        }
        else if (mouseX >= stageMidX && mouseY >= stageMidY) {
            pane = 4;
        }
        return pane;
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
        statsLabel1.setText(statActions.get(currentActionIndex));
        int paneNumber=determinePane(mouseX,mouseY);
        doStatistic(paneNumber);
        currentActionIndex = currentActionIndex;

    }

    /**
     * This will be executed when the button is clicked
     * It increments the count by 1
     */
    private void doStatistic(int number) {
        switch (number) {
            case 1:
                if (statsLabel1.getText().equals("Average reviews")) {
                    double average = calculator.calculateAverageViews(tableData);
                    {
                        statsInfoLabel1.setText(Double.toString(Math.round(average)));
                    }
                } else if (statsLabel1.getText().equals("Available properties")) {
                    int total = calculator.calculateAvailability(tableData);
                    {
                        statsInfoLabel1.setText(Integer.toString(total));
                    }
                } else if (statsLabel1.getText().equals("Homes and apartments")) {
                    int totalProperties = calculator.calculateRoomType(tableData);
                    {
                        statsInfoLabel1.setText(Integer.toString(totalProperties));
                    }
                } else if (statsLabel1.getText().equals("Most expensive borough")) {
                    String mostExpensiveBorough = "";
                    HashMap<String, Integer> filteredData = calculator.calculateMostExpensiveBorough(tableData);
                    System.out.println(filteredData);
                    ArrayList<Integer> pricesToFilter = new ArrayList<>();
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
                    statsInfoLabel1.setText(mostExpensiveBorough);

                }

                break;
            case 2:
                if (statsLabel2.getText().equals("Average reviews")) {
                    double average = calculator.calculateAverageViews(tableData);
                    {
                        statsInfoLabel2.setText(Double.toString(Math.round(average)));
                    }
                } else if (statsLabel2.getText().equals("Available properties")) {
                    int total = calculator.calculateAvailability(tableData);
                    {
                        statsInfoLabel2.setText(Integer.toString(total));
                    }
                } else if (statsLabel2.getText().equals("Homes and apartments")) {
                    int totalProperties = calculator.calculateRoomType(tableData);
                    {
                        statsInfoLabel2.setText(Integer.toString(totalProperties));
                    }
                } else if (statsLabel2.getText().equals("Most expensive borough")) {
                    String mostExpensiveBorough = "";
                    HashMap<String, Integer> filteredData = calculator.calculateMostExpensiveBorough(tableData);
                    System.out.println(filteredData);
                    ArrayList<Integer> pricesToFilter = new ArrayList<>();
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
                    statsInfoLabel2.setText(mostExpensiveBorough);
                }
                break;
            case 3:
                if (statsLabel3.getText().equals("Average reviews")) {
                    double average = calculator.calculateAverageViews(tableData);
                    {
                        statsInfoLabel3.setText(Double.toString(Math.round(average)));
                    }
                } else if (statsLabel3.getText().equals("Available properties")) {
                    int total = calculator.calculateAvailability(tableData);
                    {
                        statsInfoLabel3.setText(Integer.toString(total));
                    }
                } else if (statsLabel3.getText().equals("Homes and apartments")) {
                    int totalProperties = calculator.calculateRoomType(tableData);
                    {
                        statsInfoLabel3.setText(Integer.toString(totalProperties));
                    }
                } else if (statsLabel3.getText().equals("Most expensive borough")) {
                    String mostExpensiveBorough = "";
                    HashMap<String, Integer> filteredData = calculator.calculateMostExpensiveBorough(tableData);
                    System.out.println(filteredData);
                    ArrayList<Integer> pricesToFilter = new ArrayList<>();
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
                    statsInfoLabel3.setText(mostExpensiveBorough);
                }

                break;
            case 4:
                if (statsLabel4.getText().equals("Average reviews")) {
                    double average = calculator.calculateAverageViews(tableData);
                    {
                        statsInfoLabel4.setText(Double.toString(Math.round(average)));
                    }
                } else if (statsLabel4.getText().equals("Available properties")) {
                    int total = calculator.calculateAvailability(tableData);
                    {
                        statsInfoLabel4.setText(Integer.toString(total));
                    }
                } else if (statsLabel4.getText().equals("Homes and apartments")) {
                    int totalProperties = calculator.calculateRoomType(tableData);
                    {
                        statsInfoLabel4.setText(Integer.toString(totalProperties));
                    }
                } else if (statsLabel4.getText().equals("Most expensive borough")) {
                    String mostExpensiveBorough = "";
                    HashMap<String, Integer> filteredData = calculator.calculateMostExpensiveBorough(tableData);
                    System.out.println(filteredData);
                    ArrayList<Integer> pricesToFilter = new ArrayList<>();
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
                    statsInfoLabel4.setText(mostExpensiveBorough);
                }
                break;
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
        statsLabel1.setText(statActions.get(currentActionIndex));
        int paneNumber=determinePane(mouseX,mouseY);
        doStatistic(paneNumber);
        currentActionIndex = currentActionIndex;

    }

}
