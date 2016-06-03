package filler;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

//import com.sun.java_cup.internal.runtime.Scanner;
import com.sun.xml.internal.stream.util.ThreadLocalBufferAllocator;

import java.io.File;
import java.io.FileInputStream;

public class Save implements Serializable{
	String name;
	Board board;
	Player[] player;
	int noTour;
	long time;
	
	public  Save(String name,Board board,Player[] players,int noTour,long time) {
		this.name=name;
		this.board=board;
		this.player=players;
		this.noTour=noTour;
		this.time=time;
		this.save(name);
	}
	public Save() {
		// TODO Auto-generated constructor stub
	}
	public void save(String name) {
		ObjectOutputStream objectOutputStream = null;
		try {
			objectOutputStream = new ObjectOutputStream(new FileOutputStream("src/saves/"+name));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			objectOutputStream.writeObject(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			objectOutputStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			objectOutputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
public static Save getSave(){
	Scanner sc  = new Scanner(System.in);	
	//ObjectInputStream objectInputStreamt=new ObjectInputStream()
	
	//final File folder = new File("bin/saves");
	//listFilesForFolder(folder);
	try {
		
		Files.walk(Paths.get("src/saves/")).forEach(filePath -> {
		    if (Files.isRegularFile(filePath)) {
		    	System.out.println(filePath.getFileName());
		    }
		});
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	System.out.println("wich save do you want to load ?");
	String name=sc.next();
	ObjectInputStream objectInputStream = null;
	try {
		objectInputStream = new ObjectInputStream(new FileInputStream("bin/saves/"+name));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	Save loadedSave = null;
	try {
		loadedSave = (Save) objectInputStream.readObject();
	} catch (ClassNotFoundException | IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return loadedSave;
	
}
public static void listFilesForFolder(final File folder) {
    for (final File fileEntry : folder.listFiles()) {
        if (fileEntry.isDirectory()) {
            listFilesForFolder(fileEntry);
        } else {
            System.out.println(fileEntry.getName());
        }
    }
}



public Board getBoard() {
	return board;
}

public Player[] getPlayer() {
	return player;
}

public int getNoTour() {
	return noTour;
}

public long getTime() {
	return time;
}



	
}
