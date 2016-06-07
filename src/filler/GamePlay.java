package filler;

import java.lang.reflect.ParameterizedType;
import java.util.Scanner;

public class GamePlay {

	long allGameTime = 0;
	static Scanner sc  = new Scanner(System.in);
	
	public int nbPlayers=2;
	public int length=24;
	public int height=24;
	
	public boolean hexagonal=false;
	public boolean islet=false;
	public boolean obstacles=false;
	public double obstaclesAmount=30;
	public boolean getOldSave=false;
	public MyConnector aConnection=new MyConnector(true);
	

	public boolean playerIA[]={true,true,false,false}; 
	public int ialevel[]={4,4,1,1};//ia difficile ou non
	public boolean playerConnected[]={true,false};
		//doit transmettre le coup
	
	//boolean opponentConnected[]={false,true};//doit transmettre le coup
	public Interface interfaceG= new Interface();
	public Player[] tabJeu=new Player[4];
	public Board board=new Board();
	public Save oldSave=new Save();
	public int noTour=0 ;
	
	public GamePlay() {
		this.parameterize();
		this.allGameTime = System.currentTimeMillis();
	}
	
	
	private void parameterize(){
		
		if(Main.playConnected){
			aConnection=new MyConnector();
		}
		if (!Main.autoset){
		System.out.println("voulez vous utiliser les paramètres par défaut? (true,false)");
		Main.autoset=sc.nextBoolean();
		}	
		if (!Main.autoset){
			System.out.println("recupérer un sauvegarde? (true/false)");
			getOldSave=sc.nextBoolean();
			if(!getOldSave){
				System.out.println("ici");
					Main.playConnected=false;
					playerIA[0]=false;playerIA[1]=false;
					System.out.println("Choisir le nombre de joueurs (2, 3 ou 4) ");
					nbPlayers = sc.nextInt();
					playerIA[0]=false;playerIA[1]=false;
					System.out.println("voulez vous une ou plusieurs IA?  (true,false)");
					if(sc.nextBoolean()){
						for (int i = 0; i < nbPlayers; i++) {
							System.out.println("voulez-vous le joueur "+(i+1)+" comme IA? (true,false)");
							playerIA[i]=sc.nextBoolean();
							if(playerIA[i]){
								System.out.print("de quel niveau? ");
								if(i<2 && nbPlayers<=2){
									System.out.println("(0 à 3)");
								}else{
									System.out.println("(0 à 2)");
								}
								ialevel[i]=sc.nextInt();
							}
						}
					}
			
					
					
				

			
			
			

				System.out.println("Choisir la taille du plateau(hauteur puis longueur )");
			height=sc.nextInt();
			length=sc.nextInt();
				System.out.println("case héxagonales?");
			hexagonal=sc.nextBoolean();
				System.out.println("obstacles?");
			obstacles=sc.nextBoolean();
			if(obstacles){
				System.out.println("quantité d'obstacles? (en%, max 30) ");
			obstaclesAmount=sc.nextInt();					
			}
				System.out.println("case encerclée sont controlée? (true,false)");
			islet=sc.nextBoolean();

			board=new Board(height,length,nbPlayers,hexagonal,obstacles,obstaclesAmount,islet,aConnection);
			}
			else{
				oldSave=Save.getSave();
			}
		}
		else {
			board=new Board(height,length,nbPlayers,hexagonal,obstacles,obstaclesAmount,islet,aConnection);
		}
	
		if(!getOldSave && !Main.playConnected ){
			
			
			interfaceG= new Interface(board,2.5);
			if(!Main.autoset){
			System.out.println("do you want to change the board (true,false)");
			if(sc.nextBoolean()){
				interfaceG.creatBoard(board.height(), board.length());
		        try {
					Thread.sleep(25);
				} catch (InterruptedException e) {
					System.out.println("interuption");
					//e.printStackTrace();
				}
			}
		}
		}
	
	 
	
	for (int i = 0; i < nbPlayers; i++) {
		if (playerIA[i]){
			tabJeu[i]=new IA(i+1,nbPlayers, interfaceG);
		}
		else{
			tabJeu[i]=new Player(i+1,nbPlayers, interfaceG);
		}
	}	
	 

	
	
	if(!getOldSave){
			for (int i = 0; i < nbPlayers; i++) {//obtenir les cases voisines de début de partie
		
			tabJeu[i].setTurnParameters(board);
			board.setControl(tabJeu[i].playerColor,tabJeu[i]);
			board.setCouleur(tabJeu[i].playerColor,tabJeu[i], interfaceG);
		}
	}

		
		noTour=0;
		
		if(getOldSave){
			board=oldSave.getBoard();
			tabJeu=oldSave.getPlayer();
			noTour=oldSave.getNoTour();
			allGameTime=System.currentTimeMillis()-oldSave.getTime();
			interfaceG= new Interface(board,2.5);	
		}
		
	}

	public static boolean playgame(int i,GamePlay game) {
		char choix; 
		game.tabJeu[i].setTurnParameters(game.board);
		if(Main.playConnected&&!game.playerConnected[i]  ){//adversaire connecté
			game.tabJeu[i].montreDemandeCouleur();
			choix=game.aConnection.getMove(game.tabJeu[i]);
		}
		else if(game.tabJeu[i] instanceof IA){//IA
			choix=((IA)game.tabJeu[i]).demandeCouleur(game.board,game.ialevel[i],game.tabJeu[i]);
		}
		else{//joueur humain
			choix=game.tabJeu[i].demandeCouleur();
			if( choix=='s'){
				return true;
			}
		}
		if(Main.playConnected&&game.playerConnected[i]  ){
			boolean checkSent=game.aConnection.sendMove(choix);
			while(!checkSent){
				System.out.println("———————————————>——————————>>>mauvais envoi");
				choix=Board.couleurs.charAt((int)(Math.random() *6));
				checkSent=game.aConnection.sendMove(choix);
			}
		}
		
		if(!Main.onlyResultDisplay){System.out.println("choisit "+choix);}
		int nbModifs=game.board.setControl(choix,game.tabJeu[i]);//rajoute toutes les nouvelles cases controlées à tableControl
		if(!Main.onlyResultDisplay){System.out.println("nb modifs "+ nbModifs);}
		game.board.setCouleur(choix,game.tabJeu[i], game.interfaceG);
		game.board.toMaj();	
		return false;
	}
	
	
	
	
	
	
	
}
