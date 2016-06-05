package filler;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.text.spi.NumberFormatProvider;
import java.util.Arrays;
import java.util.Random;

import org.w3c.dom.css.ElementCSSInlineStyle;

import com.sun.glass.ui.CommonDialogs.Type;
import com.sun.jndi.url.iiopname.iiopnameURLContextFactory;
import com.sun.org.apache.xalan.internal.utils.FeatureManager.Feature;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.BooleanType;
import com.sun.xml.internal.bind.v2.model.util.ArrayInfoUtil;

import jdk.internal.org.objectweb.asm.tree.IntInsnNode;


public class Board implements Serializable{
private boolean printDebug=true;
	int nbPlayers;//default value
	boolean islet;
	
	static int caca=6;
	
	private int tableControl[][]=new int[50][50];
	private char table[][]=new char[50][50];
	
	private int height;//default value
	private int length;//default value
	
	//avancé
	boolean hexagonal;
	boolean obstacles;
	double obstaclesAmount=0;
	
	final static String couleurs=new String("rojvbi");// auiepo  roygbm //  /!\ do not use 's' nor 'w'
	
//——————————————————————————————————————————
//——————————————————————————————constructors
//——————————————————————————————————————————	
	
	public Board(int height, int length, int nbPlayers, boolean hexagonal, boolean obstacles , double obstaclesAmount, boolean islet,MyConnector aConnector) {
		this.printDebug=true;
		this.height=height;
		this.length=length;
		this.nbPlayers=nbPlayers;
		
		this.hexagonal=hexagonal;
		this.islet=islet;
		this.obstacles=obstacles;
		this.obstaclesAmount=obstaclesAmount;
		
		this.creeTableControl(nbPlayers);
		if(Main.playConnected){
			this.table=aConnector.importTable(height);
		}
		else{
			this.creeTable(nbPlayers);
		}
		
		this.setObstacles();

		this.toMaj();	
		
		if(!Main.onlyResultDisplay){
		this.afficheTable();
			System.out.println();
		//this.afficheTableControl();			
		}

	}
	
	public Board() {
		
	}
	
	public Board(Board clonedBoard){  
		this.height=clonedBoard.height;
		this.length=clonedBoard.length;
		this.nbPlayers=clonedBoard.nbPlayers;
		
		this.hexagonal=clonedBoard.hexagonal;
		this.islet=clonedBoard.islet;
		this.obstacles=clonedBoard.obstacles;
		this.obstaclesAmount=clonedBoard.obstaclesAmount;
		
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.length; j++) {
				this.table[i][j]=clonedBoard.table[i][j];
				this.tableControl[i][j]=clonedBoard.tableControl[i][j];
			}
		}
	}
//——————————————————————————————————————————
//—————————————————————————getters & setters
//——————————————————————————————————————————	
	public char[][] table(){
		return this.table;
	}
	
	public char table(int i,int j) {
		return this.table[i][j];	
	}
	
	public char getTableElement(int i, int j) {
		return this.table[i][j];
	}
	
	public int[][] tableControl(){
		return this.tableControl;
	}
	
	public int	tableControl(int i,int j) {
		return this.tableControl[i][j];
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
	
	public void setIslet(boolean islett) {
		this.islet=islett;
	}
	
	public boolean getIslet(){
		return this.islet;
	}

//——————————————————————————————————————————
//———————————————————————game initialization
//——————————————————————————————————————————
	
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
						table[this.height-1][0]==table[this.height-1][this.length-1] ) 	&& nbPlayers!=2 )||	//3 or 4 players//joueur 3, joueur en bas gauche
				(	(	table[0][this.length-1]==table[0][0]					|| 
						table[0][this.length-1]==table[this.height-1][0]			|| 
						table[0][this.length-1]==table[this.height-1][this.length-1] ) && nbPlayers==4)
				)
				
						{return creeTable(nbPlayers);}//test que les deux joueurs n'ont pas la même couleur de départ
		else{
			return table;
			}
		}
	

	
	//mappage avancé
	public boolean isDeadable(int i, int j, int nbPlayers) {//la case est au bord du terrain et éloignée de 5 du départ
		if(		(i<5 && j<5 )	||  
				(i>this.height-6 && j>this.length-6) ||
				(i>this.height-6 && j<5 && nbPlayers!=2) ||
				(i<5 && j>this.length-6 && nbPlayers==4) 
				){
			return false;
		}
		else{
			return true;
		}
	}
	
	public boolean probability (double proba){
		double nb=Math.random();
		if (nb*100<proba){
			return true;
		}
		else return false;
	}
	
	public void setObstacles() {
		if(obstacles){
			
			double quantity=(obstaclesAmount/100);
			if(quantity>0.3){quantity=0.3; }
			
			int nbDeadTiles=0;
				
			int nbTest=0;
			int nbWhile=0;
			
			System.out.println(quantity);
			System.out.println((this.height*this.length-4*5*5)*quantity);
			while(  nbDeadTiles<( (this.height*this.length-4*5*5)*quantity )  ){ //on tue 10% des cases tuables
				nbWhile++;
				for (int i = 0; i < this.height; i++) {
					for (int j = 0; j < this.length; j++) {
						nbTest++;
						if(isDeadable(i, j, this.nbPlayers) && probability(0.005) ){//toute les cases tuables du plateau
							this.tableControl[i][j]=-1;
							nbDeadTiles++;
						}
						if(isOnBorder(i, j)&& isDeadable(i, j, this.nbPlayers) && probability(0.07)){//la case est sur le bord est tuable (anciennement 0.05)
							this.tableControl[i][j]=-2;
							nbDeadTiles++;
						}
						if(!isOnBorder(i, j) && isDeadable(i, j, this.nbPlayers) && neighborType(i, j, -1) && probability(0.2)){//la case est voisine à une case morte et est tuable
							this.tableControl[i][j]=-1;
							nbDeadTiles++;
						}
						if(isOnBorder(i, j) && isDeadable(i, j, this.nbPlayers) && neighborType(i, j, -2) && probability(0.3)){//la case est voisine à une case morte2du bord et est tuable
							this.tableControl[i][j]=-2;
							nbDeadTiles++;
						}
						if(!isOnBorder(i, j) && isDeadable(i, j, this.nbPlayers) && neighborType(i, j, -2) && probability(0.2)){//la case est voisine à une case morte du bord et est tuable
							this.tableControl[i][j]=-2;
							nbDeadTiles++;
						}

					}
				}
			}
			System.out.println(nbTest);
			System.out.println(nbWhile);
			
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
					if (this.tableControl[i][j]==-1){
						this.table[i][j]=' ';
					}
				}
		}
	}

	public void setCouleur(char choix,Player player, Interface interfaceG){//transforme le tableau avec les modifications couleurs
		int noPlayer = player.player();
		for (int i=0; i<this.height;i++){
			for(int j=0; j<this.length;j++){
					if (this.tableControl[i][j]==noPlayer){
						this.table[i][j]=choix;
						if(Main.graphicDisplay){
						interfaceG.changeColor(i, j, choix);	
						}
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
	
	
	public int setControl(char choix, Player player){//Board//transforme le tableauControl avec les modifications nouveau control
		int nbModifs=0;
		int noPlayer = player.player();
    	boolean finModif=false;
    	while (!finModif){
    		finModif=true;
        	for (int i=this.height; i>=0;i--){			
        		for(int j=this.length; j>=0;j--){		
    				if (this.tableControl[i][j]==noPlayer){	//la case doit appartenir au joueur
    					if (i>0)				{	if (this.table[i-1][j]==choix && this.tableControl[i-1][j]==0)	{this.tableControl[i-1][j]=noPlayer;nbModifs++;finModif=false;} }
    					if (i<this.height-1)	{	if (this.table[i+1][j]==choix && this.tableControl[i+1][j]==0)	{this.tableControl[i+1][j]=noPlayer;nbModifs++;finModif=false;} }
    					if (j>0)				{	if (this.table[i][j-1]==choix && this.tableControl[i][j-1]==0)	{this.tableControl[i][j-1]=noPlayer;nbModifs++;finModif=false;} }
    					if (j<this.length-1)	{	if (this.table[i][j+1]==choix && this.tableControl[i][j+1]==0)	{this.tableControl[i][j+1]=noPlayer;nbModifs++;finModif=false;} }
    					if (hexagonal){
    						if(j%2==1) {
    							if(j>0 && i>0){
    								if (this.table[i-1][j-1]==choix && this.tableControl[i-1][j-1]==0)	{this.tableControl[i-1][j-1]=noPlayer;nbModifs++;finModif=false;} 
    							}
								if(j<this.length-1 && i>0){
    								if (this.table[i-1][j+1]==choix && this.tableControl[i-1][j+1]==0)	{this.tableControl[i-1][j+1]=noPlayer;nbModifs++;finModif=false;} 
    							}
    						}
    						if(j%2==0){
       							if(j>0 && i<this.length-1){
    								if (this.table[i+1][j-1]==choix && this.tableControl[i+1][j-1]==0)	{this.tableControl[i+1][j-1]=noPlayer;nbModifs++;finModif=false;} 
       							}
								if(j<this.length-1 && i<this.length-1){
    								if (this.table[i+1][j+1]==choix && this.tableControl[i+1][j+1]==0)	{this.tableControl[i+1][j+1]=noPlayer;nbModifs++;finModif=false;}     							
    							
    							}
    						}
    					
    				}
    				}
    			}
    		}
    	}
    	//boolean islet=true;
    	boolean recordislet=this.islet;
    	if(this.printDebug){System.out.println(islet);}
    	while (this.islet){
    		this.islet=false;
	    	for (int i = 0; i < this.height; i++) {
	    		for (int j = 0; j < this.length; j++) {

	    			if (this.tableControl[i][j]==0){
						if (islet(i, j, player)){if(this.printDebug){System.out.println("a");}
							this.tableControl[i][j]=noPlayer;
							//this.tableControl[i][j]=caca;caca++;//player.player();
							nbModifs++;//position à vérifier
							this.islet=true;
						}
	    			}
	    			
					// System.out.println("b");
				}
				
			}	
    	}
    	this.islet=recordislet;
    	
		return nbModifs;
	}
	
	public void changeColor(int i, int j , char choix){
		this.table[i][j]=choix;
	}
	public void changeTableControl(int i,int j,int nb){
		this.tableControl[i][j]=nb;
	}
	
	
	public void Ireverse(){
		for (int i = 0; i < table.length/2; i++) {
			for (int u = 0; u < table[0].length/2; u++) {
				int tempI = tableControl[i][u];
				char tempC = table[i][u];
				tableControl[i][u] = tableControl[tableControl[0].length-1-u][tableControl.length - 1 - i];
				tableControl[tableControl[0].length-1-u][tableControl.length - 1 - i] = tempI;
				table[i][u] = table[table[0].length-1-u][table.length - 1 - i];
				table[table[0].length-1-u][table.length - 1 - i] = tempC;
				
			}
		}
	}
	

	//——————————————————————————————————————————
	//————————————————————————————Boards display
	//——————————————————————————————————————————
		
		public void afficheTable(){//Board
			for (int i=0; i<this.height;i++){
				for(int j=0; j<this.length;j++){
					System.out.print(this.table[i][j]+" ");
				}
			System.out.println();
				
			}
		}
		
		public void afficheTableControl(){//Board
			for (int i=0; i<this.height;i++){
				for(int j=0; j<this.length;j++){
					if(this.tableControl[i][j]<0){
						System.out.print("  ");
					}
					else System.out.print(this.tableControl[i][j]+ " ");
				}
			System.out.println();
			}
			if (this.islet){
			System.out.println();
			for (int i=0; i<this.height;i++){
				for(int j=0; j<this.length;j++){
					 System.out.print(this.tableControl[i][j]+ " ");
				}
			System.out.println();
			}			
			}

		}	
		
	//——————————————————————————————————————————
	//———————————————————————————————————The End (n'essaie pas de comprendre la suite, ça sert à rien :) )
	//——————————————————————————————————————————	
	
	
	
//——————————————————————————————————————————
//——————————————————————————advanced mapping
//——————————————————————————————————————————
	public boolean isOnBorder(int i, int j) {
		if(i==0 || j==0 ||  i==this.height-1 || j==this.length-1){
			return true;
		}
		else{
			return false;
		}
	}
	
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
	public int neighborTypeAmount(int i,int j,int type) {//how many neighbors
		int amount=0;
		//int type=this.tableControl[i][j];
		if (i>0)				{	if (this.tableControl[i-1][j]==type)	{amount++;} }
		if (i<this.height-1)	{	if (this.tableControl[i+1][j]==type)	{amount++;} }
		if (j>0)				{	if (this.tableControl[i][j-1]==type)	{amount++;} }
		if (j<this.length-1)	{	if (this.tableControl[i][j+1]==type)	{amount++;} }
		if (hexagonal){
			//to do
		}
		return amount;
	}
	
	public int[] neighborTypePlace(int i,int j,int type) {//neighbor position in an array
		//int type=this.tableControl[i][j];
		int[] nbNeighbor=new int[4] ;
		if (i>0)				{	if (this.tableControl[i-1][j]==type)	{nbNeighbor[0]=1;} }
		if (j<this.length-1)	{	if (this.tableControl[i][j+1]==type)	{nbNeighbor[1]=1;} }
		if (i<this.height-1)	{	if (this.tableControl[i+1][j]==type)	{nbNeighbor[2]=1;} }
		if (j>0)				{	if (this.tableControl[i][j-1]==type)	{nbNeighbor[3]=1;} }
		if (hexagonal){
			//to do
		}
		return nbNeighbor;
	}
	
	public boolean isHeadIslet(int i,int j,int type) {  //the detection cannot start anywhere in the isle
		if (neighborTypeAmount(i, j, type)==0){
			return true;
		}
		else if (neighborTypeAmount(i, j, type)==1){
			return true;
		}
		else if(neighborTypeAmount(i, j, type)==2){
		boolean head=false;
		int neighborArray[]=neighborTypePlace(i, j, type);
		for (int k = 0; k <4; k++) {
			if (neighborArray[k]==1 && neighborArray[k]==neighborArray[(k+1)%4]){
				head=true;
			}
		}
		return head;}
		else{
			return false;
		}
	}
	
	public boolean islet(int i, int j, Player player) {//prépare la fonction isletParameterized() avec les paramètres nécéssaires
		int ibeginning = i;
		int jbeginning = j;
		int type = 0;
		//System.out.println(i +" " + j);
		boolean beginning = true;
		if(this.isHeadIslet(i, j, type)){  //2 different previous position depending on the position of the neighbors
			int neighborArray[]=neighborTypePlace(i, j, type);
			if (neighborArray[2]==1 && neighborArray[3]==1){
			if(this.printDebug){System.out.println("est une tête");	}
			return  isletParameterized(	i, 			//ordonnée
										j,			//abscisse
										i,			//la lecture a une direction, elle se fait en foncton de la position de la case précédent
										j+1,		//ici, on suppose qu'elle était en dessous
										ibeginning,	//ordonnée de départ pour savoir quand on a fait un tour
										jbeginning, //ordonnée de départ pour savoir quand on a fait un tour
										player, 	//numéro du joueur qui encercle l'isle
										type, 		//numéro des cases de l'ile (ici 0)
										beginning); //savoir on a bouclé =>commence à true puis est à false dès le deuxième test
			}else {
			if(this.printDebug){System.out.println("est une tête");}
			return  isletParameterized(	i, 			//ordonnée
										j,			//abscisse
										i+1,			//la lecture a une direction, elle se fait en foncton de la position de la case précédent
										j,		//ici, on suppose qu'elle était en dessous
										ibeginning,	//ordonnée de départ pour savoir quand on a fait un tour
										jbeginning, //ordonnée de départ pour savoir quand on a fait un tour
										player, 	//numéro du joueur qui encercle l'isle
										type, 		//numéro des cases de l'ile (ici 0)
										beginning); //savoir on a bouclé =>commence à true puis est à false dès le deuxième test
			}
		}
		else{if(this.printDebug){System.out.println("n'est pas une tête");}return false;}
		//return false;
	}
	
	
	public boolean isletParameterized(int i, int j,int iprevious,int jprevious, int ibeginning,int jbeginning,Player player,int type, boolean beginning) {//does the tile belong to an islet?
		System.out.println(i+" "+j);
		/*System.out.println(player.opponent1() +" "+ player.opponent2() + " " + player.opponent3());
		System.out.println( (this.tableControl[i][j]==type)   +" "+
				!this.neighborType(i, j, player.opponent1()) +" "+
				!this.neighborType(i, j, player.opponent2()) +" "+
				!this.neighborType(i, j, player.opponent3()) );*/

		if(		this.tableControl[i][j]==type &&
				!this.neighborType(i, j, player.opponent1()) &&
				!this.neighborType(i, j, player.opponent2()) &&
				!this.neighborType(i, j, player.opponent3())	  ){
			
			if(this.printDebug){System.out.println(i +" " + j);}
		if (neighborTypeAmount(i, j, type)==0){
			return true;
		}
		
		//clockwise turning, to circle the islet
		//System.out.println( ((i,j]==this.tableControl[ibeginning][jbeginning]) +" "+ !beginning));
		if (
			this.tableControl[i][j]==type  &&  //test que la case de départ n'appartient à personne et que on a pas déjà fait un tour pour éviter de tourner en rond indéfiniment
			!(i==ibeginning && j ==jbeginning && !beginning)){//clockwise turning to circle the islet (nand)
			
			
			//————————————————————————
			if(this.printDebug){System.out.println("c");}
			//————————————————————————
			
			
			int start = 0; //détermine la position de la case précédente pour chosir par quel bout on commence
			if (j==jprevious){
				if (iprevious==i-1){start=3;}//case précédente était à gauche	2
				if (iprevious==i+1){start=1;}//à droite	4
			}
			if (i==iprevious){
				if (jprevious==j+1){start=0;}//en bas	1
				if (jprevious==j-1){start=2;}//en haut	3
			}
			
			for (int k = start; k < 4+start; k++) {//permet de commencer par la case départ
				if(this.printDebug){
					System.out.println(k +"k"+ k%4 + "start" + start);
				}
				switch (k%4) {
				
				case 0:
					if (i<this.height-1)	{	if (this.tableControl[i+1][j]==type )
														{return isletParameterized(i+1, j,i,j, ibeginning, jbeginning, player, type,false);} }
					break;
				case 1:
					if (j>0)				{	if (this.tableControl[i][j-1]==type )
														{return isletParameterized(i, j-1,i,j, ibeginning, jbeginning, player, type,false);} }
					break;					
				case 2:
					if (i>0)				{	if (this.tableControl[i-1][j]==type    //if the tile==0 neighbor no opponent as neighbor
													 )
														{return isletParameterized(i-1, j,i,j, ibeginning, jbeginning, player, type,false);}} //therefore, we test the neighbor
					break;
				
				case 3:
					if (j<this.length-1)	{	if (this.tableControl[i][j+1]==type  )
														{return isletParameterized(i, j+1,i,j, ibeginning, jbeginning, player, type,false);} }		
					break;
					


					}//end switch
				}//end for
		}
		if(this.tableControl[i][j]==type  &&  i==ibeginning && j==jbeginning && !beginning //||
			//this.neighborType(i, j, player.player()) 
				){
			//————————————————————————
			if(this.printDebug){
			System.out.println("deeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");	
			}
			return true;
			//————————————————————————
		}

		
		//————————————————————————
		System.out.println("e");
		//————————————————————————
		
		
		return false;
		}
		return false;
	}
	

	

}
