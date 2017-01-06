package gui;


import graph_models.Edge;
import graph_models.Graph;
import graph_models.Vertex;

import java.awt.Point;
import java.util.ArrayList;

import gui.components.DijkstraFrame;
import gui.components.DijkstraPanel;
import pathfinding_algorithms.Dijkstra;


public class DijkstraDemo {
	private DijkstraFrame frame;
	private DijkstraPanel panel;
	private ArrayList<Vertex> vertices;
	private ArrayList<Edge> edges;
	private ArrayList<Vertex> path;
	private boolean addingVertices;
	private boolean addingEdges;
	private boolean fromSelected;
	private boolean selectingSource;
	private boolean selectingDestination;
	private Vertex source;
	private Vertex destination;
	private String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "X", "Y", "Z"};
	private int letterCounter = 0;

	public static void main(String[] args) {
		new DijkstraDemo();
	}
 
	public DijkstraDemo(){
		frame = new DijkstraFrame(this);
		frame.setVisible(true);
		reset();
	}

	private void reset() {
		setVertices(new ArrayList<Vertex>());
		setEdges(new ArrayList<Edge>());
	}

	public void calculate() {
		if (source != null && edges.size() != 0){
			Graph graph = new Graph(vertices, edges);
			Dijkstra dijkstra = new Dijkstra(graph);
			dijkstra.setSource(source);
			frame.setTextArea(dijkstra.getHistory());
			panel.repaint();
		}
	}
	
	public void showPath(){
		path = destination.getPath();
		panel.repaint();
	}

	public void setPanel(DijkstraPanel p){
		panel = p;
	}

	public void addVertex(Vertex v){
		getVertices().add(v);
	}

	public void addEdge(Edge e){
		getEdges().add(e);
	}

	public Vertex findNearestVertex(int x, int y){
		Point p = new Point(x, y);
		int dist = Integer.MAX_VALUE;
		Vertex nearest = null;
		for (Vertex v : getVertices()){
			int d = (int) v.getPosition().distance(p);
			if (d < dist){
				dist = d;
				nearest = v;
			}
		}
		return nearest;
	}

	public boolean isAddingEdges() {
		return addingEdges;
	}

	public void setAddingEdges(boolean addingEdges) {
		this.addingEdges = addingEdges;
	}

	public boolean isAddingVertices() {
		return addingVertices;
	}

	public void setAddingVertices(boolean addingVertices) {
		this.addingVertices = addingVertices;
	}

	public boolean isFromSelected() {
		return fromSelected;
	}

	public void setFromSelected(boolean fromSelected) {
		this.fromSelected = fromSelected;
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

	public Vertex getDestination() {
		return destination;
	}

	public void setDestination(Vertex destination) {
		this.destination = destination;
	}

	public Vertex getSource() {
		return source;
	}

	public void setSource(Vertex source) {
		this.source = source;
	}

	public boolean isSelectingDestination() {
		return selectingDestination;
	}

	public void setSelectingDestination(boolean selectingDestination) {
		this.selectingDestination = selectingDestination;
	}

	public boolean isSelectingSource() {
		return selectingSource;
	}

	public void setSelectingSource(boolean selectingSource) {
		this.selectingSource = selectingSource;
	}
	
	public ArrayList<Vertex> getPath(){
		return path;
	}
	
	public String getLetter(){
		return letters[letterCounter++];
	}
}
