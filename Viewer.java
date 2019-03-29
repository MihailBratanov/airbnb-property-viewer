import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.*;
import javafx.util.*;

import java.util.ArrayList;

/**
 * A system that illustrates Airbnb properties in London.
 *
 * Class Viewer - the main control unit of the system.
 *
 * @author Haiyun Zou, Ka Wang Sin, Mihail Bratanov and Terry Phung
 * @version 2019.03.29
 *
 * 18-19 4CCS1PPA Programming Practice and Applications
 * Term 2 Coursework 4 - London Property Marketplace
 * Created by Haiyun Zou, Ka Wang Sin, Mihail Bratanov and Terry Phung
 * Student ID:
 * k-number:
 */

public class Viewer extends Application
{

    private Stage stage;
    private BorderPane contentPane;
    private int panelNumber;
    private WelcomeViewer welcomeViewer;
    private MapViewer mapViewer;
    private StatisticsPanel statViewer;
    private Pane currentPane;
    VBox centerPane;
    private BorderPane navigationPane;

    private HBox dots;
    private Circle dotWelcomePanel;
    private Circle dotMapPanel;
    private Circle dotStatPanel;

    private Button previousPaneButton;
    private Button nextPaneButton;
    private boolean isPriceRangeValid = false;
    private int lowerLimit;
    private int upperLimit;

    private static final String VERSION = "Version 1.0";
    private static final int MAX_PANEL_NUMBER = 3;

    private AirbnbDataLoader loader;
    private ArrayList<AirbnbListing> data;
    private String username;

    private BorderPane root;
    private Scene scene;

    public Viewer(String username){
        this.username = username;
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        this.stage = stage;
        // Create a new grid pane
        loader = new AirbnbDataLoader();
        data = loader.load();
        centerPane = new VBox();
        welcomeViewer = new WelcomeViewer();
        welcomeViewer.setComboBoxAction();
        welcomeViewer.getFromComboBox().setOnAction(e -> checkRangeValidity());
        welcomeViewer.getToComboBox().setOnAction(e -> checkRangeValidity());
        currentPane = welcomeViewer.getPanel();
        panelNumber = 1;
        centerPane.getChildren().addAll(makeScrollPane(currentPane, centerPane));

        contentPane = new BorderPane();
        contentPane.setCenter(centerPane);
        contentPane.setBottom(makeNavigationPane());

        root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        root.setCenter(contentPane);
        root.setTop(makeMenuBar());


        // JavaFX must have a Scene (window content) inside a Stage (window)
        scene = new Scene(root);

        stage.setTitle("Airbnb Property Viewer");
        stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth() * 7 / 8);
        stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight() * 7 / 8);
        stage.setMinWidth(Screen.getPrimary().getVisualBounds().getWidth() * 3 / 5);
        stage.setMinHeight(Screen.getPrimary().getVisualBounds().getHeight() * 3 / 10);
        stage.setScene(scene);

        // Show the Stage (window)
        stage.show();
    }

    /**
     * Makes a menubar that provides different functions for the window.
     * @return A HBox including all the menus.
     */
    private HBox makeMenuBar() {
        MenuBar leftMenuBar = new MenuBar();
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
        actualSizeItem.setOnAction(this::actualSize);
        MenuItem zoomOutItem = new MenuItem("Zoom Out");
        zoomOutItem.setOnAction(this::zoomOut);
        zoomOutItem.setAccelerator(new KeyCodeCombination(KeyCode.MINUS, KeyCodeCombination.SHORTCUT_DOWN));

        SeparatorMenuItem viewMenuSeparator = new SeparatorMenuItem();
        MenuItem fullScreenItem = new MenuItem("Enter Full Screen");
        fullScreenItem.setOnAction(this::fullScreen);
        viewMenu.getItems().addAll(zoomInItem, actualSizeItem, zoomOutItem, viewMenuSeparator, fullScreenItem);

        Menu helpMenu = new Menu("Help");
        MenuItem instructionItem = new MenuItem("Viewer Instructions");
        instructionItem.setOnAction(this::instruction);
        helpMenu.getItems().addAll(instructionItem);

        leftMenuBar.getMenus().addAll(propertyMenu, viewMenu, helpMenu);

        Region space = new Region();
        space.setStyle("-fx-background-color: linear-gradient(#fdfdfd, #e7e7e7); -fx-border-color: #b5b5b5;  -fx-border-width: 0px 0px 1px 0px;");
        HBox.setHgrow(space, Priority.SOMETIMES);

        MenuBar rightMenuBar = new MenuBar();
        Menu userMenu = new Menu("User: " + username);
        ImageView userLogo = new ImageView();
        userLogo.setPreserveRatio(true);
        userMenu.setGraphic(new ImageView("userlogo.png"));
        MenuItem profileItem = new MenuItem("Profile");
        MenuItem logoutItem = new MenuItem("Log Out " + username);
        logoutItem.setOnAction(this:: logout);
        profileItem.setOnAction(event -> {
            Stage profileStage = new Stage();
            ProfileDisplay profileDisplay = new ProfileDisplay(username);
            profileDisplay.start(profileStage);

        });
        userMenu.getItems().addAll(profileItem, logoutItem);

        rightMenuBar.getMenus().addAll(userMenu);

        HBox menuBar = new HBox();
        menuBar.getChildren().addAll(leftMenuBar, space, rightMenuBar);

        return menuBar;
    }

    /**
     * Makes a navigation pane that allows the user to navigate between panels.
     * The pane also tells the user which panel is he/she currently on.
     * @return A Pane that include forward & backward buttons and navigating dots.
     */
    private Pane makeNavigationPane(){
        navigationPane = new BorderPane();
        navigationPane.setStyle("-fx-background-color: linear-gradient(#fdfdfd, #e1e1e1); -fx-border-color: #b5b5b5;  -fx-border-width: 2px 0px 0px 0px;");
        previousPaneButton = new Button("< Back");
        nextPaneButton = new Button("Next >");
        previousPaneButton.setDisable(true);
        nextPaneButton.setDisable(true);
        previousPaneButton.setOnAction(this::previousPane);
        nextPaneButton.setOnAction(this::nextPane);

        dots = new HBox(8);

        dotWelcomePanel = new Circle();
        dotWelcomePanel.setRadius(3);
        dotWelcomePanel.setStroke(Color.BLACK);

        dotMapPanel = new Circle();
        dotMapPanel.setRadius(3);
        dotMapPanel.setStroke(Color.BLACK);

        dotStatPanel = new Circle();
        dotStatPanel.setRadius(3);
        dotStatPanel.setStroke(Color.BLACK);

        dots.getChildren().addAll(dotWelcomePanel, dotMapPanel, dotStatPanel);
        switchDots(panelNumber);
        dots.setAlignment(Pos.CENTER);

        navigationPane.setCenter(dots);
        navigationPane.setLeft(previousPaneButton);
        navigationPane.setRight(nextPaneButton);
        navigationPane.setPadding(new Insets(5, 5, 5, 5));

        return navigationPane;
    }

    /**
     * Checks if the price range selected is valid.
     * If it is valid, the navigating buttons will be enabled.
     */
    private void checkRangeValidity() {
        isPriceRangeValid = welcomeViewer.checkValid();
        if(welcomeViewer.checkToBoxSelected()) {
            if (isPriceRangeValid) {
                previousPaneButton.setDisable(false);
                nextPaneButton.setDisable(false);
            } else {
                previousPaneButton.setDisable(true);
                nextPaneButton.setDisable(true);
                Alert wrongInput = new Alert(AlertType.ERROR);
                wrongInput.setTitle("Error");
                wrongInput.setHeaderText("Invalid Range.");
                wrongInput.setContentText("The upper limit is smaller than the lower limit.\nPlease try again.");
                wrongInput.showAndWait();
                welcomeViewer.setComoboBoxDefault();
            }
        }
        else {
            previousPaneButton.setDisable(true);
            nextPaneButton.setDisable(true);
        }
        lowerLimit = welcomeViewer.getLowerLimit();
        upperLimit = welcomeViewer.getUpperLimit();
    }

    /**
     * Navigates the user to the next pane.
     * The system will automatically return to the first panel
     * when the current panel reaches its maximum.
     * @param event When the button is clicked.
     */
    private void nextPane(ActionEvent event){

        panelNumber++;
        if (panelNumber > MAX_PANEL_NUMBER) {
            panelNumber = 1;
        }
        switchPanel(panelNumber);

        paneMoveLeft(currentPane, centerPane);

        centerPane.getChildren().addAll(makeScrollPane(currentPane, centerPane));
        contentPane.setCenter(centerPane);
        stage.show();
    }

    /**
     * Navigates the user to the previous pane.
     * The system will automatically return to the last panel
     * when the current panel reaches its minimum.
     * @param event When the button is clicked.
     */
    private void previousPane(ActionEvent event) {
        panelNumber--;
        if (panelNumber < 1) {
            panelNumber = MAX_PANEL_NUMBER;
        }
        switchPanel(panelNumber);

        paneMoveRight(currentPane, centerPane);

        centerPane.getChildren().addAll(makeScrollPane(currentPane, centerPane));
        contentPane.setCenter(centerPane);
        stage.show();
    }

    /**
     * Decides which panel to be used based on the panel number input.
     * Once the new panel is made, the navigating dots will be switched
     * @param panelNumber the panel number that needs to be switched to.
     */
    private void switchPanel(int panelNumber) {
        switch(panelNumber) {
            case 1:
                makeWelcomePanel();
                switchDots(panelNumber);
                break;
            case 2:
                makeMapPanel();
                switchDots(panelNumber);
                break;
            case 3:
                makeStatPanel();
                switchDots(panelNumber);
                break;
        }
    }

    /**
     * Decides which dots to be coloured in white based on the panel number input.
     * @param panelNumber the panel number that needs to be switched to.
     */
    private void switchDots(int panelNumber) {
        switch(panelNumber) {
            case 1:
                dotWelcomePanel.setFill(javafx.scene.paint.Color.WHITE);
                dotMapPanel.setFill(javafx.scene.paint.Color.GRAY);
                dotStatPanel.setFill(javafx.scene.paint.Color.GRAY);
                break;
            case 2:
                dotWelcomePanel.setFill(javafx.scene.paint.Color.GRAY);
                dotMapPanel.setFill(javafx.scene.paint.Color.WHITE);
                dotStatPanel.setFill(javafx.scene.paint.Color.GRAY);
                break;
            case 3:
                dotWelcomePanel.setFill(javafx.scene.paint.Color.GRAY);
                dotMapPanel.setFill(javafx.scene.paint.Color.GRAY);
                dotStatPanel.setFill(javafx.scene.paint.Color.WHITE);
                break;
        }
    }

    /**
     * Creates a welcome panel object, and assign it to the currentPane.
     */
    private void makeWelcomePanel() {
        welcomeViewer = new WelcomeViewer();

        welcomeViewer.setComboBoxAction();
        welcomeViewer.setComboBox(lowerLimit, upperLimit);
        welcomeViewer.getFromComboBox().setOnAction(e -> checkRangeValidity());
        welcomeViewer.getToComboBox().setOnAction(e -> checkRangeValidity());

        panelNumber = 1;
        currentPane = welcomeViewer.getPanel();
    }

    /**
     * Creates a map panel object, and assign it to the currentPane.
     */
    private void makeMapPanel() {
        mapViewer = new MapViewer(lowerLimit, upperLimit, data, username);
        currentPane = mapViewer.getPanel();
    }

    /**
     * Creates a statistics panel object, and assign it to the currentPane.
     */
    private void makeStatPanel() {
        statViewer = new StatisticsPanel(stage, lowerLimit, upperLimit);
        currentPane = statViewer.getPanel();
    }

    /**
     * Put the panels made into a scroll pane that allows the user to pan around the panel
     * when the window is not large enough.
     * @param child The panel that is created.
     * @param parent The panel that this scroll pane will be put into.
     * @return The scroll pane that have just been created.
     */
    private ScrollPane makeScrollPane(Pane child, Pane parent) {
        ScrollPane sp = new ScrollPane();
        sp.setContent(child);
        sp.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        sp.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
        child.prefWidthProperty().bind(sp.widthProperty().subtract(2)); // so that the ScrollBar would not appear at all times.
        child.prefHeightProperty().bind(sp.heightProperty().subtract(2));
        sp.prefWidthProperty().bind(parent.widthProperty());
        sp.prefHeightProperty().bind(parent.heightProperty());
        sp.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        return sp;
    }

    /**
     * This is an animation method that allows the panels to move in from the right.
     * @param nextPane The panel that is going to move in.
     * @param parentPane The current panel that needs to be cleared before putting the new panel in.
     */
    private void paneMoveLeft(Pane nextPane, Pane parentPane) {
        parentPane.getChildren().clear();
        nextPane.translateXProperty().set(scene.getWidth());
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(nextPane.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.millis(300), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    /**
     * This is an animation method that allows the panels to move in from the left.
     * @param nextPane The panel that is going to move in.
     * @param parentPane The current panel that needs to be cleared before putting the new panel in.
     */
    private void paneMoveRight(Pane nextPane, Pane parentPane) {
        parentPane.getChildren().clear();
        nextPane.translateXProperty().set(scene.getWidth() * -1);
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(nextPane.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.millis(300), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    // Menubar Buttons

    /**
     * Gives information to the user about this program.
     * @param event When the menu button is clicked.
     */
    private void aboutProgram(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("About Property Viewer");
        alert.setHeaderText(null);
        alert.setContentText("Airbnb Property Viewer\n\n" + VERSION);
        alert.showAndWait();
    }

    /**
     * Hides the window of the program.
     * @param event When the menu button is clicked.
     */
    private void hideViewer(ActionEvent event) {
        stage.hide();
    }

    /**
     * Shows the window of the program.
     * @param event When the menu button is clicked.
     */
    private void showViewer(ActionEvent event) {
        stage.show();
    }

    /**
     * Terminate the program.
     * @param event When the menu button is clicked.
     */
    private void quitViewer(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Zooms in the current panel.
     * @param event When the menu button is clicked.
     */
    private void zoomIn(ActionEvent event) {
        currentPane.setScaleX(currentPane.getScaleX() * 1.1);
        currentPane.setScaleY(currentPane.getScaleY() * 1.1);
        System.out.println(currentPane.getHeight());
        System.out.println(currentPane.getWidth());
    }

    /**
     * View the current panel in it's original size - which is in full.
     * @param event When the menu button is clicked.
     */
    private void actualSize(ActionEvent event) {
        currentPane.setScaleX(1);
        currentPane.setScaleY(1);
    }

    /**
     * Zooms out the current panel.
     * @param event When the menu button is clicked.
     */
    private void zoomOut(ActionEvent event) {
        currentPane.setScaleX(currentPane.getScaleX() / 1.1);
        currentPane.setScaleY(currentPane.getScaleY() / 1.1);
    }

    /**
     * Enters full screen.
     * @param event When the menu button is clicked.
     */
    private void fullScreen(ActionEvent event) {
        stage.setFullScreen(true);
    }

    /**
     * Gives the user the instruction of the program.
     * @param event When the menu button is clicked.
     */
    private void instruction(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Viewer Instructions");
        alert.setHeaderText("Instructions to this program:");  // Alerts have an optionl header. We don't want one.
        alert.setContentText("Ting tang wala wala bing bang\nTing tang wala wala bing bang\nTing tang wala wala bing bang\n");
        alert.showAndWait();
    }

    /**
     * Logout the current user, the login window will appear.
     * @param event When the menu button is clicked.
     */
    private void logout(ActionEvent event) {
       Stage login = new Stage();
       Starting loginScreen = new Starting();

       try{
           loginScreen.start(login);
       }
       catch (Exception e) {
           e.printStackTrace();
       }

       stage.close();
    }
}