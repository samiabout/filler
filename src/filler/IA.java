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
	
	public char demandeCouleur(Board board,int complexity,Player opponnent) {
		if(!Main.onlyResultDisplay){
		System.out.println("joueur" + this.player);	
		}
		
		this.intrefaceG.displayCommands(possibleChoices());
		
		if(complexity==0){
			return iaRandom(possibleChoices(), board);
		}
		if(complexity==2){
			return Board.couleurs.charAt(maybeIWillUseHisBestChoiceToPissHimOff(board, this, opponnent, Board.couleurs.indexOf(iaChoseColor(possibleChoices(), board))));
		}
		/*if (complexity){
		IAForcast test=new IAForcast(board, this,opponnent,5);	
		return test.getBestChoice();
		}*/
		if (!allmostTheEnd && complexity==4 && this.player==1){
			IAForcast2 superIA=new IAForcast2(board, this, opponnent);
			//System.out.println("——> "+Board.couleurs.charAt(superIA.getBestChoice()));
			return Board.couleurs.charAt(superIA.getBestChoice());
		}
		if (!allmostTheEnd && complexity==4 && this.player==2){
			IAForcast3 superIA=new IAForcast3(board, this, opponnent);
			//System.out.println("——> "+Board.couleurs.charAt(superIA.getBestChoice()));
			return Board.couleurs.charAt(superIA.getBestChoice());
		}
		return iaChoseColor(possibleChoices(),board);
	}


	public char iaChoseColor(boolean[] possibleChoices, Board board) {//niveau 1 choisi sont meilleur coup
		int biggestAmount=0;
		int bestChoice = 0;//corélé à l'indexation de Board.couleurs
		boolean firstChoice=false;//éviter que l'ia choisisse 'r' par défaut
		for (int i = 0; i < possibleChoices.length; i++) {
			if (possibleChoices[i]){
				if(!firstChoice){
					bestChoice=i;
					firstChoice=true;
				}
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
	
	public char iaRandom(boolean[] possibleChoices,Board board){
		int i;
		do{
			i=(int)(Math.random() * 6);
		}while(!possibleChoices[i]);
		return Board.couleurs.charAt(i);
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
	        	for (int i=0; i<board.length();i++){			//on balaie toutes les cases
	    			for(int j=0; j<board.height();j++){	
	    				if (boarTest[i][j]==this.player){	//la case doit appartenir au joueur
	    					if (i>0)				{	if (tableTest[i-1][j]==choix && boarTest[i-1][j]==0)	{boarTest[i-1][j]=this.player;finModif=false;nbModifs++;} }
	    					if (i<board.length()-1)	{	if (tableTest[i+1][j]==choix && boarTest[i+1][j]==0)	{boarTest[i+1][j]=this.player;finModif=false;nbModifs++;} }
	    					if (j>0)				{	if (tableTest[i][j-1]==choix && boarTest[i][j-1]==0)	{boarTest[i][j-1]=this.player;finModif=false;nbModifs++;} }
	    					if (j<board.height()-1)	{	if (tableTest[i][j+1]==choix && boarTest[i][j+1]==0)	{boarTest[i][j+1]=this.player;finModif=false;nbModifs++;} }
	    				}//add conditions for hexagonal = true;
	    				
	    			}
	    		}
	    	}
			return nbModifs;
    	}

//——————————————————————————————————————————————————	
//—————————————————————————————————level 3——————————
//——————————————————————————————————————————————————
	private int maybeIWillUseHisBestChoiceToPissHimOff( Board board,IA iaToClone,Player opponnentToClone,int myBestChoice){//IA ninveau2
		//utilis si son premier choix est meilleur que mon premier
		// et si j'utilise son meilleur choix est meilleur que son 2me choix
		int mbc=0; //changes amount of my best choice
		int Cmbc = myBestChoice;
		int h1bc=0; //changes amount of his first best choice
		int Ch1bc = 0;
		int ih1bc=0;  // changes amount if I use his first best choice
		int h2bc=0; //changes amount of his second best choice
			
			boolean[] possibleChoices = iaToClone.possibleChoices();
			boolean[] opponentPossibleChoices = opponnentToClone.possibleChoiceswithOpponentColor();
			
			Board testIABoard=new Board(board);
			mbc=testIABoard.setControl(Board.couleurs.charAt(Cmbc),iaToClone);
			
			for (int i = 0; i < 6; i++) {

				if (opponentPossibleChoices[i]){
					Board testOpponentBoard = new Board(board);
					int opponentChanges = testOpponentBoard.setControl(Board.couleurs.charAt(i), opponnentToClone);
					if (opponentChanges>h1bc){
						h1bc=opponentChanges;
						Ch1bc=i;
					}
					if (opponentChanges>h2bc && opponentChanges<=h1bc && Ch1bc!=i){
						h2bc=opponentChanges;
						
					}
				}
				Board testOpponentBoard = new Board(board);
				ih1bc=testOpponentBoard.setControl(Board.couleurs.charAt(Ch1bc), iaToClone);
			}
		
		if (mbc>h1bc || h1bc==h2bc){
			return Cmbc;
		}
		if (mbc<=h1bc && ih1bc>h2bc){
			return Ch1bc;
		}
		return Cmbc;
		
	}
	

	
}
