import javafx.application.Application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

import javafx.css.CssParser;
import javafx.scene.Parent;
import javafx.concurrent.Task;

/**
 * Write a description of JavaFX class Starting here.
 *
 * @author Haiyun Zou
 * @version (a version number or a date)
 */
public class Starting extends Application


{
    Task loadworker;
    private Label myLabel = new Label("0");
    private Stage stage;
    VBox root;

    private Pane newPanel;
    private double width;
    private double height;
    private double windowWidth;
    private double windowHeight;
    private Scene primaryScene;

    private boolean checkuserName;
    private boolean checkpassword;

    private AirbnbDataLoader loader=new AirbnbDataLoader();
    public ArrayList<AirbnbListing>data=loader.load();


    private ArrayList<String> checkuser=new ArrayList<>();

    private ArrayList<String> checkPassword=new ArrayList<>();



    private String userName;
    private String password;


    private boolean finishloading;
    private UserDetails userdetail;
    //CreateAccount createAccount;

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




        HBox loadingBox=new HBox();
        HBox succesfullyLoaded=new HBox();
        HBox userNameBox = new HBox();
        HBox passwordBox = new HBox();
        HBox logInOrCreate=new HBox();

        HBox reminderBox=new HBox();

        Label loadingLabel = new Label("loading...");
        loadingLabel.setTextFill(Color.web("#fa8072"));
        succesfully.setTextFill(Color.web("#fa8072"));


        Label userNameLabel=new Label("UserName: ");
        Label passwordLabel=new Label("Password: ");
        userNameLabel.setTextFill(Color.web("#fa8072"));
        passwordLabel.setTextFill(Color.web("#fa8072"));



        Label reminder = new Label("");

        TextField userNameText = new TextField();
        PasswordField passwordText = new PasswordField();


        Button createAccount=new Button ("Create Account");
        Button logIn=new Button ("LogIn");
        createAccount.setStyle("-fx-text-fill: #fa8072");
        logIn.setStyle("-fx-text-fill: #fa8072");


        /**checkuser.add(userdetail.getUserName());

        checkPassword.add(userdetail.getPassword());

        // set the action to the login button when it is clicked

        /**logIn.setOnAction((event)->{

            if(!CheckUserName(userName)) {
            reminder.setText("There is no such user name, please check again or create account");

            }
            else {if (CheckPassword(password)) {

            reminder.setText("You have succesfully loged in");
            }
            else{
                reminder.setText("You have enter the wrong password please check agin");
            }


            }


            });
        reminder.setTextFill(Color.web("#fa8072"));
*/



        loadingBar.setProgress(0);

        loadworker=createWorker();

        loadingBar.progressProperty().unbind();
        loadingBar.progressProperty().bind(loadworker.progressProperty());

        new Thread(loadworker).start();


        // set actions when create account button is clicked
        createAccount.setOnAction((event)->{

            CreateAccount createAccountWindow = new CreateAccount();

            Stage accountCreateStage = new Stage();

            try {
                createAccountWindow.start(accountCreateStage);
            } catch (Exception e) {
                e.printStackTrace();
            }



        });

        logIn.setOnAction((event) -> {

            String userNameTemp = userNameText.getText();
            String passwordTemp = passwordText.getText();

            Boolean logInPass = loginCheck(userNameTemp, passwordTemp);
            if (logInPass){
                //getUserProfile(userNameTemp);
                Stage main = new Stage();
                Viewer mainViewer = new Viewer();

                try {
                    mainViewer.start(main);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                stage.close();
            }
        });


        succesfullyLoaded.getChildren().addAll(succesfully);

        loadingBox.getChildren().addAll(loadingLabel,loadingBar);
        userNameBox.getChildren().addAll(userNameLabel,userNameText);
        passwordBox.getChildren().addAll(passwordLabel,passwordText);
        logInOrCreate.getChildren().addAll(createAccount,logIn);
        logInOrCreate.setSpacing(30);
        reminderBox.getChildren().addAll(reminder);

        loadingBox.setAlignment(Pos.CENTER);
        succesfullyLoaded.setAlignment(Pos.CENTER);
        userNameBox.setAlignment(Pos.CENTER);
        passwordBox.setAlignment(Pos.CENTER);
        logInOrCreate.setAlignment(Pos.CENTER);
        reminderBox.setAlignment(Pos.CENTER);

        root.getChildren().addAll(loadingBox,succesfullyLoaded,userNameBox,passwordBox,logInOrCreate,reminderBox);
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

    public boolean loginCheck(String userName, String password){

        boolean loginStatus = false;

        Database database = new Database();
        ArrayList<UserDetails> logins = database.getDatabaseEntries();
        // logins[2] is username, logins[3] is password.

        for (UserDetails login : logins){
            if (login.getUserName().equals(userName)){
                if (login.getPassword().equals(password)){
                    loginStatus = true;
                }
            }
        }

        return  loginStatus;
    }

    public UserDetails getUserProfile(String userName){

        UserDetails userProfile = null;

        Database database = new Database();

        ArrayList<UserDetails> logins = database.getDatabaseEntries();

        for (UserDetails login : logins) {
            if (login.getUserName().equals(userName)) {
                userProfile = login;
                break;
            }
        }

        return userProfile;
    }

    /*
    public boolean CheckUserName(String userName){
        if (checkuser.size()>0){
            for (int i=0;i<checkuser.size();i++){
                if (checkuser.get(i).equals(userName)){
                    checkuserName=true;
                    index=i;
                }
                else checkuserName=false;
            }

        }
        else{checkuserName=false;}
        return checkuserName;
    }


public boolean CheckPassword(String password){
   if (checkPassword.size()>0&&checkPassword.get(index).equals(password)){
       checkpassword=true;
   }
   else{checkpassword=false;}
   return checkpassword;

} */

    public Task createWorker() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(600);
                    updateMessage("500 milliseconds");
                    updateProgress(i + 1, 10);
                }

                return true;
            }
        };
}
}