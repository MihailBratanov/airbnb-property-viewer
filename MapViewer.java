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
import java.util.ArrayList;
import java.util.*;

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
    HBox root;
    private double width;
    private double height;
    private double windowWidth;
    private double windowHeight;
    private ArrayList<AirbnbListing> houses;
    private HashMap <Button, String> boroughVariable;
    private Button enfield, barnet, haringey, waltham, harrow, brent, camden, islington, hackney, redbridge,
    havering, hillingdon, ealing, kensington, westminster, tower, newham, barking, hounslow,
    hammersmith, wandsworth, city, greenwich, bexley, richmond,merton, lambeth, southwark, 
    lewisham, kingston, sutton, croydon, bromley ;
    private ArrayList <Button> boroughs;

    public MapViewer(int lowerLimit, int upperLimit){
        houses = loadData(lowerLimit, upperLimit);
        System.out.println(houses);

        root = new HBox();

        root.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));

        StackPane stackpane = new StackPane();

        ArrayList<Button> bouroughs = new ArrayList<>();

        Label imageLabel = LoadImage();
        windowWidth = root.getMinWidth();
        windowHeight = root.getMinHeight();

        Image house = new Image("house.png");

        createButtons();

        
        GridPane gridPane = new GridPane();

        for (int i = 0; i < 15; i ++){
            RowConstraints row = new RowConstraints(height/15);
            gridPane.getRowConstraints().add(row);
        }

        for (int i = 0; i < 16; i ++){
            ColumnConstraints col = new ColumnConstraints(width/16);
            gridPane.getColumnConstraints().add(col);
        }

        gridPane.add(enfield,8,1);

        gridPane.add(barnet,5,3);

        gridPane.add(haringey,7,3);
        gridPane.add(waltham,9,3);

        gridPane.add(harrow,2,5);
        gridPane.add(brent,4,5);
        gridPane.add(camden,6,5);
        gridPane.add(islington,8,5);
        gridPane.add(hackney,10,5);
        gridPane.add(redbridge,12,5);
        gridPane.add(havering,14,5);

        gridPane.add(hillingdon,1,7);
        gridPane.add(ealing,3,7);
        gridPane.add(kensington,5,7);
        gridPane.add(westminster,7,7);
        gridPane.add(tower,9,7);
        gridPane.add(newham,11,7);
        gridPane.add(barking,13,7);

        gridPane.add(hounslow,2,9);
        gridPane.add(hammersmith,4,9);
        gridPane.add(wandsworth,6,9);
        gridPane.add(city,8,9);
        gridPane.add(greenwich,10,9);
        gridPane.add(bexley,12,9);

        gridPane.add(richmond,3,11);
        gridPane.add(merton,5,11);
        gridPane.add(lambeth,7,11);
        gridPane.add(southwark,9,11);
        gridPane.add(lewisham,11,11);

        gridPane.add(kingston,4,13);
        gridPane.add(sutton,6,13);
        gridPane.add(croydon,8,13);
        gridPane.add(bromley,10,13);

        //setHouseButton();

        stackpane.getChildren().addAll(imageLabel, gridPane);
        FlowPane flowPane = new FlowPane();
        flowPane.getChildren().addAll(stackpane);

        root.getChildren().add(flowPane);
        root.setAlignment(Pos.CENTER);
    }

    private ArrayList loadData(int lowerLimit, int upperLimit){
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

    private void createButtons(){
        enfield = new Button("Enfield");
        barnet = new Button("Barnet");
        haringey = new Button("Haringey");
        waltham = new Button("Waltham");
        harrow = new Button("H&W");
        brent = new Button("Brent Cross");
        camden = new Button("Camden");
        islington = new Button("Islington");
        hackney = new Button("Hackney");
        redbridge = new Button("Red Bridge");
        havering = new Button("Havering");

        hillingdon = new Button("Hill");
        ealing = new Button("Ealing");
        kensington = new Button("Kens");
        westminster = new Button("Westm");
        tower = new Button("Tower hill");
        newham = new Button("Newh");
        barking = new Button("Bark");

        hounslow = new Button("Hounslow");
        hammersmith = new Button("Hamm");
        wandsworth = new Button("Wand");
        city = new Button("City");
        greenwich = new Button("Gwch");
        bexley = new Button("Bexl");

        richmond = new Button("Rich");
        merton = new Button("Mert");
        lambeth = new Button("Lambeth");
        southwark = new Button("Southwark");
        lewisham = new Button("Lewisham");
        kingston = new Button("Kingston");
        sutton = new Button("Sutton");
        croydon = new Button("Croydon");
        bromley = new Button("Bromley");

        boroughs = new ArrayList<>( Arrays.asList(
                enfield, barnet, haringey, waltham, harrow, brent, camden, islington, hackney, redbridge,
                havering, hillingdon, ealing, kensington, westminster, tower, newham, barking, hounslow,
                hammersmith, wandsworth, city, greenwich, bexley, richmond, merton, lambeth, southwark, 
                lewisham, kingston, sutton, croydon, bromley ));

    }

    
    private void setHouseButton(){
        
        Image house = new Image("house.png");
        
        for (Button borough : boroughs){
            borough.setGraphic(new ImageView(house));
            
            
            /*for (String house : houses){
                if (borough.getName() == house.substring(0, house.indexOf(' '))){
                    
                }
            }*/
        }
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
        width = width * 0.3;
        height =  image.getHeight(); 
        height = height * 0.3;

        imageViewer.setPreserveRatio(true);
        imageViewer.setFitHeight(height);
        imageViewer.setFitWidth(width);
        imageViewer.setSmooth(true);
        imageLabel.setGraphic(imageViewer);

        return imageLabel;
    }

    public void setRange(int lowerLimit, int upperLimit) {
        houses = loadData(lowerLimit, upperLimit);
    }
}
