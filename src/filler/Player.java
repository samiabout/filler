package filler;

import java.util.Scanner;

public class Player {

	static int player;
	static int opponent1;
	static int opponent2;
	static int opponent3;
	
	static char playerColor;
	static char opponnentColor1;
	static char opponnentColor2;
	static char opponnentColor3;
	
	static int noTour;
	
	static int nbPlayers=2;//default value
	
	public Player() {
		// TODO Auto-generated constructor stub
	}
	
	public Player(int player, int opponent1, int opponent2, int opponent3, char opponnentColor1, char opponnentColor2,
			char opponnentColor3, int noTour) {
		super();
		Player.player = player;
		Player.opponent1 = opponent1;
		Player.opponent2 = opponent2;
		Player.opponent3 = opponent3;
		Player.opponnentColor1 = opponnentColor1;
		Player.opponnentColor2 = opponnentColor2;
		Player.opponnentColor3 = opponnentColor3;
		Player.noTour = noTour;
	}
//——————————————————————————————————————————
//—————————————————parameters initialization
//——————————————————————————————————————————
	private static void setTurnParameters() {
		player=noTour%nbPlayers;
		opponent1=(noTour+1)%nbPlayers;
		opponent2=(noTour+2)%nbPlayers;
		opponent3=(noTour+3)%nbPlayers;
		if (player==1){
			playerColor=Board.table[0][0];
			opponnentColor1=Board.table[Board.height-1][Board.length-1];
			opponnentColor2=Board.table[Board.height-1][0];
			opponnentColor3=Board.table[0][Board.length-1];
		}
		if (player==2){
			playerColor=Board.table[Board.height-1][Board.length-1];
			opponnentColor1=Board.table[0][0];
			opponnentColor2=Board.table[Board.height-1][0];
			opponnentColor3=Board.table[0][Board.length-1];
		}
		if (player==3){
			playerColor=Board.table[Board.height-1][0];
			opponnentColor1=Board.table[0][0];
			opponnentColor2=Board.table[Board.height-1][Board.length-1];
			opponnentColor3=Board.table[0][Board.length-1];
		}
		if (player==4){
			playerColor=Board.table[0][Board.length-1];
			opponnentColor1=Board.table[0][0];
			opponnentColor2=Board.table[Board.height-1][Board.length-1];
			opponnentColor3=Board.table[Board.height-1][0];
		}
	}
	
	
//——————————————————————————————————————————
//——————————————————————————————turn setting
//——————————————————————————————————————————	
	public static char demandeCouleur(int joueur , char opponnentColor){
		System.out.println("joueur"+joueur);
		Scanner sc  = new Scanner(System.in);
		char choix = sc.next().charAt(0);
		if (Board.couleurs.indexOf(choix)<0 || choix==opponnentColor1 || (choix==opponnentColor2&&nbPlayers!=1) ||(choix==opponnentColor3&&nbPlayers==4))	
						{return demandeCouleur(joueur,opponnentColor);}//index du caractère choisi dans la liste couleurs(auiepo)
		else {return choix;}
	}
	


	private static boolean indicationGagnant() {//indique la proprotion du pateau occupée par chaque joueur
		int nbCase1=0;
		int nbCase2=0;
		int nbCase3=0;
		int nbCase4=0;
    	for (int i=0; i<Board.height;i++){
			for(int j=0; j<Board.length;j++){
					if (Board.tableControl[i][j]==1){nbCase1++;}
					if (Board.tableControl[i][j]==2){nbCase2++;}
					if (Board.tableControl[i][j]==3){nbCase3++;}
					if (Board.tableControl[i][j]==4){nbCase4++;}
				}
		}
    	System.out.println("le joueur 1 controle " + nbCase1 + "/" + Board.height*Board.length + " cases soit " + nbCase1*100/(Board.height*Board.length) + "% du plateau.");
    	System.out.println("le joueur 2 controle " + nbCase2 + "/" + Board.height*Board.length + " cases soit " + nbCase2*100/(Board.height*Board.length) + "% du plateau.");	
    	if(nbPlayers!=1){
    		System.out.println("le joueur 3 controle " + nbCase3 + "/" + Board.height*Board.length + " cases soit " + nbCase3*100/(Board.height*Board.length) + "% du plateau.");
    	}
    	if(nbPlayers==4){
    		System.out.println("le joueur 4 controle " + nbCase4 + "/" + Board.height*Board.length + " cases soit " + nbCase4*100/(Board.height*Board.length) + "% du plateau.");
    	}
    	
    	if (nbCase1>Board.height*Board.length/2 || 
    		nbCase2>Board.height*Board.length/2 ||
    		nbCase3>Board.height*Board.length/2 ||
    		nbCase4>Board.height*Board.length/2 ||
    		nbCase1+nbCase2==Board.height*Board.length){		//teste=>passer de 2 à 4
    		if (nbCase1>nbCase2){System.out.println("joueur 1 gagne");}
    		else if (nbCase1<nbCase2){System.out.println("joueur 2 gagne");}
    		else{System.out.println("égalité");}
    		return true;
    	}
    	else{return false;}
	}
}
