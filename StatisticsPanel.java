import javafx.application.Application;
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

public class StatisticsPanel {



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

    public StatisticsPanel(Stage stage, int lowerLimit, int upperLimit) {

        tableData = FXCollections.observableArrayList();

        //int lowerLimit = 100;
        //int upperLimit = 500;
        tableData.addAll(filterData(data, lowerLimit, upperLimit));


        StatValues mostPopulatedBorough = new StatValues("mostPopulatedBorough",getMostPopulatedBorough());
        StatValues mostActiveMonth = new StatValues("mostActiveMonth",getMostActiveMonth());
        StatValues mostRecentListing = new StatValues("mostRecentListing",getMostRecentListing());
        StatValues averageReviews = new StatValues("averageReviews",getAverageReviews());
        StatValues availableProperties = new StatValues("availableProperties",getAvailableProperties());
        StatValues homeAndAppartments = new StatValues("homeAndAppartments",getHomeAndAppartments());
        StatValues mostExpensiveBorough = new StatValues("mostExpensiveBorough",getMostExpensiveBorough());

        displayedText = new ArrayList<>( Arrays.asList(mostPopulatedBorough, mostActiveMonth, mostRecentListing, averageReviews,
                           availableProperties, homeAndAppartments, mostExpensiveBorough));
    /*}

    @Override
    public void start(Stage stage) throws FileNotFoundException {*/
        //data=loader.load();
        //System.out.println(data.toString());
        //int lowerLimit = 100;
        //int upperLimit = 500;
        //tableData.addAll(filterData(data, lowerLimit, upperLimit));
        //System.out.println(tableData);
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
            titleFont = Font.loadFont(new FileInputStream(new File("KingsFontBold.ttf")), 20);
            contentFont = Font.loadFont(new FileInputStream(new File("KingsFont.ttf")), 16);
        } catch (FileNotFoundException e) {
            titleFont = Font.loadFont("Verdana", 20);
        }



        Scene scene = new Scene(new Group());

        statPane = new BorderPane();
        BorderPane paneToBeCentered = new BorderPane();
        BorderPane paneSeparatorTop = new BorderPane();

        BorderPane paneSeparatorBottom = new BorderPane();

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



        //stage.setWidth(600);
        //stage.setHeight(600);

        statPane.addEventFilter(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseX = event.getX();
                mouseY = event.getY();
            }
        });
        stage.heightProperty().addListener((observe, oldVal, newVal) -> {
            stageMidY = stage.getHeight()/2;
        });

        stage.widthProperty().addListener((observe, oldVal, newVal) -> {
            stageMidX = stage.getWidth()/2;
        });

        //stage.setScene(scene);
        //stage.show();

        stageMidX = stage.getWidth()/2;
        stageMidY = stage.getHeight()/2;
    }

    public Pane getStatePane(){
        return statPane;
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

        int paneNumber = determinePane(mouseX, mouseY);

        System.out.println(displayedText);

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

        //doStatistic(paneNumber);
        currentActionIndex = currentActionIndex;

    }

    private void rightButtonClick(ActionEvent event) {
        currentActionIndex--;
        if (currentActionIndex < 0) {
            currentActionIndex = statActions.size() - 1;
        }

        int paneNumber=determinePane(mouseX,mouseY);
        switch(paneNumber) {
            case 1:
                //statsLabel1.setText(statActions.get(currentActionIndex));
                statsLabel1.setText(statActions.get(currentActionIndex));
                break;
            case 2: statsLabel2.setText(statActions.get(currentActionIndex));
                break;
            case 3: statsLabel3.setText(statActions.get(currentActionIndex));
                break;
            case 4: statsLabel4.setText(statActions.get(currentActionIndex));
                break;
        }
        //doStatistic(paneNumber);
        currentActionIndex = currentActionIndex;

    }

    //=============================================================================================================================================

    private String getMostPopulatedBorough(){
        String returnValue = "";

        HashMap<String,Integer>counts=calculator.calculateMostPopulatedBorough(tableData);
        //int max = Collections.max(counts.values());
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

    private String getMostActiveMonth(){

        String returnValue = null;

        System.out.println("most act month");
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
    private String getMostRecentListing(){

        System.out.println("running most rec listing");
        HashMap<Integer,String>dates;
        dates=calculator.calculateMostRecentListing(tableData);

        //Set<Integer>datesToCompare=dates.keySet();
        //int max=Collections.max(datesToCompare);

        int max = 0;
        for (Integer date : dates.keySet()){
            if (date > max){
                max = date;
            }
        }

        return dates.get(max);
    }

    private String getAverageReviews(){

        System.out.println("avg reviews");
        double average = calculator.calculateAverageViews(tableData);

        return Double.toString(Math.round(average));

    }
    private String getAvailableProperties() {
        System.out.println("avail prop");
        int total = calculator.calculateAvailability(tableData);

        return Integer.toString(total);

    }
    private String getHomeAndAppartments() {
        System.out.println("hms and apps");
        int totalProperties = calculator.calculateRoomType(tableData);

        return (Integer.toString(totalProperties));

    }
    private String getMostExpensiveBorough() {
        System.out.println("most exp bor");
        /**
        String mostExpensiveBorough = "";
        HashMap<String, Integer> filteredData = calculator.calculateMostExpensiveBorough(tableData);

        System.out.println("Begin Calc");
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

        System.out.println("Data filtered and got the most expensive one.");

        for (String currKey : filteredData.keySet()) {
            if (large == filteredData.get(currKey)) {
                mostExpensiveBorough = currKey;
                break;
            }
        }
        return mostExpensiveBorough;
         */
        String mostExpensiveBorough = calculator.calculateMostExpensiveBorough(tableData);

        return mostExpensiveBorough;
    }

//======================================================================================================================
    /**
     * This will be executed when the button is clicked
     * It increments the count by 1
     *
    private void doStatistic(int number) {
        int paneNumber=number;
        if (paneNumber == 1) {
            if(statsLabel1.getText().equals("Most populated borough")){
                HashMap<String,Integer>counts=calculator.calculateMostPopulatedBorough(tableData);
                int max=Collections.max(counts.values());

                for(Map.Entry<String,Integer> value:counts.entrySet()){
                    if(value.getValue()==max){
                        statsInfoLabel1.setText(value.getKey());
                    }
                }

            }
            else if(statsLabel1.getText().equals("Most active month")){
                System.out.println("most act month");
                HashMap<String,Integer>datesAndCounts=calculator.calculateBusiestMonth(tableData);
                int max=Collections.max(datesAndCounts.values());
                for(Map.Entry<String,Integer> value:datesAndCounts.entrySet()){
                    if(value.getValue()==max){
                        statsInfoLabel1.setText(value.getKey());
                    }
                }
            }
            else if(statsLabel1.getText().equals("Most recent listing")){
                System.out.println("running most rec listing");
                 HashMap<Integer,String>dates;
                dates=calculator.calculateMostRecentListing(tableData);

                Set<Integer>datesToCompare=dates.keySet();
                int max=Collections.max(datesToCompare);
                statsInfoLabel1.setText(dates.get(max));
            }
            else if (statsLabel1.getText().equals("Average reviews")) {
                System.out.println("avg reviews");
                double average = calculator.calculateAverageViews(tableData);
                {
                    statsInfoLabel1.setText(Double.toString(Math.round(average)));
                }
            } else if (statsLabel1.getText().equals("Available properties")) {
                System.out.println("avail prop");
                int total = calculator.calculateAvailability(tableData);
                {
                    statsInfoLabel1.setText(Integer.toString(total));
                }
            } else if (statsLabel1.getText().equals("Homes and apartments")) {
                System.out.println("hms and apps");
                int totalProperties = calculator.calculateRoomType(tableData);
                {
                    statsInfoLabel1.setText(Integer.toString(totalProperties));
                }
            } else if (statsLabel1.getText().equals("Most expensive borough")) {
                System.out.println("most exp bor");
                String mostExpensiveBorough = "";
                HashMap<String, Integer> filteredData = calculator.calculateMostExpensiveBorough(tableData);

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
        } else if (paneNumber == 2) {
            if(statsLabel2.getText().equals("Most active month")){
                HashMap<String,Integer>datesAndCounts=calculator.calculateBusiestMonth(tableData);
                int max=Collections.max(datesAndCounts.values());
                for(Map.Entry<String,Integer> value:datesAndCounts.entrySet()){
                    if(value.getValue()==max){
                        statsInfoLabel2.setText(value.getKey());
                    }
                }
            }
            if(statsLabel2.getText().equals("Most recent listing")){
                HashMap<Integer,String>dates;
                dates=calculator.calculateMostRecentListing(tableData);

                Set<Integer>datesToCompare=dates.keySet();
                int max=Collections.max(datesToCompare);
                statsInfoLabel2.setText(dates.get(max));
            }
            else if (statsLabel2.getText().equals("Average reviews")) {
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
        } else if (paneNumber == 3) {
            if(statsLabel3.getText().equals("Most active month")){
                HashMap<String,Integer>datesAndCounts=calculator.calculateBusiestMonth(tableData);
                int max=Collections.max(datesAndCounts.values());
                for(Map.Entry<String,Integer> value:datesAndCounts.entrySet()){
                    if(value.getValue()==max){
                        statsInfoLabel3.setText(value.getKey());
                    }
                }
            }
            else if(statsLabel3.getText().equals("Most recent listing")){
                HashMap<Integer,String>dates;
                dates=calculator.calculateMostRecentListing(tableData);

                Set<Integer>datesToCompare=dates.keySet();
                int max=Collections.max(datesToCompare);
                statsInfoLabel3.setText(dates.get(max));
            }

            else if (statsLabel3.getText().equals("Average reviews")) {
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
        } else if (paneNumber == 4) {
            if(statsLabel4.getText().equals("Most active month")){
                HashMap<String,Integer>datesAndCounts=calculator.calculateBusiestMonth(tableData);
                int max=Collections.max(datesAndCounts.values());
                for(Map.Entry<String,Integer> value:datesAndCounts.entrySet()){
                    if(value.getValue()==max){
                        statsInfoLabel4.setText(value.getKey());
                    }
                }
            }
            else if(statsLabel4.getText().equals("Most recent listing")){
                HashMap<Integer,String>dates;
                dates=calculator.calculateMostRecentListing(tableData);

                Set<Integer>datesToCompare=dates.keySet();
                int max=Collections.max(datesToCompare);
                statsInfoLabel4.setText(dates.get(max));
            }

            else if (statsLabel4.getText().equals("Average reviews")) {
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

        }
    }
     */




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

 public Pane getPanel(){
        return statPane;
 }

}
