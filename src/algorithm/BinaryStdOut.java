//package algorithm;
//
//import java.io.BufferedOutputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//
//public class BinaryStdOut {
//
////	public BinaryStdOut() throws FileNotFoundException {
////		out = new BufferedOutputStream(new FileOutputStream("C:\\Users\\MZ\\Documents\\mra\\agh\\algo\\test.txt"));
////	}
//	
//	private static BufferedOutputStream out = new BufferedOutputStream(System.out);
//
//   	private static int buff;
//	private static int remining_bits;
//	
//	public static void write(boolean b) {
//		buff <<=1;
//		if(b == true){
//			buff |=1;
//		}
//		remining_bits++;
//		if(remining_bits == 8) sendBuffer();
//	}
//
//	private static void sendBuffer() {
//		if (remining_bits == 0) return;
//        if (remining_bits > 0) buff <<= (8 - remining_bits);
//        try { out.write(buff); }
//        catch (IOException e) { e.printStackTrace(); }
//        remining_bits = 0;
//        buff = 0;
//	}
//	public static void flush() {
//		sendBuffer();
//        try { out.flush(); }
//        catch (IOException e) { e.printStackTrace(); }
//    }
//
//    public static void close() {
//        flush();
//        try { out.close(); }
//        catch (IOException e) { e.printStackTrace(); }
//    }
//    public static void write(int x) {
//        writeByte((x >>> 24) & 0xff);
//        writeByte((x >>> 16) & 0xff);
//        writeByte((x >>>  8) & 0xff);
//        writeByte((x >>>  0) & 0xff);
//    }
//    
//    private static void writeByte(int x) {
//        assert x >= 0 && x < 256;
//
//        // optimized if byte-aligned
//        if (remining_bits == 0) {
//            try { out.write(x); }
//            catch (IOException e) { e.printStackTrace(); }
//            return;
//        }
//
//        // otherwise write one bit at a time
//        for (int i = 0; i < 8; i++) {
//            boolean bit = ((x >>> (8 - i - 1)) & 1) == 1;
//            write(bit);
//        }
//    }
//
//}
