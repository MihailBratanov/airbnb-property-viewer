import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.input.*;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
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
    private Stage stage;
    private BorderPane contentPane;
    private Pane panelPane;
    private int panelNumber;
    private WelcomeViewer welcomeViewer;
    private MapViewer mapViewer;
    private StatViewer statViewer;
    private Pane currentPane;
    
    private Pane navigationPane;
    private BorderPane root;
    private Stack panelStack;

    private Scene scene;
    private Button previousPaneButton;
    private Button nextPaneButton;
    private boolean isPriceRangeValid = false;
    private int lowerLimit;
    private int upperLimit;

    private static final String VERSION = "Version 0.0.1";

    @Override
    public void start(Stage stage) throws Exception
    {
        this.stage = stage;
        // Create a new grid pane
        welcomeViewer = new WelcomeViewer();
        welcomeViewer.setComboBoxAction();
        welcomeViewer.getFromComboBox().setOnAction(e -> checkRangeValidity());
        welcomeViewer.getToComboBox().setOnAction(e -> checkRangeValidity());
        panelNumber = 1;
        panelPane = welcomeViewer.getPanel();
        contentPane = new BorderPane();
        contentPane.setCenter(panelPane);
        contentPane.setBottom(makeNavigationPane());

        root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        root.setCenter(contentPane);
        root.setTop(makeMenuBar(root));
        root.setMaxSize(500, 500);

        // JavaFX must have a Scene (window content) inside a Stage (window)
        scene = new Scene(root, root.getMaxHeight(), root.getMaxWidth());
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
        aboutViewerItem.setOnAction(this::aboutProgram);
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
        zoomInItem.setOnAction(this::zoomIn);
        zoomInItem.setAccelerator(new KeyCodeCombination(KeyCode.PLUS, KeyCodeCombination.SHORTCUT_DOWN));
        MenuItem actualSizeItem = new MenuItem("Actual Size");
        MenuItem zoomOutItem = new MenuItem("Zoom Out");
        zoomOutItem.setOnAction(this::zoomOut);
        zoomOutItem.setAccelerator(new KeyCodeCombination(KeyCode.MINUS, KeyCodeCombination.SHORTCUT_DOWN));
        SeparatorMenuItem viewMenuSeparator = new SeparatorMenuItem();
        MenuItem fullScreenItem = new MenuItem("Enter Full Screen");
        fullScreenItem.setOnAction(this::fullScreen);
        viewMenu.getItems().addAll(zoomInItem, actualSizeItem, zoomOutItem, viewMenuSeparator, fullScreenItem);

        Menu helpMenu = new Menu("Help");
        MenuItem instructionItem = new MenuItem("Instructions");
        instructionItem.setOnAction(this::instruction);
        helpMenu.getItems().addAll(instructionItem);

        menuBar.getMenus().addAll(propertyMenu, viewMenu, helpMenu);
        return menuBar;
    }

    private Pane makeNavigationPane(){
        navigationPane = new AnchorPane();
        navigationPane.setId("navigationpane");
        previousPaneButton = new Button("< Back");
        nextPaneButton = new Button("Next >");
        checkRangeValidity();
        previousPaneButton.setOnAction(this::previousPane);
        nextPaneButton.setOnAction(e -> nextPane());

        navigationPane.getChildren().addAll(previousPaneButton, nextPaneButton);
        AnchorPane.setLeftAnchor(previousPaneButton, 5.0);
        AnchorPane.setRightAnchor(nextPaneButton, 5.0);
        AnchorPane.setTopAnchor(previousPaneButton, 5.0);
        AnchorPane.setTopAnchor(nextPaneButton, 5.0);
        AnchorPane.setBottomAnchor(previousPaneButton, 5.0);
        AnchorPane.setBottomAnchor(nextPaneButton, 5.0);
        
        return navigationPane;
    }

    private void checkRangeValidity() {
        isPriceRangeValid = welcomeViewer.checkValid();
        if (isPriceRangeValid) {
            previousPaneButton.setDisable(false);
            nextPaneButton.setDisable(false);
        }
        else {
            previousPaneButton.setDisable(true);
            nextPaneButton.setDisable(true);
        }
        lowerLimit = welcomeViewer.getLowerLimit();
        upperLimit = welcomeViewer.getUpperLimit();
    }

    private void nextPane(){
        panelNumber++;
        switchPanel(lowerLimit, upperLimit);
        
        contentPane.setCenter(currentPane);
        stage.show();
    }

    private void previousPane(ActionEvent event) {
        panelNumber--;
        switchPanel(lowerLimit, upperLimit);

        contentPane.setCenter(currentPane);
        stage.show();
    }

    private void switchPanel(int lowerLimit, int upperLimit) {
        switch(panelNumber) {
            case 1:
                makeWelcomePanel();
                break;
            case 2:
                makeMapPanel();
                break;
        }
    }
    
    private void makeWelcomePanel() {
        welcomeViewer = new WelcomeViewer();
        welcomeViewer.setComboBoxAction();
        welcomeViewer.setComboBox(lowerLimit, upperLimit);
        welcomeViewer.getFromComboBox().setOnAction(e -> checkRangeValidity());
        welcomeViewer.getToComboBox().setOnAction(e -> checkRangeValidity());

        panelNumber = 1;
        currentPane = welcomeViewer.getPanel();
    }
    
    private void makeMapPanel() {
        mapViewer = new MapViewer(lowerLimit, upperLimit);
        currentPane = mapViewer.getPanel();
    }
    
    private void makeStatPanel() {
        // statViewer = new StatViewer(lowerLimit, upperLimit);
        currentPane = mapViewer.getPanel();
    }
    
    // Menubar Buttons
    private void aboutProgram(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("About Property Viewer");
        alert.setHeaderText(null);  // Alerts have an optionl header. We don't want one.
        alert.setContentText("Airbnb Property Viewer\n\n" + VERSION);
        alert.showAndWait();
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

    private void zoomIn(ActionEvent event) {

    }

    private void zoomOut(ActionEvent event) {

    }

    private void fullScreen(ActionEvent event) {
        stage.setFullScreen(true);
    }

    private void instruction(ActionEvent event) {

    }
}
