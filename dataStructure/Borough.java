package dataStructure;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Class Borough : a data Structure that is used to store
 * the name of a borough with its coordinates on the GridPane Map.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Borough
{
    private String name;
    private int x;
    private int y;

    private String nameId;

    /**
     * Constructor of Borough, creates a borough object that stores the name, coordinates x and y
     * Generates a Name ID that is later used to compare between boroughs.
     *
     * @param name borough name
     * @param x gridpane coordinate x
     * @param y gridpane coordinate y
     */
    public Borough(String name, int x, int y)
    {
        this.name = name;
        this.x = x;
        this.y = y;
        this.nameId = name.replaceAll("\\s+","");

    }

    /**
     * getter method : gets name of borough
     * @return
     */
    public String getName(){
        return name;
    }

    /**
     * getter method : gets the name as a form of ID
     * @return
     */
    public String getNameID(){
        return nameId;
    }

    /**
     * getter method : gets the name for the purpose of printing it out.
     * Adds spacing and trims the name.
     * @return
     */
    public String getFullName(){
        if (name.length() >7){
            return ("  " + name.toUpperCase());
        }
        else{
            return ("  " + name.toUpperCase());
        }
    }

    /**
     * getter method : gets a shorter version of the name
     * purpose : to be displayed in the map, when the full name can't be displayed
     * @return
     */
    public String getShortName(){
        String shortName;
        
        if (name.length() > 7){
            shortName = name.substring(0,7);
        }
        else {
            shortName = name;
        }
        
        shortName = "   "  + shortName.toUpperCase();
        
        return shortName;
    }

    /**
     * gets the x coordinate of this borough on a gridpane
     * @return
     */
    public int getX(){
        return x;
    }

    /**
     * gets the y coordinate of this borough on a gridpane
     * @return
     */
    public int getY(){
        return y;
    }
}
