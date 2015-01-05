package test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import algorithm.Huffman;
import algorithm.HuffmanElement;
import algorithm.HuffmanElement2;

public class HuffmanTest {

//	String path = "C:\\Users\\moni\\Documents\\agh\\IXsem\\algo\\text.txt";
	String path = "C:\\Users\\MZ\\Documents\\mra\\agh\\algo\\tekst_short.txt";
//	String path = "C:\\Users\\MZ\\Documents\\mra\\agh\\algo\\tekst_dlugi.txt";
	String out = "C:\\Users\\MZ\\Documents\\mra\\agh\\algo\\wynik.txt";
	Huffman huff;
	
	@Before
	public void init() throws IOException {
		huff = new Huffman(path);
	}
	
	@Test
	public void test() throws IOException {
//		doGenerateCode(huff.list.getFirst(), "");
//		huff.buildCode(huff.list.getFirst(), "");
//		System.out.println("======");
//		printCode(huff.list.getFirst());
//		huff.printToFile(path, out);
		huff.doHuff(path, out);
	}
	
	private void doGenerateCode(HuffmanElement2 node, String s) {
        if (node.left == null && node.right == null) {
        	System.out.println("asd"+node.getCharacter()+" "+s);
//        	node.setCode(Byte.valueOf(s));
//        	(2 *(x&y)+(x^y))
//            map.put(node.ch, s);
            return;
        }    
        doGenerateCode(node.left, s + '0');
        doGenerateCode(node.right, s + '1' );
    }
	
	private void printCode(HuffmanElement2 node) {
        if (node.left == null && node.right == null) {
        	System.out.println(node.getCharacter()+" "+node.getCode());
            return;
        }    
        printCode(node.left);
        printCode(node.right);
    }

}
