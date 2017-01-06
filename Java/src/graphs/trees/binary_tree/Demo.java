package graphs.trees.binary_tree;

import graphs.trees.binary_tree.BinaryTreeSet.Sort;

public class Demo {
	//private static BinaryTreeSet<String> tree;
	//private static ArrayList<String> list;
	public static void main(String[] args) {
		BinaryTreeSet<Integer> tree = new BinaryTreeSet<>();
		tree.add(5);
		tree.add(9);
		tree.add(2);
		tree.add(4);
		//tree.remove(5);
		System.out.println("In order:");
		for (Integer i : tree) System.out.println(i);
		tree.setSortMethod(Sort.POST_ORDER);
		System.out.println("Post order");
		for (Integer i : tree) System.out.println(i);

//		tree = new BinaryTreeSet<>();
//		list = new ArrayList<>();
//		loadWords();
//		System.out.println("Size: " + tree.size());
//		System.out.println("Depth: " + tree.getDepth());
//		long start = System.currentTimeMillis();
//		tree.contains("rumlepotte");
//		long split1 = System.currentTimeMillis();
//		list.contains("rumlepotte");
//		long split2 = System.currentTimeMillis();
//		long treeTime = split1 - start;
//		long listTime = split2 - split1;
//		System.out.println("Tree search took " + treeTime + " ms.");
//		System.out.println("List search took " + listTime + " ms.");
	}
//	private static void loadWords() {
//		Scanner parser = null;
//		try {
//			parser = new Scanner(new File("ordliste.txt"));
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		if (parser != null){
//			// read each line
//			while (parser.hasNextLine()){
//				String line = parser.nextLine();
//				if (line.contains("/")) line = line.split("/")[0];
//				tree.add(line);
//				list.add(line);
//			}
//		}
//	}
}
