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
 * Write a description of JavaFX class Borough here.
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
    
    public Borough(String name, int x, int y)
    {
        this.name = name;
        this.x = x;
        this.y = y;
        this.nameId = name.replaceAll("\\s+","");
    }
    
    public String getName(){
        return name;
    }
    
    public String getNameID(){
        return nameId;
    }
    
    public String getFullName(){
        if (name.length() >7){
            return ("  " + name.toUpperCase());
        }
        else{
            return ("  " + name.toUpperCase());
        }
    }
    
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
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
}
