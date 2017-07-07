package lab9;

import lab9.Flight;
import lab9.GMTtime;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class ShortestFlight {

    private static Map<String, Integer> airportTimeZoneMap = new HashMap<String, Integer>();
    private static List<Flight> flights = new ArrayList<Flight>();

    private static void findBestRoute(String airportOrigin,
				      String airportDestination,
				      GMTtime leaveTime) {
    	HashMap<String, List<Flight> > mapAdjacent = new HashMap<String, List<Flight>>();
    		
    	System.out.println(flights.get(0).toString());
    	for(int i=0; i < flights.size(); i++){
    		
    		if (!mapAdjacent.containsKey(flights.get(i).departureAirportCode)){
    			ArrayList<Flight> listofFlights = new ArrayList<Flight>();
    			listofFlights.add(flights.get(i));
    			mapAdjacent.put(flights.get(i).departureAirportCode, listofFlights);
    		}
    		else {
    			List<Flight> temp_list = new ArrayList<Flight>();
    			temp_list = mapAdjacent.get(flights.get(i).departureAirportCode);
    			mapAdjacent.put(flights.get(i).departureAirportCode, temp_list);
    		}
    }


		Map<String, Integer> minutesToAirport = new HashMap<String, Integer>();
                Comparator<String> airportComparator = new Comparator<String>();
                public int compare(String airport1, String airport2){
                  if (!minutesToAirport.containsKey(airport1)){
                    if (!minutesToAirport.containsKey(airport2)){
                      return 0;
                    }
                    
                      return 1;
                    
                  }
                  else{
                    if (!minutesToAirport.containsKey(airport2)){
                        return -1;
                    }
                    
                      return(minutesToAirport.get(airport1)-minutesToAirport.get(airport2));
                    
                  }
                }

 
 




    	























	// TODO: Implement here your code to find the earliest arrival route from
	// origin to destination.
	// Remember the constraints: the flight must leave no eariler than 1 hour
	// after leave time, it must have at least one layover, and each layover
	// needs to be at least 1 hour.
    }

    public static void main(String[] args) {
	if (args.length != 4) {
	    System.out.println("Usage: AirportOrigin AirportDestination Time A/P");
	    return;
	}
	readAirportData();
	readFlightData(airportTimeZoneMap);

	String airportOrigin = args[0];
	String airportDestination = args[1];
	GMTtime leaveTime = new GMTtime(Integer.parseInt(args[2]),
					airportTimeZoneMap.get(airportOrigin),
					args[3].startsWith("A"));

	findBestRoute(airportOrigin, airportDestination, leaveTime);
    }

    private static void readAirportData() {
	BufferedReader r;
	try {
	    InputStream is = new FileInputStream("input/airport-data.txt");
	    r = new BufferedReader(new InputStreamReader(is));
	} catch (IOException e) {
	    System.out.println("IOException while opening airport-data.txt\n" + e);
	    return;
	}
	try {
	    String nextline = r.readLine();
	    StringTokenizer st = new StringTokenizer(nextline);
	    int numAirports = Integer.parseInt(st.nextToken());
	    for (int i = 0; i < numAirports; i++){
		nextline = r.readLine();
		st = new StringTokenizer(nextline);
		String airportCode = st.nextToken();
		int gmtConv = Integer.parseInt(st.nextToken());
		airportTimeZoneMap.put(airportCode, gmtConv);
	    }
	} catch (IOException e) {
	    System.out.println("IOException while reading sequence from " +
			       "airport-data.txt\n" + e);
	    return;
	}
    }

    private static void readFlightData(Map<String, Integer> airportTimeZoneMap) {
	BufferedReader r;
	try {
	    InputStream is = new FileInputStream("input/flight-data.txt");
	    r = new BufferedReader(new InputStreamReader(is));
	} catch (IOException e) {
	    System.out.println("IOException while opening flight-data.txt\n" + e);
	    return;
	}
	try {
	    String nextline = r.readLine();
	    while (nextline != null && !nextline.trim().equals("")) { // not end of file or an empty line
		StringTokenizer st = new StringTokenizer(nextline);
		Flight flight = new Flight();
		flight.airline = st.nextToken();
		flight.flightNum = Integer.parseInt(st.nextToken());
		flight.departureAirportCode = st.nextToken();
		int localDepartureTime = Integer.parseInt(st.nextToken()); 
		boolean amDeparture = st.nextToken().equals("A");
		flight.departureTime = new GMTtime(localDepartureTime, airportTimeZoneMap.get(flight.departureAirportCode), amDeparture);
		flight.arrivalAirportCode = st.nextToken();
		int localArrivalTime = Integer.parseInt(st.nextToken());
		boolean amArrival = st.nextToken().equals("A");
		flight.arrivalTime = new GMTtime(localArrivalTime, airportTimeZoneMap.get(flight.arrivalAirportCode), amArrival);
		nextline = r.readLine();
		flights.add(flight);
	    }
	} catch (IOException e) {
	    System.out.println("IOException while reading sequence from " +
			       "flight-data.txt\n" + e);
	    return;
	}
    }
}
