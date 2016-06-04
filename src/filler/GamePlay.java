package filler;

import java.lang.reflect.ParameterizedType;
import java.util.Scanner;

public class GamePlay {

	long allGameTime = 0;
	static Scanner sc  = new Scanner(System.in);
	
	public int nbPlayers=2;
	public int length=10;
	public int height=10;
	
	public boolean hexagonal=false;
	public boolean islet=false;
	public boolean obstacles=false;
	public double obstaclesAmount=30;
	public boolean getOldSave=false;
	public MyConnector aConnection=new MyConnector(true);
	

	public boolean playerIA[]={false,false}; 
	public boolean ialevel[]={true,true};//ia difficile ou non
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
			System.out.println("recup�rer un sauvegarde? (true/false)");
			getOldSave=sc.nextBoolean();
			if(!getOldSave){
				Main.playConnected=false;
				playerIA[0]=false;playerIA[1]=false;
				System.out.println("Choisir le nombre de joueurs (2, 3 ou 4) ");
			nbPlayers = sc.nextInt();
			if(nbPlayers==2){
				playerIA[0]=false;playerIA[1]=false;
				System.out.println("voulez vous une IA (0,1,2)");
				int nbIA=sc.nextInt();
				if (nbIA==2){
					playerIA[0]=true;playerIA[1]=true;
					System.out.println("level IA 1 difficile? (true,false)");
					ialevel[0]=sc.nextBoolean();
					System.out.println("level IA 2 difficile? (true,false)");
					ialevel[1]=sc.nextBoolean();
					
				}
				if(nbIA==1){
					System.out.println("Joueur 1 ou 2? (1,2)");
					int plIA=sc.nextInt();
					if (plIA==2){
						playerIA[1]=true;playerIA[0]=false;
					}
					else{
						playerIA[0]=true;playerIA[1]=false;
					}
					ialevel[0]=false;ialevel[1]=false;
					System.out.println("IA difficile? (true,false)");
						boolean level=sc.nextBoolean();
						ialevel[0]=playerIA[0]&&level;
						ialevel[1]=playerIA[1]&&level;
				}
			}

				System.out.println("Choisir la taille du plateau(hauteur puis longueur )");
			height=sc.nextInt();
			length=sc.nextInt();
				System.out.println("case h�xagonales?");
			hexagonal=sc.nextBoolean();
				System.out.println("obstacles?");
			obstacles=sc.nextBoolean();
			if(obstacles){
				System.out.println("quantit� d'obstacles? (en%, max 30) ");
			obstaclesAmount=sc.nextInt();					
			}
				System.out.println("case encercl�e sont control�e? (true,false)");
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
	
		if(!getOldSave){
			
			interfaceG= new Interface(board,2.5);
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
	
	 
	

	 
	 if(playerIA[0]){
		 tabJeu[0]=new IA(1,nbPlayers, interfaceG);
	 }
	 else{
		 tabJeu[0]=new Player(1,nbPlayers, interfaceG);
	 }
	 if(playerIA[1]){
		 tabJeu[1]=new IA(2,nbPlayers, interfaceG);
	 }
	 else{
		 tabJeu[1]=new Player(2,nbPlayers, interfaceG);
	 }
	tabJeu[2]=new Player(3,nbPlayers, interfaceG);
	tabJeu[3]=new Player(4,nbPlayers, interfaceG);
	
	
	if(!getOldSave){
			for (int i = 0; i < nbPlayers; i++) {//obtenir les cases voisines de d�but de partie
		
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
		if(Main.playConnected&&!game.playerConnected[i]  ){//adversaire connect�
			game.tabJeu[i].montreDemandeCouleur();
			choix=game.aConnection.getMove();
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
				System.out.println("���������������>����������>>>mauvais envoi");
				checkSent=game.aConnection.sendMove(Board.couleurs.charAt((int)(Math.random() *6)));
			}
		}
		
		if(!Main.onlyResultDisplay){System.out.println("choisit "+choix);}
		int nbModifs=game.board.setControl(choix,game.tabJeu[i]);//rajoute toutes les nouvelles cases control�es � tableControl
		if(!Main.onlyResultDisplay){System.out.println("nb modifs "+ nbModifs);}
		game.board.setCouleur(choix,game.tabJeu[i], game.interfaceG);
		game.board.toMaj();	
		return false;
	}
	
	
	
	
	
	
	
}
