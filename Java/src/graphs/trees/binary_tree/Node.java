package graphs.trees.binary_tree;

public class Node<T extends Comparable<T>> {
	public Node<T> parent;
	public Node<T> left;
	public Node<T> right;
	public T value;
	
	public Node(Node<T> parent){
		this.parent = parent;
	}
}
