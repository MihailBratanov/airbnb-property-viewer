

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Write a description of JavaFX class Viewer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

public class Viewer extends Application
{
    // We keep track of the count, and label displaying the count:
    private int count = 0;
    private Label myLabel = new Label("0");
    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception
    {
        // Create a Button or any control item
        this.stage = stage;

        // Create a new grid pane
        VBox root = new VBox();
        //makeMenuBar(root);
        
        Label label1 = new Label();
        Label label2 = new Label();
        Label label3 = new Label();
        
        /*pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setMinSize(300, 300);
        //pane.setVgap(10);
        //pane.setHgap(10);

        //set an action on the button using method reference
        myButton.setOnAction(this::buttonClick);

        // Add the button and label into the pane
        pane.add(myLabel, 1, 0);
        pane.add(myButton, 0, 0);

        // JavaFX must have a Scene (window content) inside a Stage (window)
        Scene scene = new Scene(pane, 300,100);
        stage.setTitle("JavaFX Example");
        stage.setScene(scene);

        // Show the Stage (window)
        stage.show();*/
    }

    /**
     * This will be executed when the button is clicked
     * It increments the count by 1
     */
    private void buttonClick(ActionEvent event)
    {
        // Counts number of button clicks and shows the result on a label
        count = count + 1;
        myLabel.setText(Integer.toString(count));
    }
}
