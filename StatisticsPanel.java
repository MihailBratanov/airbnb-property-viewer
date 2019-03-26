import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    private Font titleFont;
    private Font contentFont;

    private ArrayList<String> statActions = new ArrayList<>();
    private int currentActionIndex = 0;

    private double stageMidX;
    private double stageMidY;

    private double mouseX;
    private double mouseY;


    @Override
    public void start(Stage stage) throws FileNotFoundException {
        //data=loader.load();
        //System.out.println(data.toString());
        int lowerLimit = 500;
        int upperLimit = 1000;
        tableData.addAll(filterData(data, lowerLimit, upperLimit));

        statActions.add("Average reviews");
        statActions.add("Available properties");
        statActions.add("Homes and apartments");
        statActions.add("Most expensive borough");

        titleFont = Font.loadFont(new FileInputStream(new File("KingsFontBold.ttf")), 20);
        contentFont = Font.loadFont(new FileInputStream(new File("KingsFont.ttf")), 16);


        Scene scene = new Scene(new Group());

        BorderPane statPane = new BorderPane();
        BorderPane paneToBeCentered = new BorderPane();
        BorderPane paneSeparatorTop = new BorderPane();

        BorderPane paneSeparatorBottom = new BorderPane();

        BorderPane statPane1 = new BorderPane();
        VBox box1 = new VBox();
        Button myLeftButton1 = new Button("<");
        statsLabel1 = new Label();
        statsLabel1.setText(statActions.get(currentActionIndex));
        Button myRightButton1 = new Button(">");
        statsInfoLabel1 = new Label("default");
        configStatPane(statPane1, box1, myLeftButton1, myRightButton1, statsLabel1, statsInfoLabel1, stage);

        BorderPane statPane2 = new BorderPane();
        VBox box2 = new VBox();
        Button myLeftButton2 = new Button("<");
        statsLabel2 = new Label();
        statsLabel2.setText(statActions.get(currentActionIndex));
        Button myRightButton2 = new Button(">");
        statsInfoLabel2 = new Label("default");
        configStatPane(statPane2, box2, myLeftButton2, myRightButton2, statsLabel2, statsInfoLabel2, stage);

        BorderPane statPane3 = new BorderPane();
        VBox box3 = new VBox();
        Button myLeftButton3 = new Button("<");
        statsLabel3 = new Label();
        statsLabel3.setText(statActions.get(currentActionIndex));
        Button myRightButton3 = new Button(">");
        statsInfoLabel3 = new Label("default");
        configStatPane(statPane3, box3, myLeftButton3, myRightButton3, statsLabel3, statsInfoLabel3, stage);

        BorderPane statPane4 = new BorderPane();
        VBox box4=new VBox();
        Button myLeftButton4 = new Button("<");
        statsLabel4 = new Label();
        statsLabel4.setText(statActions.get(currentActionIndex));
        Button myRightButton4 = new Button(">");
        statsInfoLabel4 = new Label("default");
        configStatPane(statPane4, box4, myLeftButton4, myRightButton4, statsLabel4, statsInfoLabel4, stage);

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

        stage.setWidth(600);
        stage.setHeight(600);

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

        stage.setScene(scene);
        stage.show();

        stageMidX = stage.getWidth()/2;
        stageMidY = stage.getHeight()/2;
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
        System.out.println(mouseX + " " + stageMidX);
        System.out.println(mouseY + " " + stageMidY);
        System.out.println(pane);
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

        int paneNumber = determinePane(mouseX,mouseY);
        switch(paneNumber) {
            case 1: statsLabel1.setText(statActions.get(currentActionIndex));
            break;
            case 2: statsLabel2.setText(statActions.get(currentActionIndex));
                break;
            case 3: statsLabel3.setText(statActions.get(currentActionIndex));
                break;
            case 4: statsLabel4.setText(statActions.get(currentActionIndex));
                break;
        }

        doStatistic(paneNumber);
        currentActionIndex = currentActionIndex;

    }

    /**
     * This will be executed when the button is clicked
     * It increments the count by 1
     */
    private void doStatistic(int number) {
        int paneNumber=number;
        if (paneNumber == 1) {
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
        } else if (paneNumber == 2) {
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
        } else if (paneNumber == 3) {

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
        } else if (paneNumber == 4) {

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

        int paneNumber=determinePane(mouseX,mouseY);
        switch(paneNumber) {
            case 1: statsLabel1.setText(statActions.get(currentActionIndex));
                break;
            case 2: statsLabel2.setText(statActions.get(currentActionIndex));
                break;
            case 3: statsLabel3.setText(statActions.get(currentActionIndex));
                break;
            case 4: statsLabel4.setText(statActions.get(currentActionIndex));
                break;
        }
        doStatistic(paneNumber);
        currentActionIndex = currentActionIndex;

    }

    private void configStatPane(BorderPane pane, VBox box, Button leftButton, Button rightButton, Label statsLabel, Label statsInfoLabel, Stage stage) {
        box.getChildren().addAll(statsLabel,statsInfoLabel);
        statsLabel.setFont(titleFont);
        statsInfoLabel.setFont(contentFont);
        box.setAlignment(Pos.CENTER);
        box.spacingProperty().bind(pane.heightProperty().divide(5));
        pane.setLeft(leftButton);
        pane.setCenter(box);
        pane.setRight(rightButton);
        leftButton.prefHeightProperty().bind(pane.heightProperty());
        leftButton.prefWidthProperty().bind(pane.widthProperty().divide(5));
        rightButton.prefHeightProperty().bind(pane.heightProperty());
        rightButton.prefWidthProperty().bind(pane.widthProperty().divide(5));
        pane.prefWidthProperty().bind(stage.widthProperty().divide(2));
        pane.prefHeightProperty().bind(stage.heightProperty().divide(2));
        leftButton.setOnAction(this::leftButtonClick);
        rightButton.setOnAction(this::rightButtonClick);
    }
}
