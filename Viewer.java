import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.input.*;
import javafx.stage.Stage;
import java.util.*;

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
    private BorderPane contentPane;
    private Pane panelPane;
    private Pane navigationPane;
    private BorderPane root;
    private Stack panelStack;

    private Scene scene;
    private BorderPane launchPane;

    
    @Override
    public void start(Stage stage) throws Exception
    {
        // Create a Button or any control item
        this.stage = stage;
        // Create a new grid pane

        // panelPane.setMinSize(300,75);  // temp. placeholder
        
        
       // makeNavigationPane(navigationPane);
        
        contentPane = new BorderPane(makePanelPane(), null, null, makeNavigationPane(), null);
        contentPane.setCenter(panelPane);
        contentPane.setBottom(navigationPane);

        //root.getChildren().add(contentPane);
        
        root = new BorderPane();
        root.setBottom(contentPane);
        root.setTop(makeMenuBar(root));
        
        // JavaFX must have a Scene (window content) inside a Stage (window)
        scene = new Scene(root, root.getMinHeight(), root.getMinWidth());
        scene.getStylesheets().add("viewerstyle.css");
        stage.setTitle("Airbnb Property Viewer");
        stage.setScene(scene);
        

        

        // Show the Stage (window)
        stage.show();
    }

    private MenuBar makeMenuBar(Pane parentPane) {
        MenuBar menuBar = new MenuBar();
        
        Menu propertyMenu = new Menu("Airbnb Property Viewer");
        MenuItem aboutViewerItem = new MenuItem("About Property Viewer");
        SeparatorMenuItem propertyMenuTopSeparator = new SeparatorMenuItem();
        MenuItem hideViewerItem = new MenuItem("Hide");
        hideViewerItem.setOnAction(this::hideViewer);
        hideViewerItem.setAccelerator(new KeyCodeCombination(KeyCode.H, KeyCodeCombination.SHORTCUT_DOWN));
        MenuItem showViewerItem = new MenuItem("Show");
        showViewerItem.setOnAction(this::showViewer);
        showViewerItem.setAccelerator(new KeyCodeCombination(KeyCode.G, KeyCodeCombination.SHORTCUT_DOWN));
        SeparatorMenuItem propertyMenuBottomSeparator = new SeparatorMenuItem();
        MenuItem quitViewerItem = new MenuItem("Quit Property Viewer");
        quitViewerItem.setOnAction(this::quitViewer);
        quitViewerItem.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCodeCombination.SHORTCUT_DOWN));
        propertyMenu.getItems().addAll(aboutViewerItem, propertyMenuTopSeparator, hideViewerItem, showViewerItem, propertyMenuBottomSeparator, quitViewerItem);
        
        Menu viewMenu = new Menu ("View");
        MenuItem zoomInItem = new MenuItem("Zoom In");
        MenuItem actualSizeItem = new MenuItem("Actual Size");
        MenuItem zoomOutItem = new MenuItem("Zoom Out");
        SeparatorMenuItem viewMenuSeparator = new SeparatorMenuItem();
        MenuItem fullScreenItem = new MenuItem("Enter Full Screen");
        viewMenu.getItems().addAll(zoomInItem, actualSizeItem, zoomOutItem, viewMenuSeparator, fullScreenItem);
        
        Menu helpMenu = new Menu("Help");
        MenuItem instructionItem = new MenuItem("Instructions");
        helpMenu.getItems().addAll(instructionItem);
        
        Menu aboutMenu = new Menu("About");
        MenuItem aboutItem = new MenuItem("About this program...");
        // aboutItem.setOnAction();
        aboutMenu.getItems().addAll(aboutItem);

        menuBar.getMenus().addAll(propertyMenu, viewMenu, helpMenu, aboutMenu);
        return menuBar;
    }

    private Pane makeNavigationPane(){
        navigationPane = new AnchorPane();
        navigationPane.setId("navigationpane");
        Button previousPaneButton = new Button("< Back");
        Button nextPaneButton = new Button("Next >");
        previousPaneButton.setDisable(false);
        nextPaneButton.setDisable(false);
        previousPaneButton.setOnAction(this::previousPane);
        nextPaneButton.setOnAction(this::nextPane);
        
        navigationPane.getChildren().addAll(previousPaneButton, nextPaneButton);
        AnchorPane.setLeftAnchor(previousPaneButton, 10.0);
        AnchorPane.setRightAnchor(nextPaneButton, 10.0);
        AnchorPane.setTopAnchor(previousPaneButton, 5.0);
        AnchorPane.setTopAnchor(nextPaneButton, 5.0);
        AnchorPane.setBottomAnchor(previousPaneButton, 5.0);
        AnchorPane.setBottomAnchor(nextPaneButton, 5.0);
        
        return navigationPane;
    }
    
    private Pane makePanelPane() {
        Pane panelPane = new Pane();
        /*MapViewer map = new MapViewer();
        map.start(stage);
        Pane panelPane = map.getMap();*/
        return panelPane;
    }
    
    private void nextPane(ActionEvent event){

        MapViewer newPanel = new MapViewer(0,0);
        Pane mapPane = newPanel.getPanel();
        root.setCenter(mapPane);
        stage.setWidth(root.getMaxWidth());
        stage.setHeight(root.getMaxHeight());
        stage.show();
    }
    
    private void previousPane(ActionEvent event) {
        WelcomeViewer newPanel = new WelcomeViewer();
        Pane welcomePane = newPanel.getPanel();
        root.setCenter(welcomePane);
        stage.setWidth(root.getMaxWidth());
        stage.setHeight(root.getMaxHeight());
        stage.show();
    }   
    
    private void hideViewer(ActionEvent event) {
        stage.hide();
    }
    
    private void showViewer(ActionEvent event) {
        stage.show();
    }
    
    private void quitViewer(ActionEvent event) {
        System.exit(0);

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
