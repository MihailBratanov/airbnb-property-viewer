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
 * Write a description of JavaFX class WelcomeLauncher here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class StartingViewer extends Panel
{
    // We keep track of the count, and label displaying the count:
   
    private Label myLabel = new Label("0");
    private Stage stage;
    VBox root;
    private double width;
    private double height;
    private double windowWidth;
    private double windowHeight;
    

    
    public StartingViewer()
    {
        root = new VBox();
        
   
        root.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));

        StackPane stackpane = new StackPane();
        
        root.setMinSize(700.0, 700.0);
        windowWidth= root.getMinWidth();
        windowHeight=root.getMinHeight();
        
    }
    public Pane getPanel(){
        return root;
    }

}
