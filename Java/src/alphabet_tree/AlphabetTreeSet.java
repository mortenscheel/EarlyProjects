package alphabet_tree;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/*
 * A String set designed to be fast at finding all strings beginning with a substring.
 * The search(String searchTerm) method returns an array of all strings that begin with searchTerm.
 */
@SuppressWarnings("serial")
public class AlphabetTreeSet implements Set<String>, Serializable{
	private AlphabetNode root;
	private AlphabetNode current;
	private ArrayList<String> tempResults;
	private int entries;

	public AlphabetTreeSet(){
		reset();
	}

	public void reset(){
		root = new AlphabetNode();
		entries = 0;
	}

	public boolean add(String string){
		navigateToNode(string);
		if (current.value != null && current.value.equalsIgnoreCase(string)){
			return false;
		}
		else{
			current.value = string;
			entries++;
			return true;
		}
	}

	public String[] search(String searchTerm){
		tempResults = new ArrayList<>();
		navigateToNode(searchTerm);
		recursiveSearch(current); // fills up tempResults
		return tempResults.toArray(new String[0]);
	}

	private void recursiveSearch(AlphabetNode an){
		// if this node has a value, then add it to tempResults
		if (an.value != null){
			tempResults.add(an.value);
		}
		// call recursively on every non-null child
		if (an.getNumberOfNodes() > 0){
			for (AlphabetNode a : an.getNodes()){
				if (a != null){
					recursiveSearch(a);
				}
			}
		}
	}

	// navigates through the tree and sets current to the node of the last character
	private void navigateToNode(String search){
		current = root;
		for (int i = 0 ; i < search.length() ; i++){
			char c = search.toLowerCase().charAt(i);
			AlphabetNode next = null;
			// find the node representing this character and create it if it is null
			switch (c) {
			case 'a':
				if (current.a == null) current.a = new AlphabetNode();
				next = current.a; break;
			case 'b':
				if (current.b == null) current.b = new AlphabetNode();
				next = current.b; break;
			case 'c':
				if (current.c == null) current.c = new AlphabetNode();
				next = current.c; break;
			case 'd':
				if (current.d == null) current.d = new AlphabetNode();
				next = current.d; break;
			case 'e':
				if (current.e == null) current.e = new AlphabetNode();
				next = current.e; break;
			case 'f':
				if (current.f == null) current.f = new AlphabetNode();
				next = current.f; break;
			case 'g':
				if (current.g == null) current.g = new AlphabetNode();
				next = current.g; break;
			case 'h':
				if (current.h == null) current.h = new AlphabetNode();
				next = current.h; break;
			case 'i':
				if (current.i == null) current.i = new AlphabetNode();
				next = current.i; break;
			case 'j':
				if (current.j == null) current.j = new AlphabetNode();
				next = current.j; break;
			case 'k':
				if (current.k == null) current.k = new AlphabetNode();
				next = current.k; break;
			case 'l':
				if (current.l == null) current.l = new AlphabetNode();
				next = current.l; break;
			case 'm':
				if (current.m == null) current.m = new AlphabetNode();
				next = current.m; break;
			case 'n':
				if (current.n == null) current.n = new AlphabetNode();
				next = current.n; break;
			case 'o':
				if (current.o == null) current.o = new AlphabetNode();
				next = current.o; break;
			case 'p':
				if (current.p == null) current.p = new AlphabetNode();
				next = current.p; break;
			case 'q':
				if (current.q == null) current.q = new AlphabetNode();
				next = current.q; break;
			case 'r':
				if (current.r == null) current.r = new AlphabetNode();
				next = current.r; break;
			case 's':
				if (current.s == null) current.s = new AlphabetNode();
				next = current.s; break;
			case 't':
				if (current.t == null) current.t = new AlphabetNode();
				next = current.t; break;
			case 'u':
				if (current.u == null) current.u = new AlphabetNode();
				next = current.u; break;
			case 'v':
				if (current.v == null) current.v = new AlphabetNode();
				next = current.v; break;
			case 'w':
				if (current.w == null) current.w = new AlphabetNode();
				next = current.w; break;
			case 'x':
				if (current.x == null) current.x = new AlphabetNode();
				next = current.x; break;
			case 'y':
				if (current.y == null) current.y = new AlphabetNode();
				next = current.y; break;
			case 'z':
				if (current.z == null) current.z = new AlphabetNode();
				next = current.z; break;
			case 'æ':
				if (current.ae == null) current.ae = new AlphabetNode();
				next = current.ae; break;
			case 'ø':
				if (current.oe == null) current.oe = new AlphabetNode();
				next = current.oe; break;
			case 'å':
				if (current.aa == null) current.aa = new AlphabetNode();
				next = current.aa; break;
			default: break;
			}
			// set up the next iteration
			current = next;
		}
	}

	@Override
	public int size() {
		return entries;
	}

	@Override
	public boolean isEmpty() {
		return entries == 0;
	}

	@Override
	public boolean contains(Object o) {
		if (o instanceof String){
			String s = (String)o;
			navigateToNode(s);
			return current.value.equalsIgnoreCase(s);
		}
		return false;
	}

	@Override
	public boolean remove(Object o) {
		try {
			String s = (String)o;
			navigateToNode(s);
			if (current.value.equalsIgnoreCase(s)){
				current.value = null;
				entries--;
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}

	}

	@Override
	public Iterator<String> iterator() {
		return null;
	}

	@Override
	public Object[] toArray() {
		return search("");
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return null;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends String> c) {
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return false;
	}

	@Override
	public void clear() {
		reset();
	}
	private class AlphabetNode implements Serializable{
		public String value;
		public AlphabetNode a;
		public AlphabetNode b;
		public AlphabetNode c;
		public AlphabetNode d;
		public AlphabetNode e;
		public AlphabetNode f;
		public AlphabetNode g;
		public AlphabetNode h;
		public AlphabetNode i;
		public AlphabetNode j;
		public AlphabetNode k;
		public AlphabetNode l;
		public AlphabetNode m;
		public AlphabetNode n;
		public AlphabetNode o;
		public AlphabetNode p;
		public AlphabetNode q;
		public AlphabetNode r;
		public AlphabetNode s;
		public AlphabetNode t;
		public AlphabetNode u;
		public AlphabetNode v;
		public AlphabetNode w;
		public AlphabetNode x;
		public AlphabetNode y;
		public AlphabetNode z;
		public AlphabetNode ae;
		public AlphabetNode oe;
		public AlphabetNode aa;

		public int getNumberOfNodes(){
			int count = 0;
			for (AlphabetNode an : getNodes()){
				if (an != null) count++;
			}
			return count;
		}

		public AlphabetNode[] getNodes() {
			AlphabetNode[] temp = {a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p,	q, r, s, t, u, v, w, x, y, z, ae, oe, aa};
			return temp;
		}
	}
}