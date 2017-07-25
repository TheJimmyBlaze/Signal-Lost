package object;

import java.util.ArrayList;

public class ObjectShelf {
	
	private String name;
	private int number;
	
	private ArrayList<Object> objectList = new ArrayList<Object>();
	
	public int getSpriteCount(){return objectList.size();}
	public String getName(){return name;}
	public int getNumber(){return number;}
	
	public Object collectObject(int OID){return objectList.get(OID);}
	public double collectUID(int OID){return objectList.get(OID).getUID();}
	
	public ObjectShelf(String namePass, int numberPass){
		name = namePass;
		number = numberPass;
	}
	
	public void add(Object obj){
		objectList.add(obj);
	}
}
