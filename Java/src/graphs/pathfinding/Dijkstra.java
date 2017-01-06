package graphs.pathfinding;

import graphs.Edge;
import graphs.Graph;
import graphs.Vertex;

import java.util.ArrayList;

public class Dijkstra { 
	ArrayList<Vertex> vertices;
	ArrayList<Edge> edges;
	ArrayList<Vertex> notVisited;
	ArrayList<Vertex> localVisited;
	Vertex source;
	Vertex previous;
	private Graph graph;
	private ArrayList<Vertex> path;
	private String history = "";

	@SuppressWarnings("unchecked")
	public Dijkstra(Graph g){
		this.graph = g;
		this.vertices = g.getVertices();
		this.notVisited = ((ArrayList<Vertex>) vertices.clone());
		this.edges = g.getEdges();
	}

	public void setSource(Vertex v){
		this.source = v;
		// Set all distances to Integer.MAX
		for (Vertex vertex : notVisited){
			vertex.setWeight(Integer.MAX_VALUE);
		}
		source.setWeight(0);
		ArrayList<Vertex> p = new ArrayList<Vertex>();
		p.add(source);
		source.setPath(p);
		calculateDistances(getNeighbors(source), source);
	}

	public int distanceTo(Vertex destination) throws Exception{
		return destination.getWeight();
	}

	public ArrayList<Vertex> getShortesPath(Vertex destination) throws Exception{
		path = destination.getPath();
		path.add(destination);
		path.add(0, source);
		return destination.getPath();
	}

	@SuppressWarnings("unchecked")
	private void calculateDistances(ArrayList<Vertex> list, Vertex source){
		history += "Besøger nu: " + source.getVertexName() + "\n";
		if (notVisited.size() != 0){
			for (Vertex v : vertices){
				// only check connected neighbors
				if (graph.isConnected(source, v)){
					history += "Checker nu " + source.getVertexName() + "-" + v.getVertexName() + "\n";
					Edge e = findEdge(v, source);
					if (e != null){
						int original = v.getWeight();
						int thisDistance = source.getWeight() + e.getWeight();
						if (thisDistance < original){
							v.setWeight(thisDistance);
							v.setPath((ArrayList<Vertex>) source.getPath().clone());
							v.addElementToPath(v);
							if (original == Integer.MAX_VALUE){
								history += "Rute til " + v.getVertexName() + " fundet: " + thisDistance + "\n";
							}
							else{
								history += "Kortere rute fundet til " + v.getVertexName() + ": "
										+ v.getWeight() + " (< " + original + ")\n";
							}
							printPath(v);
						}
					}
				}
			}
			notVisited.remove(source);
			for (int i = 0 ; i < list.size() ; i++){
				Vertex ver = list.get(i);
				if (notVisited.contains(ver)){
					calculateDistances(getNeighbors(ver), ver);
				}
			}
		}
	}

	private void printPath(Vertex v) {
		String s = v.getVertexName() + "'s path er nu: ";
		for (Vertex ve : v.getPath()) s += ve.getVertexName();
		history += s + "\n";

	}

	private ArrayList<Vertex> getNeighbors(Vertex v){
		ArrayList<Vertex> result = new ArrayList<Vertex>();
		for (Vertex vertex : notVisited){
			if (graph.isConnected(v, vertex) && !vertex.equals(v)) result.add(vertex);
		}
		return result;
	}

	private Edge findEdge(Vertex a, Vertex b){
		Edge result = null;
		for (Edge e : edges){
			if (e.getConnections().contains(a) && e.getConnections().contains(b)) result = e;
		}
		return result;
	}

	public ArrayList<Vertex> getPath(){
		return path;
	}

	public void printAllPaths(){
		for (Vertex v : vertices){
			String s = v.getVertexName() + "'s path: ";
			for (Vertex ve : v.getPath()) s += ve.getVertexName();
			System.out.println(s);
		}
	}

	public String getHistory(){
		return history;
	}
}
