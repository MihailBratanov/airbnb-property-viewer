import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    private Database databaseTest = new Database();
    private ArrayList actualDatabaseEntries = new ArrayList();


    @org.junit.jupiter.api.Test
    void getDatabaseEntries() {
        UserDetails user = new UserDetails("crystal", "zou", "czou123","czou123123");
        UserDetails mdb1 = new UserDetails("Mihail", "Bratanov", "mdb","mdb123456");
        actualDatabaseEntries.add(user);
        assertEquals(databaseTest.getDatabaseEntries().get(0).toString(), actualDatabaseEntries.get(0).toString());
    }

    @org.junit.jupiter.api.Test
    void getUserProfile() {
        assertEquals(databaseTest.getUserProfile("czou123").get("Camden"),2);
        assertEquals(databaseTest.getUserProfile("czou123").get("Bromley"),0);
        assertEquals(databaseTest.getUserProfile("czou123").get("Kensington and Chelsea"),1);

    }

    @org.junit.jupiter.api.Test
    void getUserDetails() {
        String[] expected = new String[]{"crystal", "zou", "czou123"};
        assertEquals(databaseTest.getUserDetails("czou123")[0].toString(), expected[0].toString());
        assertEquals(databaseTest.getUserDetails("czou123")[1].toString(), expected[1].toString());
        assertEquals(databaseTest.getUserDetails("czou123")[2].toString(), expected[2].toString());
    }

    @org.junit.jupiter.api.Test
    void getMostClickedBorough() {
        assertEquals(databaseTest.getMostClickedBorough("czou123"),"Camden");
    }
}