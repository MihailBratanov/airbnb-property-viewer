import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.stage.Stage;
import javafx.scene.paint.*;
import javafx.geometry.Pos;

/**
 * Write a description of JavaFX class Viewer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

public class MapViewer extends Panel
{

    private Label myLabel = new Label("0");
    private Stage stage;
    VBox root;
    private double width;
    private double height;
    private double windowWidth;
    private double windowHeight;
    
    public MapViewer(int lowerLimit, int upperLimit){
        root = new VBox();
        
        root.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));

        StackPane stackpane = new StackPane();
        

        Label imageLabel = LoadImage();
        
        windowWidth = root.getMinWidth();
        windowHeight = root.getMinHeight();
        
        Button enfi = new Button("Enfield");
        Button barn = new Button("Barnet");
        Button hrgy = new Button("Haringey");
        Button walt = new Button("Waltham");
        Button harr = new Button("H&W");
        Button brent = new Button("Brent Cross");
        Button cam = new Button("Camden");
        Button isli = new Button("Islington");
        Button hack = new Button("Hackney");
        Button redb = new Button("Red Bridge");
        Button hav = new Button("Havering");
        
        
        Button hill = new Button("Hill");
        Button eali = new Button("Ealing");
        Button kens = new Button("Kens");
        Button wstm = new Button("Westm");
        Button towh = new Button("Tower hill");
        Button newh = new Button("Newh");
        Button bark = new Button("Bark");

        Button houn = new Button("Hounslow");
        Button hamm = new Button("Hamm");
        Button wand = new Button("Wand");
        Button city = new Button("City");
        Button gwch = new Button("Gwch");
        Button bexl = new Button("Bexl");
        
        Button rich = new Button("Rich");
        Button mert = new Button("Mert");
        Button lamb = new Button("Lambeth");
        Button sthw = new Button("Southwark");
        Button lews = new Button("Lewisham");
        Button king = new Button("Kingston");
        Button sutt = new Button("Sutton");
        Button croy = new Button("Croydon");
        Button brom = new Button("Bromley");
        
        GridPane gridPane = new GridPane();
        
        for (int i = 0; i < 15; i ++){
            RowConstraints row = new RowConstraints(height/15);
            gridPane.getRowConstraints().add(row);
        }
        
        for (int i = 0; i < 16; i ++){
            ColumnConstraints col = new ColumnConstraints(width/16);
            gridPane.getColumnConstraints().add(col);
        }

        gridPane.add(enfi,8,1);
        
        gridPane.add(barn,5,3);
        gridPane.add(hrgy,7,3);
        gridPane.add(walt,9,3);
        
        gridPane.add(harr,2,5);
        gridPane.add(brent,4,5);
        gridPane.add(cam,6,5);
        gridPane.add(isli,8,5);
        gridPane.add(hack,10,5);
        gridPane.add(redb,12,5);
        gridPane.add(hav,14,5);
        
        gridPane.add(hill,1,7);
        gridPane.add(eali,3,7);
        gridPane.add(kens,5,7);
        gridPane.add(wstm,7,7);
        gridPane.add(towh,9,7);
        gridPane.add(newh,11,7);
        gridPane.add(bark,13,7);
        
        gridPane.add(houn,2,9);
        gridPane.add(hamm,4,9);
        gridPane.add(wand,6,9);
        gridPane.add(city,8,9);
        gridPane.add(gwch,10,9);
        gridPane.add(bexl,12,9);
        
        gridPane.add(rich,3,11);
        gridPane.add(mert,5,11);
        gridPane.add(lamb,7,11);
        gridPane.add(sthw,9,11);
        gridPane.add(lews,11,11);
        
        gridPane.add(king,4,13);
        gridPane.add(sutt,6,13);
        gridPane.add(croy,8,13);
        gridPane.add(brom,10,13);

        
        stackpane.getChildren().addAll(imageLabel, gridPane);
        FlowPane flowPane = new FlowPane();
        flowPane.getChildren().addAll(stackpane);
        root.getChildren().add(flowPane);
    }
    
    public Pane getPanel(){
        return root;
    }
    
    private Label LoadImage(){
        Label imageLabel = new Label();        
        String imagePath = "boroughs.png";
        Image image = new Image(imagePath);

        ImageView imageViewer = new ImageView(image);

        width =  image.getWidth();
        width = width / 6;
        height =  image.getHeight(); 
        height = height / 6;

        imageViewer.setPreserveRatio(true);
        imageViewer.setFitHeight(height);
        imageViewer.setFitWidth(width);
        imageViewer.setSmooth(true);
        imageLabel.setGraphic(imageViewer);
       
        return imageLabel;
    }
}
