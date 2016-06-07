/*
prendre ne compte les cas d'égalité//va faire caca

reverse board pour joueur 1 //on fait pas

check décrémentation de la profondeur de calcul//fait

changer les valeur 30 45 pour les grandes grilles//fait (variabilisé)

aller à profondeur max 6 tours pour les petites grilles

prendre en compte la foncton pisshimoff
mettre un facetuer en fct du nb de cases prise
<etape3 3
>etape3 1

*/
package filler;

import java.awt.font.TextHitInfo;

import com.sun.corba.se.impl.orb.ParserTable.TestAcceptor1;
import com.sun.media.jfxmedia.events.PlayerEvent;

import javafx.scene.layout.Border;
import jdk.nashorn.internal.ir.ReturnNode;
import sun.font.TrueTypeFont;

public class IAForcast3 {
	
	
	Board[][][][][][][] allmyBPossibilities= new Board[6][6][6][6][6][6][6];
	//IA[][][][][][][] allmyIAPossibilities=new IA[6][6][6][6][6][6][6];
	int[][][][][][][] myModifications=new int[6][6][6][6][6][6][6];
	int[][][][][][][] borderSize=new int[6][6][6][6][6][6][6];
	
	public static int profondeurB2=7;//nb de tour de prévision
	public static int profondeurA2=7;

	
	private int myBiggestDifference;
	private int biggestBorderSize;
	private int allmodifs;
	private int bestChoice;
	private int nbControledtiles;
	private IA ia;
	private int nbTile;
	
	private int nbEtapes;
	
	private int etape1;
	private int etape2;
	private int etape3;

//constructor	
	public IAForcast3(Board board, IA ia,Player opponnentToClone) {
		this.nbEtapes=0;
		this.nbTile = board.height()*board.length();
		this.ia=ia;
		this.myBiggestDifference=0;
		this.allmodifs=0;
		this.biggestBorderSize=0;
		this.nbControledtiles=getNbControledtiles(board,ia);
		if(board.length()<30){
			this.etape1=30;//border puis amount
			this.etape2=38;//gène
			this.etape3=45;//rush first play+gène
		}
		else{
			this.etape1=33;
			this.etape2=38;
			this.etape3=47;
		}
		if (nbControledtiles>this.etape3){
			this.bestChoice=getBestChoice(board,ia);
		}
		else{this.bestChoice=myForcast(board  );
		}//opponnentToClone
		if(nbControledtiles>etape2){//&&this.ia.nbPlayers==2
		int a=getBestChoice();
		setBestChoice(maybeIWillUseHisBestChoiceToPissHimOff(board, ia, opponnentToClone,getBestChoice()));
		if(a!=getBestChoice()){
			System.out.println("on l'a génééééééééééééééééééééééééééééééééééééééééééééééééééé");
			System.out.println(nbControledtiles);
		}
		}
		
	}

//getter	
public int getBestChoice() {
	return this.bestChoice;
}
public void setBestChoice(int newChoice) {
	this.bestChoice=newChoice;
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

		//System.out.println(profondeurA2);
		//System.out.println(profondeurB2);
		long timeIslet=0;
		long timeforcast=System.currentTimeMillis();
		boolean[] possibleChoices=new boolean[6];
		int profondeurDeTest=0;
		if(this.nbControledtiles<this.etape1){
			profondeurDeTest=profondeurB2;//border
		}
		else{
			profondeurDeTest=profondeurA2;
		}
		//System.out.println(profondeurDeTest);
				for (int a = 0; a < 6; a++) {
					for (int u = 0; u < 6; u++) {
						for (int i = 0; i < 6; i++) {
							for (int e = 0; e < 6; e++) {
								for (int p = 0; p < 6; p++) {
									for (int o = 0; o < 6; o++) {
										for (int b = 0; b < 6; b++) {
						this.allmyBPossibilities[a][u][i][e][p][o][b]=new Board(board);
						//this.allmyIAPossibilities[a][u][i][e][p][o][b]=new IA(iaToClone);
						
						
						possibleChoices=this.ia.possibleChoices();//this.allmyIAPossibilities[a][u][i][e][p][o][b][o]
						if(possibleChoices[a]){
							fill(a,u,i,e,p,o,b,a);
							if(profondeurDeTest>1){
								if(profondeurDeTest==2){
								fill(a,u,i,e,p,o,b,u);
							}
							if (profondeurDeTest>2){
								fill(a,u,i,e,p,o,b,i);
							}
							if (profondeurDeTest>3){
								fill(a,u,i,e,p,o,b,e);
							}
							if (profondeurDeTest>4){
								fill(a,u,i,e,p,o,b,p);
							}
							if (profondeurDeTest>5){
								fill(a,u,i,e,p,o,b,o);
							}
							if (profondeurDeTest>6){
								fill(a,u,i,e,p,o,b,b);
							}

							}
					}
						}}}}}}}
				//System.out.println(this.nbEtapes);
				if(!Main.onlyResultDisplay){
					System.out.println("temps forcast calcul : "+(System.currentTimeMillis()-timeforcast));
					//System.out.println("ici        ii          temps islet : "+ timeIslet);
				}
				
				if(this.nbControledtiles<this.etape1){
					if(!Main.onlyResultDisplay)	{
						System.out.println("doit choisir border");
					}
					bestChoice=this.bestBorderChoice();//en bct de la taille de la frontière
					
					//TODO décrémenter this.profondeurB2
				}
				if(this.nbControledtiles<=etape3 && this.nbControledtiles>=etape1){
					if(!Main.onlyResultDisplay)	{
						System.out.println("doit choisir amount");
					}
					bestChoice=this.bestAmountChoice();//en fct du gain brut
				}
				
					//System.out.println(this.nbEtapes);
				return bestChoice;
				//return maybeIWillUseHisBestChoiceToPissHimOff(board, iaToClone, opponnent, bestchoice);
	}
	
	private int bestBorderChoice() {
		long timebegin=System.currentTimeMillis();
		int bestChoiceBorder=0;
		int bestChoiceAmount=0;
		int bestBorderChoiceModifs=0;
		int bestAmountChoiceModifs=0;
		boolean profondeurB2Decremente=false;
		boolean[] possibleChoices=new boolean[6];
		for (int a = 0; a < 6; a++) {
			for (int u = 0; u < 6; u++) {
				for (int i = 0; i < 6; i++) {
					for (int e = 0; e < 6; e++) {
						for (int p = 0; p < 6; p++) {
							for (int o = 0; o < 6; o++) {
								for (int b = 0; b < 6; b++) {
							this.nbEtapes++;
								possibleChoices=this.ia.possibleChoices();
								if(possibleChoices[a]){
								this.borderSize[a][u][i][e][p][o][b]=0;
								this.borderSize[a][u][i][e][p][o][b]=borderSize(this.allmyBPossibilities[a][u][i][e][p][o][b],this.ia );//this.allmyIAPossibilities[a][u][i][e][p][o][b]
								if (this.borderSize[a][u][i][e][p][o][b]>this.biggestBorderSize){
									this.borderSize[a][u][i][e][p][o][b]=borderSize(this.allmyBPossibilities[a][u][i][e][p][o][b], this.ia);//this.allmyIAPossibilities[a][u][i][e][p][o][b]
									this.biggestBorderSize=this.borderSize[a][u][i][e][p][o][b];
									bestChoiceBorder=a;
									bestBorderChoiceModifs=this.myModifications[a][u][i][e][p][o][b];
									
									if(getNbControledtiles(allmyBPossibilities[a][u][i][e][p][o][b], ia)>this.etape2 && !profondeurB2Decremente && profondeurB2>0){
										profondeurB2--;
										profondeurB2Decremente=true;
										if(!Main.onlyResultDisplay)	{
											System.out.println(profondeurB2);
										}
									}
								}
								if(this.myModifications[a][u][i][e][p][o][b]>this.myBiggestDifference){ //calcule le plus grand nombre de changements
								this.myBiggestDifference=this.myModifications[a][u][i][e][p][o][b];
								bestChoiceAmount=a;
								bestAmountChoiceModifs=this.myModifications[a][u][i][e][p][o][b];
								}
								}
								
							}}}}}}}
		if (bestAmountChoiceModifs>3*bestBorderChoiceModifs){
			if(!Main.onlyResultDisplay)	{
				System.out.println("choix amount");
			}
			return bestChoiceAmount;
		}
		if(!Main.onlyResultDisplay)	{
			System.out.println("choix border");
		}
		if(!Main.onlyResultDisplay){
			System.out.println("temps border calcul : "+(System.currentTimeMillis()-timebegin));
		}
		return bestChoiceBorder;
	}
	private int bestAmountChoice() {
		boolean profondeurA2Decremente=false;
		boolean[] possibleChoices=new boolean[6];
		int bestChoice=0;
		for (int a = 0; a < 6; a++) {
			for (int u = 0; u < 6; u++) {
				for (int i = 0; i < 6; i++) {
					for (int e = 0; e < 6; e++) {
						for (int p = 0; p < 6; p++) {
							for (int o = 0; o < 6; o++) {
								for (int b = 0; b < 6; b++) {
							this.nbEtapes++;
								possibleChoices=this.ia.possibleChoices();
								if(possibleChoices[a]){
								if(this.myModifications[a][u][i][e][p][o][b]>this.myBiggestDifference){ //calcule le plus grand nombre de changements
								this.myBiggestDifference=this.myModifications[a][u][i][e][p][o][b];
								bestChoice=a;
								
								if(getNbControledtiles(allmyBPossibilities[a][u][i][e][p][o][b], ia)>this.etape3 && !profondeurA2Decremente && profondeurA2>0){
									profondeurA2--;
									profondeurA2Decremente=true;
									if(!Main.onlyResultDisplay)	{
										System.out.println(profondeurA2);
									}
								}
								
								}
								}
							}}}}}}}
		return bestChoice;
		
	}
	

	
	
	private int borderSize(Board board,Player player) {
		//int[][]border=new int[this.nbTile][2];
		/*for (int i = 0; i <this.nbTile; i++) {
			border[i][0]=-1;
			border[i][1]=-1;
		}*/
		int size=0;
		for (int i = 0; i < board.length(); i++) {
			for (int u = 0; u < board.height(); u++) {
				if (board.tableControl(i, u)==player.player() &&
						//isNewCoordonnee(i,u,border) && 
						board.neighborType(i, u,0)	
						){
					//border[size][0]=i;
					//border[size][1]=u;
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
	
	
	private void fill(int a, int u, int i, int e, int p,int o,int b,int color) {
		//this.allmyIAPossibilities[a][u][i][e][p][o][b].setTurnParameters(this.allmyBPossibilities[a][u][i][e][p][o][b][o]);
		if(nbControledtiles<30){
				this.myModifications[a][u][i][e][p][o][b]+=this.allmyBPossibilities[a][u][i][e][p][o][b].setControl(Board.couleurs.charAt(color),this.ia,false );//this.allmyIAPossibilities[a][u][i][e][p][o][b]
	
		}
		else{
			this.myModifications[a][u][i][e][p][o][b]+=this.allmyBPossibilities[a][u][i][e][p][o][b].setControl(Board.couleurs.charAt(color),this.ia,false );//this.allmyIAPossibilities[a][u][i][e][p][o][b]

		}
		this.allmodifs+=this.myModifications[a][u][i][e][p][o][b];
		this.allmyBPossibilities[a][u][i][e][p][o][b].setCouleur(Board.couleurs.charAt(color),this.ia);	// this.allmyIAPossibilities[a][u][i][e][p][o][b]
		
	}

	private int maybeIWillUseHisBestChoiceToPissHimOff( Board board,IA iaToClone,Player opponnentToClone,int myBestChoice){
		float facteur=0;
		if(nbControledtiles<etape3){
			facteur=(float)2.5;
		}
		if(nbControledtiles>etape3){
			facteur=(float) 1.3;
		}
		
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
		if ((float)(facteur*(float)mbc)<=(float)h1bc && (float)ih1bc>(float)(facteur*(float)h2bc)){
			return Ch1bc;
			
		}
		return Cmbc;
		
	}

}