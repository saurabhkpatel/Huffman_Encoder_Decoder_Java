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
			testHuffman(inputString);
		}
		else
		{
			System.out.println("   Unit testing of implemented program   ");
			System.out.println("*****************************************");
			testHuffman(inputString);
			System.out.println("   program ends here   ");
			System.out.println("************************");
			
		}
	}
	
	private static boolean testHuffman(String input)
	{
		Huffman huffman = new Huffman();
		try {
			System.out.println("Input Data : "+input + " "+input.length() + " bytes\n");
			if(huffman.compress(input))
			{
				System.out.println("Compressed data fits in "+huffman.getEncodedBytes().length + " bytes");
				System.out.println();
				String outputString = huffman.decompress(huffman.getEncodedBytes());
				System.out.println("Original String after decompression : ");
				System.out.println(outputString);
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

