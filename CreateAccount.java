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
 * Write a description of JavaFX class CreateAccount here.
 *
 * this class allows the user to type their details and store these information in the UserDetails class by using an arrayList
 *
 * @author Haiyun Zou
 * @version (a version number or a date)
 */
public class CreateAccount extends Application
{
    // We keep track of the count, and label displaying the count:
    private Label myLabel = new Label("0");
    private Stage stage;
    VBox root;
    private double width;
    private double height;
    private double windowWidth;
    private double windowHeight;
    private Scene scene;

    private ArrayList<UserDetails> userDetails=new ArrayList<>();
    private UserDetails userdetails;
    @Override
    public void start(Stage stage) throws Exception
    {
        // Create a Button or any control item
        this.stage=stage;

        root = new VBox();

        root.getStylesheets().add("startingdesign.css");

        root.setMinSize(1000.0, 700.0);
        windowWidth= root.getMinWidth();
        windowHeight=root.getMinHeight();


        root.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));

        StackPane stackpane = new StackPane();

        Label createAccount=new Label("Create Account");
        Label firstNameLabel=new Label("First Name: ");
        Label surNameLabel=new Label("Surname: ");
        Label userNameLabel=new Label("User Name: ");
        Label passwordLabel=new Label("Password: ");
        createAccount.setTextFill(Color.web("#fa8072"));
        firstNameLabel.setTextFill(Color.web("#fa8072"));
        surNameLabel.setTextFill(Color.web("#fa8072"));
        userNameLabel.setTextFill(Color.web("#fa8072"));
        passwordLabel.setTextFill(Color.web("#fa8072"));


        TextField firstNameText=new TextField();
        TextField surNameText=new TextField();
        TextField userNameText=new TextField();
        PasswordField passwordText=new PasswordField();



        Button submit =new Button("Submit");
        Button goBack=new Button("Go Back and LogIn");
        Label reminder=new Label();
        reminder.setText("");

        submit.getStylesheets().add("startingdesign.css");

        submit.setStyle("-fx-text-fill: #fa8072");
        goBack.setStyle("-fx-text-fill: #fa8072");


        submit.setOnAction((event)-> {


            if

            (userNameText.getText().contains(" ") || userNameText.getText().contains("-")) {

                reminder.setText("The user name can not contain space or '-', please enter the username again");

            } else if (passwordText.getText().contains(" ") || passwordText.getText().contains("-")) {
                reminder.setText("The password can not contain space or '-', please enter the password again");

            }
            else    if (passwordText.getText().length() >= 16 || passwordText.getText().length() < 8)
            {
                passwordText.clear();
                reminder.setText("Please enter a pasword from 8 to 16 charactors please!");

            }



            else if (!firstNameText.getText().equals("") && !surNameText.getText().equals("") && !userNameText.getText().equals("") && !passwordText.getText().equals("")) {

                userdetails=new UserDetails(
                        firstNameText.getText(),
                        surNameText.getText(),
                        userNameText.getText(),
                        passwordText.getText());


                userDetails.add(userdetails);
                userdetails.writeToDatabase();

                //System.out.println(userDetails.get(0));

                firstNameText.clear();
                surNameText.clear();
                userNameText.clear();
                passwordText.clear();

                reminder.setText("You have succesfully created your account");

            }


            else {
                reminder.setText("Please enter your information!");
                reminder.setDisable(false);

            }


        });

        goBack.setOnAction( event -> stage.close());


        reminder.setTextFill(Color.web("#fa8072"));



        HBox createAccountBox=new HBox();
        HBox firstNameBox=new HBox();
        HBox surNameBox=new HBox();
        HBox userNameBox=new HBox();
        HBox passwordBox=new HBox();
        HBox submitBox=new HBox();
        HBox reminderBox=new HBox();


        createAccountBox.getChildren().addAll(createAccount);
        firstNameBox.getChildren().addAll(firstNameLabel,firstNameText);
        surNameBox.getChildren().addAll(surNameLabel,surNameText);
        userNameBox.getChildren().addAll(userNameLabel,userNameText);
        passwordBox.getChildren().addAll(passwordLabel,passwordText);
        submitBox.getChildren().addAll(submit,goBack);
        reminderBox.getChildren().addAll(reminder);


        surNameBox.setSpacing(10);
        userNameBox.setSpacing(10);
        passwordBox.setSpacing(10);
        submitBox.setSpacing(10);

        createAccountBox.setAlignment(Pos.CENTER);
        firstNameBox.setAlignment(Pos.CENTER);
        surNameBox.setAlignment(Pos.CENTER);
        userNameBox.setAlignment(Pos.CENTER);
        passwordBox.setAlignment(Pos.CENTER);
        submitBox.setAlignment(Pos.CENTER);
        reminderBox.setAlignment(Pos.CENTER);

        root.getChildren().addAll(createAccountBox,firstNameBox,surNameBox,userNameBox,passwordBox,submitBox,reminderBox);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Create Account");
        stage.show();
    }

    public Pane getPanel(){
        return root;
    }

}
