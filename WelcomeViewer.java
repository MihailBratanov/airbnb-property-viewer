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
import javafx.util.Duration;

/**
 * Write a description of JavaFX class WelcomeViewer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

public class WelcomeViewer extends Panel

{
    // We keep track of the count, and label displaying the count:

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

public static Duration ZERO;

    public WelcomeViewer(){
        root = new VBox();
        
   
        root.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));

        StackPane stackpane = new StackPane();
        
        root.setMinSize(500.0, 500.0);
        windowWidth= root.getMinWidth();
        windowHeight=root.getMinHeight();
        
       
        

        
        from.getItems().addAll(
            "--Please Select--",
            "5",
            "25",
            "50",
            "100",
            "200",
            "300",
            "400",
            "500",
            "600",
            "700",
            "800",
            "900",
            "1000",
            "1500",
            "2000",
            "2500",
            "3000",
            "3500",
            "4000",
            "4500",
            "5000",
            "5500",
            "6000",
            "6500",
            "7000");
            
        from.setValue("--Please Select--");
            
        String lowerLimitString = from.getSelectionModel().getSelectedItem().toString();
        if (lowerLimitString != "--Please Select--"){
            lowerLimit = Integer.parseInt(lowerLimitString);
        }    

            


        to.getItems().addAll(
            "--Please Select--",
            "5",
            "25",
            "50",
            "100",
            "200",
            "300",
            "400",
            "500",
            "600",
            "700",
            "800",
            "900",
            "1000",
            "1500",
            "2000",
            "2500",
            "3000",
            "3500",
            "4000",
            "4500",
            "5000",
            "5500",
            "6000",
            "6500",
            "7000");

        to.setValue("--Please Select--");
        
        ProgressBar loadingBar=new ProgressBar();
        Label succesfully = new Label("Succesfully loaded!");

        


        
            
        
        
        Button myButton = new Button("Count");
        Label fromLabel= new Label("From");
        Label toLabel=new Label("To");
        
        HBox range = new HBox();
        VBox airbnb=new VBox();
        HBox loadingBox=new HBox();
        HBox succesfullyLoaded=new HBox();
        
        Label imageLabel = LoadImage();
        Label loading = new Label("loading..."); 
        
    
        
        range.getChildren().addAll(fromLabel,from,toLabel,to);
        succesfullyLoaded.getChildren().addAll(succesfully);
        airbnb.getChildren().addAll(imageLabel);
        loadingBox.getChildren().addAll(loading,loadingBar);
        
        range.setAlignment(Pos.TOP_RIGHT);
        airbnb.setAlignment(Pos.CENTER);
        loadingBox.setAlignment(Pos.CENTER);
        succesfullyLoaded.setAlignment(Pos.CENTER);
        root.getChildren().addAll(range,airbnb,loadingBox,succesfullyLoaded);
        
    }
    

     private void delay(int millisec)
    {
        try {
            Thread.sleep(millisec);
        }
        catch (InterruptedException ie) {
            // wake up
        }
    }
    
    public void setComboBoxAction(){
        from.setOnAction(e -> setLowerLimit());
        to.setOnAction(e -> setUpperLimit());
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
    
    public void setComboBox(int lowerLimit, int upperLimit) {
        String fromValue = Integer.toString(lowerLimit);
        String toValue = Integer.toString(upperLimit);
        from.setValue(fromValue);
        to.setValue(toValue);
    }
    
    private void setLowerLimit() {
        String lowerLimitString = from.getSelectionModel().getSelectedItem().toString();
        if (lowerLimitString != "--Please Select--"){
            lowerLimit = Integer.parseInt(lowerLimitString);
        }
        else {
            lowerLimit = 9999;
        }
    }
    
    private void setUpperLimit() {
        String upperLimitString = to.getSelectionModel().getSelectedItem().toString();
        if (upperLimitString != "--Please Select--"){
            upperLimit = Integer.parseInt(upperLimitString);
        }
        else {
            upperLimit = 9998;
        }
    }
    
    public boolean checkValid() {
        setLowerLimit();
        setUpperLimit();
        if (lowerLimit <= upperLimit) {
            return true;
        }
        else {
            return false;
        }
    }
 
    public Pane getPanel(){
        return root;
    }

    public int getLowerLimit()
    {
        return lowerLimit;
    }

    public int getUpperLimit()
    {
        return upperLimit;
    }
    
    public ComboBox getFromComboBox()
    {
        return from;
    }
    
    public ComboBox getToComboBox()
    {
        return to;
    }
}
