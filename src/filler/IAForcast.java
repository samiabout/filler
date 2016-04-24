package filler;

public class IAForcast {
	
	Board[][][][] allBoardsPossibilities=new Board[6][6][6][6];
	IA[][][][] alliaPossibilities=new IA[6][6][6][6];
	IA[][][][] allOponnentPossibilities=new IA[6][6][6][6];
	int[][] iaModifications=new int[6][6];
	int[][] opponnentModifications=new int[6][6];
	int[][][][] differenceModifications=new int[6][6][6][6];
	
	private int complexity;
	
	//Player opponnent;
	//Player ia;
	
	
	char bestChoice = 0;
	int biggestDifference=0;
	
	public IAForcast(Board board, IA iaToClone,Player opponnentToClone,int complexity) {
		this.bestChoice = 0;
		this.biggestDifference=-100;
		this.complexity=complexity;
		createBoardsOfAllPossibilities(board, iaToClone, opponnentToClone);
		
		//fillBoardsOfAllPossibilities (ia);
		//displayAmount();
		//auraitChoisi();
	}
	
	char getBestChoice(){
		return bestChoice;
	}

	void createBoardsOfAllPossibilities (Board board,IA iaToClone,Player opponnent){//level 3
		int nbTours=0;
		boolean[] possibleChoices=new boolean[6];
		int i = 0;
		int j = 0;if (complexity<4){j=5;}
		int k=0;if (complexity<5){k=5;}
		int l=0;if (complexity<6){l=5;}
		for (; l < 6; l++) {
			for (; k < allBoardsPossibilities.length; k++) {
				for (; j < allBoardsPossibilities.length; j++) {
					for (; i < allBoardsPossibilities.length; i++) {
						//create
						this.allBoardsPossibilities[i][j][k][l]=new Board(board);
						this.alliaPossibilities[i][j][k][l]=new IA(iaToClone);
						this.allOponnentPossibilities[i][j][k][l]=new IA(opponnent);
						this.iaModifications[i][k]=0;
						
						//fill
						
							possibleChoices=alliaPossibilities[i][j][k][l].possibleChoices();		
							if(possibleChoices[i]){
									if (this.complexity>=2){
								this.differenceModifications[i][j][k][l]+=this.allBoardsPossibilities[i][j][k][l].setControl(Board.couleurs.charAt(i), alliaPossibilities[i][j][k][l]);
								this.allBoardsPossibilities[i][j][k][l].setCouleur(Board.couleurs.charAt(i), alliaPossibilities[i][j][k][l]);	

									}
	
							
							 if (this.complexity>=3){
								this.allOponnentPossibilities[i][j][k][l].setTurnParameters(this.allBoardsPossibilities[i][j][k][l]);
								this.differenceModifications[i][j][k][l]-=this.allBoardsPossibilities[i][j][k][l].setControl(this.allOponnentPossibilities[i][j][k][l].iaChoseColor(possibleChoices, this.allBoardsPossibilities[i][j][k][l]), allOponnentPossibilities[i][j][k][l]);
								this.allBoardsPossibilities[i][j][k][l].setCouleur(this.allOponnentPossibilities[i][j][k][l].iaChoseColor(possibleChoices, this.allBoardsPossibilities[i][j][k][l]), allOponnentPossibilities[i][j][k][l]);
								   //ceci est un char->this.allOponnentPossibilities[i][j][k][l].iaChoseColor(possibleChoices, this.allBoardsPossibilities[i][j][k][l])								 
							 					}

								possibleChoices=allOponnentPossibilities[i][j][k][l].possibleChoices();
								if (possibleChoices[j]==true){
									if (this.complexity>=4){
									this.alliaPossibilities[i][j][k][l].setTurnParameters(this.allBoardsPossibilities[i][j][k][l]);
									this.differenceModifications[i][j][k][l]+=this.allBoardsPossibilities[i][j][k][l].setControl(Board.couleurs.charAt(j), alliaPossibilities[i][j][k][l]);
									this.allBoardsPossibilities[i][j][k][l].setCouleur(Board.couleurs.charAt(j), alliaPossibilities[i][j][k][l]);										
									}

								
									possibleChoices=allOponnentPossibilities[i][j][k][l].possibleChoices();
									if( possibleChoices[l] ){
										if (this.complexity>=5){
										this.allOponnentPossibilities[i][j][k][l].setTurnParameters(this.allBoardsPossibilities[i][j][k][l]);
										this.differenceModifications[i][j][k][l]-=this.allBoardsPossibilities[i][j][k][l].setControl(Board.couleurs.charAt(l), allOponnentPossibilities[i][j][k][l]);
										this.allBoardsPossibilities[i][j][k][l].setCouleur(Board.couleurs.charAt(j), allOponnentPossibilities[i][j][k][l]);											
										}

										//System.out.println(nbTours++);
										}
								
									}
								}
							//test
							if(this.differenceModifications[i][j][k][l]>this.biggestDifference){
										this.biggestDifference=this.differenceModifications[i][j][k][l];	
										//System.out.println(this.biggestDifference);
										this.bestChoice=Board.couleurs.charAt(i);	
											}							
						
									
					}
				}
			}
		}
	}
	

	
	
	private void displayAmount() {//deboggage
		for (int i = 0; i < allBoardsPossibilities.length; i++) {
			System.out.println();
			System.out.println(" "+Board.couleurs.charAt(i)+" ");
			for (int j = 0; j < allBoardsPossibilities.length; j++) {
				System.out.print(" "+Board.couleurs.charAt(j)+" ");
				for (int k = 0; k < allBoardsPossibilities.length; k++) {
					for (int l = 0; l < allBoardsPossibilities.length; l++) {
						for (int m = 0; m < allBoardsPossibilities.length; m++) {
					System.out.print(Board.couleurs.charAt(k));
					System.out.print(this.differenceModifications[i][j][k][l]);							
						}
					}
				}
			}
		}System.out.println();
	}
	


	
	
	

}
