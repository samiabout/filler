package filler;

import java.util.Scanner;

public class PlayerOpponentOnLine extends Player{


	public PlayerOpponentOnLine(int player, int nbPlayers, Interface interfaceG) {
		super(player, nbPlayers, interfaceG);
	}
	
	public char demandeCouleur() {
		System.out.println("joueur" + this.player);		
		this.intrefaceG.displayCommands(possibleChoices());
		return choix;

	}
	
	
	

	
}
