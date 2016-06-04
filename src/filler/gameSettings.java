package filler;

public class gameSettings {
	
	int nbPlayers=2;
	int length=20;
	int height=20;
	
	boolean hexagonal=false;
	boolean islet=false;
	boolean obstacles=false;
	double obstaclesAmount=30;
	boolean getOldSave=false;
	

	boolean playerIA[]={true,true}; 
	boolean ialevel[]={true,true};//ia difficile ou non
	boolean playerConnected[]={true,false};
		//doit transmettre le coup
	
	//boolean opponentConnected[]={false,true};//doit transmettre le coup
	
	board=new Board();
	Save oldSave=new Save();
	
	
	public gameSettings(int nbPlayers, int length, int height, boolean hexagonal, boolean islet, boolean obstacles,
			double obstaclesAmount, boolean getOldSave, boolean[] playerIA, boolean[] ialevel,
			boolean[] playerConnected, Save oldSave, MyConnector aConnection) {
		super();
		this.nbPlayers = nbPlayers;
		this.length = length;
		this.height = height;
		this.hexagonal = hexagonal;
		this.islet = islet;
		this.obstacles = obstacles;
		this.obstaclesAmount = obstaclesAmount;
		this.getOldSave = getOldSave;
		this.playerIA = playerIA;
		this.ialevel = ialevel;
		this.playerConnected = playerConnected;
		this.oldSave = oldSave;
		this.aConnection = aConnection;
	}
	
	
	
	MyConnector aConnection=new MyConnector(true);
	if(playConnected){
		aConnection=new MyConnector();
	}
	
	
	
	
	
	
}
