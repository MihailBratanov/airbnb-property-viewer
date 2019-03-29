import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static javafx.scene.layout.BackgroundPosition.CENTER;

/**
 * MapWebView class. Class that displays on one side, the property information
 * on the other side, a map that shows where to property is according to the
 * coordinates given.
 *
 * The Map is displayed using a link generated from the parameters, which
 * leads to a google maps webpage. The class then loads a web browser
 * displaying this page.
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
public class MapWebView extends Application {

    private Stage stage;
    private Scene scene;
    private Scene loadingScene;
    private HBox root;
    private StackPane loadingRoot;
    private WebView mapBrowser;
    private WebEngine webEngine;
    private Slider zoomSlider;
    private Font font;

    /**
     * start the JAVAFX stage of the MapWebViewer class, and displays everything accordingly.
     *
     * @param stage the javafx stage where the map (web viewer) is supposed to be displayed in.
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        root = new HBox();
        loadingRoot = new StackPane();
        mapBrowser = new WebView();
        webEngine = mapBrowser.getEngine();

        webEngine.getLoadWorker().stateProperty().addListener(event -> {
            String state = String.valueOf(webEngine.getLoadWorker().stateProperty().getValue());
            if (state.equals("FAILED")){
                webEngine.loadContent("Internet Connection error. Please Check Internet Connection");
            }
        });

        scene = new Scene(root);

        loadingScene = new Scene(loadingRoot);

        BorderPane loading = new BorderPane();

        Image image = new Image("img/loading.gif");
        ImageView imageView = new ImageView (image);

        loading.setCenter(imageView);

        loadingRoot.getChildren().addAll(loading);

        stage.setTitle("Map View");
    }

    /**
     * Get the font to be used inside of the text.
     * We have selected to use the King's font.
     *
     * @param size size of the font
     * @return returns king's font
     */
    private Font getKingsFont(int size) {
        try {
            return font = Font.loadFont(new FileInputStream(new File("font/KingsFont.ttf")), size);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return font = Font.font("Verdana", size);
        }
    }

    /**
     * Displays the map (web viewer) window with the property information.
     *
     * @param longitude latitude of the property
     * @param latitude longtitude of the property
     * @param name name of the property listing
     * @param hostName host of the property
     * @param roomType type of the room
     * @param price price per night
     * @param reviews number of reviews
     * @param borough borough where the property is located in
     */
    public void show(String longitude, String latitude, String name, String hostName, String roomType, int price, String reviews, String borough) {


        String mapLink = "https://www.google.com/maps/place/".concat(longitude).concat(",").concat(latitude);//

        webEngine.load(mapLink);


        VBox topbox = new VBox();

        Text nameText = new Text(name);
        nameText.setFont(getKingsFont(23));


        Text hostNameText = new Text("Hosted by : ".concat(hostName));
        hostNameText.setFont(getKingsFont(18));

        topbox.getChildren().addAll(nameText,hostNameText);

        VBox middlebox = new VBox();
        middlebox.setAlignment(Pos.CENTER);

        Text roomTypeText = new Text(roomType);
        roomTypeText.setFont(getKingsFont(25));

        Text priceText = new Text("Price : £".concat(String.valueOf(price)).concat(" / Night"));
        priceText.setFont(getKingsFont(20));

        middlebox.getChildren().addAll(roomTypeText, priceText);

        VBox bottombox = new VBox();

        if (reviews.equals("")){
            reviews = "Never";
        }
        Text lastReviewText = new Text("Last Reviewed : ".concat(reviews));
        lastReviewText.setFont(getKingsFont(15));

        Text boroughText = new Text("Located In : ".concat(borough));
        boroughText.setFont(getKingsFont(20));

        bottombox.getChildren().addAll(lastReviewText, boroughText);

        BorderPane property = new BorderPane();

        property.getStylesheets().add("MapWebViewBackground.css");

        property.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        property.setTop(topbox);
        property.setPadding(new Insets(20, 10, 5, 5));

        property.setCenter(middlebox);

        property.setBottom(bottombox);

        //property.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));

        property.setBackground(new Background(new BackgroundImage(new Image("img/house.gif"), null, null,CENTER ,null)));

        root.getChildren().clear();
        root.getChildren().addAll(mapBrowser, property);
        stage.setScene(scene);
        stage.show();
    }
}
