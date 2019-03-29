import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class ProfileDisplay extends Application {

    private String name;
    private String surname;
    private String username;
    private Font font;
    private Stage stage;
    private Scene scene;
    private HashMap<String,Integer> userClicks;

    public ProfileDisplay(String username){

        Database database = new Database();
        String[] details = database.getUserDetails(username);

        this.name = details[0];
        this.surname = details[1];
        this.username = details[2];

        userClicks = database.getUserProfile(username);

    public ProfileDisplay(String username){
        this.name = name;
        this.surname = surname;
        this.username = username;
    }

    private Font getKingsFont(int size) {
        try {
            return font = Font.loadFont(new FileInputStream(new File("font/KingsFont.ttf")), size);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return font = Font.font("Verdana", size);
        }
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        
        VBox root = new VBox();

        root.setMinSize(300.0,500.0);

        Text nameText = new Text(name);
        nameText.setFont(getKingsFont(40));

        Text surnameText = new Text(surname);
        nameText.setFont(getKingsFont(40));

        Text userNameText = new Text(username);
        userNameText.setFont(getKingsFont(17));

        GridPane mainPane = new GridPane();

        //mainPane.getColumnConstraints().add(new ColumnConstraints(100));
        //mainPane.getRowConstraints().add(new RowConstraints(200));

        ImageView userlogo = new ImageView("img/userlogoLarge.png");
        userlogo.setFitHeight(40);
        userlogo.setFitWidth(40);

        mainPane.add(new Text (" "), 1,1);

        mainPane.add(userlogo,1,2);
        mainPane.add(new Text("   "), 2,1);
        mainPane.add(nameText, 2, 2);
        mainPane.add(surnameText, 2, 3);
        mainPane.add(userNameText, 4, 4);

        mainPane.add(new Text (" "), 1,5);
        mainPane.add(new Text (" "), 1,6);
        mainPane.add(new Text (" "), 1,7);
        mainPane.add(new Text ("Borough Clicks : "), 1,8);

        int row = 8;

        for (String borough : userClicks.keySet()){
            if (userClicks.get(borough) > 0){
                row += 1;
                mainPane.add( new Text ( borough.concat(" : ").concat(String.valueOf(userClicks.get(borough)))), 2, row);
            }
        }


        //.setGridLinesVisible(true);
        mainPane.setAlignment(Pos.CENTER);


        root.getChildren().addAll(mainPane);

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}
