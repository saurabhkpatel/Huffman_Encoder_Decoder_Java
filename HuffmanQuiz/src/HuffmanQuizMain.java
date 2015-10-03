import java.io.IOException;

public class HuffmanQuizMain {

	public static void main(String[] args) {
		
		Huffman h = new Huffman();
		try {
			String temp  = "Hello how aer you doing, i hope you are oin% 23234 asdasdsad nice to meet you ABRRKB AARA A";
			System.out.println(temp);
			h.compress("Hello how aer you doing, i hope you are oin% 23234 asdasdsad nice to meet you ABRRKB AARA A");
		} catch (IOException e) {
			System.out.println("Exception");
			e.printStackTrace();
		}
		
//		if (sentence == null) {
//			throw new NullPointerException("Input sentence cannot be null.");
//		}
//		if (sentence.length() == 0) {
//			throw new IllegalArgumentException("The string should atleast have 1 character.");
//		}
		
	}
}

