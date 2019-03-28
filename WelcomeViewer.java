import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
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
 * Write a description of JavaFX class WelcomeViewer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

public class WelcomeViewer extends Panel

{
    // We keep track of the count, and label displaying the count:

    private VBox root;
    private BorderPane rangeBoxBackground;
    private GridPane rangeBox;

    private int lowerLimit;
    private int upperLimit;
    final ComboBox from = new ComboBox();
    final ComboBox to = new ComboBox();

    public WelcomeViewer(){
        root = new VBox();
        //root.getStylesheets().add("welcomeviewer.css");
        Button logout = new Button("< Logout");
        Label fromLabel= new Label("From:");
        Label toLabel=new Label("To:");

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

        setLowerLimit();
        setUpperLimit();

        rangeBox = new GridPane();
        rangeBox.getChildren().addAll(fromLabel, from, toLabel, to);
        rangeBox.getColumnConstraints().add(new ColumnConstraints(45));
        rangeBox.getColumnConstraints().add(new ColumnConstraints(180));
        rangeBox.getColumnConstraints().add(new ColumnConstraints(25));
        rangeBox.getColumnConstraints().add(new ColumnConstraints(155));
        GridPane.setConstraints(fromLabel, 0, 0);
        GridPane.setConstraints(from, 1, 0);
        GridPane.setConstraints(toLabel, 2, 0);
        GridPane.setConstraints(to, 3, 0);

        rangeBoxBackground = new BorderPane();
        rangeBoxBackground.setStyle("-fx-background-color: linear-gradient(#fdfdfd, #e1e1e1); -fx-border-color: #b5b5b5;  -fx-border-width: 0px 0px 2px 0px;");
        rangeBoxBackground.setPadding(new Insets(5, 5, 5, 5));
        rangeBoxBackground.setLeft(logout);
        rangeBoxBackground.setRight(rangeBox);

        ImageView logo = new ImageView("airbnb.gif");

        VBox imageBox = new VBox();
        logo.setPreserveRatio(true);
        logo.fitHeightProperty().bind(root.heightProperty().subtract(rangeBoxBackground.heightProperty().divide(0.5)));
        logo.fitWidthProperty().bind(root.widthProperty().subtract(rangeBoxBackground.widthProperty().divide(0.5)));

        imageBox.getChildren().addAll(logo);


        imageBox.setAlignment(Pos.CENTER);

        root.setBackground(new Background(new BackgroundFill(Color.rgb(255, 70, 81), null, null)));
        root.getChildren().addAll(rangeBoxBackground, imageBox);

    }

    public void setComboBoxAction(){
        from.setOnAction(e -> setLowerLimit());
        to.setOnAction(e -> setUpperLimit());
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
        checkToBoxSelected();
        if (upperLimitString != "--Please Select--"){
            upperLimit = Integer.parseInt(upperLimitString);
        }
        else {
            upperLimit = -9998;
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

    public boolean checkToBoxSelected() {
        return to.getSelectionModel().getSelectedIndex() != 0;
    }

    public void setComoboBoxDefault() {
        to.setValue("--Please Select--");
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
