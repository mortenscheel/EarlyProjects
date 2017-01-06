package graphs.trees.binary_tree;

import graphs.trees.binary_tree.BinaryTreeSet.Sort;

public class IteratorDemo {
	public static void main(String[] args) {
		BinaryTreeSet<Integer> tree = new BinaryTreeSet<>();
		tree.add(4);
		tree.add(8);
		tree.add(9);
		tree.add(1);
		tree.add(10);
		System.out.println("size: " + tree.size());
		tree.setSortMethod(Sort.PRE_ORDER);
		System.out.println("Preorder:");
		for (Integer i : tree) System.out.println(i);
		tree.setSortMethod(Sort.IN_ORDER);
		System.out.println("in-order:");
		for (Integer i : tree) System.out.println(i);
		tree.setSortMethod(Sort.POST_ORDER);
		System.out.println("postorder:");
		for (Integer i : tree) System.out.println(i);
	}
}
