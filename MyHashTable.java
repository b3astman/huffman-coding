/*
 * Bethany Eastman
 * Compressed Literature 2
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Hash Table implementing linear probing. 
 * 
 * @author Bethany Eastman
 * @version Dec 14, 2015
 * @param <K> a key type
 * @param <V> a value type
 */
public class MyHashTable<K, V> {
	
	/** Size of table. */
	private final int size;
	/** Number of non null entries */
	private int entries;
	/** Linear prob histogram */
	private ArrayList<Integer> count;
	/** Keys */
	private K[] keys;
	/** Values */
	private V[] values;

	/**
	 * Instantiates the Hash Table with capacity.
	 * @param capacity - size of hash table.
	 */
	public MyHashTable(int capacity) {
		size = capacity;
		count = new ArrayList<Integer>();
		keys = (K[]) new Object[capacity];
		values = (V[]) new Object[capacity];
		entries = 0;
	}

	/**
	 * Puts a key and value into hash table.
	 * 
	 * @param searchKey - key.
	 * @param newValue - value.
	 */
	public void put(K searchKey, V newValue) {
		int i = hash(searchKey);
		int probeAmount = 0;
		boolean added = false;
		boolean isKey = containsKey(searchKey);
		while (!added) {
			if (keys[i] == null) {
				keys[i] = searchKey;
				values[i] = newValue;
				entries++;
				added = true;

			} else if (keys[i].equals(searchKey)) {
				values[i] = newValue;
				added = true;
			}
			if (!added) {
				probeAmount++;
				i = (i + 1) % size;
			}
		}
		if (!isKey) {
			if (count.size() <= probeAmount) {
				count.add(0);
			} else {
				count.set(probeAmount, count.get(probeAmount) + 1);
			}
		}
	}

	/**
	 * Gets a value with provided key. 
	 * 
	 * @param searchKey - key associated to value location.
	 * @return value associated with key.
	 */
	public V get(K searchKey) {
		for (int i = hash(searchKey); keys[i] != null; i = (i + 1) % size) {
			if (keys[i].equals(searchKey)) return values[i];
		}
		return null;
	}

	/**
	 * Returns true if key is located in the hash table, false otherwise.
	 * 
	 * @param searchKey - key to search for in table,
	 * @return true if key is in table, false otherwise.
	 */
	public boolean containsKey(K searchKey) {
		return get(searchKey) != null;
	}

	/**
	 * Hash method return an integer in the range from [0 - Hash table size].
	 * 
	 * @param key - the key to get hash value
	 * @return a hash value
	 */
	private int hash(K key) {
		return Math.abs(key.hashCode()) % size;
	}

	/**
	 * Provides states of hash table included number of entries, buckets, a histogram
	 * of linear probing.
	 */
	public void stats() {
		StringBuilder s = new StringBuilder();
		s.append("Hash Table Stats\n");
		s.append("================\n");
		s.append("Number of Entries: ");
		s.append(entries);
		s.append("\nNumber of Buckets: ");
		s.append(values.length);
		s.append("\nHistogram of probes:");
		s.append(count.toString());
		s.append("\nFill Percentage: ");
		s.append((double) entries * 100 / size);
		s.append("%\nMax Linear Prob: ");
		s.append(count.size());
		s.append("\nAverage Linear Prob: ");
		s.append(linearProb());
		s.append("\n");
		System.out.println(s.toString());
	}

	/**
	 * Returns a key set for the hash table.
	 * @return key set
	 */
	public K[] keySet() {
		return keys;
	}

	/**
	 * Gets linear prob.
	 * @return average linear prob.
	 */
	private double linearProb() {
		double total1 = 0;
		double total = 0;
		int index = 0;
		for (int i: count) {
			total += i * index++;
			total1 += i;
		}
		return total / total1;
	}

	/**
	 * Convert hash table contents to string.
	 */
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("[");
		for (int i = 0; i < values.length; i++) {
			if (values[i] != null) {
				s.append("(");
				s.append(keys[i]);
				s.append(", ");
				s.append(values[i]);
				s.append(")");
			}
		}
		s.append("]");
		return s.toString();
	}

}
