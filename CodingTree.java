/*
 * Bethany Eastman
 * Compressed Literature 2
 */
import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Creates a huffman tree.
 * 
 * @author Bethany Eastman
 * @version Dec 16, 2015
 */
public class CodingTree {
	
	/** The word and bit string*/
	public MyHashTable<String, String> codes;
	/** The word and frequency */
	public MyHashTable<String, Integer> frequency;
	/** Priority queue of nodes sorted on frequency*/
	public PriorityQueue<Node> p;
	/** Encoded bit string */
	public String bits;
	/** The top of huffman */
	private Node top;
	/** List of words */
	private ArrayList<String> words;
	/** Output encoded */
	public byte[] out;
	
	/**
	 * Instantiates a huffman.
	 * 
	 * @param text - the string from file
	 */
	public CodingTree(String text) {
		frequency = new MyHashTable<String, Integer>(32768);
		codes = new MyHashTable<String, String>(32768);
		words = new ArrayList<String>();
		p = new PriorityQueue<Node>();
		getGoodWords(text);
		getWordFrequency();		// get frequency
		getPriorityFrequency();
		top = createHuffman();	// create huffman tree
		getBitCodes(top, ""); 	// create hash table entries in codes
		bits = compressed(text);
		out = getEncode();
	}
	
	/**
	 * Gets the frequency of words.
	 */
	private void getWordFrequency() {
		for (String word: words) {
			if (!frequency.containsKey(word)) {
				frequency.put(word, 1);
			} else {
				frequency.put(word, frequency.get(word) + 1);
			}
		}
	}
	
	/**
	 * Puts word into priority list.
	 */
	private void getPriorityFrequency() {
		for (Object i: frequency.keySet()) {
			if (i != null) p.add(new Node((String) i, null, null, frequency.get((String) i)));
		}
	}
	
	/**
	 * Creates huffman tree.
	 * 
	 * @return top of huffman.
	 */
	private Node createHuffman() {
		Node join = null;
		while (p.size() > 1) {
			Node one = p.remove();
			Node two = p.remove();
			join = new Node(null, one, two, one.frequency + two.frequency);
			p.add(join);
		}
		return p.remove();
	}
	
	/**
	 * Gets bit codes in huffman tree. 
	 * @param root
	 * @param s
	 */
	private void getBitCodes(Node root, String s) {
		if (root.isLeaf()) {
			codes.put(root.item, s);
			return;
		}
		getBitCodes(root.left, s + '0');
		getBitCodes(root.right, s + '1');
	}
	
	/**
	 * Separates words from file.
	 * @param text
	 */
	private void getGoodWords(String text) {
		StringBuilder goodWord = new StringBuilder();
		for (Character i: text.toCharArray()) {
			if (Character.isLetterOrDigit(i) || i.equals('\'') || i.equals('-')) {
				 goodWord.append(i);
			} else {
				if (goodWord.length() > 0) {
					words.add(goodWord.toString());
					goodWord = new StringBuilder();
				}
				words.add(i.toString());
			}
		}
		if (goodWord.length() > 0) {			// left over word ?
			words.add(goodWord.toString());
		}
	}
	
	/**
	 * Gets bit string representing encoded file.
	 * @param text
	 * @return
	 */
	private String compressed(String text) {
		StringBuilder out = new StringBuilder();
		for (String i: words) {
			out.append(codes.get(i));
		}
		return out.toString();
	}
	
	/**
	 * Gets the byte array from string of bits.
	 * @return
	 */
	private byte[] getEncode() {
		ArrayList<Byte> enc2 = new ArrayList<Byte>();
		StringBuilder encode = new StringBuilder();
		
		int i = 0;
		for (Character c: bits.toCharArray()) {
			if (encode.length() == 8) {
				enc2.add((byte) Integer.parseInt(encode.toString(), 2));
				encode = new StringBuilder();
			} else {
				encode.append(c);
			}		
		}
		
		if (encode.length() > 0) {
			enc2.add((byte) Integer.parseInt(encode.toString(), 2));
		}
		
		byte[] result = new byte[enc2.size()];
		for(i = 0; i < enc2.size(); i++) {
		    result[i] = enc2.get(i).byteValue();
		}
		return result;
	}
	
	/**
	 * Returns values of words and bit strings.
	 */
	public String toString() {
		return codes.toString();
	}

	
	/**
	 * A node class for a Huffman Tree. Each node will have a right and left child, 
	 * as well as a frequency which should be the frequency of the left child, 
	 * plus the frequency of the right.
	 * 
	 * @author Bethany Eastman
	 * @version Nov 20, 2015
	 */
	private class Node implements Comparable<Node> {

		String item;
		int frequency;
		Node left, right;
		
		public Node(String other, Node l, Node r, int f) {
			left = l;
			right = r;
			item = other;
			frequency = f;
		}
		
		public boolean isLeaf() {
			return left == null && right == null;
		}

		@Override
		public int compareTo(Node o1) {
			return frequency - o1.frequency;
		}
		
		@Override
		public String toString() {
			return "Char: " +  item + " F: " + frequency + " L: " + left.frequency + " R: " 
					+ right.frequency + " Leaf: " + isLeaf();
 		}
	}

}
