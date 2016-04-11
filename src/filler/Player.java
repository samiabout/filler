package filler;

import java.util.Scanner;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.w3c.dom.css.ElementCSSInlineStyle;

public class Player {
	
	private int nbPlayers;



	private int player;
	private int opponent1;
	private int opponent2;
	private int opponent3;

	private char playerColor;
	private char opponnentColor1;
	private char opponnentColor2;
	private char opponnentColor3;

	private char choix;

// ——————————————————————————————————————————
// ——————————————————————————————constructors
// ——————————————————————————————————————————

	public Player(int player, int nbPlayers) {
		this.player = player;
		this.nbPlayers = nbPlayers;
		this.opponent1=(player+1)%(nbPlayers+1);
		if (nbPlayers!=2){this.opponent2=(player+2)%(nbPlayers+1);}
		else {this.opponent2=5;}
		if (nbPlayers==4){this.opponent3=(player+3)%(nbPlayers+1);}
		else {this.opponent3=5;}
	}
	
//——————————————————————————————————————————
//—————————————————————————getters & setters
//——————————————————————————————————————————

	public int player() {
		return this.player;
	}
	
	public int opponent1() {
		return this.opponent1;
	}
	
	public int opponent2() {
		return this.opponent2;
	}
	
	public int opponent3() {
		return this.opponent3;
	}
	
	
	
// ——————————————————————————————————————————
// —————————————————parameters initialization
// ——————————————————————————————————————————
	
	public void setTurnParameters(Board board) {
		char[][]table = board.table();
		//int[][]tableControl = board.tableControl();
		int height = board.height();
		int length = board.length();
		
		/*this.player = noTour % this.nbPlayers;
		this.opponent1 = (noTour + 1) % this.nbPlayers;
		this.opponent2 = (noTour + 2) % this.nbPlayers;
		this.opponent3 = (noTour + 3) % this.nbPlayers;*/
		if (this.player == 1) {
			this.playerColor = Character.toLowerCase(table[0][0]);
			this.opponnentColor1 = Character.toLowerCase(table[height - 1][length - 1]);
			this.opponnentColor2 = Character.toLowerCase(table[height - 1][0]);
			this.opponnentColor3 = Character.toLowerCase(table[0][length - 1]);
		}
		if (this.player == 2) {
			this.playerColor = Character.toLowerCase(table[height - 1][length - 1]);
			this.opponnentColor1 = Character.toLowerCase(table[0][0]);
			this.opponnentColor2 = Character.toLowerCase(table[height - 1][0]);
			this.opponnentColor3 = Character.toLowerCase(table[0][length - 1]);
		}
		if (this.player == 3) {
			this.playerColor = Character.toLowerCase(table[height - 1][0]);
			this.opponnentColor1 = Character.toLowerCase(table[0][0]);
			this.opponnentColor2 = Character.toLowerCase(table[height - 1][length - 1]);
			this.opponnentColor3 = Character.toLowerCase(table[0][length - 1]);
		}
		if (this.player == 4) {
			this.playerColor = Character.toLowerCase(table[0][length - 1]);
			this.opponnentColor1 = Character.toLowerCase(table[0][0]);
			this.opponnentColor2 = Character.toLowerCase(table[height - 1][length - 1]);
			this.opponnentColor3 = Character.toLowerCase(table[height - 1][0]);
		}
	}

	// ——————————————————————————————————————————
	// ——————————————————————————————turn setting
	// ——————————————————————————————————————————
	
	public char demandeCouleur() {
		char choix;
		System.out.println("joueur" + this.player);
		Scanner sc = new Scanner(System.in);
		choix = sc.next().charAt(0);
		if (Board.couleurs.indexOf(choix) < 0 || 
			choix == this.playerColor ||
			choix == this.opponnentColor1 || 
			(choix == this.opponnentColor2 && this.nbPlayers != 2) || 
			(choix == this.opponnentColor3 && this.nbPlayers == 4)) {
			return demandeCouleur();
		} // index du caractère choisi dans la liste couleurs(auiepo)
		else {
			return choix;
		}
	}

	// ——————————————————————————————————————————
	// ————————————————————————————————final test
	// ——————————————————————————————————————————

	boolean indicationGagnant(Board board) {// indique la proprotion du pateau
										// occupée par chaque joueur
		//char[][]table = board.table();
		int[][]tableControl = board.tableControl();
		int height = board.height();
		int length = board.length();
		
		int nbCase1 = 0;
		int nbCase2 = 0;
		int nbCase3 = 0;
		int nbCase4 = 0;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < length; j++) {
				if (tableControl[i][j] == 1) {nbCase1++;}
				if (tableControl[i][j] == 2) {nbCase2++;}
				if (tableControl[i][j] == 3) {nbCase3++;}
				if (tableControl[i][j] == 4) {nbCase4++;}
			}
		}
		System.out.println("le joueur 1 controle " + nbCase1 + "/" + height * length + " cases soit "
				+ nbCase1 * 100 / (height * length) + "% du plateau.");
		System.out.println("le joueur 2 controle " + nbCase2 + "/" + height * length + " cases soit "
				+ nbCase2 * 100 / (height * length) + "% du plateau.");
		if (this.nbPlayers != 2) {
			System.out.println("le joueur 3 controle " + nbCase3 + "/" + height * length + " cases soit "
					+ nbCase3 * 100 / (height * length) + "% du plateau.");
		}
		if (this.nbPlayers == 4) {
			System.out.println("le joueur 4 controle " + nbCase4 + "/" + height * length + " cases soit "
					+ nbCase4 * 100 / (height * length) + "% du plateau.");
		}

		int max = Math.max(Math.max(nbCase1, nbCase2), Math.max(nbCase3, nbCase4));
		boolean gagnant1 = false;
		boolean gagnant2 = false;
		boolean gagnant3 = false;
		boolean gagnant4 = false;
		int nbGagnant = 0;

		if (nbCase1 > height*length/2 	|| 
			nbCase2>height*length/2		|| 
			nbCase3 > height*length/2 || 
			nbCase4 > height*length/2 || 
			nbCase1 + nbCase2 + nbCase3 + nbCase4 == height * length) { 
				if (nbCase1 == max) {gagnant1 = true;nbGagnant++;}
				if (nbCase2 == max) {gagnant2 = true;nbGagnant++;}
				if (nbCase3 == max) {gagnant3 = true;nbGagnant++;}
				if (nbCase4 == max) {gagnant4 = true;nbGagnant++;}
				if (nbGagnant == 1) {
					System.out.println("le gagnant est le joueur ");
					if (gagnant1) {System.out.print("1");}
					if (gagnant2) {System.out.print("2");}
					if (gagnant3) {System.out.print("3");}
					if (gagnant4) {System.out.print("4");}
				}
				if (nbGagnant > 1) {
					System.out.println("égalité entre les joueurs ");
					if (gagnant1) {System.out.println("1 ");}
					if (gagnant2) {System.out.println("2 ");}
					if (gagnant3) {System.out.println("3 ");}
					if (gagnant4) {System.out.println("4 ");}
				}

			return true;
			}
		else {return false;
		}

	}


}
