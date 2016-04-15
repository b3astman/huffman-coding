/*
 * Bethany Eastman
 * Compressed Literature 2
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Driver program reads a text file and compressed it based on a huffman algorithm. 
 * 
 * @author Bethany Eastman
 * @version Dec 16, 2015
 */
public class Main {
	
	public static void main(String theArgs[]) throws IOException {
		long start = System.currentTimeMillis();
		CodingTree wp;
		String file;
		File compressedFile;
		FileOutputStream compressed;
		try {
			file = new String(Files.readAllBytes(Paths.get("WarAndPeace.txt")));// stringbuilder
			wp = new CodingTree(file);
			
			PrintWriter writer = new PrintWriter("codes.txt", "UTF-8");
            writer.print(wp.toString());
            writer.close();
            
			compressedFile = new File("compressed.txt");
            compressed = new FileOutputStream(compressedFile);
            compressed.write(wp.out);
            compressed.close();
		
		} finally {}
		long end = System.currentTimeMillis();
		long og = file.length();
        long hf = compressedFile.length();
        wp.codes.stats();
        System.out.println("Uncompressed file size: " + og + " bytes\n" + 
        						"Compressed file size: " +  hf + " bytes\n" + 
        						"Compression ratio: " +  ((long) hf * 100 / (long) og) + 
        						"%\nRunning Time: "  + (end - start) + 
        						" milliseconds\n");
//		testMyHashTable();
//		testCodingTree();
	}
	
	public static void testMyHashTable() {
		MyHashTable<String, String> smallTable = new MyHashTable<String, String>(100);
		smallTable.put("01", "10");
		smallTable.put("011", "110");
		System.out.println("Get '01' should be '10': " + smallTable.get("01"));
		System.out.println("Contains key 01, should be true: " + smallTable.containsKey("01"));
		System.out.println("Contains key 11, should be false: " + smallTable.containsKey("11"));
		System.out.println("Table contents should be:[(01, 10)(011, 110)]" + smallTable.toString());
		
	}
	
	public static void testCodingTree() {
		CodingTree smallTree = new CodingTree("a aa   aa  aa aaa aa aa aa aabc");
		System.out.println("a's bits should be shorter than others: " + smallTree.toString());
	}

}
