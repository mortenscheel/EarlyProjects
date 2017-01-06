package alphabet_tree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.TreeSet;

public class Main {

	private static String search = "sort";
	private static AlphabetTreeSet tree;
	private static char[] legal = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'æ', 'ø', 'å'};
	private static HashSet<String> hashset;
	private static TreeSet<String> treeset;

	public static void main(String[] args) {
		tree = new AlphabetTreeSet();
		hashset = new HashSet<>();
		treeset = new TreeSet<>();
		long start = System.currentTimeMillis();
		loadWords();
		//loadSets();
		long load = System.currentTimeMillis();
		System.out.println("Loaded " + treeset.size() + " words in " + formatTime(load - start));
		String[] result = tree.search(search);
		long split1 = System.currentTimeMillis();
		String[] result2 = searchHashSet();
		long split2 = System.currentTimeMillis();
		String[] result3 = searchTreeSet();
		long split3 = System.currentTimeMillis();
		long alphaTime = split1 - load;
		long hashTime = split2 - split1;
		long treeTime = split3 - split2;
		int min = (int) Math.min(alphaTime, hashTime);
		min = (int) Math.min(min, treeTime);
		int max = (int) Math.max(alphaTime, hashTime);
		max = (int) Math.max(max, treeTime);
		System.out.println("Searching for \"" + search + "\".");
		System.out.println("AlphabetTree found " + result.length + " results in " + formatTime(alphaTime));
		System.out.println("HashSet found " + result2.length + " results in " + formatTime(hashTime));
		System.out.println("TreeSet found " + result3.length + " results in " + formatTime(treeTime));
		System.out.printf("Speed factor: %.1f\n", ((double)max / min));
		//log();
	}

	private static void log() {
		int testSize = 500;
		int minSize = 1;
		int maxChars = 5;
		String log = "";
		char[] chars = new char[26];
		char c = 'a';
		for (int i = 0 ; i < chars.length ; i++){
			chars[i] = c;
			c++;
		}
		Random r = new Random();
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new File("result.txt"));
			writer.write("Chars\tSize\tAlphabetTree\tHashSet\tTreeSet");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		for (int i = 1 ; i <= testSize ; i++){
			int numberOfCharacters = r.nextInt(maxChars - minSize) + minSize;
			String search = "";
			for (int j = 0 ; j < numberOfCharacters ; j++){
				search += chars[r.nextInt(chars.length)];
			}
			long start = System.currentTimeMillis();
			String[] results = tree.search(search);
			long alpha = System.currentTimeMillis();
			searchHashSet();
			long hash = System.currentTimeMillis();
			searchTreeSet();
			long tree = System.currentTimeMillis();
			writer.write(numberOfCharacters + "\t" + results.length + "\t" + (alpha - start) + "\t" + (hash - alpha) + "\t" + (tree - hash) + "\n");
			System.out.println("Finished test " + i + "(" + results.length + ")");
		}
		writer.close();
	}

	private static String[] searchHashSet() {
		ArrayList<String> resultlist = new ArrayList<>();
		for (String s : hashset){
			if (s.startsWith(search)) resultlist.add(s);
		}
		String[] result = resultlist.toArray(new String[0]);
		return result;
	}

	private static String[] searchTreeSet() {
		ArrayList<String> resultlist = new ArrayList<>();
		for (String s : treeset){
			if (s.startsWith(search)) resultlist.add(s);
		}
		String[] result = resultlist.toArray(new String[0]);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private static void loadSets(){
		try {
			ObjectInputStream is = new ObjectInputStream(new FileInputStream("Giant Sets.data"));
			Object data = is.readObject();
			is.close();
			Object[] objects = (Object[])data;
			tree = (AlphabetTreeSet) objects[0];
			hashset = (HashSet<String>) objects[1];
			treeset = (TreeSet<String>) objects[2];
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}



	private static boolean isLegal(char c){
		for (int i = 0 ; i < legal.length ; i++){
			if (c == legal[i]) return true;
		}
		return false;
	}
	
	private static String formatTime(long ms){
		if (ms > 60000){
			return String.format("%d minutes", ms / 60000);
		}
		else if (ms > 1000){
			return String.format("%.1f seconds", ms / 1000.0);
		}
		else{
			return "" + ms + " miliseconds";
		}
	}
	
	 private static void loadWords() {
		BufferedReader parser = null;
		int count = 0;
		try {
			parser = new BufferedReader(new FileReader("ordliste.txt"));
			String line = null;
			while ((line = parser.readLine())!= null){
				String word = "";
				for (int i = 0 ; i < line.length() ; i++){
					char c = line.charAt(i);
					if (isLegal(c)) word += c;
				}
				String copy = new String(word);
				tree.add(copy);
				hashset.add(copy);
				treeset.add(copy);
				count++;
				if (count % 1000000 == 0) System.out.println(count);
			}
	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
