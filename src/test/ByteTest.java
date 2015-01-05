package test;

import static org.junit.Assert.*;

import org.junit.Test;

public class ByteTest {

	@Test
	public void test() {
		int bitmask = 0x000F;
        int val = 0x0008;
        // prints "2"
        System.out.println(val+" "+(val & bitmask));
        byte x = 2;
        byte y = 1;
        System.out.println(Integer.toBinaryString(2 *(x&y)+(x^y)));
        System.out.println(Integer.SIZE);
//        System.out.println(Byte.valueOf("7"));
	int i = 0;
	i++;
	}

}
