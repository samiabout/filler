package filler;

import java.util.Random;
import java.util.Scanner;

//import org.omg.CORBA.PUBLIC_MEMBER;


public class main {

	
	

	
//���������������������������������������������������������������������������������������������������������������������������������������������//
//���������������������������������������������������������������������������������������������������������������������������������������������//
//���������������������������������������������������������������������������������������������������������������������������������������������//
		
	public static void main(String[] args){

		
		
		
		
		
		System.out.println("\njoueur 1 on haut � gauche \njoueur 2 en bas � droite \nle joueur 1 commence");	
	   	 	
	   	 	int ntour=0;		//num�ro du tour
	   	 	int joueur;			//no joueur en cours
	   	 	int adversaire;
	        boolean fin = false;//utiliser pour boucler jusqu'� la fin du jeu
	       
	        
	        
	        do{
	        	char couleurAdversaire = 0;
	        	char couleurJoueur;
	        	if (ntour%2==0)	{joueur=1;	adversaire=2;	couleurAdversaire=table[height-1][length-1];couleurJoueur=table[0][0];}
	        	else 			{joueur=2;	adversaire=2;	couleurJoueur=table[0][0];	couleurJoueur=table[height-1][length-1];				}
	        	


				toMaj(table,tableControl);		//met toutes les cases control�es en majuscules
	        	
	        	afficheTable(table);
	        			System.out.println();
	        	afficheTableControl(tableControl);
	        	
	        	char choix;  
	        	if(ia && joueur==2){//optention du choix du joueur par la console ou l'ia 
	        		choix=meilleurChoix(tableControl, table, couleurJoueur);
	        		System.out.println(choix);
	        	}
	        	else{
	        		choix=demandeCouleur(joueur,couleurAdversaire);
	        	}
	        	
	        	setControl(tableControl, table, joueur, choix);//rajoute toutes les nouvelles cases control�es � tableControl
	        	       	
	        	setCouleur(tableControl, table, joueur, choix);	//transforme les couleurs dans table
	        	
	        	fin=indicationGagnant(tableControl); //renvoi un boolean utilis� pour la condition du while
	           
	        			System.out.println();
	        	
	        	
	            ntour++;	//no du tour suivant
	        	
	        }while(!fin);
	}
}
