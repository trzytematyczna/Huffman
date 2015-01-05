package algorithm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Collator;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class Huffman {

	public LinkedList<HuffmanElement2> list;
	public LinkedList<HuffmanElement2> tree;
	String[] codes;
	Pair[] codes_bytes;
	Pair[] asd;
	HashMap<Character, Integer> hash;
	int depth;
	
	public Huffman(String path) throws IOException {
		this.depth=1;
		list = new LinkedList<HuffmanElement2>();
//		tree = new LinkedList<HuffmanElement2>();
		hash = new HashMap<Character, Integer>();
		BufferedReader br = null;
		br = new BufferedReader(new FileReader(path));
		int r;
		char ch;
		int i=0;
		while ((r = br.read()) != -1) {
            ch = (char) r;
            if(modifyChar(ch)!=1){
            	list.add(new HuffmanElement2(ch, null, null));
            	hash.put(ch, i++);
            }
        }
		Collections.sort(list);
    }
	
	/*
	 * result 0 = no character
	 * result 1 = there's char, modified
	 */
	public int modifyChar(char ch){
		int result = 0;
		for(HuffmanElement2 tab : this.list){
			if(tab.character == ch){
				tab.addFrequency();
				result = 1;
			}
		}
		return result;
	}

	public void doHuff(String path, String out) throws IOException{
		codes_bytes = new Pair[hash.size()];
		asd = new Pair[hash.size()];
		makeHuffmanTree();
		buildCode(this.list.getFirst(), 0, 1);
		codeToFile(path, out);
	}
	public void makeHuffmanTree(){
		while(list.size()>1){
			HuffmanElement2 element1 = list.getFirst();
			list.remove();
			HuffmanElement2 element2 = list.getFirst();
			list.remove();
			HuffmanElement2 root = new HuffmanElement2((element1.frequency+element2.frequency), element1, element2);
			list.add(root);
			Collections.sort(list);
		}
	}
	
//	public void buildCode(HuffmanElement2 hfel, String s) {
//		this.codes = new String[hash.size()];
////		(2 *(x&y)+(x^y));
//        if (!hfel.isLeaf()) {
//            buildCode(hfel.left,  s + '0');
//            buildCode(hfel.right, s + '1');
//        }
//        else {
////            st[hfel.character] = s;
////        	hfel.code = s;
//        	codes[hash.get(hfel.character)] = s;
//        }
//    }
	private void buildCode(HuffmanElement2 hfel, int x, int size) {
		int shifted = x<<1;
		int added = (2 *(shifted&1)+(shifted^1));
        if (!hfel.isLeaf()) {
    		size++;
        	this.depth++;
            buildCode(hfel.left,  shifted, size);
            buildCode(hfel.right, added, size);
        }
        else {
//            st[hfel.character] = s;
        	codes_bytes[hash.get(hfel.character)] = new Pair(x,size);
//        	codes_bytes[hash.get(hfel.character)] = hfel.character;//new Pair(x,size);
//        	asd[x] = new Pair(x, size);
        	System.out.println(hfel.frequency+" "+hfel.character + " " + Integer.toBinaryString(x)+" "+Integer.toBinaryString(x).length()+" "+
        	codes_bytes[hash.get(hfel.character)].value +" "+codes_bytes[hash.get(hfel.character)].len);
//        	System.out.println(size+" "+ this.depth);
        }
    }
	
	public void codeToFile(String path, String out) throws IOException{
		BufferedReader br = null;
		br = new BufferedReader(new FileReader(path));
		File file = new File(out);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
//		FileOutputStream fil = new FileOutputStream(out);
        byte[] bs = new byte[32];
		int pozycja = 0;
		int r;
		char ch;
		String zero = "";
		String tekst  ="";
		while ((r = br.read()) != -1) {
            ch = (char) r;
			tekst += ch;
            Pair pair = codes_bytes[hash.get(ch)];

            bw.write(Integer.toBinaryString(codes_bytes[hash.get(ch)].value));
            zero +=Integer.toBinaryString(codes_bytes[hash.get(ch)].value);
            
            //dopakuj do bufora
            //jesli bufor pelny wyslij
//            if(!(pozycja<bs.length)){
//            	fil.write(bs);
//			}//jesli bufor niepelny nic
            
//            bw.write(pair.value);
//            pair.len;
//            System.out.println(pair.value);
//            bw.write(b.byteValue());//+Integer.toBinaryString(b));
//            bw.write(Integer.toBinaryString(b));
            
		}
//        System.out.println("tekst:\n"+zero+"\n"+tekst);
        System.out.println((tekst.length()*8));
        System.out.println((zero.length()));
        System.out.println((double)(zero.length())/(tekst.length()*8));
        System.out.println((double)(tekst.length()*8)/(zero.length()));

		br.close();
		bw.close();
	}
	
	public void decodeFromFile(String out) throws IOException{
		BufferedReader br = null;
		br = new BufferedReader(new FileReader(out));
		File file = new File("C:\\Users\\MZ\\Documents\\mra\\agh\\algo\\decode.txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		int r;
		char ch;
		String s = "";
		while ((r = br.read()) != -1) {
            ch = (char) r;
            s += ch;
            
//            if(asd[Integer.valueOf(s)]){	
            	s = "";
//            }
            Pair pair = codes_bytes[hash.get(ch)];

            bw.write(Integer.toBinaryString(codes_bytes[hash.get(ch)].value));
		}
		br.close();
		bw.close();
	}
//	public void decodeFromFile(String coded, String decoded) throws IOException{
//		BufferedReader br = null;
//		br = new BufferedReader(new FileReader(coded));
//		
//		File file = new File(decoded);
//		FileWriter fw = new FileWriter(file.getAbsoluteFile());
//		BufferedWriter bw = new BufferedWriter(fw);
////		bw = new BufferedWriter(new FileWriter(out));
//		int r;
//		char ch;
//		while ((r = br.read()) != -1) {
//            ch = (char) r;
////            System.out.println("Do something with " + ch);
////            list.get(ch);
//            Integer c = hash.get(ch);
//            int i;
//            Byte b =codes_bytes[hash.get(ch)];
//            bw.write(Integer.toBinaryString(b));
//            
//		}
//		br.close();
//		bw.close();
//	}
}
class Pair{
	int value;
	int len;
	
	public Pair(int val, int len) {
		this.value = val;
		this.len = len;
	}
}
