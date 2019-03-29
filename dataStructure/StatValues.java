package dataStructure;

 

/**
 *  class StatValues : a data structure used in the program to store the statistic name
 *  and it's value. It works just like a tuple.
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
public class StatValues {

    private String name;
    private String value;

    /**
     * Comstructor of Stats Value, takes in name and value and stores it in this object.
     *
     * @param name name of Statistic
     * @param value value of Statistic
     */
    public StatValues(String name, String value){
        this.name = name;
        this.value = value;
    }

    /**
     * gets the name of the statistic
     * @return
     */
    public String getName(){
        return name;
    }

    /**
     * gets the value of the statistic
     * @return
     */
    public String getValue(){
        return value;
    }
}
