package dataStructure;

/**
 *  class StatValues : a data structure used in the program to store the statistic name
 *  and it's value. It works just like a tuple.
 *
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
