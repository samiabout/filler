package filler;

import java.util.Random;
import java.util.Scanner;

import org.w3c.dom.css.ElementCSSInlineStyle;

import sun.net.www.content.image.gif;

//import org.omg.CORBA.PUBLIC_MEMBER;


public class Main {

	static Scanner sc  = new Scanner(System.in);	
	


//—————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————//
//–––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––//
//—————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————//
		
	public static void main(String[] args){
		
		boolean autoset =true;//Developer
		
		//default values
		
		int nbPlayers=2;
		int length=13;
		int height=13;
		
		//avancé
		boolean hexagonal=false;
		boolean obstacles=false;
		double obstaclesAmount=0;
		
		Board board=new Board();
		
		
			if (autoset==false){

					System.out.println("Choisir le nombre de joueurs (2, 3 ou 4) ");
				nbPlayers = sc.nextInt();
					System.out.println("Choisir la taille du plateau(hauteur puis longueur )");
				height=sc.nextInt();
				length=sc.nextInt();
					System.out.println("case héxagonales?");
				hexagonal=sc.nextBoolean();
					System.out.println("obstacles?");
				obstacles=sc.nextBoolean();
				if(obstacles){
					System.out.println("quantité d'obstacles? (en%, max 30) ");
				obstaclesAmount=sc.nextInt();					
				}

				
				board=new Board(height,length,nbPlayers,hexagonal,obstacles,obstaclesAmount);
			}
			else {
				board=new Board(height,length,nbPlayers,hexagonal,obstacles,obstaclesAmount);
			}

		Player player1=new Player(1,nbPlayers);
		Player player2=new Player(2,nbPlayers);
		Player player3=new Player(3,nbPlayers);
		Player player4=new Player(4,nbPlayers);
		
		Interface interfaceG= new Interface(board);
		
		interfaceG.setDiplayBoard(1);

			boolean fin = false;//utiliser pour boucler jusqu'à la fin du jeu
			
			int noTour=0;
	       
	        do{
	        	
	        	char choix;  
	        	switch (noTour%nbPlayers) {
				case 0:player1.setTurnParameters(board);
					choix=player1.demandeCouleur();
					board.setControl(choix,player1);//rajoute toutes les nouvelles cases controlées à tableControl
					board.setCouleur(choix,player1);	//transforme les couleurs dans table
					break;
				case 1:player2.setTurnParameters(board);
					choix=player2.demandeCouleur();
					board.setControl(choix,player2);
					board.setCouleur(choix,player2);	
					break;
				case 2:player3.setTurnParameters(board);
					choix=player3.demandeCouleur();
					board.setControl(choix,player3);
					board.setCouleur(choix,player3);	
					break;
				case 3:player4.setTurnParameters(board);
					choix=player4.demandeCouleur();
					board.setControl(choix,player4);
					board.setCouleur(choix,player4);	
				default:
					break;
				}
	        	
	        	
	        	noTour++;
	        	
	        	board.toMaj();	
	        	
	        	board.afficheTable();
    				System.out.println();
    			board.afficheTableControl();
	        	
	        	fin=player1.indicationGagnant(board); //renvoi un boolean utilisé pour la condition du while
	           
	       		System.out.println();
	        	
	        }while(!fin);
	}
}
