
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
    private String userName;
    private String password;

    /**
     * Constructor for objects of class UserDetails
     * the parameters are the first name, surname, user name, and password
     *
     */
    public UserDetails( String firstName,String surname,String userName,String password)
    {
        // initialise instance variables

        this.firstName=firstName;
        this.surname=surname;
        this.userName=userName;
        this.password=password;
    }

    /**
     *
     * return the first name
     */
    public String getFirstName(){
        return firstName;
    }

    /**
     *
     * return the surname
     */
    
    public String getSurname(){
        return surname;
    }

    /**
     * return the user name
     *
     */
    public String getUserName(){
        return userName;
    }

    /**
     * return the password
     *
     */
    public String getPassword(){
        return password;
    }

    /**
     *
     *
     */
    @Override
    public String toString() {
        return "UserDetails{" +
                "firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
