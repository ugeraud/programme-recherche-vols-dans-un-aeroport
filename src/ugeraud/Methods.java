/**
 * 
 */
package ugeraud;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.sun.net.httpserver.Authenticator.Result;

import utilitaires.TabFileReader;

/**
 * @author geraud
 *
 */
public class Methods {
	
	private static ArrayList<String> result = new ArrayList<String>();
	
	private static ArrayList<String> resultTwo = new ArrayList<String>();
	
	private static String chaine = new String(" ");
	
	private static String chaineTwo = new String(" ");
	
	// string for saving user departure initialized to an empty chain
	private static String departure = new String(" ");
	
	// string for saving user arrival initialized to an empty chain
	
	private static String arrival = new String(" ");
	
	private static String filename= new String("departs.txt");
	
	private static Scanner sc = new Scanner(System.in);
	
	/**
	 * 
	 * @return departure
	 */
	public static String getDeparture() {
		return departure;
	}

	/**
	 * 
	 * @param departure (user departure)
	 */
	public static void setDeparture(String departure) {
		Methods.departure = departure;
	}

	/**
	 * 
	 * @return arrival
	 */
	public static String getArrival() {
		return arrival;
	}

	/**
	 * 
	 * @param arrival (user arrival)
	 */
	public static void setArrival(String arrival) {
		Methods.arrival = arrival;
	}
	
	/**
	 * static method for user menu
	 */
	public static void userMenu() {
		System.out.println("\t==========================================================================");
		System.out.println("\t\t Bienvenue sur la plateforme de recherche de vol en ligne");
		System.out.println("\t==========================================================================");
		System.out.println();
		System.out.println("\t 1 : Affichage de tous les vols directs possibles à partir d'un départ");
		System.out.println("\t 2 : Affichage vols reliant deux destinations avec au moins 1H d'escale");
		System.out.println("\t 3 : Affichage des aéroports désservis dans la journée");
		System.out.println("\t 4 : Liste aphabétique des aéroports désservir dans la journée");
		System.out.println("\t 5 : Liste des vols directs ou avec escales");
		System.out.println("\t 6 : Quitter/Annuler\n\n");
		System.out.println("\n");
	}
	
	/**
	 * header for the result
	 */
	public static void header() {
		System.out.println( "+--------------------+---------------------+---------------------+---------------------+---------------------+" );
        System.out.println( "|	N° Vol                Départs             Arrivés            Heure départs         Heure d'arrivés   |");
        System.out.println( "+--------------------+---------------------+---------------------+---------------------+---------------------+" );
	}
	
	/**
	 * footer for the result
	 */
	public static void footer() {
		System.out.println( "+--------------------+---------------------+---------------------+---------------------+---------------------+" );
	}
	
	/**
	 * static method to get user choice
	 * @return int (user choice)
	 */
	public static int choice() {
		System.out.print("Quelle est votre choix ? ");
		int choice = sc.nextInt();
		if(choice < 0 | choice > 7) {	
			return -1;
		};
		return choice;
	}
	
	
	/**
	 * 
	 * @param hour (hour to convert in minutes)
	 * @return int (hour in minute)
	 */
	public static int toMinutes(String hour) {
		String [] tokens = hour.split(":");
		int h = Integer.parseInt(tokens[0]);
		int m = Integer.parseInt(tokens[1]);
		return h*60+m;
	}
	
	/**
	 * check if a chain already exist in a list array
	 * @param T (array containing strings)
	 * @param val (value to search in the array T)
	 * @return boolean 
	 */
	public static boolean contain(ArrayList<String> T, String val) {
		for (String string : T) {
			return (string.equalsIgnoreCase(val));
		}
		return false;
	 }
	
	/**
	 * static method for asking departure city
	 * @return string
	 */
	public static String demanderVilleDepart() {
		System.out.print("Quelle est votre ville de départ ? ");
		sc.nextLine();
		String ville = sc.nextLine();
		if(ville.length() < 3) {	
			return new String("Saisie erronee : saisie trop petite ou vide. Merci de réessayer !");
		}else {	
			return ville.toUpperCase();
		}
	}
	
	
	/**
	 * static method for asking arrival city
	 * @return string
	 */
	public static String demandeVilleArrive() {
		System.out.print("Quelle est votre ville d'arrivé ? ");
		String arrive = sc.nextLine();
		if(arrive.length() < 3) {
			return new String("Merci de renseigner une ville valide.\n\n");
		}else {
			return arrive.toUpperCase();
		}
	}
	
	
	/**
	 * static method for first choice
	 * @param departure (user departure)
	 * @param paramExist (user departure exist or not)
	 * @return int
	 */
	public static int allDirectFlights(String departure,boolean paramExist) {
		Methods.result.clear();
		if(null == departure && paramExist)
			Methods.setDeparture(Methods.demanderVilleDepart());
		else
			Methods.setDeparture(departure);
		if(Methods.getDeparture() != null) {
			if(Methods.getDeparture().length() > 10 ) {
				System.out.println(Methods.getDeparture()); 
			}else {
				TabFileReader.readTextFile(Methods.filename,'\t',"data");
				for (int i=0;i<TabFileReader.nrow();i++){
					if(null == departure && !paramExist)
						Methods.setDeparture(TabFileReader.wordAt(i, 1));
					if(TabFileReader.wordAt(i, 1).equalsIgnoreCase(Methods.getDeparture())) {
						for (int j=0; j<TabFileReader.ncol();j++) {
							Methods.chaine += TabFileReader.wordAt(i, j) + "\t";
						}
						if(!chaine.isEmpty()) {
							Methods.result.add(Methods.chaine);
							Methods.chaine = " ";
						}	
					}		
				}			
				// print result
				if(!Methods.result.isEmpty()) {
					System.out.println("\n\n Résultats de votre requête \n");
					Methods.header();
					for (String string : Methods.result) {
						String [] s = string.split("\t");
						for (String string2 : s) {
							System.out.format("%20s |",string2);
						}
						System.out.println();
					}
					Methods.footer();
					Methods.result.clear();
				}else {
					return -1;
				}
			}
		}
		return 0;
	}


	/**
	 * static method for second choice
	 * @param departure (user departure)
	 * @param arrival (user arrival)
	 * @param paramsExist (user departure and arrival exist or not)
	 * @return int
	 */
	public static int flightWithStop(String departure, String arrival, boolean paramsExist) {	
		Methods.result.clear();
		Methods.resultTwo.clear();
		if(null == departure && paramsExist)
			Methods.setDeparture(Methods.demanderVilleDepart());
		else
			Methods.setDeparture(departure);	
		if(null == arrival && paramsExist)
			Methods.setArrival(Methods.demandeVilleArrive());
		else
			Methods.setArrival(arrival);
		TabFileReader.readTextFile(filename,'\t',"data");
		for (int i=0;i<TabFileReader.nrow();i++) {
			for (int i1=1;i1<TabFileReader.nrow();i1++) {
				if(TabFileReader.wordAt(i, 1).equals(Methods.getDeparture()) && TabFileReader.wordAt(i1, 2).equals(Methods.getArrival())) {
					if(TabFileReader.wordAt(i, 2).equals(TabFileReader.wordAt(i1, 1))) {
						int HeureDepart = Methods.toMinutes(TabFileReader.wordAt(i1, 3));
						int HeureArrive = Methods.toMinutes(TabFileReader.wordAt(i, 4));
						int calculHeure = HeureDepart - HeureArrive;
						if(calculHeure >= 60) {
							for (int j=0; j<TabFileReader.ncol();j++) {
								Methods.chaine += TabFileReader.wordAt(i, j) + "\t";
								Methods.chaineTwo += TabFileReader.wordAt(i1, j) + "\t";		
							}	
							if(!Methods.chaine.isEmpty()) {
								if(!Methods.contain(Methods.result, Methods.chaine)) {
									Methods.result.add(Methods.chaine);
								}
							}	
							if(!Methods.chaineTwo.isEmpty()) {
								if(!Methods.contain(Methods.resultTwo, Methods.chaineTwo)) {
									Methods.resultTwo.add(Methods.chaineTwo);	
								}
							}
						}	
						Methods.chaine=" ";
						Methods.chaineTwo=" ";
					}
				}		
			}	
		}
	
		if(!Methods.result.isEmpty() && !Methods.resultTwo.isEmpty()) {
			System.out.println("\n\n Résultats de votre requête \n");
			Methods.header();
	        for (String string : Methods.result) {
	        	String [] s = string.split("\t");			
				//loop to print one line of file
				for (String string2 : s) {
					System.out.format("%20s |",string2);
				}
			System.out.println();
			}				        
			
			for (String string3 : Methods.resultTwo) {
				String [] s2 = string3.split("\t");		
				//loop to print one line of file
				for (String string4 : s2) {
					System.out.format("%20s |",string4);
				}
				System.out.println();
			}
			Methods.footer();
			Methods.result.clear();
			Methods.resultTwo.clear();
		}else {
			return -1;
		}
		return 0;
		
	}
	
	
	/**
	 * static method to print all airport desserve in the day
	 */
	public static void deserveAirportInDay() {
		Methods.result.clear();
		TabFileReader.readTextFile(filename,'\t',"data");
		for (int i=0;i<TabFileReader.nrow();i++){
			for(int j=0;j<=TabFileReader.ncol();j++) {
				Methods.chaine = TabFileReader.wordAt(i, 2);
				int heureArrivee = Integer.parseInt(TabFileReader.wordAt(i, 4).split(":")[0]);
				//vol déservit avant 19h y compris
				if(heureArrivee <=19) {
					if(Methods.result.isEmpty()) {
						Methods.result.add(Methods.chaine);
					}else {
						if(!Methods.result.contains(Methods.chaine))
							Methods.result.add(Methods.chaine);
							Methods.chaine = " ";
					}
				}		
			}						
		}
		if(!Methods.result.isEmpty()) {
			System.out.println("\n\n Listes des aéroports désservis dans la journée \n");
			System.out.println("+--------------------+");
			System.out.println("|	Aéroports    |");
			System.out.println("+--------------------+");
			for(String string : Methods.result) {
				System.out.format("%20s |",string);
				System.out.println();
			}
			System.out.println("+--------------------+");
			Methods.result.clear();
		}
	}
	
	
	/**
	 * static method to print alphabet list of all airport desserve in the day
	 */
	public static void alphabetDeserveAirportInDay() {	
		Methods.result.clear();
		TabFileReader.readTextFile(filename,'\t',"data");
		for (int i=0;i<TabFileReader.nrow();i++){
			for(int j=0;j<=TabFileReader.ncol();j++) {
				Methods.chaine = TabFileReader.wordAt(i, 2);
				int heureArrivee = Integer.parseInt(TabFileReader.wordAt(i, 4).split(":")[0]);
				//vol déservit avant 19h y compris
				if(heureArrivee <=19) {
					if(Methods.result.isEmpty()) {
						Methods.result.add(Methods.chaine);
					}else {
						if(!Methods.result.contains(Methods.chaine))
							Methods.result.add(Methods.chaine);
						Methods.chaine = " ";
					}
				}		
			}						
		}
		if(!Methods.result.isEmpty()) {
			Collections.sort(Methods.result);
			System.out.println("\n\n Liste aphabétique des aéroports désservis dans la journée \n");
			System.out.println("+--------------------+");
			System.out.println("|	Aéroports    |");
			System.out.println("+--------------------+");
			for(String string:Methods.result) {
				System.out.format("%20s |",string);
				System.out.println();
			}
			System.out.println("+--------------------+");
			Methods.result.clear();
		}
	}
	
	
	/**
	 * static method to print all direct and stop flight
	 */
	public static void directOrStopFlight() {
		Methods.result.clear();
		Methods.resultTwo.clear();
		ArrayList<String> tabDeparture = new ArrayList<String>();
		ArrayList<String> tabArrival = new ArrayList<String>();
		TabFileReader.readTextFile(Methods.filename,'\t',"data");
		for (int i=0;i<TabFileReader.nrow();i++){
			if(tabDeparture.isEmpty())
				tabDeparture.add(TabFileReader.wordAt(i, 1));		
			if(!tabDeparture.contains(TabFileReader.wordAt(i, 1)))
				tabDeparture.add(TabFileReader.wordAt(i, 1));
		}
			
		for (String string : tabDeparture) {
			Methods.allDirectFlights(string, false);
			Methods.result.clear();
			Methods.resultTwo.clear();
		}
		
		System.out.println();
		
		for (int i=0;i<TabFileReader.nrow();i++){
			if(tabArrival.isEmpty())
				tabArrival.add(TabFileReader.wordAt(i, 2));		
			if(!tabArrival.contains(TabFileReader.wordAt(i, 2)))
				tabArrival.add(TabFileReader.wordAt(i, 2));
		}
			
		for (String string : tabDeparture) {
			for (String string2 : tabArrival) {
				Methods.flightWithStop(string, string2, false);
				Methods.result.clear();
				Methods.resultTwo.clear();
			}
		}
		
	}
}
