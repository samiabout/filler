package filler;

import java.util.Scanner;

import org.omg.CORBA.PRIVATE_MEMBER;

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

	public static int nbPlayer;// default value

	private char choix;

	

// 覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧
// 覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧constructors
// 覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧

	public Player(int player, int nbPlayers) {
		this.player = player;
		this.nbPlayers = nbPlayers;
		
	}
	
//覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧
//覧覧覧覧覧覧覧覧覧覧覧覧揚etters & setters
//覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧

	public int player() {
		return this.player;
	}
	
// 覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧
// 覧覧覧覧覧覧覧覧用arameters initialization
// 覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧
	
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
			this.playerColor = table[0][0];
			this.opponnentColor1 = table[height - 1][length - 1];
			this.opponnentColor2 = table[height - 1][0];
			this.opponnentColor3 = table[0][length - 1];
		}
		if (this.player == 2) {
			this.playerColor = table[height - 1][length - 1];
			this.opponnentColor1 = table[0][0];
			this.opponnentColor2 = table[height - 1][0];
			this.opponnentColor3 = table[0][length - 1];
		}
		if (this.player == 3) {
			this.playerColor = table[height - 1][0];
			this.opponnentColor1 = table[0][0];
			this.opponnentColor2 = table[height - 1][length - 1];
			this.opponnentColor3 = table[0][length - 1];
		}
		if (this.player == 4) {
			this.playerColor = table[0][length - 1];
			this.opponnentColor1 = table[0][0];
			this.opponnentColor2 = table[height - 1][length - 1];
			this.opponnentColor3 = table[height - 1][0];
		}
	}

	// 覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧
	// 覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧turn setting
	// 覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧
	
	public char demandeCouleur() {
		char choix;
		System.out.println("joueur" + this.player);
		Scanner sc = new Scanner(System.in);
		choix = sc.next().charAt(0);
		if (Board.couleurs.indexOf(choix) < 0 || 
			choix == this.playerColor ||
			choix == this.opponnentColor1 || 
			(choix == this.opponnentColor2 && this.nbPlayers != 1) || 
			(choix == this.opponnentColor3 && this.nbPlayers == 4)) {
			return demandeCouleur();
		} // index du caract鑽e choisi dans la liste couleurs(auiepo)
		else {
			return choix;
		}
	}

	// 覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧
	// 覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧final test
	// 覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧

	boolean indicationGagnant(Board board) {// indique la proprotion du pateau
										// occup馥 par chaque joueur
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
				if (tableControl[i][j] == 1) {
					nbCase1++;
				}
				if (tableControl[i][j] == 2) {
					nbCase2++;
				}
				if (tableControl[i][j] == 3) {
					nbCase3++;
				}
				if (tableControl[i][j] == 4) {
					nbCase4++;
				}
			}
		}
		System.out.println("le joueur 1 controle " + nbCase1 + "/" + height * length + " cases soit "
				+ nbCase1 * 100 / (height * length) + "% du plateau.");
		System.out.println("le joueur 2 controle " + nbCase2 + "/" + height * length + " cases soit "
				+ nbCase2 * 100 / (height * length) + "% du plateau.");
		if (this.nbPlayers != 1) {
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

		if (nbCase1 > height * length / 2 || nbCase2 > height * length / 2
				|| nbCase3 > height * length / 2 || nbCase4 > height * length / 2
				|| nbCase1 + nbCase2 + nbCase3 + nbCase4 == height * length) { // teste=>passer
																							// de
																							// 2
																							// �
																							// 4
			if (nbCase1 == max) {
				gagnant1 = true;
				nbGagnant++;
			}
			if (nbCase2 == max) {
				gagnant2 = true;
				nbGagnant++;
			}
			if (nbCase3 == max) {
				gagnant3 = true;
				nbGagnant++;
			}
			if (nbCase4 == max) {
				gagnant4 = true;
				nbGagnant++;
			}
			if (nbGagnant == 1) {
				System.out.println("le gagnant est le joueur ");
				if (gagnant1) {
					System.out.print("1");
				}
				if (gagnant2) {
					System.out.print("2");
				}
				if (gagnant3) {
					System.out.print("3");
				}
				if (gagnant4) {
					System.out.print("4");
				}
			}
			if (nbGagnant > 1) {
				System.out.println("馮alit� entre les joueurs");
				if (gagnant1) {
					System.out.println("1");
				}
				if (gagnant2) {
					System.out.println("2");
				}
				if (gagnant3) {
					System.out.println("3");
				}
				if (gagnant4) {
					System.out.println("4");
				}
			}

			return true;

		} else {
			return false;
		}

	}


}
