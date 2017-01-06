package graph_models;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Graph implements Serializable {
	private ArrayList<Vertex> vertices;
	private ArrayList<Edge> edges;
	
	public Graph(ArrayList<Vertex> vertices, ArrayList<Edge> edges){
		this.setVertices(vertices);
		this.setEdges(edges);
	}
	
	public Graph(){
		setVertices(new ArrayList<Vertex>());
		setEdges(new ArrayList<Edge>());
	}

	public void addVertex(Vertex v){
		getVertices().add(v);
	}
	
	public void addEdge(Edge e){
		getEdges().add(e);
	}
	
	public boolean isConnected(Vertex a, Vertex b){
		boolean result = false;
		for (Edge e : getEdges()){
			ArrayList<Vertex> connections = e.getConnections();
			if (connections.contains(a) && connections.contains(b)) result = true;
		}
		return result;
	}

	public ArrayList<Vertex> getVertices() {
		return vertices;
	}

	public void setVertices(ArrayList<Vertex> vertices) {
		this.vertices = vertices;
	}

	public ArrayList<Edge> getEdges() {
		return edges;
	}

	public void setEdges(ArrayList<Edge> edges) {
		this.edges = edges;
	}
}
