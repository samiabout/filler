package filler;

import java.awt.font.TextHitInfo;

import com.sun.corba.se.impl.orb.ParserTable.TestAcceptor1;
import com.sun.media.jfxmedia.events.PlayerEvent;

import javafx.scene.layout.Border;
import jdk.nashorn.internal.ir.ReturnNode;
import sun.font.TrueTypeFont;

public class IAForcast2 {
	
	
	Board[][][][][] allmyBPossibilities= new Board[6][6][6][6][6];
	//IA[][][][][] allmyIAPossibilities=new IA[6][6][6][6][6];
	int[][][][][] myModifications=new int[6][6][6][6][6];
	int[][][][][] borderSize=new int[6][6][6][6][6];
	
	public static int profondeurB=5;//nb de tour de prévision
	public static int profondeurA=5;

	
	private int myBiggestDifference;
	private int biggestBorderSize;
	private int allmodifs;
	private int bestChoice;
	private int nbControledtiles;
	private IA ia;
	private int nbTile;
	

//constructor	
	public IAForcast2(Board board, IA ia,Player opponnentToClone) {
		this.nbTile = board.height()*board.length();
		this.ia=ia;
		this.myBiggestDifference=0;
		this.allmodifs=0;
		this.biggestBorderSize=0;
		this.nbControledtiles=getNbControledtiles(board,ia);
		if (nbControledtiles>45){
			this.bestChoice=getBestChoice(board,ia);
		}
		else{this.bestChoice=myForcast(board  );}//opponnentToClone
		
		
	}

//getter	
public int getBestChoice() {
	return this.bestChoice;
}

	
//methods
	
	private int getNbControledtiles(Board board, IA player) {
		int amount=0;
		for (int i = 0; i < board.length(); i++) {
			for (int u = 0; u < board.height(); u++) {
				if (board.tableControl(i, u)==player.player() ){
					amount++;
				}
			}}
		return amount*100/(board.height()*board.length());
	}
	
	public int myForcast(Board board){//ne prend en compte que mes coups //Player opponnent
		boolean[] possibleChoices=new boolean[6];
				for (int a = 0; a < 6; a++) {
					for (int u = 0; u < 6; u++) {
						for (int i = 0; i < 6; i++) {
							for (int e = 0; e < 6; e++) {
								for (int p = 0; p < 6; p++) {
									for (int o = 0; o < 6; o++) {
						this.allmyBPossibilities[a][u][i][e][p]=new Board(board);
						//this.allmyIAPossibilities[a][u][i][e][p]=new IA(iaToClone);
						
						
						possibleChoices=this.ia.possibleChoices();//this.allmyIAPossibilities[a][u][i][e][p][o]
						if(possibleChoices[a]){						
								//fill
							if(profondeurB==1){
								this.allmyBPossibilities[a][u][i][e][p].setIslet(true);
							}
							fill(a,u,i,e,p,a);
							if(profondeurB>1){
								if(profondeurB==2){
									this.allmyBPossibilities[a][u][i][e][p].setIslet(true);
								}
								fill(a,u,i,e,p,u);
							}
							if (profondeurB>2){
								if(profondeurB==3){
									this.allmyBPossibilities[a][u][i][e][p].setIslet(true);
								}
								fill(a,u,i,e,p,i);
							}
							if (profondeurB>3){
								if(profondeurB==4){
									this.allmyBPossibilities[a][u][i][e][p].setIslet(true);
								}
								fill(a,u,i,e,p,e);
							}
							if (profondeurB>4){
								this.allmyBPossibilities[a][u][i][e][p].setIslet(true);
								fill(a,u,i,e,p,p);
								this.allmyBPossibilities[a][u][i][e][p].setIslet(true);
							}
							

							}
					}}}}}}
				
				
				if(this.nbControledtiles<30){
					if(!Main.onlyResultDisplay)	{
						System.out.println("doit choisir border");
					}
					bestChoice=this.bestBorderChoice();//en bct de la taille de la frontière
					
					//TODO décrémenter this.profondeurB
				}
				if(this.nbControledtiles<45 && this.nbControledtiles>30){
					if(!Main.onlyResultDisplay)	{
						System.out.println("doit choisir amount");
					}
					bestChoice=this.bestAmountChoice();//en fct du gain brut
				}
				

				return bestChoice;
				//return maybeIWillUseHisBestChoiceToPissHimOff(board, iaToClone, opponnent, bestchoice);
	}
	
	private int bestBorderChoice() {
		int bestChoiceBorder=0;
		int bestChoiceAmount=0;
		int bestBorderChoiceModifs=0;
		int bestAmountChoiceModifs=0;
		boolean profondeurBDecremente=false;
		for (int a = 0; a < 6; a++) {
			for (int u = 0; u < 6; u++) {
				for (int i = 0; i < 6; i++) {
					for (int e = 0; e < 6; e++) {
						for (int p = 0; p < 6; p++) {
							for (int o = 0; o < 6; o++) {
								this.borderSize[a][u][i][e][p]=0;
								this.borderSize[a][u][i][e][p]=borderSize(this.allmyBPossibilities[a][u][i][e][p],this.ia );//this.allmyIAPossibilities[a][u][i][e][p]
								if (this.borderSize[a][u][i][e][p]>this.biggestBorderSize){
									this.borderSize[a][u][i][e][p]=borderSize(this.allmyBPossibilities[a][u][i][e][p], this.ia);//this.allmyIAPossibilities[a][u][i][e][p]
									this.biggestBorderSize=this.borderSize[a][u][i][e][p];
									bestChoiceBorder=a;
									bestBorderChoiceModifs=this.myModifications[a][u][i][e][p];
									
									if(getNbControledtiles(allmyBPossibilities[a][u][i][e][p], ia)>30 && !profondeurBDecremente && profondeurB>0){
										profondeurB--;
										profondeurBDecremente=true;
										if(!Main.onlyResultDisplay)	{
											System.out.println(profondeurB);
										}
									}
								}
								if(this.myModifications[a][u][i][e][p]>this.myBiggestDifference){ //calcule le plus grand nombre de changements
								this.myBiggestDifference=this.myModifications[a][u][i][e][p];
								bestChoiceAmount=a;
								bestAmountChoiceModifs=this.myModifications[a][u][i][e][p];
								}
								
								
							}}}}}}
		if (bestAmountChoiceModifs>2*bestBorderChoiceModifs){
			if(!Main.onlyResultDisplay)	{
				System.out.println("choix amount");
			}
			return bestChoiceAmount;
		}
		if(!Main.onlyResultDisplay)	{
			System.out.println("choix border");
		}
		return bestChoiceBorder;
	}
	private int bestAmountChoice() {
		boolean profondeurADecremente=false;
		int bestChoice=0;
		for (int a = 0; a < 6; a++) {
			for (int u = 0; u < 6; u++) {
				for (int i = 0; i < 6; i++) {
					for (int e = 0; e < 6; e++) {
						for (int p = 0; p < 6; p++) {
							for (int o = 0; o < 6; o++) {
								if(this.myModifications[a][u][i][e][p]>this.myBiggestDifference){ //calcule le plus grand nombre de changements
								this.myBiggestDifference=this.myModifications[a][u][i][e][p];
								bestChoice=a;
								
								if(getNbControledtiles(allmyBPossibilities[a][u][i][e][p], ia)>45 && !profondeurADecremente && profondeurA>0){
									profondeurA--;
									profondeurADecremente=true;
									if(!Main.onlyResultDisplay)	{
										if(!Main.onlyResultDisplay)	{
											System.out.println(profondeurA);
										}
									}
								}
								
								}
							}}}}}}
		return bestChoice;
		
	}
	

	
	
	private int borderSize(Board board,Player player) {
		int[][]border=new int[this.nbTile][2];
		for (int i = 0; i <this.nbTile; i++) {
			border[i][0]=-1;
			border[i][1]=-1;
		}
		int size=0;
		for (int i = 0; i < board.length(); i++) {
			for (int u = 0; u < board.height(); u++) {
				if (board.tableControl(i, u)==player.player() &&
						//isNewCoordonnee(i,u,border) && 
						board.neighborType(i, u,0)	
						){
					border[size][0]=i;
					border[size][1]=u;
					size++;
				}
			}}
		return size;
	}
	
	private boolean isNewCoordonnee(int i,int u,int[][]border){//inutile
		boolean absent=true;
		for (int k = 0; k < 1000; k++) {
			if (border[k][0]==-1){break;};
			if (i==border[k][0]){
				if (u==border[k][1]){
					absent=false;

					
				}
			}
		}
		return absent;
	}
	
	public int getBestChoice( Board board,Player player) {
		boolean[]possibleChoices=player.possibleChoices();
		int biggestAmount=0;
		int bestChoice = 0;
		for (int i = 0; i < possibleChoices.length; i++) {
			if (possibleChoices[i]){
				Board testBoard=new Board(board);
				int amount=testBoard.setControl(Board.couleurs.charAt(i), player);
				if (amount>biggestAmount){
					biggestAmount=amount;
					bestChoice=i;
				}
			}
		}
		return bestChoice;
	}
	
	
	private void fill(int a, int u, int i, int e, int p,int color) {
		//this.allmyIAPossibilities[a][u][i][e][p].setTurnParameters(this.allmyBPossibilities[a][u][i][e][p][o]);
		this.myModifications[a][u][i][e][p]+=this.allmyBPossibilities[a][u][i][e][p].setControl(Board.couleurs.charAt(color),this.ia );//this.allmyIAPossibilities[a][u][i][e][p]
		this.allmodifs+=this.myModifications[a][u][i][e][p];
		this.allmyBPossibilities[a][u][i][e][p].setCouleur(Board.couleurs.charAt(color),this.ia);	// this.allmyIAPossibilities[a][u][i][e][p]
		
	}

	private int maybeIWillUseHisBestChoiceToPissHimOff( Board board,IA iaToClone,Player opponnentToClone,int myBestChoice){
		
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