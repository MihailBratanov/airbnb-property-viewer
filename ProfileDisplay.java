import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ProfileDisplay extends Application {

    private String name;
    private String surname;
    private String username;
    private Font font;
    private Stage stage;
    private Scene scene;

    public ProfileDisplay(String username){
        this.name = name;
        this.surname = surname;
        this.username = username;
    }

    private Font getKingsFont(int size) {
        try {
            return font = Font.loadFont(new FileInputStream(new File("KingsFont.ttf")), size);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return font = Font.font("Verdana", size);
        }
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;

        HBox root = new HBox();

        VBox usernameLine = new VBox();

        //Text nameText = new Text(name);
        //nameText.setFont(getKingsFont(20));

        //usernameLine.getChildren().addAll(nameText);

        Text blank = new Text("");

        //Text surnameText = new Text(surname);
        //nameText.setFont(getKingsFont(20));

        Text userNameText = new Text(username);
        userNameText.setFont(getKingsFont(15));


        root.getChildren().addAll(blank, userNameText);

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}
