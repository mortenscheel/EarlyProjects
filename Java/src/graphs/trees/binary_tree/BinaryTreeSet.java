package graphs.trees.binary_tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

// Comparable is necessary in order to build the tree.

public class BinaryTreeSet<T extends Comparable<T>> implements Set<T>{
	
	private Node<T> root;
	private ArrayList<T> tempList;
	private Node<T> currentNode;
	private int height = 0;
	private int tempHeight;
	public enum Sort{PRE_ORDER, IN_ORDER, POST_ORDER};
	private Sort sortMethod = Sort.IN_ORDER;

	public BinaryTreeSet(){
		clear();
	}

	@Override
	public int size() {
		return toArray().length;
	}

	@Override
	public boolean isEmpty() {
		return toArray().length == 0;
	}

	@Override
	public boolean contains(Object o) {
		@SuppressWarnings("unchecked")
		T element = (T)o;
		search(root, element); // sets currentNode to where the element ought to be.
		return currentNode.value == element;
	}

	@Override
	public Iterator<T> iterator() {
		tempList = new ArrayList<>();
		traverse(root);
		return new Iterator<T>() {

			private Object[] elements = tempList.toArray(new Object[0]);
			private int currentIndex = 0;

			@Override
			public boolean hasNext() {
				return elements.length > currentIndex && elements[currentIndex] != null;
			}

			@SuppressWarnings("unchecked")
			@Override
			public T next() {
				if (hasNext()){
					return (T)elements[currentIndex++];
				}
				else throw new NoSuchElementException();			
			}

			@Override
			public void remove() {

			}
		};
	}

	@Override
	public Object[] toArray() {
		tempList = new ArrayList<>();
		traverse(root); // populates tempList with all elements
		return tempList.toArray(new Object[0]);
	}

	@SuppressWarnings("hiding")
	@Override
	public <T> T[] toArray(T[] a) {
		return null;
	}

	@Override
	public boolean add(T e) {
		tempHeight = 0;
		search(root, e); // sets currentNode to where e fits in the tree.
		if (currentNode.value == e){
			System.out.println("Element exists.");
			return false;
		}
		if (currentNode.value == null) currentNode.value = e;
		System.out.println(e + " added.");
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(Object o) {
		T element = (T)o;
		search(root, element); // sets currentNode to where o ought to be in the tree.
		if (currentNode.value == null || !currentNode.value.equals(element)) return false;
		else{
			// save the children!
			tempList = new ArrayList<>();
			traverse(currentNode); // all children saved to tempList
			tempList.remove(element);
			// step back and delete the node.
			// if root
			if (currentNode.equals(root)){
				root = new Node<>(null);
			}
			// not root
			else{
				currentNode = currentNode.parent;
				if (currentNode.left.value.equals(element)){
					currentNode.left = null;
				}
				else{
					currentNode.right = null;
				}
			}
			
			
			for (T t : tempList) {
				add(t);
			}
			return true;
		}
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object o : c){
			if (!contains(o)) return false;
		}
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		for (T t : c) add(t);
		return true;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		for (Object o : c) remove(o);
		return true;
	}

	@Override
	public void clear() {
		root = new Node<>(null);
	}

	private void traverse(Node<T> n){
		// recursive traversal of all child nodes, adding values to tempList along the way.
		switch (sortMethod) {
		case PRE_ORDER:
			if (n.value != null) tempList.add(n.value);
			if (n.left != null) traverse (n.left);
			if (n.right != null) traverse (n.right);
			break;
		case IN_ORDER:
			if (n.left != null) traverse (n.left);
			if (n.value != null) tempList.add(n.value);
			if (n.right != null) traverse (n.right);
			break;
		case POST_ORDER:
			if (n.left != null) traverse (n.left);
			if (n.right != null) traverse (n.right);
			if (n.value != null) tempList.add(n.value);
			break;
		default:
			break;
		}
	}

	private void search(Node<T> n, T value){
		tempHeight++;
		if (tempHeight > height) height = tempHeight;
		currentNode = n;
		if (n.value != null){
			int compare = value.compareTo(n.value);
			if (compare < 0){
				//System.out.println("Going left at " + n.value);
				if (n.left == null) n.left = new Node<T>(currentNode);
				search(n.left, value);
			}
			else if (compare > 0){
				//System.out.println("Going right at " + n.value);
				if (n.right == null) n.right = new Node<T>(currentNode);
				search(n.right, value);
			}
		}
	}

	public HashSet<T> getSubSet(T value){
		tempList = new ArrayList<>();
		search(root, value); // navigates to the start node
		traverse(currentNode); // records all child nodes to tempList
		return new HashSet<>(tempList);
	}

	public int getHeight(){
		return height;
	}

	public Sort getSortMethod() {
		return sortMethod;
	}

	public void setSortMethod(Sort sortMethod) {
		this.sortMethod = sortMethod;
	}
}
