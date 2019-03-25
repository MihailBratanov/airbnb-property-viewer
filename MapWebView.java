import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class MapWebView extends Application {

    private Stage stage;
    private Scene scene;
    private HBox root;
    private WebView mapBrowser;
    private WebEngine webEngine;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        root = new HBox();
        mapBrowser = new WebView();
        webEngine = mapBrowser.getEngine();

        String longitude = "49.46800006494457";
        String latitude = "17.11514008755796";


        String html = "https://www.google.com/maps/place/49.46800006494457,17.11514008755796";

        webEngine.load(html);

        root.getChildren().addAll(mapBrowser);

        scene = new Scene(root);
        stage.setTitle("Map View");
        stage.setScene(scene);
        //stage.show();

        show("3","3");
    }

    /*public void show(long longitude, long latitude) {
        String longitudeString = longitude.toString();
        String latitudeString = latitude.toString();

        show(longitudeString, latitudeString);
    }*/

    public void show(String longitude, String latitude) {

        String mapLink = "https://www.google.com/maps/place/".concat(longitude).concat(",").concat(latitude);
        webEngine.load(mapLink);
        stage.show();
    }

    public void showByPlace(String place){

        String mapLink = "https://www.google.com/maps/place/".concat(place);
        webEngine.load(mapLink);
        stage.show();
    }
}


