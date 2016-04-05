package filler;

import java.util.Random;
import java.util.Scanner;

public class Board {
	
	Scanner sc  = new Scanner(System.in);
	
	static int tableControl[][];
	static char table[][];
	
	static int height=13;//default value
	static int length=13;//default value
	boolean hexagonal=false;
	
	static String couleurs=new String("auiepo");
	
	static int nbJoueurs=Player.nbPlayers;//élément de classe Player->changer nbJoueur par Player.nbPlayers
	
	boolean autoset =false;//Developer
	


	public Board(int[][] tableControl, char[][] table, int height, int lenght, String couleurs, int nbJoueurs) {
		super();
		Board.tableControl = tableControl;
		Board.table = table;
		Board.height = height;
		Board.length = lenght;
		Board.couleurs = couleurs;
		Board.nbJoueurs = nbJoueurs;
	}

//——————————————————————————————————————————
//———————————————————————game initialization
//——————————————————————————————————————————
	public static char[][] creeTable(){//used only in setGame()
		Random r=new Random();
		char table[][] = new char[height][length];
		//String couleurs="auiepo";//"rojvbi"
		for (int i=0; i<height;i++){
			for(int j=0; j<length;j++){
					table[i][j]=couleurs.charAt(r.nextInt(couleurs.length()));
				}
		}
		if (
				(table[0][0]==table[height-1][length-1])									||		//2 players
				(	( 	table[height-1][0]==table[0][0] 				|| 
						table[height-1][0]==table[height-1][length-1] ) 	&& nbJoueurs!=1 )||	//3 or 4 players//joueur 3, joueur en bas gauche
				(	(	table[0][length-1]==table[0][0]					|| 
						table[0][length-1]==table[height-1][0]			|| 
						table[0][length-1]==table[height-1][length-1] ) && nbJoueurs==4)
				)
				
						{return creeTable();}//test que les deux joueurs n'ont pas la même couleur de départ
		
		else{return table;}
	}
	
	public static int[][] creeTableControl(){//used only in setGame()
		if(nbJoueurs==2){
			tableControl[0][0]=1;
			tableControl[height-1][length-1]=2;
		}
		if(nbJoueurs==3){
			tableControl[height-1][0]=3;
		}
		if(nbJoueurs==4){
			tableControl[0][length-1]=4;
		}
		return tableControl;
		
	}
	
	public void setGame(){
		if (autoset=false){
				System.out.println("Choisir le nombre de joueurs");
			nbJoueurs = sc.nextInt();
				System.out.println("Choisir la taille du plateau(longueur puis hauteur)");
			length = sc.nextInt();
			height = sc.nextInt();
			
		creeTable();
		creeTableControl();
		}
	}
//——————————————————————————————————————————
//—————————————————————in-game modifications
//——————————————————————————————————————————

	public static void toMaj(){	//Board//transforme le tableau avec les modifications majuscules
    	for (int i=0; i<height;i++){
			for(int j=0; j<length;j++){
					if (tableControl[i][j]!=0){
						table[i][j]=Character.toUpperCase(table[i][j]);
					}
				}
		}
	}

	public static void setCouleur(char choix,int joueur){//transforme le tableau avec les modifications couleurs
    	for (int i=0; i<height;i++){
			for(int j=0; j<length;j++){
					if (tableControl[i][j]==joueur){
						table[i][j]=choix;
					}
				}
		}				
	}
	
	public static void setControl(char choix,int joueur){//Board//transforme le tableauControl avec les modifications nouveau control
    	boolean finModif=false;
    	while (!finModif){
    		finModif=true;
        	for (int i=0; i<height;i++){			//on balaie toutes les cases
    			for(int j=0; j<length;j++){		
    				if (tableControl[i][j]==joueur){	//la case doit appartenir au joueur
    					if (i>0)		{	if (table[i-1][j]==choix && tableControl[i-1][j]==0)	{tableControl[i-1][j]=joueur;finModif=false;} }
    					if (i<height-1)	{	if (table[i+1][j]==choix && tableControl[i+1][j]==0)	{tableControl[i+1][j]=joueur;finModif=false;} }
    					if (j>0)		{	if (table[i][j-1]==choix && tableControl[i][j-1]==0)	{tableControl[i][j-1]=joueur;finModif=false;} }
    					if (j<length-1)	{	if (table[i][j+1]==choix && tableControl[i][j+1]==0)	{tableControl[i][j+1]=joueur;finModif=false;} }
    				}//add conditions for hexagonal = true;
    			}
    		}
    	}
	}
	
//——————————————————————————————————————————
//————————————————————————————Boards display
//——————————————————————————————————————————
	
	public static void afficheTable(){//Board
		for (int i=0; i<height;i++){
			for(int j=0; j<length;j++){
				System.out.print(table[i][j]+ " ");
			}
		System.out.println();
			
		}
	}
	
	public static void afficheTableControl(){//Board
		for (int i=0; i<height;i++){
			for(int j=0; j<length;j++){
				System.out.print(tableControl[i][j]+ " ");
			}
		System.out.println();
			
		}
	}	
	
	//——————————————————————————————————————————
	//———————————————————————————————————The End
	//——————————————————————————————————————————	
	

}
