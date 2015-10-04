import list.*; 
import dict.*; 

public class Vertex{
	Object data; 
	DList edges; 
	DListNode list_entry; 

	public Vertex(Object o){
		data = o; 
		list_entry = null; 
		edges = new DList();
	}

	public int hashCode(){
		return data.hashCode(); 
	}

	public boolean equals (Vertex v){
		return data == v.data; 
	}

	public boolean equals (Object O){
		return data ==O ; 
	}
}
