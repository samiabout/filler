package filler;

import java.util.Random;
import java.util.Scanner;

//import org.omg.CORBA.PUBLIC_MEMBER;


public class Main {

	
	

	
//���������������������������������������������������������������������������������������������������������������������������������������������//
//���������������������������������������������������������������������������������������������������������������������������������������������//
//���������������������������������������������������������������������������������������������������������������������������������������������//
		
	public static void main(String[] args){

		
		
		
		Board.setGame();
		
		//System.out.println("\njoueur 1 on haut � gauche \njoueur 2 en bas � droite \nle joueur 1 commence");	
	   	 	
	   	 	//int ntour=0;		//num�ro du tour
	   	 	//int joueur;			//no joueur en cours
	   	 	//int adversaire;
	        boolean fin = false;//utiliser pour boucler jusqu'� la fin du jeu
	       
	        
	        
	        do{
	        	
	        	Player.setTurnParameters();
	        	
	        	Board.setGame();


	        	Board.toMaj();		//met toutes les cases control�es en majuscules
	        	
	        	Board.afficheTable();
	        			System.out.println();
	        	Board.afficheTableControl();
	        	
	        	char choix;  
	        	
	        	Player.demandeCouleur();
	        	
	        	
	        	Board.setControl();//rajoute toutes les nouvelles cases control�es � tableControl
	        	       	
	        	Board.setCouleur();	//transforme les couleurs dans table
	        	
	        	fin=Player.indicationGagnant(); //renvoi un boolean utilis� pour la condition du while
	           
	        			System.out.println();
	        	
	        	
	           
	        	
	        }while(!fin);
	}
}
