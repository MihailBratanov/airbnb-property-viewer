import dataStructure.StatValues;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.paint.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

import java.util.*;

/**
 * This class implements and visualizes all the statistics, done over the filtered data by the user selection
 * of lower and upper limit. The calculations themselves, however are done in a separate class called Calculator.
 *
 * @author Haiyun Zou, Ka Wang Sin, Mihail Bratanov and Terry Phung
 * @version 2019.03.29
 *
 * 18-19 4CCS1PPA Programming Practice and Applications
 * Term 2 Coursework 4 - London Property Marketplace
 * Created by Haiyun Zou, Ka Wang Sin, Mihail Bratanov and Terry Phung
 * Student ID: 1828556, 1850162, 1838362, 1833386
 * k-number: k1895418, k1802265, k1888765, k1895389
 */

public class  StatisticsPanel {



    private Calculator calculator = new Calculator();

    public AirbnbDataLoader loader = new AirbnbDataLoader();
    public ArrayList<AirbnbListing> data = loader.load();

    private ObservableList<AirbnbListing> tableData;

    private Label statsLabel1;
    private Label statsInfoLabel1;
    private Label statsLabel2;
    private Label statsInfoLabel2;
    private Label statsLabel3;
    private Label statsInfoLabel3;
    private Label statsLabel4;
    private Label statsInfoLabel4;
    private Font titleFont;
    private Font contentFont;

    private ArrayList<String> statActions = new ArrayList<>();
    private ArrayList<StatValues> displayedText;
    private int currentActionIndex = 0;

    private double stageMidX;
    private double stageMidY;

    private double mouseX;
    private double mouseY;

    private BorderPane statPane;

    private String username;

    Database database=new Database();

    /**
     * Constructor for class StatisticsPanel
     * @param stage -the stage where the stats are to appear
     * @param lowerLimit - lower price limit from Viewer
     * @param upperLimit - upper price limit from Viewer
     * @param username - username of the currently logged in user
     */

    public StatisticsPanel(Stage stage, int lowerLimit, int upperLimit, String username) {

        this.username = username;

        tableData = FXCollections.observableArrayList();
        tableData.addAll(filterData(data, lowerLimit, upperLimit));


        StatValues mostPopulatedBorough = new StatValues("mostPopulatedBorough",getMostPopulatedBorough());
        StatValues mostActiveMonth = new StatValues("mostActiveMonth",getMostActiveMonth());
        StatValues mostRecentListing = new StatValues("mostRecentListing",getMostRecentListing());
        StatValues averageReviews = new StatValues("averageReviews",getAverageReviews());
        StatValues availableProperties = new StatValues("availableProperties",getAvailableProperties());
        StatValues homeAndAppartments = new StatValues("homeAndAppartments",getHomeAndAppartments());
        StatValues mostExpensiveBorough = new StatValues("mostExpensiveBorough",getMostExpensiveBorough());
        StatValues mostClickedBorough=new StatValues("mostClickedBorough", getClickedBorough());

        displayedText = new ArrayList<>( Arrays.asList(mostPopulatedBorough, mostActiveMonth, mostRecentListing, averageReviews,
                           availableProperties, homeAndAppartments, mostExpensiveBorough,mostClickedBorough));

//statistics:
//--------------core---------------------------
        statActions.add("Average reviews");
        statActions.add("Available properties");
        statActions.add("Homes and apartments");
        statActions.add("Most expensive borough");
//---------------extra-------------------------
        statActions.add("Most recent listing");
        statActions.add("Most active month");
        statActions.add("Most populated borough");
        statActions.add("Most clicked borough");

        try {
            titleFont = Font.loadFont(new FileInputStream(new File("font/KingsFontBold.ttf")), 20);
            contentFont = Font.loadFont(new FileInputStream(new File("font/KingsFont.ttf")), 16);
        } catch (FileNotFoundException e) {
            titleFont = Font.loadFont("Verdana", 20);
        }



        //Setting a new scene
        Scene scene = new Scene(new Group());

        //creating a border pane for all four stats
        statPane = new BorderPane();
        BorderPane paneToBeCentered = new BorderPane();
        BorderPane paneSeparatorTop = new BorderPane();

        BorderPane paneSeparatorBottom = new BorderPane();

        //adding the first stat box elements to the pane
        BorderPane statPane1 = new BorderPane();
        VBox box1 = new VBox();
        Button myLeftButton1 = new Button("<");
        statsLabel1 = new Label();
        StatValues stats1 = displayedText.get(0);
        statsLabel1.setText(stats1.getName());
        Button myRightButton1 = new Button(">");
        statsInfoLabel1 = new Label("default");
        statsInfoLabel1.setText(stats1.getValue());
        configStatPane(statPane1, box1, myLeftButton1, myRightButton1, statsLabel1, statsInfoLabel1, stage);
        displayedText.remove(0);

        //adding the second stat box elements to the pane
        BorderPane statPane2 = new BorderPane();
        VBox box2 = new VBox();
        Button myLeftButton2 = new Button("<");
        statsLabel2 = new Label();
        StatValues stats2 = displayedText.get(0);
        statsLabel2.setText(stats2.getName());
        Button myRightButton2 = new Button(">");
        statsInfoLabel2 = new Label("default");
        statsInfoLabel2.setText(stats2.getValue());
        configStatPane(statPane2, box2, myLeftButton2, myRightButton2, statsLabel2, statsInfoLabel2, stage);
        displayedText.remove(0);

        //adding the third stat box elements to the pane
        BorderPane statPane3 = new BorderPane();
        VBox box3 = new VBox();
        Button myLeftButton3 = new Button("<");
        statsLabel3 = new Label();
        StatValues stats3 = displayedText.get(0);
        statsLabel3.setText(stats3.getName());
        Button myRightButton3 = new Button(">");
        statsInfoLabel3 = new Label("default");
        statsInfoLabel3.setText(stats3.getValue());
        configStatPane(statPane3, box3, myLeftButton3, myRightButton3, statsLabel3, statsInfoLabel3, stage);
        displayedText.remove(0);

        //adding the forth stat box elements to the pane
        BorderPane statPane4 = new BorderPane();
        VBox box4=new VBox();
        Button myLeftButton4 = new Button("<");
        statsLabel4 = new Label();
        StatValues stats4 = displayedText.get(0);
        statsLabel4.setText(stats4.getName());
        Button myRightButton4 = new Button(">");
        statsInfoLabel4 = new Label("default");
        statsInfoLabel4.setText(stats4.getValue());
        configStatPane(statPane4, box4, myLeftButton4, myRightButton4, statsLabel4, statsInfoLabel4, stage);
        displayedText.remove(0);

        //distributing them via another pane
        paneSeparatorTop.setLeft(statPane1);
        paneSeparatorTop.setRight(statPane2);
        paneSeparatorBottom.setLeft(statPane3);
        paneSeparatorBottom.setRight(statPane4);

        paneToBeCentered.setTop(paneSeparatorTop);
        paneToBeCentered.setBottom(paneSeparatorBottom);
        paneSeparatorTop.setStyle("-fx-border-color: #7a7a7a;  -fx-border-width: 0px 0px 2px 0px;");
        statPane1.setStyle("-fx-border-color: #7a7a7a;  -fx-border-width: 0px 2px 0px 0px;");
        statPane3.setStyle("-fx-border-color: #7a7a7a;  -fx-border-width: 0px 2px 0px 0px;");
        paneToBeCentered.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));

        statPane.setCenter(paneToBeCentered);

        ((Group) scene.getRoot()).getChildren().addAll(statPane);


        //adding an eventlistener to track the mouse position
        statPane.addEventFilter(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseX = event.getX();
                mouseY = event.getY();
            }
        });
        //a function to get the stage height/2 dynamically
        stage.heightProperty().addListener((observe, oldVal, newVal) -> {
            stageMidY = stage.getHeight()/2;
        });

        //a function to get the stage width/2 dynamically
        stage.widthProperty().addListener((observe, oldVal, newVal) -> {
            stageMidX = stage.getWidth()/2;
        });

        stageMidX = stage.getWidth()/2;
        stageMidY = stage.getHeight()/2;
    }

    /**
     * a method to determine the position of the mouse cursor at any give time so as to detect the quadrant
     * of the stage in which the user is currently
     * @param mouseX the X coordinate of the mouse
     * @param mouseY the Y coordinate of the mouse
     * @return and integer with the number of the current pane
     * [1,2]
     * [3,4]
     */
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
     * This method executes upon a click on any left button. At any point only the stats that are not shown
     * by any of the boxes are available to iterate through.
     */
    private void leftButtonClick(ActionEvent event) {
        currentActionIndex++;
        if (currentActionIndex > statActions.size() - 1) {
            currentActionIndex = 0;
        }

        int paneNumber = determinePane(mouseX, mouseY);


        switch (paneNumber) {
            case 1:
                StatValues tempValue1 = new StatValues(statsLabel1.getText(), statsInfoLabel1.getText());
                displayedText.add(tempValue1);

                StatValues newValue1 = displayedText.get(0);

                statsLabel1.setText(newValue1.getName());
                statsInfoLabel1.setText(newValue1.getValue());

                displayedText.remove(0);

                break;
            case 2:
                StatValues tempValue2 = new StatValues(statsLabel2.getText(), statsInfoLabel2.getText());
                displayedText.add(tempValue2);

                StatValues newValue2 = displayedText.get(0);

                statsLabel2.setText(newValue2.getName());
                statsInfoLabel2.setText(newValue2.getValue());

                displayedText.remove(0);
                break;
            case 3:
                StatValues tempValue3 = new StatValues(statsLabel3.getText(), statsInfoLabel3.getText());
                displayedText.add(tempValue3);

                StatValues newValue3 = displayedText.get(0);

                statsLabel3.setText(newValue3.getName());
                statsInfoLabel3.setText(newValue3.getValue());

                displayedText.remove(0);
                break;
            case 4:
                StatValues tempValue4 = new StatValues(statsLabel4.getText(), statsInfoLabel4.getText());
                displayedText.add(tempValue4);

                StatValues newValue4 = displayedText.get(0);

                statsLabel4.setText(newValue4.getName());
                statsInfoLabel4.setText(newValue4.getValue());

                displayedText.remove(0);
                break;
        }

        currentActionIndex = currentActionIndex;

    }

    /**
     * This method executes upon a click on any right button. At any point only the stats that are not shown
     * by any of the boxes are available to iterate through.
     */
    private void rightButtonClick(ActionEvent event) {
        currentActionIndex--;
        if (currentActionIndex < 0) {
            currentActionIndex = statActions.size() - 1;
        }

        int paneNumber=determinePane(mouseX,mouseY);
        switch(paneNumber) {
            case 1:
                StatValues tempValue1 = new StatValues(statsLabel1.getText(), statsInfoLabel1.getText());
                displayedText.add(tempValue1);

                StatValues newValue1 = displayedText.get(0);

                statsLabel1.setText(newValue1.getName());
                statsInfoLabel1.setText(newValue1.getValue());

                displayedText.remove(0);
                break;
            case 2:
                StatValues tempValue2 = new StatValues(statsLabel2.getText(), statsInfoLabel2.getText());
                displayedText.add(tempValue2);

                StatValues newValue2 = displayedText.get(0);

                statsLabel2.setText(newValue2.getName());
                statsInfoLabel2.setText(newValue2.getValue());

                displayedText.remove(0);
                break;
            case 3: StatValues tempValue3 = new StatValues(statsLabel3.getText(), statsInfoLabel3.getText());
                displayedText.add(tempValue3);

                StatValues newValue3 = displayedText.get(0);

                statsLabel3.setText(newValue3.getName());
                statsInfoLabel3.setText(newValue3.getValue());

                displayedText.remove(0);
                break;
            case 4: StatValues tempValue4 = new StatValues(statsLabel4.getText(), statsInfoLabel4.getText());
                displayedText.add(tempValue4);

                StatValues newValue4 = displayedText.get(0);

                statsLabel4.setText(newValue4.getName());
                statsInfoLabel4.setText(newValue4.getValue());

                displayedText.remove(0);
                break;
        }
        currentActionIndex = currentActionIndex;

    }

    //====================Methods to calculate the statistics through the Calculator class===============================

    /**
     * A method to get the most populated borough aka the one with the most properties
     * @return the borough
     */
    private String getMostPopulatedBorough(){
        String returnValue = "";

        HashMap<String,Integer>counts=calculator.calculateMostPopulatedBorough(tableData);
        int max = 0;
        for (String borough : counts.keySet()){
            if (counts.get(borough) > max){
                max = counts.get(borough);
            }
        }

        for(Map.Entry<String,Integer> value:counts.entrySet()){
            if(value.getValue()==max){
                returnValue = value.getKey();
                break;
            }

        }

        return returnValue;
    }

    /**
     * a method to get the month when the most listings happen
     * @return the month
     */
    private String getMostActiveMonth(){

        String returnValue = null;


        HashMap<String,Integer> datesAndCounts =calculator.calculateBusiestMonth(tableData);

        //int max=Collections.max(datesAndCounts.values());
        int max = 0;
        for (String date : datesAndCounts.keySet()){
            if (datesAndCounts.get(date) > max){
                max = datesAndCounts.get(date);
            }
        }

        for(Map.Entry<String,Integer> value:datesAndCounts.entrySet()){
            if(value.getValue()==max){
                returnValue = (value.getKey());
                break;
            }
        }
        return returnValue;
    }

    /**
     * A method to get the most recent listing's io
     * @return the id of the listing
     */
    private String getMostRecentListing(){


        HashMap<Integer,String>dates;
        dates=calculator.calculateMostRecentListing(tableData);

        int max = 0;
        for (Integer date : dates.keySet()){
            if (date > max){
                max = date;
            }
        }

        return dates.get(max);
    }

    /**
     * A method to calculate the average reviews from the data
     * @return the average, rounded
     */
    private String getAverageReviews(){


        double average = calculator.calculateAverageViews(tableData);

        return Double.toString(Math.round(average));

    }

    /**
     * A method which return the amount of all the currently available properties
     * @return the number of properties
     */
    private String getAvailableProperties() {

        int total = calculator.calculateAvailability(tableData);

        return Integer.toString(total);

    }

    /**
     * A method which returns the amount of all the whole properties
     * @return amount of whole properties
     */
    private String getHomeAndAppartments() {

        int totalProperties = calculator.calculateRoomType(tableData);

        return (Integer.toString(totalProperties));

    }

    /**
     * A method used to calculate the most expensive borough
     * @return the borough
     */
    private String getMostExpensiveBorough() {


        String mostExpensiveBorough = calculator.calculateMostExpensiveBorough(tableData);
        return mostExpensiveBorough;
    }

    /**
     * A method which reads from a database, which stores the clicks per borough and then displays the most clicked one
     * @return the most clicked borough
     */
    private String getClickedBorough( ){

        String mostClicked=database.getMostClickedBorough(username);
        return mostClicked;
    }

//======================================================================================================================

    /**
     *A method to filter the data by a lower price limit and an upper price limit
     */
    private ArrayList<AirbnbListing> filterData(ArrayList<AirbnbListing> data, int lowerLimit, int upperLimit) {

        ArrayList<AirbnbListing> newList = new ArrayList<>();
        for (AirbnbListing listing : data) {

            if (listing.getPrice() >= lowerLimit && listing.getPrice() <= upperLimit) {

                newList.add(listing);
            }
        }
        return newList;
    }


    /**
     * A method to help display and modify the visuals of the border panes for the stats
     */
    private void configStatPane(BorderPane pane, VBox box, Button leftButton, Button rightButton, Label statsLabel, Label statsInfoLabel, Stage stage) {
        box.getChildren().addAll(statsLabel,statsInfoLabel);
        statsLabel.setFont(titleFont);
        statsInfoLabel.setFont(contentFont);
        box.setAlignment(Pos.CENTER);
        box.spacingProperty().bind(pane.heightProperty().divide(5));
        pane.setLeft(leftButton);
        pane.setCenter(box);
        pane.setRight(rightButton);
        pane.setAlignment(leftButton, Pos.CENTER_LEFT);
        pane.setAlignment(rightButton, Pos.CENTER_RIGHT);
        pane.setPadding(new Insets(15 ,15, 15, 15));
        leftButton.prefHeightProperty().bind(pane.heightProperty());
        leftButton.prefWidthProperty().bind(pane.widthProperty().divide(7));
        leftButton.setOpacity(0.5);
        rightButton.prefHeightProperty().bind(pane.heightProperty());
        rightButton.prefWidthProperty().bind(pane.widthProperty().divide(7));
        rightButton.setOpacity(0.5);
        pane.prefWidthProperty().bind(stage.widthProperty().divide(2));
        pane.prefHeightProperty().bind(stage.heightProperty().divide(2));
        leftButton.setOnAction(this::leftButtonClick);
        rightButton.setOnAction(this::rightButtonClick);
    }
    /**
     * a getter method to allow access to the statPane from another class
     * @return statPane
     */
    public Pane getPanel(){
        return statPane;
 }

}
