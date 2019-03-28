import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;


public class MapWebView extends Application {

    private Stage stage;
    private Scene scene;
    private Scene loadingScene;
    private StackPane root;
    private StackPane loadingRoot;
    private WebView mapBrowser;
    private WebEngine webEngine;
    private Slider zoomSlider;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        root = new StackPane();
        loadingRoot = new StackPane();

        mapBrowser = new WebView();
        webEngine = mapBrowser.getEngine();

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
        stage.show();

        //show("3","3");
    }

    /*public void show(long longitude, long latitude) {
        String longitudeString = longitude.toString();
        String latitudeString = latitude.toString();

        show(longitudeString, latitudeString);
    }*/

    public void show(String longitude, String latitude) {

        String mapLink = "https://www.google.com/maps/place/".concat(longitude).concat(",").concat(latitude);//

        webEngine.load(mapLink);

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