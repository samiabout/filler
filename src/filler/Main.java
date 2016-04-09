package filler;

import java.util.Random;
import java.util.Scanner;

import sun.net.www.content.image.gif;

//import org.omg.CORBA.PUBLIC_MEMBER;


public class Main {

	static Scanner sc  = new Scanner(System.in);	
	


//—————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————//
//–––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––//
//—————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————//
		
	public static void main(String[] args){
		
		boolean autoset =true;//Developer
		
		int nbPlayers=4;
		
		int noTour=0;
		
		Board board1 = new Board(1);
		
			if (autoset==false){
					System.out.println("Choisir le nombre de joueurs (2, 3 ou 4) ");
				nbPlayers = sc.nextInt();
					System.out.println("Choisir la taille du plateau(longueur puis hauteur)");
				board1.length(sc.nextInt());
				board1.height(sc.nextInt());
				

			}
		board1.creeTable(nbPlayers);
		board1.creeTableControl(nbPlayers);	
	
		Player player1=new Player(1,nbPlayers);
		Player player2=new Player(2,nbPlayers);
		Player player3=new Player(3,nbPlayers);
		Player player4=new Player(4,nbPlayers);
		/*for (int i = 0; i < nbPlayers; i++) {
				switch (i) {
				case 1:Player player1=new Player(1,nbPlayers);
					player1.setTurnParameters(board1);
					break;
				case 2:Player player2=new Player(2,nbPlayers);
					player2.setTurnParameters(board1);
					break;
				case 3:Player player3=new Player(3,nbPlayers);
					player3.setTurnParameters(board1);
					break;
				case 4:Player player4=new Player(4,nbPlayers);
					player4.setTurnParameters(board1);
					break;
				default:
					break;
				}
			}*/

			boolean fin = false;//utiliser pour boucler jusqu'à la fin du jeu
			
        	board1.afficheTable();
				System.out.println();
			board1.afficheTableControl();
	       
	        do{
	        	
	        	board1.toMaj();		//met toutes les cases controlées en majuscules
	        	
	        	char choix;  
	        	switch (noTour%nbPlayers) {
				case 0:player1.setTurnParameters(board1);
					choix=player1.demandeCouleur();
					board1.setControl(choix,player1);//rajoute toutes les nouvelles cases controlées à tableControl
					board1.setCouleur(choix,player1);	//transforme les couleurs dans table
					break;
				case 1:player2.setTurnParameters(board1);
					choix=player2.demandeCouleur();
					board1.setControl(choix,player2);
					board1.setCouleur(choix,player2);	
					break;
				case 2:player3.setTurnParameters(board1);
					choix=player3.demandeCouleur();
					board1.setControl(choix,player3);
					board1.setCouleur(choix,player3);	
					break;
				case 3:player4.setTurnParameters(board1);
					choix=player4.demandeCouleur();
					board1.setControl(choix,player4);
					board1.setCouleur(choix,player4);	
				default:
					break;
				}
	        	
	        	noTour++;
	        	
	        	board1.afficheTable();
    				System.out.println();
    			board1.afficheTableControl();
	        	
	        	fin=player1.indicationGagnant(board1); //renvoi un boolean utilisé pour la condition du while
	           
	       		System.out.println();
	        	
	        }while(!fin);
	}
}
