package filler;

import java.util.Random;
import java.util.Scanner;

//import org.omg.CORBA.PUBLIC_MEMBER;


public class Main {

	static Scanner sc  = new Scanner(System.in);	
	


//—————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————//
//–––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––//
//—————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————//
		
	public static void main(String[] args){
		
		boolean autoset =false;//Developer
		
		int nbPlayers=2;
		
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
	

		board1.afficheTable();
		System.out.println();
		board1.afficheTableControl();
		
		
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
		
		//System.out.println("\njoueur 1 on haut à gauche \njoueur 2 en bas à droite \nle joueur 1 commence");	
	   	 	
	   	 	//int ntour=0;		//numéro du tour
	   	 	//int joueur;			//no joueur en cours
	   	 	//int adversaire;
	        boolean fin = false;//utiliser pour boucler jusqu'à la fin du jeu
	       
	        
	        
	        do{
	        	


	        	board1.toMaj();		//met toutes les cases controlées en majuscules
	        	
	        	board1.afficheTable();
	        			System.out.println();
	        	board1.afficheTableControl();
	        	
	        	char choix;  
	        	
	        	
	        	switch (noTour%nbPlayers) {
				case 1:choix=player1.demandeCouleur();
					board1.setControl(choix,player1);//rajoute toutes les nouvelles cases controlées à tableControl
					board1.setCouleur(choix,player1);	//transforme les couleurs dans table
					break;
				case 2:choix=player2.demandeCouleur();
					board1.setControl(choix,player2);
					board1.setCouleur(choix,player2);	
					break;
				case 3:choix=player3.demandeCouleur();
					board1.setControl(choix,player3);
					board1.setCouleur(choix,player3);	
					break;
				case 4:choix=player4.demandeCouleur();
					board1.setControl(choix,player4);
					board1.setCouleur(choix,player4);	
				default:
					break;
				}
	        	
	        	
	        	
	        	
	        	
	        	
	        	       	
	        	
	        	
	        	fin=player1.indicationGagnant(board1); //renvoi un boolean utilisé pour la condition du while
	           
	        	
	       		System.out.println();
	        	
	        	
	           
	        	
	        }while(!fin);
	}
}
