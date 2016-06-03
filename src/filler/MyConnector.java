package filler;
import java.util.Scanner;

import com.tdebroc.filler.connector.PlayerConnector;
import com.tdebroc.filler.game.Colors;
import com.tdebroc.filler.game.Game;
import com.tdebroc.filler.game.Grid;

public class MyConnector {
	
	Game game;
	static String baseUrl = "http://62.210.105.118:8083";
	PlayerConnector playerConnector1 ;
	
	public MyConnector(){
		System.out.println("Please, Enter GameId: ");
		Scanner scanner = new Scanner(System.in);
		//new MyConnector().onlyIA(scanner.nextInt());
		int gameId=scanner.nextInt();
		scanner.close();
		System.out.println("Open a new game is " + gameId);
		this.playerConnector1 = new PlayerConnector(gameId, baseUrl);
		playerConnector1.registerPlayer("BlueBird");
		this.game = playerConnector1.getGame();
	}
	
	public MyConnector(boolean pouet){
		
	}

	public char[][] importTable(int taille) {
		char table[][]=new char[taille][taille];
		Grid grille=this.game.getGrid();
		for (int i = 0; i < taille; i++) {
			for (int u = 0; u < taille; u++) {
				System.out.print(Character.toLowerCase(grille.getCell(i, u).getColor())+" ");
				table[i][u]=Character.toLowerCase(grille.getCell(i, u).getColor());
			}
			System.out.println();
		}
		return table;
	}	
	

	// ================================================
	// Example 1: Simple IA subscribing
	// ================================================
	
	public boolean sendMove(char choix){
		return playerConnector1.sendMove(Character.toUpperCase(choix));
	}
	
	public char getMove(){
		/*playerConnector1.waitOppenentsAndGetTheirMoves();
		char choix=getLastMovePlayed(this.game);*/
		
		Game game = playerConnector1.waitOppenentsAndGetTheirMoves();
		char choix=getLastMovePlayed(game);

		
		System.out.println("on a récupéré çççççççaaaa!!!!!"+ choix);
		choix=Character.toLowerCase(choix);
		System.out.println("on a récupéré çççççççaaaa!!!!!"+ choix);
		
		return choix;
	}
	
	/*public void onlyIA(int gameId) {
		
		
		System.out.println("Open a new game is " + gameId);
		PlayerConnector playerConnector1 = new PlayerConnector(gameId, baseUrl);
		playerConnector1.registerPlayer("BlueBird");
		Game game = playerConnector1.getGame();
		IA simpleIA = new SimpleIA();
		
		
		Grid grille=game.getGrid();
		for (int i = 0; i < 13; i++) {
			for (int u = 0; u < 6; u++) {
				System.out.print(Character.toLowerCase(grille.getCell(i, u).getColor()));
			}
			System.out.println();
		}
		
		
		

		do {
			game = playerConnector1.waitOppenentsAndGetTheirMoves();
			char c = simpleIA.getNextMove(game);
			playerConnector1.sendMove(c);
			game = playerConnector1.getGame();
		} while (!game.isFinished());

	}*/
	


	// ================================================
	// Simple IA
	// ================================================
	/*public class SimpleIA implements IA {

	    public char getNextMove(Game game) {
	        char c;
	        do {
	            c = Colors.getRandomColor();
	        } while (!Colors.isValidColor(c, game.getPlayers()));
	        return c;
	    }

	}*/
	public static char getLastMovePlayed(Game game) {
		System.out.println("ii"+ game.getPlayers().size());
		System.out.println("uu"+ game.getCurrentIdPlayerTurn());
		/*System.out.println(game.getPlayers().get(0).getPlayerColor());
		System.out.println(game.getPlayers().get(1).getPlayerColor());
		System.out.println(game.getPlayers().get(2).getPlayerColor());
		System.out.println(game.getPlayers().get(3).getPlayerColor());*/
	    int lastPlayerIdTurn = game.getCurrentIdPlayerTurn() == 0 ?
	            game.getPlayers().size() - 1 : game.getCurrentIdPlayerTurn();
	    return game.getPlayers().get(lastPlayerIdTurn).getPlayerColor();
	}

	public static char getLastMovePlayedd(Game game) {

		int lastPlayerIdTurn = game.getCurrentIdPlayerTurn() == 0 ?
		game.getPlayers().size() - 1 : game.getCurrentIdPlayerTurn();
		return game.getPlayers().get(lastPlayerIdTurn).getPlayerColor();
		}





	

}