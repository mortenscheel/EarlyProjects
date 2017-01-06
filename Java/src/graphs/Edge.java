package graphs;

import java.io.Serializable;
import java.util.ArrayList; 

@SuppressWarnings("serial")
public class Edge implements Serializable{
	private ArrayList<Vertex> connectedVertices;
	private int weight;
	
	public Edge(Vertex from, Vertex to, int weight){
		this.setWeight(weight);
		connectedVertices = new ArrayList<Vertex>();
		connectedVertices.add(from);
		connectedVertices.add(to);
	}
	
	public Edge(){
		connectedVertices = new ArrayList<Vertex>();
	}
	
	public void addVertex(Vertex v){
		connectedVertices.add(v);
	}
	
	public int getWeight(){
		return weight;
	}
	
	public ArrayList<Vertex> getConnections(){
		return connectedVertices;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
}
