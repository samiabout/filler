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
	


	static Scanner sc  =  new Scanner(System.in);	
	
	
	
	//variables de déboguage
	
	static boolean graphicDisplay=true;
	static boolean consoleDisplay=false;
	static boolean onlyResultDisplay=false;
	
	static boolean autoset =false;
	
	static boolean playConnected=false;//connnector //(exxp concours IA)
	
	static int g1=0;//nombre de point par joureur
	static int g2=0;

//—————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————//
//–––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––//
//—————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————//
		
	public static void main(String[] args){
		
		int nbParties=0;
		
		while(nbParties<1 || true){
			
		GamePlay game1=new GamePlay();
	
		long allGameTime = System.currentTimeMillis();	
		IAForcast2.profondeurB=5;
		IAForcast2.profondeurA=5;
		IAForcast3.profondeurB2=7;
		IAForcast3.profondeurA2=7;
		System.out.println();
		System.out.print(g1);
		System.out.println(" "+g2);
		
		//default values
		

			boolean fin = false;//utiliser pour boucler jusqu'à la fin du jeu
			int noTour=game1.noTour;
			if(!Main.onlyResultDisplay){
			game1.board.afficheTableControl();
			game1.board.afficheTable();}
			

	        do{
	        	long startTime = System.currentTimeMillis();
	        	boolean weSave=false;
	        	do{
		        	weSave=GamePlay.playgame(noTour%game1.nbPlayers,game1);//game1.ialevel,game1.playerConnected,game1.aConnection
		        	if(weSave){
		        		System.out.println("give a name to your save (or write \"abort\")");
		        		String saveName=sc.next();
		        		if(saveName!="abort") {
		        			Save save=new Save(saveName,game1.board, game1.tabJeu,noTour,System.currentTimeMillis()-game1.allGameTime);
		        		}
		        	}
	        	}while(weSave);
	        	
	        	
	        	noTour++;
	        	if(!onlyResultDisplay){
	        		System.out.println("temps : "+(System.currentTimeMillis()- startTime));
	        	}
	        	
	        	
	        	if(consoleDisplay){
	        		game1.board.afficheTableControl();
    			
	        		game1.board.afficheTable();	        		
	        	}
	        	if(!Main.onlyResultDisplay){
	        	System.out.println("no tour "+noTour);
	        	}
	        	fin=game1.tabJeu[0].indicationGagnant(game1.board); //renvoi un boolean utilisé pour la condition du while
	        	if(!Main.onlyResultDisplay){System.out.println();}
	        
	        }while(!fin);
	        System.out.println("durée de la partie: "+(double)(System.currentTimeMillis()- game1.allGameTime)/1000+" s");
	        System.out.println("nb de tours : "+noTour);
	 nbParties++;       
	}
	}


	
	
}
