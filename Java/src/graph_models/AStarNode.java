package graph_models;

import java.awt.Point;

@SuppressWarnings("serial")
public class AStarNode extends Point {
	
	private String name;
	private int g; // afstanden fra start til denne node
	private int h; // den forventede afstand til målet
	private boolean legalMove; // væg eller ej
	private AStarNode parent;
	private boolean source;
	private boolean destination;
	private boolean onPath;
	private boolean tested;
	
	public AStarNode(int x, int y){
		this.x = x;
		this.y = y;
		this.g = Integer.MAX_VALUE;
		legalMove = true;
	}
	
	public String toString(){
		return name;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	// returner afstanden til denne node plus den forventede afstand til målet
	public int getF() {
		return g + h;
	}
	
	public int getH(){
		return h;
	}
	
	public void setH(int h){
		this.h = h;
	}

	public boolean isLegalMove() {
		return legalMove;
	}

	public void setLegalMove(boolean legalMove) {
		this.legalMove = legalMove;
	}

	public AStarNode getParent() {
		return parent;
	}

	public void setParent(AStarNode parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isDestination() {
		return destination;
	}

	public void setDestination(boolean destination) {
		this.destination = destination;
	}

	public boolean isSource() {
		return source;
	}

	public void setSource(boolean source) {
		this.source = source;
	}

	public boolean isOnPath() {
		return onPath;
	}

	public void setOnPath(boolean onPath) {
		this.onPath = onPath;
	}

	public boolean isTested() {
		return tested;
	}

	public void setTested(boolean tested) {
		this.tested = tested;
	}
}
