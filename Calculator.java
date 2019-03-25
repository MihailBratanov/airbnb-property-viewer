import java.util.*;
import javafx.collections.ObservableList;

/**
 * This class is where all statistical computations take place.
 *
 * @author Mihail Bratanov
 * @version 0.1
 */
public class Calculator
{
    // instance variables - replace the example below with your own
    private int x;
    private AirbnbDataLoader loader=new AirbnbDataLoader();
    private ArrayList<AirbnbListing> data=new ArrayList<>();
    /**
     * Constructor for objects of class Calculator
     */
    public Calculator()
    {
        // initialise instance variables
        x = 0;
    }

    public double calculateAverageViews(ObservableList<AirbnbListing> dataToDoStatsOn)
    {
        //dataToDoStatsOn.addAll(data);
        double current=0;
        double average;
        // Counts number of button clicks and shows the result on a label
        for(AirbnbListing listing :dataToDoStatsOn){
            current+=listing.getNumberOfReviews();
        }
        average=current/dataToDoStatsOn.size();

        return average;
    }

    public int calculateAvailability(ObservableList<AirbnbListing> dataToDoStatsOn)
    {
        int totalAvailableProperties=0;
        for(AirbnbListing listing :dataToDoStatsOn){
            if( listing.getAvailability365()>0)
                totalAvailableProperties+=1;
        }
        return totalAvailableProperties;
    }

    public int calculateRoomType(ObservableList<AirbnbListing> dataToDoStatsOn)
    {
        int propertyCount=0;
        for(AirbnbListing listing :dataToDoStatsOn){
            if(listing.getRoom_type()!="Private room")
                propertyCount+=1;
        }
        return propertyCount;
    }

    public HashMap<String,Integer>  calculateMostExpensiveBorough(ObservableList<AirbnbListing> dataToDoStatsOn)
    {
        ArrayList<String> boroughs= new ArrayList<>();
        ArrayList<String> filteredBoroughs=new ArrayList<>();
        for (AirbnbListing listing :dataToDoStatsOn){
            boroughs.add(listing.getNeighbourhood());
        }
        
        for(int i=0;i<boroughs.size();i++){
            for(int j=i+1;j<boroughs.size();j++){
                if(!boroughs.get(i).equals(boroughs.get(j)))
                    filteredBoroughs.add(boroughs.get(j));
            }

        }

        int minimumStayListing=0;
        int priceListing=0;
        int totalPriceForListing=0;
         HashMap<String,Integer> priceBoroughs=new HashMap<>();
        for(AirbnbListing listing :dataToDoStatsOn){
            for(String borough :filteredBoroughs){
                if(listing.getNeighbourhood().equals(borough)){
                    minimumStayListing=listing.getMinimumNights();
                    priceListing=listing.getPrice();
                    totalPriceForListing+=minimumStayListing*priceListing;
                    priceBoroughs.put(borough,totalPriceForListing);
                    
                }
            }
            
        }
        return priceBoroughs;
    }
}
