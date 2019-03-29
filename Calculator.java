import java.util.*;
import javafx.collections.ObservableList;

/**
 * This class is where all statistical computations take place.
 */
public class Calculator {

    /**
     * Constructor for objects of class Calculator
     */
    public Calculator() {
    }

    /**
     * A methid to calculate the average reviews
     * @param dataToDoStatsOn
     * @return the average
     */
    public double calculateAverageViews(ObservableList<AirbnbListing> dataToDoStatsOn) {
        double current = 0;
        double average;
        for (AirbnbListing listing : dataToDoStatsOn) {
            current += listing.getNumberOfReviews();
        }
        average = current / dataToDoStatsOn.size();

        return average;
    }

    /**
     * A method to get the amount of available properties
     * @param dataToDoStatsOn
     * @return amount of available properties
     */
    public int calculateAvailability(ObservableList<AirbnbListing> dataToDoStatsOn) {
        int totalAvailableProperties = 0;
        for (AirbnbListing listing : dataToDoStatsOn) {
            if (listing.getAvailability365() > 0)
                totalAvailableProperties += 1;
        }
        return totalAvailableProperties;
    }

    /**
     *A method to calculate the amount of properties which are not private rooms
     * @param dataToDoStatsOn
     * @return the amount of properties
     */
    public int calculateRoomType(ObservableList<AirbnbListing> dataToDoStatsOn) {
        int propertyCount = 0;
        for (AirbnbListing listing : dataToDoStatsOn) {
            if (listing.getRoom_type() != "Private room")
                propertyCount += 1;
        }
        return propertyCount;
    }

    /**
     * A method that calculates the most expensive borough
     * @param dataToDoStatsOn
     * @return name of borough
     */
    public String calculateMostExpensiveBorough(ObservableList<AirbnbListing> dataToDoStatsOn) {

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


        int max = 0;
        String mostExpensiveBorough=" ";

        for (String listing : priceListings.keySet()){
            if (priceListings.get(listing) > max){
                max = priceListings.get(listing);
                mostExpensiveBorough=listing;
            }
        }


        return mostExpensiveBorough;
    }

    /**
     * A method to calculate the most recent listing
     * @param dataToDoStatsOn
     * @return a hashmap with the listing id's and dates
     */
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

    /**
     * Calculates the month with most listings
     * @param dataToDoStatsOn
     * @return a hashmap of each month mapped to the amount of listings
     */
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

    /**
     * Calculates the borough with most listings
     * @param dataToDoStatsOn
     * @return a hashmap of the borough mapped to the count of listings
     */
    public HashMap<String, Integer> calculateMostPopulatedBorough(ObservableList<AirbnbListing> dataToDoStatsOn) {

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

