import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.*;
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
 * Write a description of JavaFX class Starting here.
 *
 * @author Haiyun Zou
 * @version (a version number or a date)
 */
public class Starting extends Application
{

    private Label myLabel = new Label("0");
    private Stage stage;
    VBox root;

    private Pane newPanel;
    private double width;
    private double height;
    private double windowWidth;
    private double windowHeight;
    private Scene primaryScene;

    private AirbnbDataLoader loader=new AirbnbDataLoader();
    public ArrayList<AirbnbListing>data=loader.load();

    private ArrayList<UserDetails> details=new ArrayList<>();

    private Database database=new Database();
    public ArrayList<UserDetails> login=new ArrayList<>();


    private String userName;
    private String password;
    private UserDetails userdetail;
    private CreateAccount createaccount;

    private WelcomeViewer welcomViewr;
    private int index;

    @Override
    public void start(Stage stage) throws Exception
    {
        // Create new stage, VBox,HBox and all the labels, textFields,buttons that are needed

        this.stage=stage;

        root = new VBox();




        root.getStylesheets().add("startingdesign.css");


        root.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));

        StackPane stackpane = new StackPane();

        root.setMinSize(1000.0, 700.0);
        windowWidth= root.getMinWidth();
        windowHeight=root.getMinHeight();


        ProgressBar loadingBar=new ProgressBar();
        Label succesfully = new Label("Succesfully loaded!");



        VBox airbnb=new VBox();
        HBox loadingBox=new HBox();
        HBox succesfullyLoaded=new HBox();
        HBox userNameBox = new HBox();
        HBox passwordBox = new HBox();
        HBox logInOrCreate=new HBox();
        HBox remainderBox=new HBox();



        Label imageLabel = LoadImage();
        Label loadingLabel = new Label("loading...");
        loadingLabel.setTextFill(Color.web("#fa8072"));
        succesfully.setTextFill(Color.web("#fa8072"));


        Label userNameLabel=new Label("UserName: ");
        Label passwordLabel=new Label("Password: ");
        userNameLabel.setTextFill(Color.web("#fa8072"));
        passwordLabel.setTextFill(Color.web("#fa8072"));


        Label remainder = new Label("");

        TextField userNameText = new TextField();
        PasswordField passwordText = new PasswordField();


        Button createAccount=new Button ("Create Account");
        Button logIn=new Button ("LogIn");
        createAccount.setStyle("-fx-text-fill: #fa8072");
        logIn.setStyle("-fx-text-fill: #fa8072");

        // set the action to the login button when it is clicked

        logIn.setOnAction((event)->{


                if (!checkUserName(userNameText.getText())) {
                    remainder.setText("There is no such account, please create an account!");

                } else {
                    if (!checkPassword(passwordText.getText())) {
                        remainder.setText("Please enter the correct password!");
                    }
                    else {

                        remainder.setText("You have loged in succesfully!");
                    }

                }





            });
        remainder.setTextFill(Color.web("#fa8072"));

        // set actions when create account button is clicked
        createAccount.setOnAction((event)->{

            createaccount=new CreateAccount();

            newPanel = createaccount.getPanel();


        });




        succesfullyLoaded.getChildren().addAll(succesfully);
        airbnb.getChildren().addAll(imageLabel);
        loadingBox.getChildren().addAll(loadingLabel,loadingBar);
        userNameBox.getChildren().addAll(userNameLabel,userNameText);
        passwordBox.getChildren().addAll(passwordLabel,passwordText);
        logInOrCreate.getChildren().addAll(createAccount,logIn);
        logInOrCreate.setSpacing(10);
        remainderBox.getChildren().addAll(remainder);

        airbnb.setAlignment(Pos.CENTER);
        loadingBox.setAlignment(Pos.CENTER);
        succesfullyLoaded.setAlignment(Pos.CENTER);
        userNameBox.setAlignment(Pos.CENTER);
        passwordBox.setAlignment(Pos.CENTER);
        logInOrCreate.setAlignment(Pos.CENTER);
        remainderBox.setAlignment(Pos.CENTER);

        root.getChildren().addAll(airbnb,loadingBox,succesfullyLoaded,userNameBox,passwordBox,logInOrCreate,remainderBox);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);

        primaryScene=new Scene(root);
        stage.setScene(primaryScene);
        stage.setTitle("Airbnb Viewer");
        stage.show();
    }

    /**
     *
     * method to load the image
     */
     private Label LoadImage(){
        Label imageLabel = new Label();
        String imagePath = "airbnb.png";
        Image image = new Image(imagePath);

        ImageView imageViewer = new ImageView(image);

        width =  image.getWidth();
        width = width/1.5;
        height =  image.getHeight();
        height = height/1.5;

        imageViewer.setPreserveRatio(true);
        imageViewer.setFitHeight(height);
        imageViewer.setFitWidth(width);
        imageViewer.setSmooth(true);
        imageLabel.setGraphic(imageViewer);

        return imageLabel;
    }

    /**
     * return the VBOx
     * @return
     */
    public Pane getPanel(){
        return root;
    }

    /**
     * check if the userName is in the list or not
     */
    private boolean checkuserName;
    public boolean checkUserName(String userName) {

        for (int i = 0; i <=details.size(); i++) {
            if (details.contains(userName)) {
                index = i;
                checkuserName = true;
            } else {
                checkuserName = false;
            }
        }

        return checkuserName;
    }

    /**
     * check if the password meet the respective userName
     */
    private boolean checkpassword;
    private boolean checkPassword(String password) {

        for (int i = 0; i <=details.size(); i++) {

            if (details.get(index).equals(password)) {

                checkpassword = true;
            } else {
                checkpassword = false;
            }
        }


            return checkpassword;

    }}
