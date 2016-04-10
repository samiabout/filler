package filler;

import java.text.spi.NumberFormatProvider;
import java.util.Random;

import org.w3c.dom.css.ElementCSSInlineStyle;

import com.sun.glass.ui.CommonDialogs.Type;
import com.sun.jndi.url.iiopname.iiopnameURLContextFactory;


public class Board {

	private int noTable;
	

	
	private int tableControl[][]=new int[100][100];
	private char table[][]=new char[100][100];
	
	private int height=10;//default value
	private int length=10 ;//default value
	boolean hexagonal=false;

	
	final static String couleurs=new String("auiepo");
	
	public Board(int noTable) {
		this.noTable=noTable;

	}
//覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧
//覧覧覧覧覧覧覧覧覧覧覧覧揚etters & setters
//覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧	
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

//覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧
//覧覧覧覧覧覧覧覧覧覧覧揚ame initialization
//覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧
	public char[][] creeTable(int nbPlayers){//used only in setGame()
		Random r=new Random();
		//String couleurs="auiepo";//"rojvbi"
		for (int i=0; i<this.height;i++){
			for(int j=0; j<this.length;j++){
					table[i][j]=couleurs.charAt(r.nextInt(couleurs.length()));
				}
		}
		if (
				(table[0][0]==table[this.height-1][this.length-1])			||		//2 players
				(	( 	table[this.height-1][0]==table[0][0] 				|| 
						table[this.height-1][0]==table[this.height-1][this.length-1] ) 	&& nbPlayers!=1 )||	//3 or 4 players//joueur 3, joueur en bas gauche
				(	(	table[0][this.length-1]==table[0][0]					|| 
						table[0][this.length-1]==table[this.height-1][0]			|| 
						table[0][this.length-1]==table[this.height-1][this.length-1] ) && nbPlayers==4)
				)
				
						{return creeTable(nbPlayers);}//test que les deux joueurs n'ont pas la m麥e couleur de d駱art
		
		else{return table;
			}
		}
	
	
	public void creeTableControl(int nbPlayers){//used only in setGame()
			this.tableControl[0][0]=1;
			this.tableControl[this.height-1][this.length-1]=2;
		if(nbPlayers!=2){
			this.tableControl[this.height-1][0]=3;
		}
		if(nbPlayers==4){
			this.tableControl[0][this.length-1]=4;
		}
	}
	

//覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧
//覧覧覧覧覧覧覧覧覧覧擁n-game modifications
//覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧

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
    	for (int i = 0; i < this.height; i++) {
    		for (int j = 0; j < this.length; j++) {
				if (islet(i, j, player)){System.out.println("a");
					this.tableControl[i][j]=6;//player.player();
				}
			}
			
		}
	}

//覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧
//覧覧覧覧覧覧覧覧覧覧覧覧覧advanced mapping
//覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧
	
	public boolean neighborType(int i,int j,int type) {//does the tile have a neighbor of number type?
		//int type=this.tableControl[i][j];
		if (i>0)				{	if (this.tableControl[i-1][j]==type)	{return true;} }
		if (i<this.height-1)	{	if (this.tableControl[i+1][j]==type)	{return true;} }
		if (j>0)				{	if (this.tableControl[i][j-1]==type)	{return true;} }
		if (j<this.length-1)	{	if (this.tableControl[i][j+1]==type)	{return true;} }
		if (hexagonal){
			//to do
		}
		return false;
	}
	
	

	
	public boolean islet(int i, int j, Player player) {//pr駱are la fonction isletParameterized() avec les param鑼res n馗駸saires
		int ibeginning = i;
		int jbeginning = j;
		int type = 0;
		boolean beginning = true;
		return  isletParameterized(	i, 			//ordonn馥
									j,			//abscisse
									i,			//la lecture a une direction, elle se fait en foncton de la position de la case pr馗馘ent
									j+1,		//ici, on suppose qu'elle 騁ait en dessous
									ibeginning,	//ordonn馥 de d駱art pour savoir quand on a fait un tour
									jbeginning, //ordonn馥 de d駱art pour savoir quand on a fait un tour
									player, 	//num駻o du joueur qui encercle l'isle
									type, 		//num駻o des cases de l'ile (ici 0)
									beginning); //savoir on a boucl� =>commence � true puis est � false d鑚 le deuxi鑪e test
	}
	
	
	public boolean isletParameterized(int i, int j,int iprevious,int jprevious, int ibeginning,int jbeginning,Player player,int type, boolean beginning) {//does the tile belong to an islet?
		
		//clockwise turning, to circle the islet
		if (this.tableControl[i][j]==type  &&  //test que la case de d駱art n'appartient � personne et que on a pas d駛� fait un tour pour 騅iter de tourner en rond ind馭iniment
			!(this.tableControl[i][j]==this.tableControl[ibeginning][jbeginning] && !beginning)){//clockwise turning to circle the islet (nand)
			
			int start = 0; //d騁ermine la position de la case pr馗馘ente pour chosir par quel bout on commence
			if (j==jprevious){
				if (iprevious==i-1){start=2;}//case pr馗馘ente 騁ait � gauche
				if (iprevious==i+1){start=4;}//� droite
			}
			if (i==iprevious){
				if (jprevious==j+1){start=1;}//en bas
				if (jprevious==j-1){start=3;}//en haut
			}
			
			for (int k = start; k < 4+start; k++) {//permet de commencer par la case d駱art
				switch (k%4) {
				
				case 1:
					if (i<this.height-1)	{	if (this.tableControl[i+1][j]==type &&   
													!this.neighborType(i+1, j,player.opponent1()) &&
													!this.neighborType(i+1, j,player.opponent2()) &&
													!this.neighborType(i+1, j,player.opponent3()) )
														{return isletParameterized(i+1, j,i,j, ibeginning, jbeginning, player, type,false);} }
					break;
				case 2:
					if (j>0)				{	if (this.tableControl[i][j-1]==type &&   
													!this.neighborType(i, j-1,player.opponent1()) &&
													!this.neighborType(i, j-1,player.opponent2()) &&
													!this.neighborType(i, j-1,player.opponent3()) )
														{return isletParameterized(i, j-1,i,j, ibeginning, jbeginning, player, type,false);} }
					break;					
				case 3:
					if (i>0)				{	if (this.tableControl[i-1][j]==type &&   //if the tile==0 neighbor no opponent as neighbor
													!this.neighborType(i-1, j,player.opponent1()) &&
													!this.neighborType(i-1, j,player.opponent2()) &&
													!this.neighborType(i-1, j,player.opponent3()) )
														{return isletParameterized(i-1, j,i,j, ibeginning, jbeginning, player, type,false);} //therefore, we test the neighbor
					break;
				}
				case 0:
					if (j<this.length-1)	{	if (this.tableControl[i][j+1]==type &&   
													!this.neighborType(i, j+1,player.opponent1()) &&
													!this.neighborType(i, j+1,player.opponent2()) &&
													!this.neighborType(i, j+1,player.opponent3()) )
														{return isletParameterized(i, j+1,i,j, ibeginning, jbeginning, player, type,false);} }		
					break;
					


					}//end switch
				}//end for
		}
		if(this.tableControl[i][j]==type  &&  this.tableControl[i][j]==this.tableControl[ibeginning][jbeginning] && !beginning){return true;}
		System.out.println("o駱o");
		return false;
	}
	
//覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧
//覧覧覧覧覧覧覧覧覧覧覧覧覧覧Boards display
//覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧
	
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
	
//覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧
//覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧裕he�End
//覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧覧	
	

}
