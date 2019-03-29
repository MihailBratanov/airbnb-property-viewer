import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class database : writer and reader that does database operations
 * tailored to this program. Creates user profiles, reads from the profiles
 * writes to the profiles and passes it on to the classes which needs it.
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

public class Database {

    File database;
    FileWriter fileWriter;
    BufferedWriter writer;
    BufferedReader reader;
    ArrayList<String> existingEntries;

    /**
     * Constructor of the Database class.
     */
    public Database(){
        existingEntries = new ArrayList();
    }

    /**
     * Creates a new user, according to the details inputted by the user.
     * @param name user's name
     * @param surname user's surname
     * @param username user's username
     * @param password user's password.
     */
    public void writeDetails(String name, String surname, String username, String password){
        String entry = name + "-" + surname + "-" + username + "-" + password;
        writeToDatabase(entry);

        createUserFile(name, surname, username);


    }

    /**
     * Records, appends to the database : writes to the database file
     * the user login, password, surname and name.
     * Stored in database/database.txt
     *
     * @param entry
     */
    public void writeToDatabase(String entry){

        try (BufferedReader currentReader = new BufferedReader(new FileReader("database/database.txt"))) {

            String currentEntry = null;

            while ((currentEntry = currentReader.readLine()) != null) {
                existingEntries.add(currentEntry);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            fileWriter = new FileWriter("database/database.txt");
            writer = new BufferedWriter(fileWriter);
            for (String currentEntry : existingEntries){
                writer.append(currentEntry + "\n");
            }
            writer.append(entry + "\n");
            writer.flush();
        }

        catch (IOException e) {
            e.printStackTrace();
            System.out.println("System Error while doing database operations.");
        }

        finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("System Error while doing database operations.");
            }
        }
    }

    /**
     * Creates a new user profile. Writes initial settings and information of the user.
     * Stores it in users/ [username].user
     *
     * @param name
     * @param surname
     * @param userName
     */
    public void createUserFile(String name, String surname, String userName){
        String fileName = userName;
        String firstLine = name.concat("-").concat(surname).concat("-").concat(userName);
        String pathToFile = "users/".concat(fileName).concat(".user");
        File userFile = new File(pathToFile);

        if (! userFile.exists()){
            //create file

            try {
                fileWriter = new FileWriter(pathToFile);
                writer = new BufferedWriter(fileWriter);

                writer.append(firstLine);
                writer.append("\nfavs : ");


            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        else{
            System.out.println("ERROR !");
        }
    }

    /**
     * gets the database entries of each login and their respective password.
     * Used to check if login is correct.
     *
     * @return
     */
    public ArrayList<UserDetails> getDatabaseEntries(){
        ArrayList<UserDetails> users = new ArrayList<>();

        try {
            reader = new BufferedReader(new FileReader("database/database.txt"));

            String entry = reader.readLine();

            while (entry != null){

                if (entry != null) {

                    String[] details = entry.split("-");
                    UserDetails user = new UserDetails(details[0], details[1], details[2], details[3]);
                    users.add(user);
                    entry = reader.readLine();
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("System Error while doing database operations.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading Line");
        }

        return users;
    }

    /**
     * returns userProfile. By UserProfile in this program it is meant the data on
     * how many clicks there are on each borough click by the user since the
     * creation of the user.
     *
     * @param username
     * @return
     */
    public HashMap<String, Integer> getUserProfile(String username){

        HashMap<String, Integer> clickCount = new HashMap<>();

        String path = "users/".concat(username).concat(".user");
        try {
            reader = new BufferedReader(new FileReader(path));
            reader.readLine();
            reader.readLine();
            String borough;

            while ((borough = reader.readLine()) != null){
                String[] currentLine = borough.split("-");
                clickCount.put(currentLine[0], Integer.parseInt(currentLine[1]));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("User Not Found, Error.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("line not found.");
        }
        return clickCount;
    }

    /**
     * Writes to the user profile file. Writes the click count of the user,
     * according to the user from username.
     *
     * @param username
     * @param clickCount
     */
    public void writeToProfile(String username, HashMap<String, Integer> clickCount){

        String idLine = username;
        String favLine = "fav : ";

        String path = "users/".concat(username).concat(".user");
        Boolean empty = false;

        try {
            reader = new BufferedReader(new FileReader(path));
            idLine = reader.readLine();
            favLine = reader.readLine();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            fileWriter = new FileWriter(path);
            writer = new BufferedWriter(fileWriter);
            writer.append(idLine + "\n");
            writer.append(favLine + "\n");

            for (String borough : clickCount.keySet()){
                String line = borough.concat("-").concat(clickCount.get(borough)+"\n");
                writer.append(line);
            }
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * gets the user ID data : surname, name.
     * Identifies the user by their username.
     *
     * @param username
     * @return
     */
    public String[] getUserDetails(String username){

        String[] details = null;

        String path = "users/".concat(username).concat(".user");
        try {
            reader = new BufferedReader(new FileReader(path));
            details = reader.readLine().split("-");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("User Not Found, Error.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("line not found.");
        }
        return details;
    }

    /**
     * gets the most clicked borough from the user by their username.
     * @param username
     * @return
     */
    public String getMostClickedBorough(String username){
        HashMap<String, Integer> clickCount = getUserProfile(username);

        String mostClicked = null;
        int mostClickedCount = 0;

        for (String borough : clickCount.keySet()){
            if (clickCount.get(borough) > mostClickedCount){
                mostClicked = borough;
                mostClickedCount = clickCount.get(borough);
            }
        }

        return mostClicked;
    }
}
