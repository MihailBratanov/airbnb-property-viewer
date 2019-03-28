import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class Database {

    File database;
    FileWriter fileWriter;
    BufferedWriter writer;
    BufferedReader reader;
    ArrayList<String> existingEntries;

    public Database(){
        //database = new File("database.txt");
        existingEntries = new ArrayList();
    }

    public void writeDetails(String name, String surname, String username, String password){
        String entry = name + "-" + surname + "-" + username + "-" + password;
        writeToDatabase(entry);

        createUserFile(username);

    }

    public void writeToDatabase(String entry){

        try (BufferedReader currentReader = new BufferedReader(new FileReader("database/database.txt"))) {

            String currentEntry = null;

            //existingEntries.add("Firstname - Surname - UserName - Password");

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

    public void createUserFile(String userName){
        String fileName = userName;
        String pathToFile = "users/".concat(fileName).concat(".user");
        File userFile = new File(pathToFile);

        if (! userFile.exists()){
            //create file

            try {
                fileWriter = new FileWriter(pathToFile);
                writer = new BufferedWriter(fileWriter);

                writer.append(fileName);
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

    public ArrayList<UserDetails> getDatabaseEntries(){
        ArrayList<UserDetails> users = new ArrayList<>();

        try {
            reader = new BufferedReader(new FileReader("database/database.txt"));

            String entry = reader.readLine();

            while (entry != null){
                //System.out.println(entry);

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
        System.out.println(clickCount);
        return clickCount;
    }

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
                String line = borough.concat("-").concat(String.valueOf(clickCount.get(borough))+"\n");
                //System.out.println(line);
                writer.append(line);
            }
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
