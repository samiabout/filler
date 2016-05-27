package filler;

import com.tdebroc.filler.connector.PlayerConnector;
import com.tdebroc.filler.game.Colors;
import com.tdebroc.filler.game.Game;

import java.util.Random;
import java.util.Scanner;

import org.w3c.dom.css.ElementCSSInlineStyle;

import jdk.internal.dynalink.beans.StaticClass;
import sun.net.www.content.image.gif;

//import org.omg.CORBA.PUBLIC_MEMBER;


public class Main {

	static Scanner sc  = new Scanner(System.in);	
	
	static boolean graphicDisplay=false;
	static boolean consoleDisplay=false;
	static boolean onlyResultDisplay=true;
	static int g1=0;//nombre de point par joureur
	static int g2=0;

//—————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————//
//–––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––//
//—————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————//
		
	public static void main(String[] args){

		while(true){
			long allGameTime = System.currentTimeMillis();	
		IAForcast2.profondeurB=5;
		IAForcast2.profondeurA=5;
		System.out.println();
		System.out.print(g1);
		System.out.println(" "+g2);
		boolean autoset =true;//Developer
		
		//default values
		
		int nbPlayers=2;
		int length=20;
		int height=20;

		
		//avancé
		boolean hexagonal=false;
		boolean islet=false;
		boolean obstacles=false;
		double obstaclesAmount=30;
		boolean player1ia=true;
		boolean player2ia=true;
		
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
		IA ia1=new IA(1,nbPlayers, interfaceG);
		Player player2=new Player(2,nbPlayers, interfaceG);
		IA ia2=new IA(2,nbPlayers, interfaceG);
		Player player3=new Player(3,nbPlayers, interfaceG);
		Player player4=new Player(4,nbPlayers, interfaceG);

			boolean fin = false;//utiliser pour boucler jusqu'à la fin du jeu
			
			int noTour=0;
	       
	        do{
	        	long startTime = System.currentTimeMillis();
	        	char choix; 
	        	int nbModifs=0;
	        	switch (noTour%nbPlayers) {
				case 0:
					if(player1ia){
						ia1.setTurnParameters(board);
						choix=ia1.demandeCouleur(board,true,player2);//false:ia niveau 1 (code à mieux intégrer)
						if(!Main.onlyResultDisplay){System.out.println("choisit "+choix);}
						nbModifs=board.setControl(choix,ia1);//rajoute toutes les nouvelles cases controlées à tableControl
						if(!Main.onlyResultDisplay){System.out.println("nb modifs "+ nbModifs);}
						board.setCouleur(choix,ia1, interfaceG);	//transforme les couleurs dans table
						break;}
					else{
						player1.setTurnParameters(board);
						choix=player1.demandeCouleur();
						board.setControl(choix,player1);//rajoute toutes les nouvelles cases controlées à tableControl
						board.setCouleur(choix,player1, interfaceG);	//transforme les couleurs dans table
						break;}
				
				case 1:
					if(player2ia){
						ia2.setTurnParameters(board);
						choix=ia2.demandeCouleur(board,false,player1);//true:ia niveau 1 (code à mieux intégrer)
						if(!Main.onlyResultDisplay){System.out.println("choisit "+choix);}
						nbModifs=board.setControl(choix,ia2);
						if(!Main.onlyResultDisplay){System.out.println("nb modifs "+ nbModifs);}
				    	if(nbModifs==0){System.out.println("........iiiiiiiiiii.................................................................iiiiiii......................");}
						board.setCouleur(choix,ia2, interfaceG);	
						break;}
					else{
						player2.setTurnParameters(board);
						choix=player2.demandeCouleur();
						board.setControl(choix,player2);
						board.setCouleur(choix,player2, interfaceG);	
						break;}
				
				case 2:
					player3.setTurnParameters(board);
					choix=player3.demandeCouleur();
					System.out.println(choix);
					board.setControl(choix,player3);
					board.setCouleur(choix,player3, interfaceG);	
					break;
					
				case 3:
					player4.setTurnParameters(board);
					choix=player4.demandeCouleur();
					board.setControl(choix,player4);
					board.setCouleur(choix,player4, interfaceG);
					
				default:
					break;
				}
	        	
	        	
	        	noTour++;
	        	if(!onlyResultDisplay){
	        		System.out.println("temps : "+(System.currentTimeMillis()- startTime));
	        	}
	        	
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
	        System.out.println("durée de la partie: "+(double)(System.currentTimeMillis()- allGameTime)/1000+" s");
	        System.out.println("nb de tours : "+noTour);
	        
	}
	}
}
