package filler;

import java.text.spi.NumberFormatProvider;
import java.util.Random;


public class Board {

	private int noTable;
	

	
	private int tableControl[][]=new int[100][100];
	private char table[][]=new char[100][100];
	
	private int height=13;//default value
	private int length=13;//default value
	boolean hexagonal=false;

	
	final static String couleurs=new String("auiepo");
	
	public Board(int noTable) {
		this.noTable=noTable;

	}
//——————————————————————————————————————————
//—————————————————————————getters & setters
//——————————————————————————————————————————	
	public char[][] table(){
		return this.table;
	}
	
	public int[][] tableControl(){
		return this.tableControl;
	}
	
	public int height(){
		return this.height;
	}
	
	public int length(){
		return this.length;
	}
	
	public void height(int height){
		this.height=height;
	}
	
	public void length(int length){
		this.length=length;
	}

//——————————————————————————————————————————
//———————————————————————game initialization
//——————————————————————————————————————————
	public char[][] creeTable(int nbPlayers){//used only in setGame()
		Random r=new Random();
		//String couleurs="auiepo";//"rojvbi"
		for (int i=0; i<this.height;i++){
			for(int j=0; j<this.length;j++){
					table[i][j]=couleurs.charAt(r.nextInt(couleurs.length()));
				}
		}
		if (
				(table[0][0]==table[this.height-1][this.length-1])									||		//2 players
				(	( 	table[this.height-1][0]==table[0][0] 				|| 
						table[this.height-1][0]==table[this.height-1][this.length-1] ) 	&& nbPlayers!=1 )||	//3 or 4 players//joueur 3, joueur en bas gauche
				(	(	table[0][this.length-1]==table[0][0]					|| 
						table[0][this.length-1]==table[this.height-1][0]			|| 
						table[0][this.length-1]==table[this.height-1][this.length-1] ) && nbPlayers==4)
				)
				
						{return creeTable(nbPlayers);}//test que les deux joueurs n'ont pas la même couleur de départ
		
		else{return table;
			}
		}
	
	
	public void creeTableControl(int nbPlayers){//used only in setGame()
		if(nbPlayers==2){
			this.tableControl[0][0]=1;
			this.tableControl[this.height-1][this.length-1]=2;
		}
		if(nbPlayers==3){
			this.tableControl[this.height-1][0]=3;
		}
		if(nbPlayers==4){
			this.tableControl[0][this.length-1]=4;
		}
	}
	

//——————————————————————————————————————————
//—————————————————————in-game modifications
//——————————————————————————————————————————

	public void toMaj(){	//Board//transforme le tableau avec les modifications majuscules
    	for (int i=0; i<this.height;i++){
			for(int j=0; j<this.length;j++){
					if (this.tableControl[i][j]!=0){
						this.table[i][j]=Character.toUpperCase(this.table[i][j]);
					}
				}
		}
	}

	public void setCouleur(char choix,Player player){//transforme le tableau avec les modifications couleurs
    	int noPlayer = player.player();
		for (int i=0; i<this.height;i++){
			for(int j=0; j<this.length;j++){
					if (this.tableControl[i][j]==noPlayer){
						this.table[i][j]=choix;
					}
				}
		}				
	}
	
	public void setControl(char choix, Player player){//Board//transforme le tableauControl avec les modifications nouveau control
		int noPlayer = player.player();
    	boolean finModif=false;
    	while (!finModif){
    		finModif=true;
        	for (int i=0; i<this.height;i++){			//on balaie toutes les cases
    			for(int j=0; j<this.length;j++){		
    				if (this.tableControl[i][j]==noPlayer){	//la case doit appartenir au joueur
    					if (i>0)				{	if (this.table[i-1][j]==choix && this.tableControl[i-1][j]==0)	{this.tableControl[i-1][j]=noPlayer;finModif=false;} }
    					if (i<this.height-1)	{	if (this.table[i+1][j]==choix && this.tableControl[i+1][j]==0)	{this.tableControl[i+1][j]=noPlayer;finModif=false;} }
    					if (j>0)				{	if (this.table[i][j-1]==choix && this.tableControl[i][j-1]==0)	{this.tableControl[i][j-1]=noPlayer;finModif=false;} }
    					if (j<this.length-1)	{	if (this.table[i][j+1]==choix && this.tableControl[i][j+1]==0)	{this.tableControl[i][j+1]=noPlayer;finModif=false;} }
    				}//add conditions for hexagonal = true;
    			}
    		}
    	}
	}
	
//——————————————————————————————————————————
//————————————————————————————Boards display
//——————————————————————————————————————————
	
	public void afficheTable(){//Board
		for (int i=0; i<this.height;i++){
			for(int j=0; j<this.length;j++){
				System.out.print(this.table[i][j]+ " ");
			}
		System.out.println();
			
		}
	}
	
	public void afficheTableControl(){//Board
		for (int i=0; i<this.height;i++){
			for(int j=0; j<this.length;j++){
				System.out.print(this.tableControl[i][j]+ " ");
			}
		System.out.println();
			
		}
	}	
	
//——————————————————————————————————————————
//———————————————————————————————————The End
//——————————————————————————————————————————	
	

}
