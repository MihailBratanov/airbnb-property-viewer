import javafx.application.Application;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.geometry.Pos;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import javafx.scene.image.*;
import javafx.concurrent.Task;

/**
 * Write a description of JavaFX class Starting here.
 *
 * @author Haiyun Zou
 * @version 2019.03.29
 *
 *  18-19 4CCS1PPA Programming Practice and Applications
 *  Term 2 Coursework 4 - London Property Marketplace
 *  Created by Haiyun Zou, Ka Wang Sin, Mihail Bratanov and Terry Phung
 *  Student ID:
 *  k-number:
 */
public class Starting extends Application {
    Task loadworker;
    private Stage stage;
    VBox root;
    private double width;
    private double height;

    private Scene primaryScene;

    private ProgressBar loadingBar;
    private Label successfully;
    private PasswordField passwordText;
    private TextField userNameText;
    private Label userNameLabel;
    private Label passwordLabel;
    private Label loadingLabel;
    private Button quit;
    private Button createAccount;
    private Button logIn;

    private Font titleFont;

    private AirbnbDataLoader loader = new AirbnbDataLoader();
    public ArrayList<AirbnbListing> data = loader.load();

    @Override
    public void start(Stage stage) throws Exception {
        // Create new stage, VBox,HBox and all the labels, textFields,buttons that are needed

        this.stage = stage;

        root = new VBox();

        titleFont = Font.loadFont(new FileInputStream(new File("Roboto-Regular.ttf")), 20);


        root.getStylesheets().add("startingdesign.css");

        root.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));


        loadingBar = new ProgressBar();
        loadingBar.prefWidthProperty().bind(root.widthProperty().multiply(0.4));
        successfully = new Label("Successfully loaded!");

        VBox loadingBox = new VBox();
        HBox successfullyLoaded = new HBox();
        HBox userNameBox = new HBox();
        HBox passwordBox = new HBox();
        HBox logInOrCreate = new HBox();

        HBox reminderBox = new HBox();

        loadingLabel = new Label("Loading...");
        loadingLabel.setTextFill(Color.WHITE);
        loadingLabel.setFont(titleFont);
        successfully.setTextFill(Color.WHITE);
        successfully.setVisible(false);
        successfully.setFont(titleFont);

        userNameLabel = new Label("Username: ");
        passwordLabel = new Label("Password: ");
        userNameLabel.setFont(titleFont);
        passwordLabel.setFont(titleFont);
        userNameLabel.setTextFill(Color.WHITE);
        passwordLabel.setTextFill(Color.WHITE);
        userNameLabel.setVisible(false);
        passwordLabel.setVisible(false);

        userNameText = new TextField();
        userNameText.setVisible(false);
        passwordText = new PasswordField();
        passwordText.setVisible(false);
        passwordText.setOnKeyReleased(e -> {
            if(e.getCode() == KeyCode.ENTER) {
                logIn();
            }
        });

        quit = new Button("Quit");
        createAccount = new Button("Create Account");
        logIn = new Button("Login");
        createAccount.setStyle("-fx-text-fill: #000000");
        logIn.setStyle("-fx-text-fill: #000000");
        quit.setVisible(false);
        createAccount.setVisible(false);
        logIn.setVisible(false);

        loadingBar.setProgress(0);

        loadworker = createWorker();

        loadingBar.progressProperty().unbind();
        loadingBar.progressProperty().bind(loadworker.progressProperty());

        new Thread(loadworker).start();


        quit.setOnAction(e -> {
            System.exit(0);
        });

        // set actions when create account button is clicked
        createAccount.setOnAction((event) -> {

            CreateAccount createAccountWindow = new CreateAccount();

            Stage accountCreateStage = new Stage();

            try {
                createAccountWindow.start(accountCreateStage);
            } catch (Exception e) {
                e.printStackTrace();
            }


        });

        logIn.setOnAction(e -> logIn());

        successfullyLoaded.getChildren().addAll(successfully);

        loadingBox.getChildren().addAll(loadingBar, loadingLabel);
        userNameBox.getChildren().addAll(userNameLabel, userNameText);
        passwordBox.getChildren().addAll(passwordLabel, passwordText);
        logInOrCreate.getChildren().addAll(quit, createAccount, logIn);
        logInOrCreate.setSpacing(30);


        loadingBox.setAlignment(Pos.BOTTOM_CENTER);
        successfullyLoaded.setAlignment(Pos.BOTTOM_CENTER);
        userNameBox.setAlignment(Pos.BOTTOM_CENTER);
        passwordBox.setAlignment(Pos.BOTTOM_CENTER);
        logInOrCreate.setAlignment(Pos.BOTTOM_CENTER);
        reminderBox.setAlignment(Pos.BOTTOM_CENTER);

        root.getChildren().addAll(loadingBox, successfullyLoaded, userNameBox, passwordBox, logInOrCreate);
        root.setAlignment(Pos.BOTTOM_CENTER);
        root.setSpacing(15);
        root.setPadding(new Insets(10, 10, 10, 10));


        primaryScene = new Scene(root);
        stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth() * 7 / 8);
        stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight() * 15 / 16);
        stage.setMinWidth(Screen.getPrimary().getVisualBounds().getHeight() * 1 / 10);
        stage.setMinHeight(Screen.getPrimary().getVisualBounds().getHeight() * 1 / 10);
        stage.setScene(primaryScene);
        stage.setTitle("Airbnb Viewer");
        stage.show();


    }


    /**
     * Return the VBox from this viewer
     * @return VBox
     */

    public Pane getPanel() {
        return root;
    }

    /**
     * Check the information for login
     * @param userName When the username is entered in the TextField
     * @param password When the password is entered in th TextField
     * @return The boolean weather it is has the correct infromation to log in
     */
    private boolean loginCheck(String userName, String password) {

        boolean loginStatus = false;

        Database database = new Database();
        ArrayList<UserDetails> logins = database.getDatabaseEntries();
        // logins[2] is username, logins[3] is password.

        for (UserDetails login : logins) {
            if (login.getUserName().equals(userName)) {
                if (login.getPassword().equals(password)) {
                    loginStatus = true;
                }
            }
        }

        return loginStatus;
    }

    /**
     *
     * @param userName
     * @return
     */
    public UserDetails getUserProfile(String userName) {

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

    /**
     * Set the action when clicking the login button,
     * which will bring to the main viewer
     */
    private void logIn() {
        String userNameTemp = userNameText.getText();
        String passwordTemp = passwordText.getText();

        Boolean logInPass = loginCheck(userNameTemp, passwordTemp);
        if (logInPass) {
            Stage main = new Stage();
            Viewer mainViewer = new Viewer(userNameTemp);

            try {
                mainViewer.start(main);
            } catch (Exception e) {
                e.printStackTrace();
            }

            stage.close();
        } else {
            Alert wrongInput = new Alert(Alert.AlertType.ERROR);
            wrongInput.setTitle("Error");
            wrongInput.setHeaderText("Incorrect user credentials.");
            wrongInput.setContentText("The username or password is incorrect.\nPlease check and try again.");
            wrongInput.showAndWait();
        }

    }

    /**
     * Set the task for the loading bar to load
     * @return True when it finished loading
     */

    private Task createWorker() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                for (int i = 0; i < 3; i++) {
                    Thread.sleep(300);
                    updateMessage("500 milliseconds");
                    updateProgress(i + 1, 10);
                }

                userNameText.setVisible(true);
                passwordText.setVisible(true);
                successfully.setVisible(true);
                userNameLabel.setVisible(true);
                passwordLabel.setVisible(true);
                quit.setVisible(true);
                createAccount.setVisible(true);
                logIn.setVisible(true);
                Thread.sleep(600);
                loadingLabel.setVisible(false);
                loadingBar.setVisible(false);
                Thread.sleep(600);
                successfully.setVisible(false);
                return true;
            }
        };
    }
}