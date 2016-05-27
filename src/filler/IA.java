package filler;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.omg.CORBA.PUBLIC_MEMBER;

import sun.font.CreatedFontTracker;
import sun.font.TrueTypeFont;
import sun.net.www.content.text.plain;

public class IA extends Player{
	
	//int IAdifficulty;
	



	private boolean allmostTheEnd;

	public IA(int player, int nbPlayers, Interface interfaceG) {
		super(player, nbPlayers, interfaceG);
		
		//this.IAdifficulty=IAdifficulty;
		
		// TODO Auto-generated constructor stub
	}
	
	public IA(IA clonedIA){
		super(clonedIA);
	}
	
	public IA(Player clonedPlayerToIA){
		super(clonedPlayerToIA);
	}

	
//methods
	
	public char demandeCouleur(Board board,boolean complexity,Player opponnent) {
		if(!Main.onlyResultDisplay){
		System.out.println("joueur" + this.player);	
		}
		
		
		this.intrefaceG.displayCommands(possibleChoices());
		
		
		/*if (complexity){
		IAForcast test=new IAForcast(board, this,opponnent,5);	
		return test.getBestChoice();
		}*/
		
		if (!allmostTheEnd && complexity){
			IAForcast2 superIA=new IAForcast2(board, this, opponnent);
			//System.out.println("——> "+Board.couleurs.charAt(superIA.getBestChoice()));
			return Board.couleurs.charAt(superIA.getBestChoice());
		}
		
		return iaChoseColor(possibleChoices(),board);
	}


	public char iaChoseColor(boolean[] possibleChoices, Board board) {
		int biggestAmount=0;
		int bestChoice = 0;//corélé à l'indexation de Board.couleurs
		for (int i = 0; i < possibleChoices.length; i++) {
			if (possibleChoices[i]){
				int amount=changesAmount(Board.couleurs.charAt(i), board);
				if (amount>biggestAmount){
					biggestAmount=amount;
					bestChoice=i;
				}
			}
		}
		//System.out.println(Board.couleurs.charAt(bestChoice));
		return Board.couleurs.charAt(bestChoice);
	}
	
	public int changesAmount(char choix, Board board){
			int boarTest[][]=new int[board.length()][board.height()];
			for (int i = 0; i < board.length(); i++) {
				for (int j = 0; j < board.height(); j++) {
					boarTest[i][j]=board.tableControl(i, j);
				}
			}
			char [][]tableTest=board.table();
			int nbModifs=0;
	    	boolean finModif=false;
	    	
	    	while (!finModif){
	    		finModif=true;
	        	for (int i=0; i<board.height();i++){			//on balaie toutes les cases
	    			for(int j=0; j<board.length();j++){	
	    				if (boarTest[i][j]==this.player){	//la case doit appartenir au joueur
	    					if (i>0)				{	if (tableTest[i-1][j]==choix && boarTest[i-1][j]==0)	{boarTest[i-1][j]=this.player;finModif=false;nbModifs++;} }
	    					if (i<board.height()-1)	{	if (tableTest[i+1][j]==choix && boarTest[i+1][j]==0)	{boarTest[i+1][j]=this.player;finModif=false;nbModifs++;} }
	    					if (j>0)				{	if (tableTest[i][j-1]==choix && boarTest[i][j-1]==0)	{boarTest[i][j-1]=this.player;finModif=false;nbModifs++;} }
	    					if (j<board.length()-1)	{	if (tableTest[i][j+1]==choix && boarTest[i][j+1]==0)	{boarTest[i][j+1]=this.player;finModif=false;nbModifs++;} }
	    				}//add conditions for hexagonal = true;
	    				
	    			}
	    		}
	    	}
			return nbModifs;
    	}

//——————————————————————————————————————————————————	
//—————————————————————————————————level 3——————————
//——————————————————————————————————————————————————
	
	

	
}
