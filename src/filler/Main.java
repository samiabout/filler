package filler;

import com.sun.org.apache.xpath.internal.functions.FuncFalse;
import com.tdebroc.filler.connector.Connector;
import com.tdebroc.filler.connector.PlayerConnector;
import com.tdebroc.filler.game.Colors;
import com.tdebroc.filler.game.Game;

import edu.princeton.cs.introcs.StdDraw;
import javafx.scene.layout.Border;

import java.util.Random;
import java.util.Scanner;

import org.w3c.dom.css.ElementCSSInlineStyle;

import jdk.internal.dynalink.beans.StaticClass;
import sun.misc.ThreadGroupUtils;
import sun.net.www.content.image.gif;

//import org.omg.CORBA.PUBLIC_MEMBER;


public class Main {
	
	private static Player[] tabJeu=new Player[4];
	private static Board board=new Board();
	private static Interface interfaceG= new Interface();

	static Scanner sc  = new Scanner(System.in);	
	
	static boolean graphicDisplay=true;
	static boolean consoleDisplay=false;
	static boolean onlyResultDisplay=false;
	
	static boolean autoset =false;
	
	static boolean playConnected=false;
	
	static int g1=0;//nombre de point par joureur
	static int g2=0;

//—————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————//
//–––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––//
//—————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————//
		
	public static void main(String[] args){
		
		int nbParties=0;
		while(nbParties<1 || true){
			
		
			
		long allGameTime = System.currentTimeMillis();	
		IAForcast2.profondeurB=5;
		IAForcast2.profondeurA=5;
		System.out.println();
		System.out.print(g1);
		System.out.println(" "+g2);
		
		//default values
		
		int nbPlayers=2;
		int length=13;
		int height=13;

		
		//avancé
		boolean hexagonal=false;
		boolean islet=false;
		boolean obstacles=false;
		double obstaclesAmount=30;
		boolean getOldSave=false;
		

		boolean playerIA[]={false,false}; 
		boolean ialevel[]={true,true};//ia difficile ou non
		boolean playerConnected[]={true,false};//doit transmettre le coup
		//boolean opponentConnected[]={false,true};//doit transmettre le coup
		
		board=new Board();
		Save oldSave=new Save();
		
		
		MyConnector aConnection=new MyConnector(true);
		if(playConnected){
			aConnection=new MyConnector();
		}
		
			if (!autoset){
				System.out.println("recupérer un sauvegarde? (true/false)");
				getOldSave=sc.nextBoolean();
				if(!getOldSave){
					playConnected=false;
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
		
			if(!getOldSave){
				
				interfaceG= new Interface(board,2.5);
				System.out.println("do you want to change the board (true,false)");
				if(sc.nextBoolean()){
					interfaceG.creatBoard(board.height(), board.length());
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
				for (int i = 0; i < nbPlayers; i++) {//obtenir les cases voisines de début de partie
			
				tabJeu[i].setTurnParameters(board);
				board.setControl(tabJeu[i].playerColor,tabJeu[i]);
				board.setCouleur(tabJeu[i].playerColor,tabJeu[i], interfaceG);
			}
		}

			boolean fin = false;//utiliser pour boucler jusqu'à la fin du jeu
			
			int noTour=0;
			
			if(getOldSave){
				board=oldSave.getBoard();
				tabJeu=oldSave.getPlayer();
				noTour=oldSave.getNoTour();
				allGameTime=System.currentTimeMillis()-oldSave.getTime();
				interfaceG= new Interface(board,2.5);	
			}
			
			
			
			board.afficheTableControl();	
	        do{
	        	long startTime = System.currentTimeMillis();
	        	boolean weSave=playgame(noTour%nbPlayers,ialevel,playerConnected,aConnection);
	        	if(weSave){
	        		System.out.println("give a name to your save");
	        		String saveName=sc.next();
	        		Save save=new Save(saveName,board, tabJeu,noTour,System.currentTimeMillis()-allGameTime);
	        	}
	        	
	        	
	        	
	        	noTour++;
	        	if(!onlyResultDisplay){
	        		System.out.println("temps : "+(System.currentTimeMillis()- startTime));
	        	}
	        	
	        	
	        	if(consoleDisplay){
    			board.afficheTableControl();
    			
	        	board.afficheTable();	        		
	        	}
	        	if(!Main.onlyResultDisplay){
	        	System.out.println("no tour "+noTour);
	        	}
	        	fin=tabJeu[0].indicationGagnant(board); //renvoi un boolean utilisé pour la condition du while
	        	if(!Main.onlyResultDisplay){System.out.println();}
	        
	        }while(!fin);
	        System.out.println("durée de la partie: "+(double)(System.currentTimeMillis()- allGameTime)/1000+" s");
	        System.out.println("nb de tours : "+noTour);
	 nbParties++;       
	}
	}
	
	private static boolean playgame(int i,boolean[] ialevel,boolean playerConnected[],MyConnector aConnector){
		char choix; 
		tabJeu[i].setTurnParameters(board);
		if(playConnected&&!playerConnected[i]  ){//adversaire connecté
			tabJeu[i].montreDemandeCouleur();
			choix=aConnector.getMove();
		}
		else if(tabJeu[i] instanceof IA){//IA
			choix=((IA)tabJeu[i]).demandeCouleur(board,ialevel[i],tabJeu[i]);
		}
		else{//joueur humain
			choix=tabJeu[i].demandeCouleur();
			if( choix=='s'){
				return true;
			}
		}
		if(playConnected&&playerConnected[i]  ){
			boolean checkSent=aConnector.sendMove(choix);
			while(!checkSent){
				System.out.println("———————————————>——————————>>>mauvais envoi");
				checkSent=aConnector.sendMove(Board.couleurs.charAt((int)(Math.random() *6)));
			}
		}
		
		if(!Main.onlyResultDisplay){System.out.println("choisit "+choix);}
		int nbModifs=board.setControl(choix,tabJeu[i]);//rajoute toutes les nouvelles cases controlées à tableControl
		if(!Main.onlyResultDisplay){System.out.println("nb modifs "+ nbModifs);}
		board.setCouleur(choix,tabJeu[i], interfaceG);
		board.toMaj();	
		return false;
	}


	
	
}
