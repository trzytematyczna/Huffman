package test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import algorithm.BiHuffman;
import algorithm.Huffman;

public class AllTest {

	String path = "C:\\Users\\MZ\\Documents\\mra\\agh\\algo\\tekst_dlugi.txt";
	String outHuffman = "C:\\Users\\MZ\\Documents\\mra\\agh\\algo\\Huffman_coded.txt";
	String outBiHuffman = "C:\\Users\\MZ\\Documents\\mra\\agh\\algo\\BiHuffman_coded.txt";
	BiHuffman bi_huff;
	Huffman huff;
	
	@Before
	public void init() throws IOException {
		huff = new Huffman(path);
		bi_huff = new BiHuffman(path);
	}
	
	@Test
	public void test() throws IOException {
		System.out.println("====Huffman====");
		huff.doHuff(path, outHuffman);
		System.out.println("====Bi-Huffman====");
		bi_huff.doHuff(path, outBiHuffman);
	}

}
