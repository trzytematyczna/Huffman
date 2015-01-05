package algorithm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
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
import java.util.Map;
import java.util.Map.Entry;

public class BiHuffman {

	public LinkedList<HuffmanElement2> list;
	Pair[] codes_bytes;
//	codes[] cds;
	Map<String, Integer> hash_frequency;
	LinkedList<codes> list_codes;
	int depth;
	
	public BiHuffman(String path) throws IOException {
		this.depth=0;
		list = new LinkedList<HuffmanElement2>();
		hash_frequency = new HashMap<String, Integer>();
		BufferedReader br = null;
		br = new BufferedReader(new FileReader(path));
		int r;
		String s;
		int i=0;
		while ((r = br.read()) != -1) {
            s = String.valueOf((char)r);
            if((r = br.read()) != -1){
                s += String.valueOf((char)r);
	            if(modifyChar(s)!=1){
	            	list.add(new HuffmanElement2(s, null, null));
	            	hash_frequency.put(s, i++);
	            }
            }
            else{
 	            if(modifyChar(s)!=1){
 	            	list.add(new HuffmanElement2(s, null, null));
 	            	hash_frequency.put(s, i++);
 	            }
            }
        }
		Collections.sort(list);
    }
	
	/*
	 * result 0 = no character
	 * result 1 = there's char, modified
	 */
	public int modifyChar(String ch){
		int result = 0;
		for(HuffmanElement2 tab : this.list){
			if(tab.character.equals(ch)){
				tab.addFrequency();
				result = 1;
			}
		}
		return result;
	}

	public void doHuff(String path, String out) throws IOException{
//		cds = new codes[hash_frequency.size()];
		codes_bytes = new Pair[hash_frequency.size()];
		list_codes = new LinkedList<codes>();
		makeHuffmanTree();
		buildCode(this.list.getFirst(), "");
		Collections.sort(this.list_codes);
		codeToFile(path, out);
		decodeFromFile(out);
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
	
//	private void buildCode(HuffmanElement2 hfel, int x, int size) {
//		int shifted = x<<1;
//		int added = (2 *(shifted&1)+(shifted^1));
//        if (!hfel.isLeaf()) {
//    		size++;
//        	this.depth++;
//            buildCode(hfel.left,  shifted, size);
//            buildCode(hfel.right, added, size);
//        }
//        else {
////        	System.out.println(hash_frequency.get(hfel.character));
//        	codes_bytes[hash_frequency.get(hfel.character)] = new Pair(x,Integer.toBinaryString(x).length());
//        	
//        	System.out.println(hfel.frequency+" "+hfel.character + " " + Integer.toBinaryString(x)+" "+Integer.toBinaryString(x).length()+" "+
//        	codes_bytes[hash_frequency.get(hfel.character)].value +" "+codes_bytes[hash_frequency.get(hfel.character)].len);
////        	System.out.println(size+" "+ this.depth);
//        	this.last = codes_bytes[hash_frequency.get(hfel.character)].value+1;
//        }
//    }
	private void buildCode(HuffmanElement2 hfel, String x) {
        if (!hfel.isLeaf()) {
            buildCode(hfel.left,  x +"0");
            buildCode(hfel.right, x +"1");
        }
        else {
        	codes_bytes[hash_frequency.get(hfel.character)] = new Pair(x,x.length());
//        	cds[this.depth++] = new codes(hfel.character,x);
        	list_codes.add(new codes(hfel.character,x));
        	System.out.println(hfel.frequency+" "+hfel.character + " "+x.length()+"asd "+
			        	codes_bytes[hash_frequency.get(hfel.character)].value +" "+ 
			        	Integer.parseInt(codes_bytes[hash_frequency.get(hfel.character)].value,2));
        }
    }
//	private String findAlph(String z){
//		for(int i=0; i<cds.length; i++){
//			if(cds[i].zerone.equals(z)) return cds[i].charact;
////			if(cds[i].zerone.length()>z.length()) return null;
//		}
//		return null;
//	}
	private String findAlph(String z){
		for(codes cod : list_codes){
			if(cod.zerone.equals(z)) return cod.charact;
			if(cod.zerone.length()>z.length()) return null;
		}
		return null;
	}
	public void codeToFile(String path, String out) throws IOException{
		ByteArrayOutputStream bOutput = new ByteArrayOutputStream();
		BufferedReader br = null;
		br = new BufferedReader(new FileReader(path));
		File file = new File(out);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		int r;
		String s = "";
		String zero = "";
		String tekst  ="";
		while ((r = br.read()) != -1) {
            s = String.valueOf((char)r);
            if((r = br.read()) != -1){
                s += String.valueOf((char)r);
                System.out.println(Byte.valueOf(codes_bytes[hash_frequency.get(s)].value));
//                bOutput.write(codes_bytes[hash_frequency.get(s)].value);
                bw.write(codes_bytes[hash_frequency.get(s)].value);
            	
            }
            else{
                bw.write(codes_bytes[hash_frequency.get(s)].value);
            }
			tekst += s;

            zero +=codes_bytes[hash_frequency.get(s)].value;
		}
        System.out.println((tekst.length()*8));
        System.out.println((zero.length()));
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
		String zero = "";
		String tekst  ="";

		while ((r = br.read()) != -1) {
            ch = (char) r;
            s += ch;
            String found = findAlph(s);
            if(found!=null){
//            	System.out.println(s);
            	bw.write(found);
                zero += s;
                tekst += found;
                s="";
            }
		}
        System.out.println((tekst.length()*8));
        System.out.println((zero.length()));
        System.out.println((double)(tekst.length()*8)/(zero.length()));
		br.close();
		bw.close();
	}
}

//class Pair{
//	String value;
//	int len;
//	
//	public Pair(String s, int len) {
//		this.value = s;
//		this.len = len;
//	}
//}
//
//class codes implements Comparable<codes>{
//	String charact;
//	String zerone;
//	
//	public codes(String c, String z) {
//		this.charact = c;
//		this.zerone = z;
//	}
//
//	@Override
//	public int compareTo(codes cd) {
//		int compared = cd.zerone.length();
//		if (this.zerone.length() > compared){
//			return 1;
//		}
//		else if (this.zerone.length() == compared){
//			return 0;
//		}
//		else{
//			return -1;
//		}
//	}
//
	
//}
