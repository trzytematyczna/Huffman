package algorithm;

public class HuffmanElement implements Comparable<HuffmanElement>{

	char character;
	int frequency;
	byte code; 

	public byte getCode() {
		return code;
	}
	public void setCode(byte code) {
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

	public HuffmanElement(char character) {
		this.character = character;
		this.frequency = 1;
	}
	public HuffmanElement(char character, int code) {
		this.character = character;
		this.frequency = 1;
		this.code = (byte) code;
	}
	
	public void addFrequency(){
		this.frequency++;
	}
	
	@Override
	public int compareTo(HuffmanElement o) {
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