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

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        root = new HBox();
        loadingRoot = new StackPane();
        //System.out.println("loaded the class (terry class)");
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

        Image image = new Image("loading.gif");
        ImageView imageView = new ImageView (image);

        loading.setCenter(imageView);

        loadingRoot.getChildren().addAll(loading);



        stage.setTitle("Map View");
        //stage.setScene(scene);



        //stage.setResizable(false);
        //stage.show();

        //show("3","3");
    }

    /*public void show(long longitude, long latitude) {
        String longitudeString = longitude.toString();
        String latitudeString = latitude.toString();

        show(longitudeString, latitudeString);
    }*/

    private Font getKingsFont(int size) {
        try {
            return font = Font.loadFont(new FileInputStream(new File("KingsFont.ttf")), size);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return font = Font.font("Verdana", size);
        }
    }

    public void show(String longitude, String latitude) {

        System.out.println("showing map");

        String mapLink = "https://www.google.com/maps/place/".concat(longitude).concat(",").concat(latitude);//

        webEngine.load(mapLink);

        root.getChildren().clear();
        root.getChildren().addAll(mapBrowser);
        stage.setScene(scene);
        stage.show();
    }

    public void show(String longitude, String latitude, String name, String hostName, String roomType, int price, String reviews, String borough) {

        //System.out.println("showing map");


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

    public void showByPlace(String place){

        place = " ".concat("London Borough of").concat(place);
        String mapLink = "https://www.google.com/maps/place/".concat(place);

        webEngine.load(mapLink);

        root.getChildren().addAll(mapBrowser);
        stage.setScene(scene);
        stage.show();
    }

}
