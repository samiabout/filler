package filler;

import java.util.Random;
import java.util.Scanner;

//import org.omg.CORBA.PUBLIC_MEMBER;


public class Main {

	
	

	
//—————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————//
//–––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––//
//—————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————//
		
	public static void main(String[] args){

		
		
		
		Board.setGame();
		
		//System.out.println("\njoueur 1 on haut à gauche \njoueur 2 en bas à droite \nle joueur 1 commence");	
	   	 	
	   	 	//int ntour=0;		//numéro du tour
	   	 	//int joueur;			//no joueur en cours
	   	 	//int adversaire;
	        boolean fin = false;//utiliser pour boucler jusqu'à la fin du jeu
	       
	        
	        
	        do{
	        	
	        	Player.setTurnParameters();
	        	
	        	Board.setGame();


	        	Board.toMaj();		//met toutes les cases controlées en majuscules
	        	
	        	Board.afficheTable();
	        			System.out.println();
	        	Board.afficheTableControl();
	        	
	        	char choix;  
	        	
	        	Player.demandeCouleur();
	        	
	        	
	        	Board.setControl();//rajoute toutes les nouvelles cases controlées à tableControl
	        	       	
	        	Board.setCouleur();	//transforme les couleurs dans table
	        	
	        	fin=Player.indicationGagnant(); //renvoi un boolean utilisé pour la condition du while
	           
	        			System.out.println();
	        	
	        	
	           
	        	
	        }while(!fin);
	}
}
