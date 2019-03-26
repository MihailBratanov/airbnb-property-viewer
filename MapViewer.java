import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.stage.Stage;
import javafx.scene.paint.*;
import javafx.geometry.Pos;
import javafx.scene.shape.Polygon;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import java.util.*;
import javafx.scene.text.Text;
import java.lang.Math; 
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.text.*;
import javafx.scene.control.Slider;
/**
 * Write a description of JavaFX class Viewer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

public class MapViewer extends Panel
{

    private Label myLabel = new Label("0");
    private Stage stage;

    VBox root;
    ScrollPane scrollPane;
    private double width;
    private double height;
    private double windowWidth;
    private double windowHeight;
    private ArrayList<String> houses;
    private HashMap <Button, String> boroughVariable;
    private Borough enfield, barnet, haringey, waltham, harrow, brent, camden, islington, hackney, redbridge,
    havering, hillingdon, ealing, kensington, westminster, tower, newham, barking, hounslow,
    hammersmith, wandsworth, city, greenwich, bexley, richmond,merton, lambeth, southwark, 
    lewisham, kingston, sutton, croydon, bromley ;
    private ArrayList <Borough> boroughs;
    private HashMap <String, Integer> boroughCount;

    private Stage webViewStage;
    private MapWebView webView;

    public MapViewer(int lowerLimit, int upperLimit){
        houses = loadData(lowerLimit, upperLimit);
        
        boroughCount = countBoroughs(houses);

        root = new VBox();
        
        root.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));

        StackPane stackpane = new StackPane();

        ArrayList<Button> bouroughs = new ArrayList<>();
        
        Slider priceSlider = new Slider(lowerLimit, 7000, upperLimit);
        priceSlider.setShowTickMarks(true);

        Label imageLabel = LoadImage();
        windowWidth = root.getMinWidth();
        windowHeight = root.getMinHeight();

        createBoroughs();

        DropShadow shadow = new DropShadow(7,2,7, Color.GREY);
        DropShadow noShadow = new DropShadow(7,2,7, Color.TRANSPARENT);
        InnerShadow pressed = new InnerShadow(3,4,3, Color.WHITE);
        
        Text boroughHover = new Text();
        boroughHover.setFont(new Font(20));
               
        GridPane gridPane = new GridPane();

        //for testing mapwebview

        webViewStage = new Stage();

        webView = new MapWebView();


        // testing mapwebview

        for (int i = 0; i < 15; i ++){
            RowConstraints row = new RowConstraints(height/17);
            gridPane.getRowConstraints().add(row);
        }

        for (int i = 0; i < 16; i ++) {
            ColumnConstraints col = new ColumnConstraints(height / 15);
            gridPane.getColumnConstraints().add(col);
        }

        for (Borough borough : boroughs){
            for (String currentBorough : boroughCount.keySet()){
                if (currentBorough.equals(borough.getNameID())){
                    Polygon hexagon = new Polygon();
        
                    hexagon.getPoints().addAll(new Double[]{        
                        hexPointX(30), hexPointY(30),
                        hexPointX(90), hexPointY(90),
                        hexPointX(150), hexPointY(150),
                        hexPointX(210), hexPointY(210),
                        hexPointX(270), hexPointY(270),
                        hexPointX(330), hexPointY(330),
                        
                    });
                    
                    int colorGreen = (int) Math.round(boroughCount.get(currentBorough)* 0.1);
                    colorGreen = colorGreen % 255;
                    colorGreen = 255 - colorGreen;
                    int colorRed = 255 - colorGreen;
                    hexagon.setFill(Color.rgb(colorRed,colorGreen,0));

                    hexagon.setStroke(Color.BLACK);

                    hexagon.setEffect(noShadow);
                    
                    Text text = new Text(borough.getX(),borough.getY(), borough.getShortName());

                    double initialScaleX = hexagon.getScaleX();
                    double initialScaleY = hexagon.getScaleY();
                    
                    hexagon.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            hexagon.setEffect(pressed);
                            hexagon.setScaleX(initialScaleX);
                            hexagon.setScaleY(initialScaleY);
                            try {
                                webView.start(webViewStage);
                                webView.showByPlace(borough.getFullName());
                            } catch (Exception e) {
                                e.printStackTrace();
                                System.out.println("ERROR !!!!");
                            }

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
                 
                    gridPane.add(hexagon,borough.getX(),borough.getY());
                    gridPane.add(text,borough.getX(),borough.getY());
                }
            }
        }

        stackpane.getChildren().addAll(gridPane);

        FlowPane flowPane = new FlowPane();
        flowPane.getChildren().addAll(stackpane,boroughHover);
        flowPane.prefWidthProperty().bind(root.widthProperty());
        flowPane.prefHeightProperty().bind(root.heightProperty());

        scrollPane = new ScrollPane();
        scrollPane.setContent(flowPane);
        
        root.getChildren().addAll(flowPane);
        root.setAlignment(Pos.CENTER);

    }
    
    private double hexPointX(double degree){
        double centerPoint = height/15;
        
        double rad = (Math.PI / 180) * degree;
        
        double X = centerPoint + height/14 * Math.cos(rad);
        
        return X;
    }
    
    private double hexPointY(double degree){
        double centerPoint = height/15;
        
        double rad = (Math.PI / 180) * degree;
        
        double X = centerPoint + height/14 * Math.sin(rad);
        
        return X;
    }
    
    private ArrayList<String> loadData(int lowerLimit, int upperLimit){
        AirbnbDataLoader loader = new AirbnbDataLoader();
        ArrayList<AirbnbListing> data = loader.load();
        ArrayList<AirbnbListing> specifiedData = new ArrayList<>();
        ArrayList<String> neighbourhoods = new ArrayList<>();
        for (AirbnbListing listing : data){
            if (listing.getPrice() <= upperLimit && listing.getPrice() >= lowerLimit){
                specifiedData.add(listing);
            }
        }

        for (AirbnbListing house : specifiedData){
            neighbourhoods.add(house.getNeighbourhood());
        }

        return neighbourhoods;
    }
    
    private HashMap countBoroughs(ArrayList<String> neighbourhoods){
        
        ArrayList<String> boroughs = new ArrayList <>();
        
        for (String borough : neighbourhoods){
            String thisBorough = borough.replaceAll("\\s+","");
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
    
    public Pane getPanel(){
        return root;
    }

    private Label LoadImage(){
        Label imageLabel = new Label();        
        String imagePath = "boroughs.png";
        Image image = new Image(imagePath);

        ImageView imageViewer = new ImageView(image);

        width =  image.getWidth();
        width = width * 0.2;
        height =  image.getHeight(); 
        height = height * 0.2;

        imageViewer.setPreserveRatio(true);
        imageViewer.setFitHeight(height);
        imageViewer.setFitWidth(width);
        imageViewer.setSmooth(true);
        imageLabel.setGraphic(imageViewer);

        return imageLabel;
    }

    public void setRange(int lowerLimit, int upperLimit) {
        houses = loadData(lowerLimit, upperLimit);
        boroughCount = countBoroughs(houses);
    }
}
