import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * Test class for Airbnb Property Viewer.
 * This class tests for the class Database of the project.
 * 
 * @author Haiyun Zou, Ka Wang Sin, Mihail Bratanov and Terry Phung
 * @version 2019.03.29
 *
 * 18-19 4CCS1PPA Programming Practice and Applications
 * Term 2 Coursework 4 - London Property Marketplace
 * Created by Haiyun Zou, Ka Wang Sin, Mihail Bratanov and Terry Phung
 * Student ID: 1828556, 1850162, 1838362, 1833386
 * k-number: k1895418, k1802265, k1888765, k1895389
 *
 */
public class DatabaseTest
{
    private Database databaseTest = new Database();
    private ArrayList actualDatabaseEntries = new ArrayList();

    @Test
    public void getDatabaseEntries() {
        UserDetails user = new UserDetails("crystal", "zou", "czou123","czou123123");
        UserDetails mdb1 = new UserDetails("Mihail", "Bratanov", "mdb","mdb123456");
        actualDatabaseEntries.add(user);
        assertEquals(databaseTest.getDatabaseEntries().get(0).toString(), actualDatabaseEntries.get(0).toString());
    }
    
    @Test
    public void getUserProfile() {
        assertEquals(databaseTest.getUserProfile("czou123").get("Camden").toString(),"2");
        assertEquals(databaseTest.getUserProfile("czou123").get("Bromley").toString(), "0");
        assertEquals(databaseTest.getUserProfile("czou123").get("Kensington and Chelsea").toString(),"1");
    }
    
    @Test
    public void getUserDetails() {
        String[] expected = new String[]{"crystal", "zou", "czou123"};
        assertEquals(databaseTest.getUserDetails("czou123")[0].toString(), expected[0].toString());
        assertEquals(databaseTest.getUserDetails("czou123")[1].toString(), expected[1].toString());
        assertEquals(databaseTest.getUserDetails("czou123")[2].toString(), expected[2].toString());
    }
    
    @Test
    public void getMostClickedBorough() {
        assertEquals(databaseTest.getMostClickedBorough("czou123"),"Camden");
    }

}

