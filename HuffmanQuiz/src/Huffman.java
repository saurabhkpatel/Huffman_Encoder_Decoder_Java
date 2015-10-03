import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.PriorityQueue;

public class Huffman {
	
	
	// Count the frequency of all the characters
    HashMap<Character, Integer> freuencyTable = new HashMap<Character, Integer>();
    PriorityQueue<HuffmanTreeNode> queue = new PriorityQueue<HuffmanTreeNode>();
    
    HashMap<Character, String> codeTable = new HashMap<Character, String>();
    byte[] mEncodedBytes;
    int mRemainingBits = 0;
    
    private void prepareFreqTable(final String text)
    {
    	for(int i = 0; i < text.length(); i++) {
            final char a = text.charAt(i);
 
            if(freuencyTable.containsKey(a))
            	freuencyTable.put(a, freuencyTable.get(a)+1);
            else
            	freuencyTable.put(a, 1);
        }
    }
    
    private void printFreqTable(final HashMap<Character,Integer> freqHashmap)
    {
    	System.out.println("Frequency Table : ");
        for (final Entry<Character, Integer> entry : freqHashmap.entrySet())
            System.out.println(entry.getKey() + " : " + entry.getValue());
        System.out.println("\n\n");
    }

    private void buildHuffmanTree(final HashMap<Character,Integer> freqHashmap)
    {
    	createPriorityQueue(freqHashmap);
    	buildTree(queue);
    }
    
    
    private void createPriorityQueue(final HashMap<Character,Integer> freqHashmap)
    {
    	for (final Entry<Character, Integer> entry : freqHashmap.entrySet())
    	{
    		queue.add(new HuffmanTreeNode(entry.getKey(), entry.getValue(), null, null));
    	}
    }
    
    private void buildTree(final PriorityQueue<HuffmanTreeNode> queue)
    {
    	 while (queue.size() > 1) {
             final HuffmanTreeNode left = queue.poll();
             final HuffmanTreeNode right = queue.poll();
             final int totalFreq = left.mFrequency + right.mFrequency;
             final HuffmanTreeNode node = new HuffmanTreeNode('#',totalFreq, left, right);
             queue.add(node);
         }
    }
    
    private void getCodeTable()
    {
    	HuffmanTreeNode root = getRoot();
    	generateCode("",root);
    	
    }
    
    private void generateCode(String code,HuffmanTreeNode root)
    {
    	if(root.mLeft == null && root.mRight == null)
    		codeTable.put(root.mLetter, code);
    	else
    	{
    		if(root.mLeft!= null)
        		generateCode(code+"1",root.mLeft);
        	if(root.mRight!= null)
        		generateCode(code+"0",root.mRight);
    	}
    }
    
    private HuffmanTreeNode getRoot()
    {
    	return queue.peek();
    }
    
    private void printCodingTable(final HashMap<Character,String> codeTable)
    {
    	System.out.println("Encoding Table : ");
        for (final Entry<Character, String> entry : codeTable.entrySet())
            System.out.println(entry.getKey() + " : " + entry.getValue());
        System.out.println("\n\n");
    }
    
    private String generateEncodedMessage(String inputStr)
    {
    	String result = "";
    	for (int i = 0; i < inputStr.length(); i++) {
			result = result +  codeTable.get(inputStr.charAt(i));
		}
    	System.out.println(result);
    	convertInBits(result);
		return result;
    }
    
    private void convertInBits(String binaryString)
    {
    	boolean completeBytes = true;
    	int numOfBytes = binaryString.length() / 8;
    	int remainingBytes = binaryString.length()%8 ;
    	if(remainingBytes != 0)
    	{
    		numOfBytes++;
    		completeBytes = false;
    	}
    		
    	
    	mEncodedBytes = new byte[numOfBytes];
    	for(int i = 0; i < numOfBytes; ++i) {
    		if(!completeBytes && (i==(numOfBytes-1)))
    		{
    			String temp = binaryString.substring(8 * i, (8 * i) + remainingBytes);
    			System.out.println(temp);
    			mRemainingBits=remainingBytes;
    			mEncodedBytes[i] = (byte) (Integer.parseInt(temp, 2) & 0xFF);
    		}
    		else
    		{
    			String temp = binaryString.substring(8 * i, (8 * i) + 8);
    			System.out.println(temp);
    			mEncodedBytes[i] = (byte) (Integer.parseInt(temp, 2) & 0xFF);
    		}
    		
    		
    		
    	}
    	
    	for (int i = 0; i < mEncodedBytes.length; i++) {
			System.out.println((int)mEncodedBytes[i]);
		}
    }
    
    private String getBitsString(byte [] compressBytes)
    {
    	String result = "";
    	for (int i = 0; i < compressBytes.length; i++) {
    		byte b1 = (byte) compressBytes[i];
    		String byteString = "";
    		if(mRemainingBits > 0 && i==(compressBytes.length -1))
    		{
    			String format = "%"+mRemainingBits+"s";
    			byteString = String.format(format, Integer.toBinaryString(b1 & 0xFF)).replace(' ', '0');
    		}else
    		{
    			byteString = String.format("%8s", Integer.toBinaryString(b1 & 0xFF)).replace(' ', '0');
    		}
        	result+=byteString;
		}
    	return result;
    }
    
    public void decompress(byte[] compressBytes)
    {
    	String str = getBitsString(compressBytes);
    	HuffmanTreeNode root = getRoot();
    	System.out.println(decodeMessage(str,root));
    }
    
    private String decodeMessage(String message,HuffmanTreeNode node)
    {
    	StringBuilder decodedMessage = new StringBuilder();
    	HuffmanTreeNode root = node;
    	if(root == null)
    		return new String(decodedMessage);
    	for (int i = 0; i < message.length(); i++) {
    		// check this is leaf node or not.
    		if(root.mLeft == null && root.mRight == null)
    		{
    			//System.out.println(root.mLetter);
    			decodedMessage.append(root.mLetter);
    			root = node;
    		}
    		if(message.charAt(i) == '1')
    		{
    			root = root.mLeft;
    		}
    		else if(message.charAt(i) == '0')
    		{
    			root = root.mRight;
    		}
    		
		}
    	return new String(decodedMessage);
    }
    
	/**
	 * Compresses the string using huffman algorithm. The huffman tree and the
	 * huffman code are serialized to disk
	 * 
	 * @param sentence
	 *            The sentence to be serialized
	 * @throws FileNotFoundException
	 *             If file is not found
	 * @throws IOException
	 *             If IO exception occurs.
	 */
	public void compress( String sentence) throws FileNotFoundException, IOException {
		
		// add new extra character :
		String tempExtra = String.valueOf(Character.toChars(3));
		String input = sentence+ tempExtra;
		prepareFreqTable(input);
		printFreqTable(freuencyTable);
		buildHuffmanTree(freuencyTable);
		getCodeTable();
		printCodingTable(codeTable);
		generateEncodedMessage(input);
		decompress(mEncodedBytes);
	}

}
