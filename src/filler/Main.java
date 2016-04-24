package filler;

import java.util.Random;
import java.util.Scanner;

import org.w3c.dom.css.ElementCSSInlineStyle;

import sun.net.www.content.image.gif;

//import org.omg.CORBA.PUBLIC_MEMBER;


public class Main {

	static Scanner sc  = new Scanner(System.in);	
	
	static boolean graphicDisplay=true;
	static boolean consoleDisplay=true;
	static boolean onlyResultDisplay=false;

//—————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————//
//–––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––//
//—————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————//
		
	public static void main(String[] args){
		
		while(true){
		
		boolean autoset =true;//Developer
		
		//default values
		
		int nbPlayers=2;
		int length=21;
		int height=21;
		
		//avancé
		boolean hexagonal=false;
		boolean islet=false;
		boolean obstacles=false;
		double obstaclesAmount=5;
		
		Board board=new Board();
		
		
			if (!autoset){

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

				
				board=new Board(height,length,nbPlayers,hexagonal,obstacles,obstaclesAmount,islet);
			}
			else {
				board=new Board(height,length,nbPlayers,hexagonal,obstacles,obstaclesAmount,islet);
			}
			
		Interface interfaceG= new Interface(board,2.5);
		
		Player player1=new Player(1,nbPlayers, interfaceG);
		//IA ia1=new IA(1,nbPlayers, interfaceG);
		Player player2=new Player(2,nbPlayers, interfaceG);
		//IA ia2=new IA(2,nbPlayers, interfaceG);
		Player player3=new Player(3,nbPlayers, interfaceG);
		Player player4=new Player(4,nbPlayers, interfaceG);

			boolean fin = false;//utiliser pour boucler jusqu'à la fin du jeu
			
			int noTour=0;
	       
	        do{
	        	
	        	char choix; 
	        	int nbModifs=0;
	        	switch (noTour%nbPlayers) {
				/*case 0:ia1.setTurnParameters(board);
				choix=ia1.demandeCouleur(board,false,player2);//false:ia niveau 1 (code à mieux intégrer)
				if(!Main.onlyResultDisplay){System.out.println("choisit "+choix);}
				nbModifs=board.setControl(choix,ia1);//rajoute toutes les nouvelles cases controlées à tableControl
				if(!Main.onlyResultDisplay){System.out.println("nb modifs "+ nbModifs);}
				board.setCouleur(choix,ia1, interfaceG);	//transforme les couleurs dans table
				break;*/
				case 0:player1.setTurnParameters(board);
					choix=player1.demandeCouleur();
					board.setControl(choix,player1);//rajoute toutes les nouvelles cases controlées à tableControl
					board.setCouleur(choix,player1, interfaceG);	//transforme les couleurs dans table
					break;
				/*case 1:ia2.setTurnParameters(board);
					choix=ia2.demandeCouleur(board,true,player1);//true:ia niveau 1 (code à mieux intégrer)
					if(!Main.onlyResultDisplay){System.out.println("choisit "+choix);}
					nbModifs=board.setControl(choix,ia2);
					if(!Main.onlyResultDisplay){System.out.println("nb modifs "+ nbModifs);}
			    	if(nbModifs==0){System.out.println("........iiiiiiiiiii.....................iiiiiii......................");}
					board.setCouleur(choix,ia2, interfaceG);	
					break;*/
				case 1:player2.setTurnParameters(board);
					choix=player2.demandeCouleur();
					board.setControl(choix,player2);
					board.setCouleur(choix,player2, interfaceG);	
					break;
				case 2:player3.setTurnParameters(board);
					choix=player3.demandeCouleur();
					System.out.println(choix);
					board.setControl(choix,player3);
					board.setCouleur(choix,player3, interfaceG);	
					break;
				case 3:player4.setTurnParameters(board);
					choix=player4.demandeCouleur();
					board.setControl(choix,player4);
					board.setCouleur(choix,player4, interfaceG);	
				default:
					break;
				}
	        	
	        	
	        	noTour++;
	        	
	        	board.toMaj();	
	        	if(consoleDisplay){
    			board.afficheTableControl();
    			
	        	board.afficheTable();	        		
	        	}
	        	if(!Main.onlyResultDisplay){
	        	System.out.println("no tour "+noTour);
	        	}
	        	fin=player1.indicationGagnant(board); //renvoi un boolean utilisé pour la condition du while
	        	if(!Main.onlyResultDisplay){System.out.println();}
	        	
	        }while(!fin);
	}
	}
}
