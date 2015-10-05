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

	public static void main(final String[] args) {
		String inputString = "";
		if (args.length == 1) {
		    try {
		    	inputString = args[0];
		    } catch (final NumberFormatException e) {
		        System.err.println("Argument" + args[0]);
		    }
		}
		
		if(inputString.length() > 0)
		{
			testHuffman(inputString,false);
		}
		else
		{
			System.out.println("   Unit testing of implemented program   ");
			System.out.println("*****************************************");
			
			testHuffman("Hello how are you?",true);
			testHuffman("AS ss   AS.",true);
			testHuffman("",true);
			testHuffman(null,true);
			testHuffman("A",true);
			testHuffman("AB",true);
			testHuffman("ABRRKBAARAA",true);
			

			System.out.println("   program ends here   ");
			System.out.println("************************");
			
		}
	}
	
	private static boolean testHuffman(final String input,final boolean flag)
	{
		final Huffman huffman = new Huffman();
		try {
			
			if(flag)
				System.out.println("\n === Unit Test Case === ");	
			else
				System.out.println("\n === User Input Test Case === ");
			
			
			if(huffman.compress(input))
			{
				int originalBytes = 0;
				if( input!= null)
					originalBytes = input.length(); 
				final int compressedBytes =  huffman.getEncodedBytes().length;
				final String outputString = huffman.decompress(huffman.getEncodedBytes());
				System.out.println();
				System.out.println("=> Original Input string after decompression : ");
				System.out.println("   "+outputString);
				System.out.println();
				
				if(input.equals(outputString))
				{
					final int percentage =(int) ((Math.abs(originalBytes-compressedBytes)/(double)originalBytes)*100);
					System.out.println("*** Compressed :      "+percentage+"%");
					return true;
				}
					
			}
			return false;
		} catch (final Exception e) {
			System.out.println("Exception");
			e.printStackTrace();
			return false;
		}
	}
}

