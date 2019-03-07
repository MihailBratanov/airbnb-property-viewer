import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.ArrayList;
public class TableViewSample extends Application {

   
    public AirbnbDataLoader loader=new AirbnbDataLoader();
    public ArrayList<AirbnbListing> data=loader.load();
  private final ObservableList<AirbnbListing> tableData =
        FXCollections.observableArrayList(loader.load());
    private TableView table = new TableView();
    public static void main(String[] args) {
      //  data=loader.load();
        launch(args);

    }

    @Override
    public void start(Stage stage) {
        //data=loader.load();
        //System.out.println(data.toString());
        Scene scene = new Scene(new Group());
        stage.setTitle("Table View Sample");
        stage.setWidth(500);
        stage.setHeight(500);

        final Label label = new Label("Name");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);
        table.setItems(tableData);
        
        TableColumn hostNameCol = new TableColumn("Host");
        hostNameCol.setMinWidth(100);
        hostNameCol.setCellValueFactory(
        new PropertyValueFactory<>("host_name"));
        
        TableColumn priceCol = new TableColumn("Price");
            priceCol.setMinWidth(100);
        priceCol.setCellValueFactory(
        new PropertyValueFactory<>("price"));
        
        TableColumn reviewsCol = new TableColumn("Reviews");
            reviewsCol.setMinWidth(100);
        reviewsCol.setCellValueFactory(
        new PropertyValueFactory<>("numberOfReviews"));
        
        TableColumn minNightsCol = new TableColumn("Min Nights");
            minNightsCol.setMinWidth(100);
        minNightsCol.setCellValueFactory(
        new PropertyValueFactory<>("minimumNights"));
        table.getColumns().addAll(hostNameCol, priceCol, reviewsCol, minNightsCol);

        final VBox vbox = new VBox();
        vbox.setSpacing(20);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(label, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }
}