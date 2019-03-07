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

/**
 * Write a description of JavaFX class WelcomeViewer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class WelcomeViewer extends Application
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
    
    @Override
    public void start(Stage stage) throws Exception
    {
        // Create a Button or any control item

        // Create a Button or any control item
        this.stage = stage;

        // Create a new grid pane
        root = new VBox();

        root.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));

        StackPane stackpane = new StackPane();

        windowWidth = root.getMinWidth();
        windowHeight = root.getMinHeight();

        //Label imageLabel = LoadImage();

        final ComboBox from = new ComboBox();
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
            

        final ComboBox to =new ComboBox();
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
        
        String upperLimitString = from.getSelectionModel().getSelectedItem().toString();
        if (lowerLimitString != "--Please Select--"){
            upperLimit = Integer.parseInt(upperLimitString);
        }
            
        Button myButton = new Button("Count");
        Label fromLabel= new Label("From");
        Label toLabel=new Label("To");

        HBox range = new HBox();
        range.getChildren().addAll(fromLabel,from,toLabel,to);
        range.setAlignment(Pos.TOP_RIGHT);
        root.getChildren().addAll(range);

        //set an action on the button using method reference
        //myButton.setOnAction(this::buttonClick);

        // Add the button and label into the pane


        // JavaFX must have a Scene (window content) inside a Stage (window)
        Scene scene = new Scene(root, root.getMinHeight(), root.getMinWidth());
        stage.setTitle("Welcome");
        stage.setScene(scene);

        // Show the Stage (window)
        stage.show();
    }

    public Integer getLowerLimit()
    {
        return lowerLimit;
    }

    public Integer getUpperLimit()
    {
        return upperLimit;
    }

    public Pane getPanel(){
        return root;
    }
}
