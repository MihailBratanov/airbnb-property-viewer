import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.geometry.Pos;
import javafx.scene.image.*;
import javafx.scene.text.Font;

import static javafx.scene.text.TextAlignment.*;

/**
 * A system that illustrates Airbnb properties in London.
 *
 * Class RangeSelectorView - Creates a panel that allows the user to choose the price range of properties.
 *
 * @author Haiyun Zou, Ka Wang Sin, Mihail Bratanov and Terry Phung
 * @version 2019.03.29
 *
 * 18-19 4CCS1PPA Programming Practice and Applications
 * Term 2 Coursework 4 - London Property Marketplace
 * Created by Haiyun Zou, Ka Wang Sin, Mihail Bratanov and Terry Phung
 * Student ID: 1828556, 1850162, 1838362, 1833386
 * k-number: k1895418, k1802265, k1888765, k1895389
 */
public class RangeSelectorView extends Panel {
    private VBox root;
    private BorderPane rangeBoxBackground;
    private GridPane rangeBox;

    private int lowerLimit;
    private int upperLimit;
    final ComboBox from = new ComboBox();
    final ComboBox to = new ComboBox();

    /**
     * Initialize the panel.
     */
    public RangeSelectorView(){
        root = new VBox();
        Label fromLabel = new Label("From:");
        Label toLabel = new Label("To:");

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
        rangeBoxBackground.setRight(rangeBox);

        ImageView logo = new ImageView("img/airbnb.gif");

        VBox imageBox = new VBox();
        logo.setPreserveRatio(true);
        logo.fitHeightProperty().bind(root.heightProperty().subtract(rangeBoxBackground.heightProperty().divide(0.4)));
        logo.fitWidthProperty().bind(root.widthProperty().subtract(rangeBoxBackground.widthProperty().divide(0.4)));

        Font font = new Font("font/Roboto-Regular.ttf", 20);
        Label instruction = new Label("To begin viewing properties, please select a price range on the boxes above.\nUpon selection, press 'Next'.");
        instruction.setFont(font);
        instruction.setTextFill(Color.WHITE);
        instruction.setTextAlignment(CENTER);
        imageBox.getChildren().addAll(logo, instruction);


        imageBox.setAlignment(Pos.CENTER);

        root.setBackground(new Background(new BackgroundFill(Color.rgb(255, 70, 81), null, null)));
        root.getChildren().addAll(rangeBoxBackground, imageBox);

    }

    /**
     * Set the Combo Box that the upper and lower limits will be updated when boxes are changed.
     */
    public void setComboBoxAction(){
        from.setOnAction(e -> setLowerLimit());
        to.setOnAction(e -> setUpperLimit());
    }

    /**
     * Convert the selection into string values.
     */
    public void setComboBox(int lowerLimit, int upperLimit) {
        String fromValue = Integer.toString(lowerLimit);
        String toValue = Integer.toString(upperLimit);
        from.setValue(fromValue);
        to.setValue(toValue);
    }

    /**
     * Assign the selected value when lower limit is chosen.
     */
    private void setLowerLimit() {
        String lowerLimitString = from.getSelectionModel().getSelectedItem().toString();
        if (lowerLimitString != "--Please Select--"){
            lowerLimit = Integer.parseInt(lowerLimitString);
        }
        else {
            lowerLimit = 9999;
        }
    }

    /**
     * Assign the selected value when upper limit is chosen.
     */
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

    /**
     * Check if the selected range is valid.
     * @return True if it is valid, false otherwise.
     */
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

    /**
     * Check if the upper limit combo box is chosen.
     * @return True if it is selected, false otherwise.
     */
    public boolean checkToBoxSelected() {
        return to.getSelectionModel().getSelectedIndex() != 0;
    }

    /**
     * Set Combo Box to its default value.
     */
    public void setComoboBoxDefault() {
        to.setValue("--Please Select--");
    }

    /**
     * Return the pane of this panel.
     * @return The pane of this panel.
     */
    public Pane getPanel(){
        return root;
    }

    /**
     * Return the lower limit chosen.
     * @return The lower limit chosen as an integer.
     */
    public int getLowerLimit()
    {
        return lowerLimit;
    }

    /**
     * Return the upper limit chosen.
     * @return The upper limit chosen as an integer.
     */
    public int getUpperLimit()
    {
        return upperLimit;
    }

    /**
     * Return the combo box of the lower limit.
     * @return The lower limit combo box.
     */
    public ComboBox getFromComboBox()
    {
        return from;
    }

    /**
     * Return the combo box of the upper limit.
     * @return The upper limit combo box.
     */
    public ComboBox getToComboBox()
    {
        return to;
    }
}
