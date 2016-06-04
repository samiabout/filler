package filler;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import com.sun.jmx.snmp.tasks.ThreadService;
import com.sun.swing.internal.plaf.metal.resources.metal;

import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdDraw3D;

public class Interface implements Serializable{
	
	double squareSizeI;
	double squareSizeJ;
	Board board;
	boolean hexagonal;
	
	public Interface() {
		
	}

	public Interface(Board board,double taille) {
		if(Main.graphicDisplay){
		this.board=board;
		this.squareSizeI=1/((double)this.board.height()*2);
		this.squareSizeJ=1/((double)this.board.length()*2);
		double h=0;
		this.hexagonal=board.hexagonal;
		if(this.hexagonal){
			h=0.5;
		}
        int canvasSize=(int) (512*taille);
        
        if(this.board.length()>=(double)this.board.height()){
        	StdDraw.setCanvasSize((int)(canvasSize*((double)this.board.length()+h)/((double)this.board.height()))  ,canvasSize );	
        }
        else StdDraw.setCanvasSize((int)(canvasSize*1.11),(int)(canvasSize *((double)this.board.height())/((double)this.board.length())));		
		StdDraw.setYscale(0,1.11);
			
		this.setDiplayBoard();			
		}
	}
	
	public Interface(int i,int j,double taille){
        int canvasSize=(int) (512*taille);
        
       if(this.board.length()>=(double)this.board.height()){
        	StdDraw.setCanvasSize((int)(canvasSize*((double)this.board.length())/((double)this.board.height()))  ,canvasSize );	
        }
        else StdDraw.setCanvasSize((int)(canvasSize*1.11),(int)(canvasSize *((double)this.board.height())/((double)this.board.length())));		
		StdDraw.setYscale(0,1.11);
		
		this.creatBoard(i,j);
		
	}
	
	void setDiplayBoard ()  {

		for (int i=this.board.height()-1 ; i>=0;i--) {
			for (int j = 0; j < this.board.length(); j++) {
				double u=0;
				if (hexagonal && j%2==0){
					u=(double)(0.25);
				}
				if (hexagonal && j%2==1){
					u=(double)(-0.25);
				}
					setPenColor(i,j);
					if (board.tableControl(i,j)==-1){
						StdDraw.setPenColor(StdDraw.WHITE);//BLACK
					}
					if(board.tableControl(i,j)==-2){
						StdDraw.setPenColor(StdDraw.WHITE);
					}
					if(!hexagonal){
						StdDraw.filledRectangle((j+0.5)/((double)this.board.length()),1-(i+0.5+u)/((double)this.board.height()) , this.squareSizeJ, this.squareSizeI);	
					}
					else{
						hexagone((j+0.5)/((double)this.board.length()),1-(i+0.5+u)/((double)this.board.height()) , this.squareSizeI, this.squareSizeJ);
					}
					if(board.tableControl(i,j)>0){
						StdDraw.setPenColor(StdDraw.BLACK);
						StdDraw.text((j+0.5)/((double)this.board.length()),1-(i+0.5)/((double)this.board.height()), ""+this.board.tableControl(i, j));
					}
				}
			}
		
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.rectangle(this.squareSizeJ,1.11-(double)(0.5*this.squareSizeI), 2*this.squareSizeJ, this.squareSizeI);
		StdDraw.text(this.squareSizeJ,1.11-(double)(this.squareSizeI), "save");
	}
	
	private static void hexagone(double x,double y,double sizeI,double sizeJ){
		double size=sizeI*1.15;
        double c1 = (Math.sqrt(3) / 2);
        double c2 = (0.5)*1.15;
        double[] px = { x - c2*size, x + c2*size, x+ size*1.15,        x + c2*size, x - c2*size, x - size*1.15   };
        double[] py = { y + c1*size, y + c1*size, y , y - c1*size, y - c1*size, y  };
        StdDraw.filledPolygon(px, py);
	}
	
	
	
	
	public void creatBoard(int l, int h){
		double displayBegining=0.5-2.5*this.squareSizeJ*2;
		int selectedColor=0;
		setColorToSelect(selectedColor);

		//char[][] table=new char[l][h];
		boolean notFinished=true;
		
		while(notFinished){
			if(StdDraw.mousePressed()){
				double x = StdDraw.mouseX();
				double y = StdDraw.mouseY();
				for (int i=0; i < 7; i++) {
					double mx=(double)i*squareSizeJ*2+displayBegining;
					double my=1.06;
					double hsx=this.squareSizeJ;//hover size
					double hsy=this.squareSizeI;
					if ( mx-hsx<x && x<mx+hsx && my-hsy<y && y<my+hsy){
						
						selectedColor=i;
				        try {
							Thread.sleep(20);
						} catch (InterruptedException e) {
							System.out.println("interuption");
							//e.printStackTrace();
						}
						setColorToSelect(selectedColor);
					}
				}
				for (int i=board.height() ; i>=0;i--) {
					for (int j = 0; j < board.length(); j++) {
						double mx=(j+0.5)/((double)this.board.length());
						double my=1-(i+0.5)/((double)this.board.height());
						double hsx=this.squareSizeJ;//hover size
						double hsy=this.squareSizeI;
						if ( mx-hsx<x && x<mx+hsx && my-hsy<y && y<my+hsy){
									if(selectedColor<6){
									//table[i][j]=Board.couleurs.charAt(selectedColor);
									this.board.changeColor(i, j, Board.couleurs.charAt(selectedColor));
									this.changeColor(i, j, Board.couleurs.charAt(selectedColor));
									if(this.board.tableControl(i, j)<0){
									this.board.changeTableControl(i, j, 0);
										}
									}
									else{
										if(this.board.isDeadable(i, j, board.nbPlayers)){
										this.changeColor(i, j, 'w');
										this.board.changeTableControl(i, j, -1);

										}
									}
								}							
							}
						}
				if( 1.11-(double)(0.5*this.squareSizeI)-this.squareSizeI<y && 3*this.squareSizeJ>x){
					StdDraw.setPenColor(StdDraw.WHITE);
					StdDraw.rectangle((double)6*squareSizeJ*2+displayBegining,1.06 , this.squareSizeJ, this.squareSizeI);
					StdDraw.filledRectangle((double)6*squareSizeJ*2+displayBegining,1.06 , this.squareSizeJ, this.squareSizeI);
					notFinished=false;
				}
			this.board.afficheTable();	
			this.board.afficheTableControl();
			
	        try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				System.out.println("interuption");
				//e.printStackTrace();
			}			
			
			}
			

		}
	}
	
	private void setColorToSelect(int selectedColor){
		double displayBegining=0.5-2.5*this.squareSizeJ*2;
		for (int i=0; i < 7; i++) {
			if(i<6){
			setPenColor(Board.couleurs.charAt(i));
			StdDraw.filledRectangle((double)i*squareSizeJ*2+displayBegining,1.06 , this.squareSizeJ, this.squareSizeI);	
			if(i==selectedColor){
				StdDraw.setPenColor(StdDraw.BLACK);
				StdDraw.circle((double)i*squareSizeJ*2+displayBegining,1.06 , this.squareSizeJ);
			}
		}
			else{
				StdDraw.setPenColor(StdDraw.WHITE);
				StdDraw.filledRectangle((double)6*squareSizeJ*2+displayBegining,1.06 , this.squareSizeJ, this.squareSizeI);
				StdDraw.setPenColor(StdDraw.BLACK);
				StdDraw.rectangle((double)6*squareSizeJ*2+displayBegining,1.06 , this.squareSizeJ, this.squareSizeI);
				if(6==selectedColor){
					StdDraw.setPenColor(StdDraw.BLACK);
					StdDraw.circle((double)6*squareSizeJ*2+displayBegining,1.06 , this.squareSizeJ);
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
		if (StdDraw.mousePressed()){		
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
	
						return Board.couleurs.charAt(i);
					
				}

				//StdDraw.rectangle(this.squareSizeJ,1.11-(double)(0.5*this.squareSizeI), 2*this.squareSizeJ, this.squareSizeI);
			}
	        try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				System.out.println("interuption");
				//e.printStackTrace();
			}
			if(  1.11-(double)(0.5*this.squareSizeI)-this.squareSizeI<y && 3*this.squareSizeJ>x){
				return 's';//for save
			}
		}
        try {
			Thread.sleep(40);
		} catch (InterruptedException e) {
			System.out.println("interuption");
			//e.printStackTrace();
		}
        StdDraw.setPenColor(StdDraw.BLACK);
		return choseColor(possibleChoices);
	}
	
	
	
	void setPenColor(char choix){ 
		if(choix=='w'){
			StdDraw.setPenColor(StdDraw.WHITE);
		}else{
			
		
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
	}
	void changeColor(int i,int j,char choix){
		double u=0;
		if (hexagonal && j%2==0){
			u=(double)(0.25);
		}
		if (hexagonal && j%2==1){
			u=(double)(-0.25);
		}
		setPenColor(choix);
		
		if(!hexagonal){
			StdDraw.filledRectangle((j+0.5)/((double)this.board.length()),1-(i+0.5+u)/((double)this.board.height()) , this.squareSizeJ, this.squareSizeI);	
		}
		else{
			hexagone((j+0.5)/((double)this.board.length()),1-(i+0.5+u)/((double)this.board.height()) , this.squareSizeJ, this.squareSizeI);
			}
	}
	
	
	
	
}
