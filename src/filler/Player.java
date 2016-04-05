package filler;

import java.util.Scanner;

public class Player {

	int player;
	int opponent1;
	int opponent2;
	int opponent3;
	
	int noTour;
	
	static int nbPlayers=2;//default value
	
	public Player() {
		// TODO Auto-generated constructor stub
	}
	
	private int nbPlayers(){
		return nbPlayers;
	}

	
	public static char demandeCouleur(int joueur , char couleurAdversaire){
		System.out.println("joueur"+joueur);
		Scanner sc  = new Scanner(System.in);
		char choix = sc.next().charAt(0);
		if (Board.couleurs.indexOf(choix)<0 || choix==couleurAdversaire)	{return demandeCouleur(joueur,couleurAdversaire);}//index du caractère choisi dans la liste couleurs(auiepo)
		else														{return choix;}
	}
	
	
	
	
	
	
	private static boolean indicationGagnant(int[][] tableControl) {//indique la proprotion du pateau occupée par chaque joueur
		int nbCase1=0;
		int nbCase2=0;
    	for (int i=0; i<height;i++){
			for(int j=0; j<length;j++){
					if (tableControl[i][j]==1){nbCase1++;}
					if (tableControl[i][j]==2){nbCase2++;}
				}
		}
    	System.out.println("le joueur 1 controle " + nbCase1 + "/" + height*length + " cases soit " + nbCase1*100/(height*length) + "% du plateau.");
    	System.out.println("le joueur 2 controle " + nbCase2 + "/" + height*length + " cases soit " + nbCase2*100/(height*length) + "% du plateau.");	
    	
    	if (nbCase1>height*length/2 || nbCase2>height*length/2 || nbCase1+nbCase2==height*length){
    		if (nbCase1>nbCase2){System.out.println("joueur 1 gagne");}
    		else if (nbCase1<nbCase2){System.out.println("joueur 2 gagne");}
    		else{System.out.println("égalité");}
    		return true;
    	}
    	else{return false;}
	}
}
