package algorithm;

import java.util.LinkedList;

public class HuffmanEdge {

	
	int weight;
	LinkedList<HuffmanElement> children;
	
	public HuffmanEdge(int weig) {
		this.weight = weig;
		this.children = new LinkedList<HuffmanElement>();
	}
	
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public LinkedList<HuffmanElement> getChildren() {
		return children;
	}
	public void setChildren(LinkedList<HuffmanElement> children) {
		this.children = children;
	}
	
	public void addChild(HuffmanElement el){
		this.children.add(el);
	}
}
