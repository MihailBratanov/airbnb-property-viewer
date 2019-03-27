import java.io.*;
import java.util.ArrayList;

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
    }

    public void writeToDatabase(String entry){

        try (BufferedReader currentReader = new BufferedReader(new FileReader("database.txt"))) {

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
            fileWriter = new FileWriter("database.txt");
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

    public ArrayList<UserDetails> getDatabaseEntries(){
        ArrayList<UserDetails> users = new ArrayList<>();

        try {
            reader = new BufferedReader(new FileReader("database.txt"));

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
}