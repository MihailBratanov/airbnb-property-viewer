import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.*;
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
    private Pane panelPane;
    private Pane contentPane;
    private Pane navigationPane;
    VBox root;

    @Override
    public void start(Stage stage) throws Exception
    {
        // Create a Button or any control item
        this.stage = stage;
        // Create a new grid pane
        root = new VBox();
        makeMenuBar(root);

        Label label1 = new Label("main thing");
        Label label2 = new Label("from ~ to");
        Label label3 = new Label("panel controls");

        //Pane panelPane = new Pane();
        makePanelPane();

        // panelPane.setMinSize(300,75);  // temp. placeholder
        navigationPane = new AnchorPane();
        navigationPane.setId("navigationpane");
        makeNavigationPane(navigationPane);

        contentPane = new BorderPane(panelPane, null, null, navigationPane, null);

        root.getChildren().add(contentPane);
        //root.getChildren().add(contentPane2);
        /*pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setMinSize(300, 300);
        //pane.setVgap(10);
        //pane.setHgap(10);

        //set an action on the button using method reference
        myButton.setOnAction(this::buttonClick);

        // Add the button and label into the pane
        pane.add(myLabel, 1, 0);
        pane.add(myButton, 0, 0);*/

        // JavaFX must have a Scene (window content) inside a Stage (window)
        Scene scene = new Scene(root, root.getMinHeight(), root.getMinWidth());
        scene.getStylesheets().add("viewerstyle.css");
        stage.setTitle("Airbnb Property Viewer");
        stage.setScene(scene);

        // Show the Stage (window)
        stage.show();
    }

    private void makeMenuBar(Pane parentPane) {
        MenuBar menuBar = new MenuBar();
        parentPane.getChildren().add(menuBar);

        Menu helpMenu = new Menu("Help");
        MenuItem aboutItem = new MenuItem("About this program...");
        // aboutItem.setOnAction();
        helpMenu.getItems().addAll(aboutItem);

        menuBar.getMenus().addAll(helpMenu);
    }

    private void makeNavigationPane(Pane parentPane){
        Button previousPaneButton = new Button("< Back");
        Button nextPaneButton = new Button("Next >");
        
        nextPaneButton.setOnAction(this::nextPane);
        
        parentPane.getChildren().addAll(previousPaneButton, nextPaneButton);
        AnchorPane.setLeftAnchor(previousPaneButton, 10.0);
        AnchorPane.setRightAnchor(nextPaneButton, 10.0);
        AnchorPane.setTopAnchor(previousPaneButton, 5.0);
        AnchorPane.setTopAnchor(nextPaneButton, 5.0);
    }
    
    private void makePanelPane() {
        Pane panelPane = new Pane();
        /*MapViewer map = new MapViewer();
        map.start(stage);
        Pane panelPane = map.getMap();*/
    }
    
    private void nextPane(ActionEvent event){

    }
    
    private void updateScreen(Stage stage){
        try {
            start(stage);
        }
        catch(Exception NullPointerException){
        }
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
