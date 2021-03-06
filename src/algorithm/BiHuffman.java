package algorithm;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
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
	int textlen;
	static int codelen;
	HuffmanElement2 root;
	Map<String, Integer> hash_frequency;
	LinkedList<codes> list_codes;
	int depth;
   	private static int buff;
   	private static int buff2;
	private static int occupied_bits;
	private static int remaining_bits;
	static BufferedOutputStream out;
	static BufferedInputStream in; 
	
	public BiHuffman(String path) throws IOException {
		out = new BufferedOutputStream(new FileOutputStream("C:\\Users\\MZ\\Documents\\mra\\agh\\algo\\BiHuffman_binary_coded.txt"));
		in = new BufferedInputStream(new FileInputStream("C:\\Users\\MZ\\Documents\\mra\\agh\\algo\\BiHuffman_binary_coded.txt"));
		this.depth=0;
		list = new LinkedList<HuffmanElement2>();
		hash_frequency = new HashMap<String, Integer>();
		BufferedReader br = null;
		br = new BufferedReader(new FileReader(path));
		int r;
		String s;
		int i=0;
		while ((r = br.read()) != -1) {
			this.textlen++;
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
		this.root= this.list.getFirst();
		buildCode(this.root, "");
		Collections.sort(this.list_codes);
		codeToFile(path, out);
		decodeFromFile(out);
		decode(this.root);
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
	
	private void buildCode(HuffmanElement2 hfel, String x) {
        if (!hfel.isLeaf()) {
            buildCode(hfel.left,  x +"0");
            buildCode(hfel.right, x +"1");
        }
        else {
        	codes_bytes[hash_frequency.get(hfel.character)] = new Pair(x,x.length());
//        	cds[this.depth++] = new codes(hfel.character,x);
        	list_codes.add(new codes(hfel.character,x));
//        	System.out.println(hfel.frequency+" "+hfel.character + " "+x.length()+"asd "+
//			        	codes_bytes[hash_frequency.get(hfel.character)].value +" "+ 
//			        	Integer.parseInt(codes_bytes[hash_frequency.get(hfel.character)].value,2));
        }
    }
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
		String[] text_coded = new String[this.textlen];
		while ((r = br.read()) != -1) {
            s = String.valueOf((char)r);
            if((r = br.read()) != -1){
                s += String.valueOf((char)r);
//                System.out.println(Byte.valueOf(codes_bytes[hash_frequency.get(s)].value));
//                bOutput.write(codes_bytes[hash_frequency.get(s)].value);
                bw.write(codes_bytes[hash_frequency.get(s)].value);
            	
            }
            else{
                bw.write(codes_bytes[hash_frequency.get(s)].value);
            }
			tekst += s;

            zero +=codes_bytes[hash_frequency.get(s)].value;
		}
		text_coded=zero.split("(?!^)");
		codelen = zero.length();
//		BinaryStdOut outf = new BinaryStdOut();
		writeBin(text_coded);
		int len_coded=(zero.length()/8)+1;
        System.out.println("Text to code (len): "+(tekst.length()));
        System.out.println("Coded text (len): "+len_coded);
        System.out.println("Ratio (toCode/coded): "+(double)(tekst.length())/len_coded);

		br.close();
		bw.close();
	}
	
	public void decodeFromFile(String coded) throws IOException{
		BufferedReader br = null;
		br = new BufferedReader(new FileReader(coded));
		File file = new File("C:\\Users\\MZ\\Documents\\mra\\agh\\algo\\BiHuffman_decoded.txt");
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
		int len_coded=(zero.length()/8)+1;
        System.out.println("Text decoded (len): "+(tekst.length()));
        System.out.println("Text coded (len): "+len_coded);
        System.out.println("Ratio (decoded/coded): "+(double)(tekst.length())/len_coded);
		br.close();
		bw.close();
	}
	
	private void writeBin(String[] text_coded) {
		 for (int j = 0; j < text_coded.length; j++) {
            if (text_coded[j].equals("0")) {
               writeBit(false);
            }
            else if (text_coded[j].equals("1")) {
           	writeBit(true);
            }
        }
		 close(out);
	}

	private static void close(BufferedOutputStream out) {
		flush(out);
       try { out.close(); }
       catch (IOException e) { e.printStackTrace(); }		
	}

	private static void flush(BufferedOutputStream out) {
		sendBuffer(out);
       try { out.flush(); }
       catch (IOException e) { e.printStackTrace(); }		
	}

	private static void writeBit(boolean b) {
		buff <<=1;
		if(b == true){
			buff |=1;
		}
		occupied_bits++;
		if(occupied_bits == 8) sendBuffer(out);		
	}

	private static void sendBuffer(BufferedOutputStream out) {
		if (occupied_bits == 0) return;
       if (occupied_bits > 0) buff <<= (8 - occupied_bits);
       try { 
       	out.write(buff); 
//       	System.out.println(Integer.toBinaryString(buff));
       } catch (IOException e) { 
       	e.printStackTrace(); 
       }
       occupied_bits = 0;
       buff = 0;
	}
	
	public static void decode(HuffmanElement2 root) throws IOException{
//		remaining_bits = 0;
		File file = new File("C:\\Users\\MZ\\Documents\\mra\\agh\\algo\\BiHuffman_binary_decoded.txt");
        boolean bit = readBoolean(in);
		 for (int i = 0; i < codelen; i++) {
			 	HuffmanElement2 x = root;
	            while (!x.isLeaf()) {
	                try{
	                	bit = readBoolean(in);
	                }
	                catch(RuntimeException e) {
//	                    System.out.println("File (len): "+(double)file.length());
	                	return;
	                }
//	                System.out.println("bit: "+Boolean.toString(bit));
	                if (bit) x = x.right;
	                else     x = x.left;
	            }
//	            System.out.println("x: "+x.character + " " +x.code);
	            writeDecoded(x.character,file);
	        }
//	        close(decoded);	
	}

	private static void writeDecoded(String character, File file) throws IOException {
		BufferedReader br = null;
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
		BufferedWriter bw = new BufferedWriter(fw);
       	bw.write(character);
       	bw.close();

	}
	private static boolean readBoolean(BufferedInputStream in) throws RuntimeException{
		if (isEmpty()) throw new RuntimeException("Reading from empty input stream");
        remaining_bits--;
//		System.out.println(remaining_bits);
        if (remaining_bits < 0) fillBuffer(in);
        boolean bit = ((buff2 >> remaining_bits) & 1) == 1;
        if (remaining_bits == 0) fillBuffer(in);
        return bit;
	}
	
	private static void fillBuffer(BufferedInputStream in) {
		try { 
			buff2 = in.read(); 
			remaining_bits = 8; 
//			System.out.print(buff2);
        }
		catch (IOException e) { 
//        	System.out.println("EOF"); buff2 = -1; remaining_bits = -1; 
        	}
    }

	public static boolean isEmpty() {
        return buff2 == -1;
    }
}

