import javafx.scene.control.DatePicker;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Write a description of class UserDetails here.
 *
 * @author Haiyun Zou
 * @version (a version number or a date)
 */
public class UserDetails
{
    // instance variables - replace the example below with your own

    private String firstName;
    private String surname;
    private String username;
    private String password;

    /**
     * Constructor for objects of class UserDetails
     * the parameters are the first name, surname, user name, and password
     *
     */
    public UserDetails( String firstName,String surname,String username,String password)
    {
        // initialise instance variables

        this.firstName=firstName;
        this.surname=surname;
        this.username=username;
        this.password=password;
    }

    public void writeToDatabase(){
        Database database = new Database();
        database.writeDetails(firstName, surname, username, password);
    }

    /**
     * Return the first name from the constructor
     * @return firstName as a String
     */
    public String getFirstName(){
        return firstName;
    }
    /**
     * Return the surname from the constructor
     * @return surname as a String
     */
    public String getSurname(){
        return surname;
    }

    /**
     * Return the username from the constructor
     * @return username as a String
     */
    public String getUserName(){
        return username;
    }

    /**
     * Return the password from the constructor
     * @return password as a String
     */
    public String getPassword(){
        return password;
    }

    /**
     * Get user details to String
     * @return UserDetails as String
     */

    @Override
    public String toString() {
        return "UserDetails{" +
                "firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                ", userName='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }


}
