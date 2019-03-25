import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.*;
import javafx.util.*;

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
    private int panelNumber;
    private WelcomeViewer welcomeViewer;
    private MapViewer mapViewer;
    private StatViewer statViewer;
    private Pane currentPane;
    VBox centerPane;
    

    private BorderPane navigationPane;
    private HBox dots;
    private Circle dotWelcomePanel;
    private Circle dotMapPanel;
    
    private BorderPane root;

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
        centerPane = new VBox();
        welcomeViewer = new WelcomeViewer();
        welcomeViewer.setComboBoxAction();
        welcomeViewer.getFromComboBox().setOnAction(e -> checkRangeValidity());
        welcomeViewer.getToComboBox().setOnAction(e -> checkRangeValidity());
        currentPane = welcomeViewer.getPanel();
        panelNumber = 1;
        centerPane.getChildren().addAll(makeScrollPane(currentPane, centerPane));

        // centerPane.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        
        contentPane = new BorderPane();
        contentPane.setCenter(centerPane);
        contentPane.setBottom(makeNavigationPane());

        root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        root.setCenter(contentPane);
        root.setTop(makeMenuBar(root));
        root.setMaxSize(800, 800);

        // JavaFX must have a Scene (window content) inside a Stage (window)
        scene = new Scene(root);
        
        /*scene.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Image image = new Image("cursor_clicked.png");
                scene.setCursor(new ImageCursor(image));
            }
        });
        
        scene.addEventFilter(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Image image = new Image("cursor.png");
                scene.setCursor(new ImageCursor(image));
            }
        });*/
        
        stage.setTitle("Airbnb Property Viewer");
        stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth() * 3 / 4);
        stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight() * 3 / 4);
        stage.setMinWidth(400);
        stage.setMinHeight(200);
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

        menuBar.getMenus().addAll(propertyMenu, viewMenu, helpMenu);
        return menuBar;
    }

    private Pane makeNavigationPane(){
        navigationPane = new BorderPane();
        navigationPane.setStyle("-fx-background-color: linear-gradient(#fdfdfd, #e1e1e1); -fx-border-color: #b5b5b5;  -fx-border-width: 2px 0px 0px 0px;");                
        previousPaneButton = new Button("< Back");
        nextPaneButton = new Button("Next >");
        checkRangeValidity();
        previousPaneButton.setOnAction(this::previousPane);
        nextPaneButton.setOnAction(this::nextPane);
        
        dots = new HBox(8);
        
        dotWelcomePanel = new Circle();
        dotWelcomePanel.setRadius(3);
        dotWelcomePanel.setStroke(Color.BLACK);
        
        dotMapPanel = new Circle();
        dotMapPanel.setRadius(3);
        dotMapPanel.setStroke(Color.BLACK);
        
        dots.getChildren().addAll(dotWelcomePanel, dotMapPanel);
        switchDots();
        dots.setAlignment(Pos.CENTER);

        navigationPane.setCenter(dots);
        navigationPane.setLeft(previousPaneButton);
        navigationPane.setRight(nextPaneButton);
        navigationPane.setPadding(new Insets(5, 5, 5, 5));

        return navigationPane;
    }

    private void checkRangeValidity() {
        isPriceRangeValid = welcomeViewer.checkValid();
        if (isPriceRangeValid) {
            nextPaneButton.setDisable(false);
        }
        else {
            previousPaneButton.setDisable(true);
            nextPaneButton.setDisable(true);
        }
        lowerLimit = welcomeViewer.getLowerLimit();
        upperLimit = welcomeViewer.getUpperLimit();
    }

    private void nextPane(ActionEvent event){
        panelNumber++;
        //paneMoveLeft();
        centerPane.getChildren().clear();
        switchPanel(lowerLimit, upperLimit);
        //paneMoveLeft();
        
        centerPane.getChildren().addAll(makeScrollPane(currentPane, centerPane));
        contentPane.setCenter(centerPane);
        //contentPane.setBottom(navigationPane);
        stage.show();
    }

    private void previousPane(ActionEvent event) {
        panelNumber--;
        //paneMoveRight();
        centerPane.getChildren().clear();
        switchPanel(lowerLimit, upperLimit);
        //paneMoveRight();
        checkRangeValidity();
        
        centerPane.getChildren().addAll(makeScrollPane(currentPane, contentPane));
        contentPane.setCenter(centerPane);
        stage.show();
    }

    private void switchPanel(int lowerLimit, int upperLimit) {
        switch(panelNumber) {
            case 1:
                makeWelcomePanel();
                previousPaneButton.setDisable(true);
                switchDots();
                break;
            case 2:
                makeMapPanel();
                previousPaneButton.setDisable(false);
                nextPaneButton.setDisable(true);
                switchDots();
                break;
        }
    }
    
    private void switchDots() {
        switch(panelNumber) {
            case 1:
                dotWelcomePanel.setFill(javafx.scene.paint.Color.WHITE);
                dotMapPanel.setFill(javafx.scene.paint.Color.GRAY);
                break;
            case 2:
                dotWelcomePanel.setFill(javafx.scene.paint.Color.GRAY);
                dotMapPanel.setFill(javafx.scene.paint.Color.WHITE);
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
        //centerPane.setContent(mapViewer.getPanel());
    }
    
    private void makeStatPanel() {
        // statViewer = new StatViewer(lowerLimit, upperLimit);
        currentPane = mapViewer.getPanel();
    }
    
    private ScrollPane makeScrollPane(Pane child, Pane parent) {
        ScrollPane sp = new ScrollPane();
        sp.setContent(child);
        sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        sp.setHbarPolicy(ScrollBarPolicy.ALWAYS);
        child.prefWidthProperty().bind(sp.widthProperty());
        child.prefHeightProperty().bind(sp.heightProperty());
        sp.prefWidthProperty().bind(parent.widthProperty());
        sp.prefHeightProperty().bind(parent.heightProperty());
        sp.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        return sp;
    }
    
    private void paneMoveLeft() {
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000), currentPane);
        double distance = stage.getWidth() * -1;
        tt.setByX(distance);
        tt.setAutoReverse(true);
        tt.play();
    }
    
    private void paneMoveRight() {
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000), currentPane);
        double distance = stage.getWidth();
        tt.setByX(distance);
        tt.setAutoReverse(true);
        tt.play();
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
        currentPane.setScaleX(currentPane.getScaleX() * 1.1);
        currentPane.setScaleY(currentPane.getScaleY() * 1.1);
        System.out.println(currentPane.getHeight());
        System.out.println(currentPane.getWidth());
    }
    
    private void actualSize(ActionEvent event) {
        currentPane.setScaleX(1);
        currentPane.setScaleY(1);
    }

    private void zoomOut(ActionEvent event) {
        currentPane.setScaleX(currentPane.getScaleX() / 1.1);
        currentPane.setScaleY(currentPane.getScaleY() / 1.1);
    }

    private void fullScreen(ActionEvent event) {
        stage.setFullScreen(true);
    }

    private void instruction(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Viewer Instructions");
        alert.setHeaderText("Instructions to this program:");  // Alerts have an optionl header. We don't want one.
        alert.setContentText("Ting tang wala wala bing bang\nTing tang wala wala bing bang\nTing tang wala wala bing bang\n");
        alert.showAndWait();
    }
}
