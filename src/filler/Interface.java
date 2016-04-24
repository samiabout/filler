package filler;

import java.util.concurrent.TimeUnit;

import com.sun.swing.internal.plaf.metal.resources.metal;

import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdDraw3D;

public class Interface {
	
	double squareSizeI;
	double squareSizeJ;
	Board board;

	public Interface(Board board,double taille) {
		if(Main.graphicDisplay){
		this.board=board;
		this.squareSizeI=1/((double)this.board.height()*2);
		this.squareSizeJ=1/((double)this.board.length()*2);
		

        int canvasSize=(int) (512*taille);
        
        if(this.board.length()>=(double)this.board.height()){
        	StdDraw.setCanvasSize((int)(canvasSize*((double)this.board.length())/((double)this.board.height()))  ,canvasSize );	
        }
        else StdDraw.setCanvasSize((int)(canvasSize*1.11),(int)(canvasSize *((double)this.board.height())/((double)this.board.length())));		
		StdDraw.setYscale(0,1.11);
			
		this.setDiplayBoard();			
		}
	}
	
	void setDiplayBoard ()  {

		for (int i=this.board.height()-1 ; i>=0;i--) {
			for (int j = 0; j < this.board.length(); j++) {
					setPenColor(i,j);
					if (board.tableControl(i,j)==-1){
						StdDraw.setPenColor(StdDraw.BLACK);
					}
					if(board.tableControl(i,j)==-2){
						StdDraw.setPenColor(StdDraw.WHITE);
					}
					StdDraw.filledRectangle((j+0.5)/((double)this.board.length()),1-(i+0.5)/((double)this.board.height()) , this.squareSizeJ, this.squareSizeI);	
					if(board.tableControl(i,j)>0){
						StdDraw.setPenColor(StdDraw.BLACK);
						StdDraw.text((j+0.5)/((double)this.board.length()),1-(i+0.5)/((double)this.board.height()), ""+this.board.tableControl(i, j));
					}
				}
			}
	}
	void setPenColor(int i, int j){
					switch (Board.couleurs.indexOf(Character.toLowerCase(this.board.getTableElement(i, j)))) {
					case 0:
						StdDraw.setPenColor(StdDraw.RED);//a
						break;
					case 1:
						StdDraw.setPenColor(StdDraw.ORANGE);//u
						break;
					case 2:
						StdDraw.setPenColor(StdDraw.YELLOW);//i
						break;
					case 3:
						StdDraw.setPenColor(StdDraw.GREEN);//e
						break;
					case 4:
						StdDraw.setPenColor(StdDraw.BLUE);//p
						break;
					case 5:
						StdDraw.setPenColor(StdDraw.MAGENTA);//o
					default:						
						break;
		}
	}
	
	void displayCommands(boolean[] possibleChoices){
		double displayBegining=0.5-2.5*this.squareSizeJ*2;
		for (int i=0; i < 6; i++) {
			setPenColor(Board.couleurs.charAt(i));
			StdDraw.filledRectangle((double)i*squareSizeJ*2+displayBegining,1.06 , this.squareSizeJ, this.squareSizeI);	
			StdDraw.setPenColor(StdDraw.BLACK);
			if (possibleChoices[i]==true){
				//StdDraw.rectangle((double)i*squareSizeJ*2+displayBegining,1.06 , this.squareSizeJ, this.squareSizeI);	
			}
			else {
				StdDraw.filledCircle((double)i*squareSizeJ*2+displayBegining,1.06 , this.squareSizeJ);	
			}
		}
	}
	
	char choseColor(boolean[] possibleChoices){
		double x = StdDraw.mouseX();
		double y = StdDraw.mouseY();
		double displayBegining=0.5-2.5*this.squareSizeJ*2;
		//double hoverBegining=0.5-3*this.squareSizeJ*2;
		for (int i=0; i < 6; i++) {
			double mx=(double)i*squareSizeJ*2+displayBegining;
			double my=1.06;
			double hsx=this.squareSizeJ;//hover size
			double hsy=this.squareSizeI;
			//setPenColor(Board.couleurs.charAt(i));
			//StdDraw.filledRectangle((double)i*squareSizeJ*2+displayBegining,1.06 , this.squareSizeJ, this.squareSizeI);	
			StdDraw.setPenColor(StdDraw.BLACK);
			if (possibleChoices[i]==true && mx-hsx<x && x<mx+hsx && my-hsy<y && y<my+hsy){
				//StdDraw.rectangle(mx,my , hsx, hsy);	
				if (StdDraw.mousePressed()){
					return Board.couleurs.charAt(i);
				}
			}
		}
        try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			System.out.println("interuption");
			//e.printStackTrace();
		}
        StdDraw.setPenColor(StdDraw.BLACK);
		return choseColor(possibleChoices);
	}
	
	
	
	void setPenColor(char choix){ 
		switch (Board.couleurs.indexOf(choix)) {
		case 0:
			StdDraw.setPenColor(StdDraw.RED);//a
			break;
		case 1:
			StdDraw.setPenColor(StdDraw.ORANGE);//u
			break;
		case 2:
			StdDraw.setPenColor(StdDraw.YELLOW);//i
			break;
		case 3:
			StdDraw.setPenColor(StdDraw.GREEN);//e
			break;
		case 4:
			StdDraw.setPenColor(StdDraw.BLUE);//p
			break;
		case 5:
			StdDraw.setPenColor(StdDraw.MAGENTA);//o
		default:						
			break;
}
	}
	void changeColor(int i,int j,char choix){
		setPenColor(choix);
		StdDraw.filledRectangle((j+0.5)/((double)this.board.length()),1-(i+0.5)/((double)this.board.height()) , this.squareSizeJ, this.squareSizeI);	
	}
	
	
	
	
}
