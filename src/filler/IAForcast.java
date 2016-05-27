package filler;

import com.sun.corba.se.impl.orb.ParserTable.TestAcceptor1;

import sun.font.TrueTypeFont;

public class IAForcast {
	
	Board[][] allBoardsPossibilities=new Board[6][6];
	IA[][] alliaPossibilities=new IA[6][6];
	IA[][] allOponnentPossibilities=new IA[6][6];
	int[][] iaModifications=new int[6][6];
	int[][] opponnentModifications=new int[6][6];
	int[][] differenceModifications=new int[6][6];
	
	
	Board[][][][][][] allmyBPossibilities= new Board[6][6][6][6][6][6];
	IA[][][][][][] allmyIAPossibilities=new IA[6][6][6][6][6][6];
	int[][][][][][] myModifications=new int[6][6][6][6][6][6];
	private int[][][][][][] myDifferenceModifications=new int[6][6][6][6][6][6];
	private int myBiggestDifference=0;
	
	private int complexity;
	
	//Player opponnent;
	//Player ia;
	
	
	char bestChoice = 0;
	int[]bestChoiceT;
	boolean[]avancee;//le choix nous rapporte des points ou non
	int biggestDifference=0;
	private int allmodifs;

	
	public IAForcast(Board board, IA iaToClone,Player opponnentToClone,int complexity) {
		this.bestChoice = 0;
		this.bestChoiceT=new int[6];
		this.avancee=new boolean[6];
		this.biggestDifference=-100;
		this.complexity=complexity;
		this.allmodifs=0;
		//createBoardsOfAllPossibilities(board, iaToClone, opponnentToClone);
		
		//fillBoardsOfAllPossibilities (ia);
		//displayAmount();
		//auraitChoisi();
	}
	
	char getBestChoice(){
		this.bestChoice=Board.couleurs.charAt(0);
		for (int i = 1; i < allBoardsPossibilities.length; i++) {
			if (this.bestChoiceT[i]>this.bestChoiceT[i-1]){
				this.bestChoice=Board.couleurs.charAt(i);
			}
			if (this.bestChoiceT[i]==this.bestChoiceT[i-1] && avancee[i]==true){
				this.bestChoice=Board.couleurs.charAt(i);
			}
		}
		//System.out.println(this.bestChoice);
		return this.bestChoice;
	}

	void createBoardsOfAllPossibilities (Board board,IA iaToClone,Player opponnent){//level 3
		int nbTours=0;
		boolean[] possibleChoices=new boolean[6];
				for (int i = 0; i < allBoardsPossibilities.length; i++) {
					for (int j = 0; j < allBoardsPossibilities.length; j++) {
						nbTours++;
						this.differenceModifications[i][j]-=1000;
						/*System.out.println("ici");
						System.out.println(i);
						System.out.println(j);
						System.out.println(k);
						System.out.println(l);*/
						//create
						this.allBoardsPossibilities[i][j]=new Board(board);
						this.alliaPossibilities[i][j]=new IA(iaToClone);
						this.allOponnentPossibilities[i][j]=new IA(opponnent);
						this.iaModifications[i][j]=0;
						
						//fill
						this.alliaPossibilities[i][j].setTurnParameters(this.allBoardsPossibilities[i][j]);
							possibleChoices=this.alliaPossibilities[i][j].possibleChoices();
							if(possibleChoices[i]){
								//System.out.println(possibleChoices[i] +""+i);
								this.differenceModifications[i][j]+=1000+this.allBoardsPossibilities[i][j].setControl(Board.couleurs.charAt(i), this.alliaPossibilities[i][j]);
								if(this.allBoardsPossibilities[i][j].setControl(Board.couleurs.charAt(i), this.alliaPossibilities[i][j])==0){
									this.avancee[i]=false;
								}
								else{
									this.avancee[i]=true;
								}
								this.allBoardsPossibilities[i][j].setCouleur(Board.couleurs.charAt(i), this.alliaPossibilities[i][j]);	

							
								this.allOponnentPossibilities[i][j].setTurnParameters(this.allBoardsPossibilities[i][j]);
								possibleChoices=this.allOponnentPossibilities[i][j].possibleChoices();
								this.differenceModifications[i][j]-=this.allBoardsPossibilities[i][j].setControl(this.allOponnentPossibilities[i][j].iaChoseColor(possibleChoices, this.allBoardsPossibilities[i][j]), this.allOponnentPossibilities[i][j]);
								this.allBoardsPossibilities[i][j].setCouleur(this.allOponnentPossibilities[i][j].iaChoseColor(possibleChoices, this.allBoardsPossibilities[i][j]), this.allOponnentPossibilities[i][j]);
								   //ceci est un char->this.allOponnentPossibilities[i][j][k][l].iaChoseColor(possibleChoices, this.allBoardsPossibilities[i][j])								 
							 					

							 
								this.alliaPossibilities[i][j].setTurnParameters(this.allBoardsPossibilities[i][j]);
								possibleChoices=this.alliaPossibilities[i][j].possibleChoices();
								if (possibleChoices[j]==true){
									this.alliaPossibilities[i][j].setTurnParameters(this.allBoardsPossibilities[i][j]);
									this.differenceModifications[i][j]+=this.allBoardsPossibilities[i][j].setControl(Board.couleurs.charAt(j), this.alliaPossibilities[i][j]);
									this.allBoardsPossibilities[i][j].setCouleur(Board.couleurs.charAt(j), this.alliaPossibilities[i][j]);										
								
									}
								}
							//test
							if(this.differenceModifications[i][j]>this.biggestDifference){
								for (int u = 0; u < 6; u++) {
									this.bestChoiceT[u]=0;
								}
								this.biggestDifference=this.differenceModifications[i][j];
								possibleChoices=iaToClone.possibleChoices();
								//System.out.println(this.biggestDifference);
							}							
							if(this.differenceModifications[i][j]==this.biggestDifference){
								this.bestChoiceT[i]+=1;
								
							}
									
					}
				}
				//displayAmount() ;
	}
	
	
	public int myForcast(Board board,IA iaToClone,Player opponnent){//ne prend en compte que mes coups
	//	int nbTours=0;
		
		int bestchoice=0;
		boolean[] possibleChoices=new boolean[6];
				for (int a = 0; a < 6; a++) {
					for (int u = 0; u < 6; u++) {
						for (int i = 0; i < 6; i++) {
							for (int e = 0; e < 6; e++) {
								for (int p = 0; p < 6; p++) {
									for (int o = 0; o < 6; o++) {
					//	nbTours++;
										possibleChoices=this.allmyIAPossibilities[a][u][i][e][p][o].possibleChoices();
										if(possibleChoices[a]){
						this.allmyBPossibilities[a][u][i][e][p][o]=new Board(board);
						this.allmyBPossibilities[a][u][i][e][p][o].setIslet(true);
						this.allmyIAPossibilities[a][u][i][e][p][o]=new IA(iaToClone);
						
						//fill
						fill(a,u,i,e,p,o,a);
						fill(a,u,i,e,p,o,u);
						fill(a,u,i,e,p,o,i);
						fill(a,u,i,e,p,o,e);
						fill(a,u,i,e,p,o,p);
						int testEndBoard=this.allmodifs;
						fill(a,u,i,e,p,o,o);
						if (this.allmodifs==testEndBoard){
							
						}
						//test
						if(this.myDifferenceModifications[a][u][i][e][p][o]>this.myBiggestDifference){
							this.myBiggestDifference=this.myDifferenceModifications[a][u][i][e][p][o];
							bestchoice=a;
							//System.out.println(this.biggestDifference);
						}
							}
					}}}}}}
				return maybeIWillUseHisBestChoiceToPissHimOff(board, iaToClone, opponnent, bestchoice);
	}
	
	private void fill(int a, int u, int i, int e, int p, int o,int color) {
		this.allmyIAPossibilities[a][u][i][e][p][o].setTurnParameters(this.allmyBPossibilities[a][u][i][e][p][o]);
		this.myModifications[a][u][i][e][p][o]+=this.allmyBPossibilities[a][u][i][e][p][o].setControl(Board.couleurs.charAt(color), this.allmyIAPossibilities[a][u][i][e][p][o]);
		this.allmodifs+=this.myModifications[a][u][i][e][p][o];
		this.allmyBPossibilities[a][u][i][e][p][o].setCouleur(Board.couleurs.charAt(color), this.allmyIAPossibilities[a][u][i][e][p][o]);	
		
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

			for (int i = 0; i < 6; i++) {

			
				
					Board testIABoard=new Board(board);
					mbc=testIABoard.setControl(Board.couleurs.charAt(Cmbc),iaToClone);
					
				
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

	
	
	private void displayAmount() {//deboggage
		for (int i = 0; i < this.allBoardsPossibilities.length; i++) {
			//System.out.println();
			System.out.println(" "+Board.couleurs.charAt(i)+" ");
			for (int j = 0; j < this.allBoardsPossibilities.length; j++) {
				System.out.print(" "+Board.couleurs.charAt(j)+" ");
					System.out.print(this.differenceModifications[i][j]);							
			}
			System.out.println();
		}System.out.println();
		for (int b = 0; b < 6; b++) {
			System.out.println(" "+this.bestChoiceT[b]);
		}
	}	

}
