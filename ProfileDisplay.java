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

/**
 * Class ProfileDisplay: class that displays the profile of the logged in user.
 * Displays the name, surname and username in the window.
 * It then loads from the stored data, the amount of clicks the user has ever clicked on each borough.
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
public class ProfileDisplay extends Application {

    private String name;
    private String surname;
    private String username;
    private Font font;
    private Stage stage;
    private Scene scene;
    private HashMap<String,Integer> userClicks;

    /**
     * Constructor for the Profile display class. Displays the profile for
     * the user, read from the database, from the username.
     * @param username - username of the user.
     */
    public ProfileDisplay(String username) {

        Database database = new Database();
        String[] details = database.getUserDetails(username);

        this.name = details[0];
        this.surname = details[1];
        this.username = details[2];

        userClicks = database.getUserProfile(username);

    }

    /**
     * Get the font to be used inside of the text.
     * We have selected to use the King's font.
     *
     * @param size gets the size of the font
     * @return the font with the size.
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
     * Stats the JAVAFX Stage : displays the user Profile window.
     * @param stage - stage in which this window will be displayed in.
     */
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

        //formats the text for the grid pane

        ImageView userlogo = new ImageView("img/userlogoLarge.png");
        userlogo.setFitHeight(40);
        userlogo.setFitWidth(40);

        mainPane.add(new Text (" "), 1,1);

        mainPane.add(userlogo,4,2);

        mainPane.add(new Text("   "), 2,1);
        mainPane.add(nameText, 2, 2);
        mainPane.add(surnameText, 2, 3);
        mainPane.add(userNameText, 4, 4);

        mainPane.add(new Text (" "), 1,5);
        mainPane.add(new Text (" "), 1,6);
        mainPane.add(new Text (" "), 1,7);

        Text boroughCount = new Text("Borough Clicks");
        boroughCount.setFont(getKingsFont(25));

        mainPane.add(boroughCount, 2,8);
        mainPane.add(new Text (" "), 1,9);

        //profile window - adds the data in this for loop.

        int row = 9;

        for (String borough : userClicks.keySet()){
            if (userClicks.get(borough) > 0){
                row += 1;
                Text line = new Text(borough.concat(" : ").concat(String.valueOf(userClicks.get(borough))));
                line.setFont(getKingsFont(15));
                mainPane.add(line , 2, row);
            }
        }

        mainPane.setAlignment(Pos.CENTER);


        root.getChildren().addAll(mainPane);

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}
