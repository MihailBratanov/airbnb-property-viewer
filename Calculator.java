import java.util.*;
import javafx.collections.ObservableList;

/**
 * This class is where all statistical computations take place.
 *
 * @author Mihail Bratanov
 * @version 0.1
 */
public class Calculator {
    // instance variables - replace the example below with your own
    private int x;
    private AirbnbDataLoader loader = new AirbnbDataLoader();
    private ArrayList<AirbnbListing> data = new ArrayList<>();

    /**
     * Constructor for objects of class Calculator
     */
    public Calculator() {
        // initialise instance variables
        x = 0;
    }

    public double calculateAverageViews(ObservableList<AirbnbListing> dataToDoStatsOn) {
        //dataToDoStatsOn.addAll(data);
        double current = 0;
        double average;
        // Counts number of button clicks and shows the result on a label
        for (AirbnbListing listing : dataToDoStatsOn) {
            current += listing.getNumberOfReviews();
        }
        average = current / dataToDoStatsOn.size();

        return average;
    }

    public int calculateAvailability(ObservableList<AirbnbListing> dataToDoStatsOn) {
        int totalAvailableProperties = 0;
        for (AirbnbListing listing : dataToDoStatsOn) {
            if (listing.getAvailability365() > 0)
                totalAvailableProperties += 1;
        }
        return totalAvailableProperties;
    }

    public int calculateRoomType(ObservableList<AirbnbListing> dataToDoStatsOn) {
        int propertyCount = 0;
        for (AirbnbListing listing : dataToDoStatsOn) {
            if (listing.getRoom_type() != "Private room")
                propertyCount += 1;
        }
        return propertyCount;
    }

    public String calculateMostExpensiveBorough(ObservableList<AirbnbListing> dataToDoStatsOn) {
        ArrayList<String> boroughs = new ArrayList<>();
        ArrayList<String> filteredBoroughs = new ArrayList<>();

        //System.out.println(dataToDoStatsOn);

       // for (AirbnbListing listing : dataToDoStatsOn) {
        //    boroughs.add(listing.getNeighbourhood());
        //}



//        //for (int i = 0; i < boroughs.size(); i++) {
//            for (int j = i + 1; j < boroughs.size(); j++) {
//                if (!boroughs.get(i).equals(boroughs.get(j)))
//                    filteredBoroughs.add(boroughs.get(j));
//            }
//
//        }

        int minimumStayListing = 0;
        int priceListing = 0;
        int totalPriceForListing = 0;
        HashMap<String, Integer> priceListings = new HashMap<>();

        for (AirbnbListing listing : dataToDoStatsOn) {
            minimumStayListing = listing.getMinimumNights();
            priceListing = listing.getPrice();
            totalPriceForListing += minimumStayListing * priceListing;
            priceListings.put(listing.getNeighbourhood(), totalPriceForListing);

        }

        System.out.println("priceListing : " + priceListings);

        int max = 0; //Collections.max(priceListings.values());
        String mostExpensiveBorough=" ";

        for (String listing : priceListings.keySet()){
            if (priceListings.get(listing) > max){
                System.out.println(priceListings.get(listing));
                max = priceListings.get(listing);
                mostExpensiveBorough=listing;
            }
        }

        System.out.println("calc says : "+max);

        return mostExpensiveBorough;
    }

    public HashMap<Integer, String> calculateMostRecentListing(ObservableList<AirbnbListing> dataToDoStatsOn) {
        ArrayList<String> dates = new ArrayList<>();
        ArrayList<String> ids = new ArrayList<>();

        HashMap<Integer, String> datesAndNames = new HashMap<>();
        for (AirbnbListing listing : dataToDoStatsOn) {
            if (!listing.getLastReview().equals("")) {

                dates.add(listing.getLastReview());
                ids.add(listing.getId());

            }
        }for (String date : dates) {

            String[] dateToNumber = date.replace("/", " ").split(" ");
            String day = dateToNumber[0];
            String month = dateToNumber[1];
            String year = dateToNumber[2];
            String finalDate = year + month + day;
            //replaceAll("\\s+", "");
            int numberFromDate = Integer.parseInt(finalDate);
            for (String id : ids) {
                datesAndNames.put(numberFromDate, id);
            }

        }
        return datesAndNames;

    }

    public HashMap<String, Integer> calculateBusiestMonth(ObservableList<AirbnbListing> dataToDoStatsOn) {
        ArrayList<String> dates = new ArrayList<>();
        List<String> months = new ArrayList<>();
        HashMap<String, Integer> datesAndCounts = new HashMap<>();
        for (AirbnbListing listing : dataToDoStatsOn) {
            if (!listing.getLastReview().equals("")) {

                dates.add(listing.getLastReview());

            }
        }
        for (String date : dates) {

            String[] dateToNumber = date.split("/");

            String month = dateToNumber[1];
            //int monthNum = Integer.parseInt(month);
            months.add(month);

        }
        int counter = 0;
        Collections.sort(months);
        for (int i = 0; i < months.size(); i++) {
            for (int j = 1; j < months.size(); j++) {
                if (months.get(i) == months.get(j)) {
                    counter += 1;
                } else {
                    datesAndCounts.put(months.get(i), counter);
                    counter = 0;

                }

        }


        }
        return datesAndCounts;

    }

    public HashMap<String, Integer> calculateMostPopulatedBorough(ObservableList<AirbnbListing> dataToDoStatsOn) {
        /**
        ArrayList<String> boroughs = new ArrayList<>();
        ArrayList<String> filteredBoroughs = new ArrayList<>();
        for (AirbnbListing listing : dataToDoStatsOn) {
            boroughs.add(listing.getNeighbourhood());
        }

         for (int i = 0; i < boroughs.size(); i++) {
            for (int j = i + 1; j < boroughs.size(); j++) {
                if (!boroughs.get(i).equals(boroughs.get(j)))
                    filteredBoroughs.add(boroughs.get(j));
                System.out.println(filteredBoroughs);
            }


            }
            return null;
        }*/

        HashMap<String, Integer> boroughPropertyCount = new HashMap<>();

        for(AirbnbListing listing : dataToDoStatsOn){
            String borough = listing.getNeighbourhood();

            Boolean hasBorough = boroughPropertyCount.containsKey(borough);

            if (! hasBorough){
                boroughPropertyCount.put(borough, 1);
            }
            else if (hasBorough){
                int count = boroughPropertyCount.get(borough);
                count += 1;
                boroughPropertyCount.replace(borough, count);
            }


        }

        return boroughPropertyCount;
    }

    }

