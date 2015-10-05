/**
 * HuffmanQuizMain.java
 * This class is the starting point of this application. 
 * It will demonstrates the compression and decompression of input string message by command line argument or
 * show results of unit testing if command line argument has not provided.

 * @author Saurabh Patel, skpatel@syr.edu
 * @version 1.0
 * @date 10/05/2015
 */
public class HuffmanQuizMain {

	public static void main(String[] args) {
		String inputString = "";
		if (args.length == 2) {
		    try {
		    	inputString = args[0];
		    } catch (NumberFormatException e) {
		        System.err.println("Argument" + args[0]);
		    }
		}
		
		if(inputString.length() > 0)
		{
	
		}
		else
		{
			System.out.println("   Unit testing of implemented program   ");
			System.out.println("*****************************************");
			
			testHuffman("Hello how are you doing?");
			testHuffman("Good morning.");
			testHuffman("");
			testHuffman(null);
			testHuffman("A");
			testHuffman("AB");
			testHuffman("ABC");
			

			System.out.println("   program ends here   ");
			System.out.println("************************");
			
		}
	}
	
	private static boolean testHuffman(String input)
	{
		Huffman huffman = new Huffman();
		try {
			
			System.out.println(" === Test Case === ");
			
			if(huffman.compress(input))
			{
				String outputString = huffman.decompress(huffman.getEncodedBytes());
				System.out.println();
				System.out.println("=> Original Input string after decompression : ");
				System.out.println("   "+outputString);
				System.out.println();
				if(input.equals(outputString))
					return true;
			}
			return false;
		} catch (Exception e) {
			System.out.println("Exception");
			e.printStackTrace();
			return false;
		}
	}
}

