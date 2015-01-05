package algorithm;

import java.util.LinkedList;

public class HuffmanElement2 implements Comparable<HuffmanElement2>{
	char character;
	int frequency;
	String code; 
	public HuffmanElement2 left, right;
	
//	LinkedList<HuffmanElement2> children;
	public HuffmanElement2(char character, HuffmanElement2 right, HuffmanElement2 left) {
		this.character = character;
		this.frequency = 1;
		this.left = left;
		this.right = right;
	}
	public HuffmanElement2(char character, int freq, HuffmanElement2 right, HuffmanElement2 left) {
		this.character = character;
		this.frequency = freq;
		this.left = left;
		this.right = right;
	}
	public HuffmanElement2(int freq, HuffmanElement2 right, HuffmanElement2 left) {
//		this.character = (Character) null;
		this.frequency = freq;
		this.left = left;
		this.right = right;
	}
	boolean isLeaf(){
		return (left == null || right == null);
	}
	public HuffmanElement2(char character) {
		this.character = character;
		this.frequency = 1;
	}
	public HuffmanElement2(char character, String code) {
		this.character = character;
		this.frequency = 1;
		this.code = code;
	}
	public HuffmanElement2(int freq) {
		this.character = (Character) null;
		this.code = null;
		this.frequency = freq;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public char getCharacter() {
		return character;
	}

	public void setCharacter(char character) {
		this.character = character;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public void addFrequency(){
		this.frequency++;
	}
//	public LinkedList<HuffmanElement2> getChildren() {
//		return children;
//	}
//	public void setChildren(LinkedList<HuffmanElement2> children) {
//		this.children = children;
//	}
//	
//	public void addChild(HuffmanElement2 el){
//		this.children.add(el);
//	}
	@Override
	public int compareTo(HuffmanElement2 o) {
		int comparedFreq = o.frequency;
		if (this.frequency > comparedFreq){
			return 1;
		}
		else if(this.frequency == comparedFreq){
			return 0;
		}
		else{
			return -1;
		}
	}
	
	
}