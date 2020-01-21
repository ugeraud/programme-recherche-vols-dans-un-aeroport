/**
 * 
 */
package ugeraud;

import java.util.Scanner;

/**
 * @author gerau
 *
 */
public class InfoVols {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		boolean validChoice=false, continuer=true;
		
		Scanner clavier = new Scanner(System.in);
		
		Methods.userMenu();
		
		while(continuer) {
			while(!validChoice) {
				int getChoice = Methods.choice();
				switch (getChoice) 
				{
				case 1:
					if(Methods.allDirectFlights(null, true) == -1) {
						break;
					}else {
						validChoice=true;
					}
				break;
				
				case 2:
					if(Methods.flightWithStop(null, null, true) == -1) {
						System.out.format("\n>>Oups : Pas de trajet possible pour le vol %s --->  %s!\n",Methods.getDeparture(), Methods.getArrival());
						break;
					}
					else {
						validChoice = true;
						break;
					}
						
				case 3:
					Methods.deserveAirportInDay();
					validChoice = true;
					break;
				
				case 4:
					Methods.alphabetDeserveAirportInDay();
					validChoice = true;
					break;
					
				case 5:
					Methods.directOrStopFlight();
					validChoice = true;
					break;
				
				case 6:
					validChoice = true;
					break;
					
				default:
					System.out.println("\n>> Erreur, merci de consulter le menu.\n");
					validChoice=false;
					break;
				}
				
			}
			System.out.print("\nVoulez-vous continuer (O/N) ? ");
			String reponse = clavier.nextLine();
			if(reponse.equals("O") || reponse.equals("o")) {
				continuer = true;
				validChoice = false;
			}	
			else {
				System.out.println("\n>> Merci de votre visite.Au revoir....");
				continuer = false;
			}			
		}
		clavier.close();
	}

}
