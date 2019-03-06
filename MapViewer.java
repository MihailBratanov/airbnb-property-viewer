import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.stage.Stage;
import javafx.scene.paint.*;

/**
 * Write a description of JavaFX class Viewer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

public class MapViewer extends Application
{

    private Label myLabel = new Label("0");
    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception
    {
        // Create a Button or any control item
        this.stage = stage;
        // Create a new grid pane
        VBox root = new VBox();
        makeMenuBar(root);

        root.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));

        Label imageLabel = new Label();        
        String imagePath = "boroughs.png";
        Image image = new Image(imagePath);

        ImageView imageViewer = new ImageView(image);

        double width =  image.getWidth();
        width = width / 4;
        double height =  image.getHeight(); 
        height = height / 4;

        imageViewer.setPreserveRatio(true);
        imageViewer.setFitHeight(width);
        imageViewer.setFitWidth(height);
        imageViewer.setSmooth(true);
        imageLabel.setGraphic(imageViewer);

        Pane contentPane = new BorderPane(imageLabel ,null, null, null, null);
        //center north east south west
        root.getChildren().add(contentPane);

        // JavaFX must have a Scene (window content) inside a Stage (window)
        Scene scene = new Scene(root, width, height);
        stage.setTitle("Airbnb Property Viewer");
        stage.setScene(scene);
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
}
