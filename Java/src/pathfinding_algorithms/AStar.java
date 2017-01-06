package pathfinding_algorithms;

import graph_models.AStarNode;

import java.util.ArrayList;


public class AStar implements Runnable{
	private ArrayList<AStarNode> allNodes;
	private ArrayList<AStarNode> openSet;
	private ArrayList<AStarNode> closedSet;
	private ArrayList<AStarNode> path;
	private AStarNode origin;
	private AStarNode destination;
	private int recursions;

	public AStar(ArrayList<AStarNode> nodes, AStarNode origin, AStarNode destination){
		this.origin = origin;
		this.destination = destination;
		allNodes = nodes;
		openSet = new ArrayList<AStarNode>();
		closedSet = new ArrayList<AStarNode>();
	}

	private void recordPathToNodes(){
		// AStarNode har en boolean der viser om de er en del af ruten eller ej.
		for (AStarNode n : path){
			if (!n.isSource() && !n.isDestination()) n.setOnPath(true);
		}
	}

	private void calculate(AStarNode current) { // rekursiv metode
		recursions++;
		// base case: destinationen er fundet
		if (current.equals(destination)){
			path = new ArrayList<AStarNode>();
			path.add(destination);
			findPath(destination);
		}
		else{
			// undersøg nabo-noderne
			ArrayList<AStarNode> neighbors = findBorderingNodes(current);
			for (AStarNode n : neighbors){
				// hvis den ikke er i open-set skal den tilføjes
				if (!openSet.contains(n)) openSet.add(n);
				// udregn afstand til naboen (10 for lodret/vandret, 14 (ca 10 * kvadratrod 2) for diagonal
				boolean isDiagonal = (Math.abs(current.x - n.x) == 1 && Math.abs(current.y - n.y) == 1);
				// beregn cirka afstand herfra til målet (måles som afstand mellem to punkter).
				n.setH((int) n.distance(destination) * 10);
				int newG = current.getG() + 10;
				if (isDiagonal) newG += 4;
				// hvis vejen til n via denne "current" node er kortere end den der allerede findes, erstattes den.
				if (n.getG() > newG){
					n.setG(newG);
					// forældre-noden registreres så man kan trace ruten tilbage til start.
					n.setParent(current);
				}
			}
			// fjern "current" noden når alle dens naboer er beregnet
			openSet.remove(current);
			current.setTested(true);
			closedSet.add(current);
			// kør metoden igen på den node, der formentlig er tættest på målet.
			calculate(findLowestF());
		}
	}

	private void findPath(AStarNode child) { // tracer ruten baglæns efter destinationen er nået
		if (!child.equals(origin)){
			AStarNode parent = child.getParent();
			path.add(parent);
			findPath(parent);
		}
	}
	
	// find den node, der formentlig er tættest på destinationen.
	private AStarNode findLowestF(){ 
		AStarNode result = null;
		int lowestScore = Integer.MAX_VALUE;
		for (AStarNode n : openSet){
			if (n.getF() < lowestScore){
				lowestScore = n.getH();
				result = n;
			}
		}
		if (result == null) System.out.println("returning null");
		return result;
	}
	
	// find alle nabo-noder, der ikke allerede er beregnet
	private ArrayList<AStarNode> findBorderingNodes(AStarNode center){ 
		ArrayList<AStarNode> result = new ArrayList<AStarNode>();
		for (AStarNode node : allNodes){
			boolean x = Math.abs(center.x - node.x) <= 1;
			boolean y = Math.abs(center.y - node.y) <= 1;
			boolean legal = node.isLegalMove();
			boolean inClosed = closedSet.contains(node);
			if (x && y && legal && !inClosed) result.add(node);
		}
		return result;
	}

	@Override
	public void run() {
		System.out.println("Finding path (" + allNodes.size() + " nodes)");
		openSet.add(origin);
		origin.setG(0);
		origin.setH((int) origin.distance(destination) * 10);
		calculate(origin);
		System.out.println("Finished calculating path - " + path.size() + " steps, " + recursions + " recursive calls.");
		recordPathToNodes();
	}
}
