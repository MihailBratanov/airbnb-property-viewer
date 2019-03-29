import javafx.application.Application;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.geometry.Pos;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

/**
 * Write a description of JavaFX class CreateAccount here.
 *
 * this class allows the user to type their details and store these information in the UserDetails class by using an arrayList
 *
 * @author Haiyun Zou
 * @version (a version number or a date)
 *
 *
 */
public class CreateAccount extends Application
{
    // We keep track of the count, and label displaying the count:
    private Stage stage;
    VBox root;
    private Scene scene;
    private Font font;

    private ArrayList<UserDetails> userDetails = new ArrayList<>();
    private UserDetails userdetails;
    @Override
    public void start(Stage stage) throws Exception
    {
        // Create a Button or any control item
        this.stage=stage;

        font = Font.loadFont(new FileInputStream(new File("font/Roboto-Regular.ttf")), 20);

        root = new VBox();

        root.getStylesheets().add("startingdesign.css");

        root.setMinSize(1000.0, 700.0);


        root.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));


        Label createAccount = new Label("Please enter your details:");
        createAccount.setFont(font);
        Label firstNameLabel = new Label("First Name: ");
        firstNameLabel.setFont(font);
        Label lastNameLabel = new Label("Last Name: ");
        lastNameLabel.setFont(font);
        Label userNameLabel = new Label("Username: ");
        userNameLabel.setFont(font);
        Label passwordLabel = new Label("Password: ");
        passwordLabel.setFont(font);
        createAccount.setTextFill(Color.WHITE);
        firstNameLabel.setTextFill(Color.WHITE);
        lastNameLabel.setTextFill(Color.WHITE);
        userNameLabel.setTextFill(Color.WHITE);
        passwordLabel.setTextFill(Color.WHITE);


        TextField firstNameText = new TextField();
        TextField surNameText = new TextField();
        TextField userNameText = new TextField();
        PasswordField passwordText = new PasswordField();


        Button submit =new Button("Submit");

        Button goBack=new Button("Go Back and LogIn");
        Label reminder=new Label();
        reminder.setText("");


        //submit.getStylesheets().add("startingdesign.css");

        submit.setStyle("-fx-text-fill: #000000");

        goBack.setStyle("-fx-text-fill: #000000");

        goBack.setStyle("-fx-text-fill: #000000");

        // Set action to the submit button
        submit.setOnAction((event)-> {

            Database database = new Database();

            ArrayList<UserDetails> logins = database.getDatabaseEntries();

            ArrayList<String> userNameList = new ArrayList<>();

            for (UserDetails login : logins){
                userNameList.add(login.getUserName());
            }

            // Check the username, and make sure the username does not contain " " or "-"
            if
            (userNameText.getText().contains(" ") || userNameText.getText().contains("-")) {

                reminder.setText("The username can not contain space or '-', please enter the username again");

            } else if (passwordText.getText().contains(" ") || passwordText.getText().contains("-")) {
                reminder.setText("The password can not contain space or '-', please enter the password again");

            }
            // Check the password, and make sure the password is between 8 and 16 characters
            else    if (passwordText.getText().length() >= 16 || passwordText.getText().length() < 8)
            {
                passwordText.clear();
                reminder.setText("Please enter a password of 8-16 characters.");

            }
            // Check the username, and make sure the username dose not exist in the database
            else if (userNameList.contains(userNameText.getText())){
                reminder.setText("This username has been taken! Choose another one. How about Steve?");
            }
            // Check the TextField, and make sure all the TextFields are not empty
            else if (!firstNameText.getText().equals("") && !surNameText.getText().equals("") && !userNameText.getText().equals("") && !passwordText.getText().equals("")) {

                userdetails=new UserDetails(
                        firstNameText.getText(),
                        surNameText.getText(),
                        userNameText.getText(),
                        passwordText.getText());


                userDetails.add(userdetails);
                userdetails.writeToDatabase();

                firstNameText.clear();
                surNameText.clear();
                userNameText.clear();
                passwordText.clear();


                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Create Account");
                alert.setHeaderText("Thank you.");  // Alerts have an optionl header. We don't want one.
                alert.setContentText("The account has successfully been created.\nYou can now login with your credentials.");
                alert.showAndWait();

                stage.close();

            }
            else {
                reminder.setText("An error occurred.");
                reminder.setDisable(false);
            }
        });


        goBack.setOnAction( event -> stage.close());


        reminder.setTextFill(Color.WHITE);


        HBox submitBox = new HBox();
        submitBox.getChildren().addAll(goBack,submit);
        submitBox.setSpacing(20);

        GridPane detailsBox = new GridPane();
        detailsBox.setAlignment(Pos.CENTER);
        detailsBox.getChildren().addAll(firstNameLabel, firstNameText, lastNameLabel, surNameText,
        userNameLabel, userNameText, passwordLabel, passwordText, submitBox, reminder);
        detailsBox.setVgap(10);
        detailsBox.getColumnConstraints().add(new ColumnConstraints(150));
        detailsBox.getColumnConstraints().add(new ColumnConstraints(500));
        GridPane.setConstraints(firstNameLabel, 0,1);
        GridPane.setConstraints(firstNameText, 1,1);
        GridPane.setConstraints(lastNameLabel, 0,2);
        GridPane.setConstraints(surNameText, 1,2);
        GridPane.setConstraints(userNameLabel, 0,3);
        GridPane.setConstraints(userNameText, 1,3);
        GridPane.setConstraints(passwordLabel, 0,4);
        GridPane.setConstraints(passwordText, 1,4);
        GridPane.setConstraints(submitBox, 1,6);
        GridPane.setConstraints(reminder, 1,7);




        root.getChildren().addAll(createAccount, detailsBox);
        root.setAlignment(Pos.BOTTOM_CENTER);
        root.setSpacing(8);
        root.setPadding(new Insets(5, 5, 5, 5));
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Create Account");
        stage.show();
    }

    /**
     * Return the VBox form this viewer
     * @return VBox
     */
    public Pane getPanel(){
        return root;
    }

}