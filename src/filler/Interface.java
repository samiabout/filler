package filler;



public class Interface {
	
	Board board;

	public Interface(Board board) {
		this.board=board;
		
	}
	
	void setDiplayBoard (double taille)  {
        int canvasSize=(int) (512*taille);
        StdDraw.setCanvasSize(canvasSize,canvasSize );		
		//StdDraw.setPenColor(r, g, b);
		//StdDraw.setPenRadius(0.4);
		StdDraw.filledSquare(0, 0,1);
		
        StdDraw.setPenColor(StdDraw.BLACK);

        double borderSpace=1/((double)this.board.length()*3);
		for (double i = 1; i < this.board.length()+1; i++) {
			StdDraw.line(i/(this.board.length()+1),0+borderSpace,i/(this.board.length()+1),1-borderSpace);
			}
		for (double j = 1; j < this.board.height()+1; j++) {
			StdDraw.line(0+borderSpace,j/(this.board.height()+1),1-borderSpace,j/(this.board.height()+1));	
			}
	}
	

}
