import dataStructure.Borough;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.stage.Stage;
import javafx.scene.paint.*;
import javafx.geometry.Pos;
import javafx.scene.shape.Polygon;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.*;
import javafx.scene.text.Text;
import java.lang.Math; 
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.text.*;
import javafx.scene.control.Slider;

/**
 * Class MapPanel. Displays a simplified Map of London : the map is only
 * made out of london boroughs. Represented as hexagons, it creates
 * a clickable button that then initialises the table from TableView class
 * and gives it parameters to launch the Table.
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
public class MapPanel extends Panel {

    private String username;

    private Label myLabel = new Label("0");
    private Stage stage;

    HBox root;
    ScrollPane scrollPane;
    private double width;
    private double height;
    private double windowWidth;
    private double windowHeight;
    private ArrayList<AirbnbListing> boroughSortedProperties;
    private ArrayList<AirbnbListing> dateSortedProperties;
    private HashMap<Button, String> boroughVariable;
    private Borough enfield, barnet, haringey, waltham, harrow, brent, camden, islington, hackney, redbridge,
            havering, hillingdon, ealing, kensington, westminster, tower, newham, barking, hounslow,
            hammersmith, wandsworth, city, greenwich, bexley, richmond, merton, lambeth, southwark,
            lewisham, kingston, sutton, croydon, bromley;
    private ArrayList<Borough> boroughs;
    private HashMap<String, Integer> boroughCount;
    private int lowerLimit, upperLimit;
    private ArrayList<AirbnbListing> data;

    private HashMap<String, Integer> boroughClick;

    private GridPane gridPane;

    private ComboBox numberOfNights = new ComboBox();

    private Stage webViewStage;
    private MapWebView webView;

    private Stage tableViewStage;
    private TableViewer tableViewer;
    private Font font;

    /**
     * Constructor for the MapPanel class. Takes in lower limit and upper limit, the data
     * then to filter the data. MapPanel creates the map of boroughs, links the data to the boroughs.
     * It also tracks the number of click of boroughs of the user.
     */
    public MapPanel(int lowerLimit, int upperLimit, ArrayList<AirbnbListing> data, String username) {

        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
        this.data = data;
        this.username = username;

        boroughSortedProperties = loadData(lowerLimit, upperLimit, data);


        boroughCount = countBoroughs(boroughSortedProperties);

        numberOfNights.getItems().addAll(
                "Any",
                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8",
                "9",
                "10",
                "> 10"
        );

        numberOfNights.setValue("Any");
        numberOfNights.setOnAction(e -> updateMap());

        root = new HBox();
        root.getStylesheets().add("MapViewer.css");
        root.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        root.setAlignment(Pos.CENTER);

        StackPane stackpane = new StackPane();

        ArrayList<Button> bouroughs = new ArrayList<>();

        Slider priceSlider = new Slider(lowerLimit, 7000, upperLimit);
        priceSlider.setShowTickMarks(true);

        Label imageLabel = LoadImage();
        windowWidth = root.getMinWidth();
        windowHeight = root.getMinHeight();

        createBoroughs();


        gridPane = new GridPane();

        //for testing mapwebview

        webViewStage = new Stage();

        webView = new MapWebView();

        Database database = new Database();

        boroughClick = database.getUserProfile(username);

        if (boroughClick.isEmpty()) {
            boroughClick = setUpUserClick(boroughs);
        }

        // testing mapwebview

        for (int i = 0; i < 15; i++) {
            RowConstraints row = new RowConstraints(height / 17);
            gridPane.getRowConstraints().add(row);
        }

        for (int i = 0; i < 16; i++) {
            ColumnConstraints col = new ColumnConstraints(height / 15);
            gridPane.getColumnConstraints().add(col);
        }


        DropShadow shadow = new DropShadow(7, 2, 7, Color.GREY);
        DropShadow noShadow = new DropShadow(7, 2, 7, Color.TRANSPARENT);
        InnerShadow pressed = new InnerShadow(3, 4, 3, Color.WHITE);

        Text boroughHover = new Text();
        boroughHover.setFont(new Font(20));

        for (Borough borough : boroughs) {
            for (String currentBorough : boroughCount.keySet()) {
                if (currentBorough.equals(borough.getNameID())) {
                    Polygon hexagon = new Polygon();

                    hexagon.getPoints().addAll(new Double[]{
                            hexPointX(30), hexPointY(30),
                            hexPointX(90), hexPointY(90),
                            hexPointX(150), hexPointY(150),
                            hexPointX(210), hexPointY(210),
                            hexPointX(270), hexPointY(270),
                            hexPointX(330), hexPointY(330),

                    });
                    

                    int colorGreen = (int) Math.round(boroughCount.get(currentBorough) * 0.1);
                    colorGreen = colorGreen % 255;
                    colorGreen = 255 - colorGreen;
                    int colorRed = 255 - colorGreen;
                    hexagon.setFill(Color.rgb(colorRed, colorGreen, 0));

                    hexagon.setStroke(Color.BLACK);

                    hexagon.setEffect(noShadow);

                    Text text = new Text(borough.getX(), borough.getY(), borough.getShortName());

                    double initialScaleX = hexagon.getScaleX();
                    double initialScaleY = hexagon.getScaleY();

                    hexagon.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            hexagon.setEffect(pressed);
                            hexagon.setScaleX(initialScaleX);
                            hexagon.setScaleY(initialScaleY);

                            String thisBorough = borough.getName();
                            int count = boroughClick.get(thisBorough);
                            count += 1;
                            boroughClick.replace(thisBorough, count);

                            tableViewStage = new Stage();
                            dateSortedProperties = boroughSortedProperties;

                            Database database = new Database();
                            database.writeToProfile(username, boroughClick);

                            tableViewer = new TableViewer(borough.getName(), dateSortedProperties);

                            tableViewer.start(tableViewStage);

                        }

                    });

                    hexagon.addEventFilter(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            hexagon.setEffect(shadow);
                            hexagon.setScaleX(initialScaleX * 1.1);
                            hexagon.setScaleY(initialScaleY * 1.1);
                        }
                    });

                    hexagon.addEventFilter(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            boroughHover.setText(borough.getName());
                            //text.setText(borough.getFullName());
                            hexagon.setEffect(shadow);
                            hexagon.setScaleX(initialScaleX * 1.1);
                            hexagon.setScaleY(initialScaleY * 1.1);
                        }
                    });

                    hexagon.addEventFilter(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            hexagon.setEffect(null);
                            //text.setText(borough.getShortName());
                            hexagon.setScaleX(initialScaleX);
                            hexagon.setScaleY(initialScaleY);
                        }
                    });


                    text.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            hexagon.setEffect(pressed);
                        }
                    });

                    text.addEventFilter(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            hexagon.setEffect(shadow);
                        }
                    });


                    text.addEventFilter(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            hexagon.setEffect(shadow);
                            text.setText(borough.getFullName());
                            hexagon.setScaleX(initialScaleX * 1.1);
                            hexagon.setScaleY(initialScaleY * 1.1);
                        }
                    });

                    text.addEventFilter(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            text.setText(borough.getShortName());
                            hexagon.setScaleX(initialScaleX);
                            hexagon.setScaleY(initialScaleY);
                        }
                    });

                    gridPane.add(hexagon, borough.getX(), borough.getY());
                    gridPane.add(text, borough.getX(), borough.getY());
                }
            }
        }

        gridPane.setAlignment(Pos.CENTER);
        stackpane.getChildren().addAll(gridPane);
        stackpane.setAlignment(Pos.CENTER);

        VBox content = new VBox();
        content.getChildren().addAll(stackpane, boroughHover);
        content.prefHeightProperty().bind(root.heightProperty());

        content.getStyleClass().add("contentbox");
        content.setAlignment(Pos.CENTER_LEFT);
        content.setMaxWidth(500);
        content.setPadding(new Insets(10,10,10,10));

        VBox slogan = new VBox();
        slogan.setAlignment(Pos.CENTER_LEFT);
        //font = new Font("font/Roboto-Regular.ttf", 50);
        font = getKingsFont(45);
        Label instructions = new Label("Please select the\nminimum number of nights\nyou want to stay.");
        instructions.setFont(font);
        instructions.setTextFill(Color.WHITE);
        instructions.setTextAlignment(TextAlignment.LEFT);
        slogan.getChildren().addAll(instructions, numberOfNights);

    //root.setBackground(new Background(new BackgroundImage(new Image("mapViewerBg.jpg"), null, null, CENTER, null)));
        root.getChildren().addAll(content, slogan);
        root.setAlignment(Pos.CENTER_LEFT);
        root.getStyleClass().add("root");
        root.setSpacing(30);

    }

    /**
     * Sets up a HashMap where it stores the
     * amount of times each borough is clicked by the user.
     *
     * Initialises it to 0.
     *
     * @param boroughs list of boroughs.
     * @return
     */

    private HashMap<String, Integer> setUpUserClick(ArrayList<Borough> boroughs){

        HashMap<String, Integer> boroughClick = new HashMap<>();

        for (Borough borough : boroughs){
            String boroughName = borough.getName();
            boroughClick.put(boroughName, 0);
        }

        return boroughClick;
    }

    /**
     * Updates the map when changing the value of the minimum amount of nights.
     * Refilters the data and then assigns it back to the map of boroughs.
     */

    private void updateMap(){
        setNumberOfNights(numberOfNights.getSelectionModel().getSelectedItem().toString());
        boroughCount = countBoroughs(dateSortedProperties);
        gridPane.getChildren().clear();

        DropShadow shadow = new DropShadow(7, 2, 7, Color.GREY);
        DropShadow noShadow = new DropShadow(7, 2, 7, Color.TRANSPARENT);
        InnerShadow pressed = new InnerShadow(3, 4, 3, Color.WHITE);

        Text boroughHover = new Text();
        boroughHover.setFont(new Font(20));
        for (Borough borough : boroughs) {
            for (String currentBorough : boroughCount.keySet()) {
                if (currentBorough.equals(borough.getNameID())) {
                    Polygon hexagon = new Polygon();

                    hexagon.getPoints().addAll(new Double[]{
                            hexPointX(30), hexPointY(30),
                            hexPointX(90), hexPointY(90),
                            hexPointX(150), hexPointY(150),
                            hexPointX(210), hexPointY(210),
                            hexPointX(270), hexPointY(270),
                            hexPointX(330), hexPointY(330),

                    });

                    int colorGreen = (int) Math.round(boroughCount.get(currentBorough) * 0.1);
                    colorGreen = colorGreen % 255;
                    colorGreen = 255 - colorGreen;
                    int colorRed = 255 - colorGreen;
                    hexagon.setFill(Color.rgb(colorRed, colorGreen, 0));

                    hexagon.setStroke(Color.BLACK);

                    hexagon.setEffect(noShadow);

                    Text text = new Text(borough.getX(), borough.getY(), borough.getShortName());

                    double initialScaleX = hexagon.getScaleX();
                    double initialScaleY = hexagon.getScaleY();

                    hexagon.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            hexagon.setEffect(pressed);
                            hexagon.setScaleX(initialScaleX);
                            hexagon.setScaleY(initialScaleY);

                            tableViewStage = new Stage();
                            tableViewer = new TableViewer(borough.getName(),dateSortedProperties);

                            tableViewer.start(tableViewStage);

                        }

                    });

                    hexagon.addEventFilter(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            hexagon.setEffect(shadow);
                            hexagon.setScaleX(initialScaleX * 1.1);
                            hexagon.setScaleY(initialScaleY * 1.1);
                        }
                    });

                    hexagon.addEventFilter(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            boroughHover.setText(borough.getName());
                            //text.setText(borough.getFullName());
                            hexagon.setEffect(shadow);
                            hexagon.setScaleX(initialScaleX * 1.1);
                            hexagon.setScaleY(initialScaleY * 1.1);
                        }
                    });

                    hexagon.addEventFilter(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            hexagon.setEffect(null);
                            //text.setText(borough.getShortName());
                            hexagon.setScaleX(initialScaleX);
                            hexagon.setScaleY(initialScaleY);
                        }
                    });


                    text.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            hexagon.setEffect(pressed);
                        }
                    });

                    text.addEventFilter(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            hexagon.setEffect(shadow);
                        }
                    });


                    text.addEventFilter(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            hexagon.setEffect(shadow);
                            text.setText(borough.getFullName());
                            hexagon.setScaleX(initialScaleX * 1.1);
                            hexagon.setScaleY(initialScaleY * 1.1);
                        }
                    });

                    text.addEventFilter(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            text.setText(borough.getShortName());
                            hexagon.setScaleX(initialScaleX);
                            hexagon.setScaleY(initialScaleY);
                        }
                    });

                    gridPane.add(hexagon, borough.getX(), borough.getY());
                    gridPane.add(text, borough.getX(), borough.getY());
                }
            }
        }
    }

    /**
     * Method that updates what the user selected for the minimum amount of nights.
     *
     * @param nights number of minimum nights - input from the user.
     */
    private void setNumberOfNights(String nights) {
        if (nights != "Any"){
            dateSortedProperties = sortByDay(Integer.parseInt(nights), boroughSortedProperties);
        }
        else if (nights == ">10"){
            dateSortedProperties = sortByDay(11,boroughSortedProperties);
        }
        else {
            dateSortedProperties = boroughSortedProperties;
        }
    }

    /**
     * Math method that gets the X coordinates
     * for the hexagon drawing.
     * @param degree degree of the angle
     * @return
     */
    private double hexPointX(double degree){
        double centerPoint = height/15;

        double rad = (Math.PI / 180) * degree;

        double X = centerPoint + height/14 * Math.cos(rad);

        return X;
    }

    /**
     * Math method that gets the Y coordinates
     * for the hexagon drawing.
     *
     * @param degree degree of the angle
     * @return
     */
    private double hexPointY(double degree){
        double centerPoint = height/15;

        double rad = (Math.PI / 180) * degree;

        double X = centerPoint + height/14 * Math.sin(rad);

        return X;
    }

    /**
     * loads the data from the Airbnb csv File.
     *
     * @param lowerLimit lower limit of the price
     * @param upperLimit upper limit of the price
     * @param data entire data of airbnb listings.
     * @return returns the data filtered.
     */
    private ArrayList<AirbnbListing> loadData(int lowerLimit, int upperLimit, ArrayList<AirbnbListing> data){
        ArrayList<AirbnbListing> specifiedData = new ArrayList<>();
        ArrayList<String> neighbourhoods = new ArrayList<>();
        for (AirbnbListing listing : data){
            if (listing.getPrice() <= upperLimit && listing.getPrice() >= lowerLimit){
                specifiedData.add(listing);
            }
        }

        return specifiedData;
    }

    /**
     * Sorts all the AirBnb listings loaded by
     * minimum amount of nights.
     *
     * @param nights minimum amount of nights
     * @param data data of airbnb listings.
     * @return
     */
    private ArrayList<AirbnbListing> sortByDay(int nights, ArrayList<AirbnbListing> data) {

        ArrayList<AirbnbListing> sortedByDay = new ArrayList<>();

        if (nights < 10) {
            for (AirbnbListing listing : data) {
                if (listing.getMinimumNights() >= nights) {
                    sortedByDay.add(listing);
                }
            }
        }

        return sortedByDay;
    }

    /**
     * Count the amount of properties there are in each borough.
     *
     * @param neighbourhoods List of boroughs.
     * @return a hashmap of count of boroughs.
     */
    private HashMap countBoroughs(ArrayList<AirbnbListing> neighbourhoods){
        
        ArrayList<String> boroughs = new ArrayList <>();
        
        for (AirbnbListing borough : neighbourhoods){
            String thisBorough = borough.getNeighbourhood().replaceAll("\\s+","");
            boroughs.add(thisBorough);
        }
        
        
        String thisProperty;
        
        boroughCount = new HashMap<>();
        
        while(boroughs.size() > 0 ){
            
            thisProperty = boroughs.get(0);
            
            Boolean exists = boroughCount.containsKey(thisProperty);
            
            if ( ! exists){
                boroughCount.put(thisProperty, 1);
            }
            
            else if (exists){
                int count = boroughCount.get(thisProperty);
                count += 1;
                boroughCount.replace(thisProperty, count);
            }
            
            boroughs.remove(0);
        }
        
        return boroughCount;
    }

    /**
     * Creates all the boroughs of type Borough,
     * where it stores the name and the coordinate on the gridPane.
     */

    private void createBoroughs(){
        
        enfield     = new Borough("Enfield",8,1);

        barnet      = new Borough("Barnet",5,3);
        
        haringey    = new Borough("Haringey",7,3);
        waltham     = new Borough("Waltham Forest",9,3);
        
        harrow      = new Borough("Harrow",2,5);
        brent       = new Borough("Brent",4,5);
        camden      = new Borough("Camden",6,5);
        islington   = new Borough("Islington",8,5);
        hackney     = new Borough("Hackney",10,5);
        redbridge   = new Borough("Redbridge",12,5);
        havering    = new Borough("Havering",14,5);
        
        hillingdon  = new Borough("Hillingdon",1,7);
        ealing      = new Borough("Ealing",3,7);
        kensington  = new Borough("Kensington and Chelsea",5,7);
        westminster = new Borough("Westminster",7,7);
        tower       = new Borough("Tower Hamlets",9,7);
        newham      = new Borough("Newham",11,7);
        barking     = new Borough("Barking and Dagenham",13,7);
        
        hounslow    = new Borough("Hounslow",2,9);
        hammersmith = new Borough("Hammersmith and Fulham",4,9);
        wandsworth  = new Borough("Wandsworth",6,9);
        city        = new Borough("City of London",8,9);
        greenwich   = new Borough("Greenwich",10,9);
        bexley      = new Borough("Bexley",12,9);
        
        richmond    = new Borough("Richmond upon Thames",3,11);
        merton      = new Borough("Merton",5,11);
        lambeth     = new Borough("Lambeth",7,11);
        southwark   = new Borough("Southwark",9,11);
        lewisham    = new Borough("Lewisham",11,11);
        
        kingston    = new Borough("Kingston upon Thames",4,13);
        sutton      = new Borough("Sutton",6,13);
        croydon     = new Borough("Croydon",8,13);
        bromley     = new Borough("Bromley",10,13);
        
        boroughs = new ArrayList<>( Arrays.asList(
                enfield, barnet, haringey, waltham, harrow, brent, camden, islington, hackney, redbridge,
                havering, hillingdon, ealing, kensington, westminster, tower, newham, barking, hounslow,
                hammersmith, wandsworth, city, greenwich, bexley, richmond, merton, lambeth, southwark, 
                lewisham, kingston, sutton, croydon, bromley ));

    }

    /**
     * Main Method that returns the panel to the View class,
     * for the map to get displayed.
     *
     * @return returns the panel.
     */
    public Pane getPanel(){
        return root;
    }

    /**
     * Loads the background image and returns it.
     *
     * @return returns the loaded image.
     */
    private Label LoadImage(){
        Label imageLabel = new Label();        
        String imagePath = "img/boroughs.png";
        Image image = new Image(imagePath);

        ImageView imageViewer = new ImageView(image);

        width =  image.getWidth();
        width = width * 0.18;
        height =  image.getHeight(); 
        height = height * 0.18;

        imageViewer.setPreserveRatio(true);
        imageViewer.setFitHeight(height);
        imageViewer.setFitWidth(width);
        imageViewer.setSmooth(true);
        imageLabel.setGraphic(imageViewer);

        return imageLabel;
    }


    /**
     * Get the font to be used inside of the text.
     * We have selected to use the King's font.
     *
     * @param size gets the size of the font
     * @return the font with the size.
     */
    private Font getKingsFont(int size) {
        Font font;
        try {
            return font = Font.loadFont(new FileInputStream(new File("font/KingsFont.ttf")), size);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return font = Font.font("Verdana", size);
        }
    }
}