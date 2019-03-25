import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.*;
import javafx.geometry.Pos;
import java.util.*;
import javafx.scene.image.*;
import javafx.scene.image.*;
import javafx.animation.*;

/**
 * Write a description of class StartingView here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class StartingViewer extends Panel
{
    // instance variables - replace the example below with your own
    
    private Label myLabel = new Label("0");
    private Stage stage;
    VBox root;
    private double width;
    private double height;
    private double windowWidth;
    private double windowHeight;
    private int lowerLimit;
    private int upperLimit;
    final ComboBox from = new ComboBox();
    final ComboBox to = new ComboBox();
    
    private AirbnbDataLoader loader=new AirbnbDataLoader();
    public ArrayList<AirbnbListing>data=loader.load();
    private HashMap <Integer,String> userName=new HashMap<>();
    private HashMap <Integer,String> password=new HashMap<>();
    

    /**
     * Constructor for objects of class StartingView
     */
    public StartingViewer()
    {
        // initialise instance variables
        root = new VBox();
        
   
        root.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));

        StackPane stackpane = new StackPane();
        
        root.setMinSize(500.0, 500.0);
        windowWidth= root.getMinWidth();
        windowHeight=root.getMinHeight();
        

        ProgressBar loadingBar=new ProgressBar();
        Label succesfully = new Label("Succesfully loaded!");
        
        VBox airbnb=new VBox();
        HBox loadingBox=new HBox();
        HBox succesfullyLoaded=new HBox();
        
       
        
        Label imageLabel = LoadImage();
        Label loading = new Label("loading..."); 
        
    
        
        succesfullyLoaded.getChildren().addAll(succesfully);
        airbnb.getChildren().addAll(imageLabel);
        loadingBox.getChildren().addAll(loading,loadingBar);
        

        airbnb.setAlignment(Pos.CENTER);
        loadingBox.setAlignment(Pos.CENTER);
        succesfullyLoaded.setAlignment(Pos.CENTER);
        root.getChildren().addAll(airbnb,loadingBox,succesfullyLoaded);
        
    }
    
     private Label LoadImage(){
        Label imageLabel = new Label();        
        String imagePath = "airbnb.png";
        Image image = new Image(imagePath);

        ImageView imageViewer = new ImageView(image);

        width =  image.getWidth();
        width = width/2;
        height =  image.getHeight(); 
        height = height/2;

        imageViewer.setPreserveRatio(true);
        imageViewer.setFitHeight(height);
        imageViewer.setFitWidth(width);
        imageViewer.setSmooth(true);
        imageLabel.setGraphic(imageViewer);
       
        return imageLabel;
    }
    
    public Pane getPanel(){
        return root;
    }

    }

