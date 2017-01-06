package graph_models;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Vertex implements Serializable{
	
	private Point position;
	private String name;
	private int weight;
	private ArrayList<Vertex> path = new ArrayList<Vertex>();
	
	public Vertex(String name){
		this.name = name;
	}
	
	public String getVertexName(){
		return name;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int distance) {
		this.weight = distance;
	}

	public void addElementToPath(Vertex v){
		path.add(v);
	}
	
	public ArrayList<Vertex> getPath(){
		return path;
	}
	
	public void setPath(ArrayList<Vertex> list){
		path = list;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}
}
