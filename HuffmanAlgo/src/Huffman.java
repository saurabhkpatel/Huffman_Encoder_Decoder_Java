import java.util.HashMap;
import java.util.Map.Entry;
import java.util.PriorityQueue;

/**
 * Huffman.java This class contains compression and decompression methods.
 * Compression method will take input as string (original text) and convert that
 * input string in compressed format using huffman algo. Final outcome after
 * compression is encoded bytes of input string.
 * 
 * In Decompression, those encoded bytes will be converted to original string
 * using huffman tree which we already computed in compression part.
 * 
 * 
 * In quiz description there is one problem stated :
 * 
 * " You may encounter one issue during the decompression/decoding phase. Your
 * encoded string may not be a multiple of 8. This means that when you compress
 * your encoding into a binary number, padding 0’s get added. Then, upon
 * decompression, you may see extra characters. To counter this, one solution is
 * to add your own padding of 1 extra character every time. And then simply
 * strip it off once you have decoded."
 * 
 * To over come this problem, I have added new Extra character that character is
 * End ot Text (0x03) In addition to that, After encoded string while we are
 * converting string to bytes, at that time in last byte we need to keep track
 * of number of bits are in multiple of 8 or not. If not then we need to keep
 * information somewhere that we are going to take only last number of bits
 * which we kept.
 * 
 * Lets take a example to explain this :
 * 
 *  * // @formatter:off
 * 
** Input Data : ABRRKBAARAA 11 bytes

=> Frequency Table : 
   A : 5
   B : 2
   R : 3
    : 1
   K : 1

=> The encoding for each character : 
   A : 1
   R : 01
   B : 001
    : 0001
   K : 0000

=> Here is the original data encoded : 
   1001010100000011101110001
   Compressed data fits in 4 bytes

=> Original Input string after decompression : 
   ABRRKBAARAA
   
 * 
 * 
 * In above example, input text is going to be convert in this after huffman code
 * 1001010100000011101110001. This string we can represent in 4 bytes.
 * 
 * 10010101 => 149
 * 00000011 => 3
 * 10111000 => 184  
 * 1        => 1 
 * 
 * // @formatter:on
 * 
 * Here in last 4th byte we have only 1 bit information which is important for
 * us, so we need to share this information to decompression side. So when
 * decompression algo will convert all bytes as 8 bits but only last byte as 1
 * bit only.
 * 
 * So, this information I am sharing with tree, when tree is serialize in file.
 * At that time I am also storing this number of remaining bits information for
 * last byte.
 * 
 * I have another idea to overcome from this problem. In that idea we can add
 * one character with only 1 frequency and add in huffman tree at last. ut in
 * that case in every character's code one bit information will be increase.
 * Thus, I felt this approach better than that one.
 * 
 * @author Saurabh Patel, skpatel@syr.edu
 * @version 1.0
 * @date 10/05/2015
 */
public class Huffman {
	// Store encoded bytes after compression.
	// This is local variable which is not shared with decompression.
	// We use this remaining bits idea to overcome from the problem which has
	// been mentioned in quiz.
	// this variable value will be serialize with huffman tree, so at the time
	// of decompression we can share
	// on other computer as like huffman tree.
	private byte[] mEncodedBytes;
	private int mRemainingBits = 0;

	/**
	 * @return the mEncodedBytes
	 */
	public byte[] getEncodedBytes() {
		return mEncodedBytes;
	}

	/**
	 * @param mEncodedBytes
	 *            the mEncodedBytes to set
	 */
	public void setEncodedBytes(final byte[] mEncodedBytes) {
		this.mEncodedBytes = mEncodedBytes;
	}

	/**
	 * Prepare frequency table for huffman tree.
	 * 
	 * @param text
	 *            input message text.
	 * @return freuencyTable Frequency table.
	 */
	private HashMap<Character, Integer> prepareFreqTable(final String text) {
		final HashMap<Character, Integer> freuencyTable = new HashMap<Character, Integer>();
		// Read all the contents of the input text and count them.
		// Add in hashmap with number of count, thus, we can prepare frequency
		// table.
		for (int i = 0; i < text.length(); i++) {
			final char a = text.charAt(i);
			if (freuencyTable.containsKey(a))
				freuencyTable.put(a, freuencyTable.get(a) + 1);
			else
				freuencyTable.put(a, 1);
		}
		return freuencyTable;
	}

	/**
	 * Print frequency table.
	 * 
	 * @param freqHashmap
	 *            Frequency hash table.
	 */
	private void printFreqTable(final HashMap<Character, Integer> freqHashmap) {
		System.out.println("=> Frequency Table : ");
		for (final Entry<Character, Integer> entry : freqHashmap.entrySet())
			System.out.println("   " + entry.getKey() + " : "
					+ entry.getValue());
		System.out.println();
	}

	/**
	 * Build huffman tree from frequency table.
	 * 
	 * @param freqHashmap
	 *            Frequency hash table.
	 * @return rootNode of HuffmanTree
	 */
	private HuffmanTreeNode buildHuffmanTree(
			final HashMap<Character, Integer> freqHashmap) {
		final PriorityQueue<HuffmanTreeNode> mPriorityQueue = new PriorityQueue<HuffmanTreeNode>();
		for (final Entry<Character, Integer> entry : freqHashmap.entrySet()) {
			mPriorityQueue.add(new HuffmanTreeNode(entry.getKey(),
					entry.getValue(), null, null));
		}

		while (mPriorityQueue.size() > 1) {
			final HuffmanTreeNode left = mPriorityQueue.poll();
			final HuffmanTreeNode right = mPriorityQueue.poll();
			final int totalFreq = left.mFrequency + right.mFrequency;
			final HuffmanTreeNode node = new HuffmanTreeNode('#', totalFreq,
					left, right);
			mPriorityQueue.add(node);
		}
		return mPriorityQueue.peek();
	}

	/**
	 * Generate code table for characters using huffman tree.
	 * 
	 * @param rootNode
	 *            root node of Huffman tree, we can get code of each character
	 *            by traverse this.
	 * @return codeTable return table which contains huffman code for every
	 *         character.
	 */
	private HashMap<Character, String> getCodeTable(final HuffmanTreeNode rootNode) {
		final HashMap<Character, String> codeTable = new HashMap<Character, String>();
		generateCode("", rootNode, codeTable);
		return codeTable;
	}

	/**
	 * Build code table for characters using huffman tree. This is recursive
	 * function.
	 * 
	 * @param code
	 *            code string which will build based on position of character in
	 *            huffman tree. initially its empty string.
	 * @param root
	 *            root node of huffman tree.
	 * @param codeTable
	 *            out variable code table will be filled up using this function.
	 */
	private void generateCode(final String code, final HuffmanTreeNode root,
			final HashMap<Character, String> codeTable) {
		if (root.mLeft == null && root.mRight == null)
			codeTable.put(root.mLetter, code);
		else {
			if (root.mLeft != null)
				generateCode(code + "1", root.mLeft, codeTable);
			if (root.mRight != null)
				generateCode(code + "0", root.mRight, codeTable);
		}
	}

	/**
	 * Print encoding table, where each character and its respective code will
	 * be display.
	 * 
	 * @param codeTable
	 *            code table is hashmap which stores key/value = character/code.
	 */
	private void printCodingTable(final HashMap<Character, String> codeTable) {
		System.out.println("=> The encoding for each character : ");
		for (final Entry<Character, String> entry : codeTable.entrySet())
			System.out.println("   " + entry.getKey() + " : "
					+ entry.getValue());
		System.out.println();
	}

	/**
	 * Generate encoded message using code table which we build with help of
	 * huffman tree.
	 * 
	 * @param inputStr
	 *            original input message string.
	 * @param codeTable
	 *            huffman code table.
	 * @return result binary format string.
	 */
	private String generateEncodedMessage(final String inputStr,
			final HashMap<Character, String> codeTable) {
		String result = "";
		for (int i = 0; i < inputStr.length(); i++) {
			result = result + codeTable.get(inputStr.charAt(i));
		}
		convertInBits(result);
		return result;
	}

	/**
	 * Get bytes from binary string which we formed using huffman tree.
	 * 
	 * @param binaryString
	 *            binary string representation of original message using huffman
	 *            tree.
	 * @return bytes resultant array of bytes.
	 */
	private byte[] convertInBits(final String binaryString) {
		boolean completeBytes = true; // flag of complete bytes.
		int numOfBytes = binaryString.length() / 8; // calculate number of bytes
													// using binary string
													// length.
		final int remainingBytes = binaryString.length() % 8;// check is there
																// any
		// remaining bytes.
		// actually we are here checking that we have binary string length is
		// multiple of 8 or not.
		// if not then we are storing remaining bytes after divide of 8 in one
		// variable which we will use at the time of decompression.
		if (remainingBytes != 0) {
			numOfBytes++;
			completeBytes = false;
		}
		final byte[] bytes = new byte[numOfBytes];
		// convert every 8 characters from binary format string to integer.
		for (int i = 0; i < numOfBytes; ++i) {
			if (!completeBytes && (i == (numOfBytes - 1))) {
				final String temp = binaryString.substring(8 * i, (8 * i)
						+ remainingBytes);
				mRemainingBits = remainingBytes;
				bytes[i] = (byte) (Integer.parseInt(temp, 2) & 0xFF);
			} else {
				final String temp = binaryString.substring(8 * i, (8 * i) + 8);
				bytes[i] = (byte) (Integer.parseInt(temp, 2) & 0xFF);
			}
		}
		return bytes;
	}

	/**
	 * Decode message using huffman tree.
	 * 
	 * @param message
	 *            encoded bytes in string format.
	 * @return decodedMessage string after decoded input string.
	 */
	private String decodeMessage(final String message,
			final HuffmanTreeNode node) {
		final StringBuilder decodedMessage = new StringBuilder();
		HuffmanTreeNode root = node;
		if (root == null)
			return new String(decodedMessage);
		for (int i = 0; i < message.length(); i++) {
			// check this node is leaf node or not.
			if (root.mLeft == null && root.mRight == null) {
				decodedMessage.append(root.mLetter);
				// assign root to again node.
				root = node;
			}
			// if character is '1' then its left side
			if (message.charAt(i) == '1')
				root = root.mLeft;
			// if character is '0' then its right side.
			else if (message.charAt(i) == '0')
				root = root.mRight;

		}
		return new String(decodedMessage);
	}

	/**
	 * Get string representation of input bytes. Here we are using remaining
	 * bytes which help us up to how many bytes we need to read in last byte.
	 * Please check README file for more information.
	 * 
	 * @param bytes
	 *            input bytes
	 * @param remainingBytes
	 *            bytes which we need to take in last byte.
	 * @return result string representation of input bytes.
	 */
	private String getBitsString(final byte[] bytes, final int remainingBytes) {
		String result = "";
		for (int i = 0; i < bytes.length; i++) {
			final byte b1 = (byte) bytes[i];
			String byteString = "";
			if (remainingBytes > 0 && i == (bytes.length - 1)) {
				final String format = "%" + remainingBytes + "s";
				byteString = String.format(format,
						Integer.toBinaryString(b1 & 0xFF)).replace(' ', '0');
			} else {
				byteString = String.format("%8s",
						Integer.toBinaryString(b1 & 0xFF)).replace(' ', '0');
			}
			result += byteString;
		}
		return result;
	}

	/*
	 * private void seri() { Utils.writeSerializeTree(getRoot(),4);
	 * HuffmanTreeNode h = Utils.readSerializeTree();
	 * System.out.println("hello"); }
	 */

	/**
	 * Decompress the string using huffman algorithm. Here we are assuming that
	 * huffman tree is already build and frequency table also shared with this
	 * function. We can build huffman tree again with help of frequency table,
	 * but we already computed that in compression part. Thus, we are not doing
	 * it again.
	 * 
	 * @param compressBytes
	 *            bytes of input string after compression.
	 * @return result original string message after decompression.
	 */
	public String decompress(final byte[] compressBytes) {
		// get string representation of bytes.
		// for example "010000101010".
		// Here we need to read huffman serialize tree with remaining bits from
		// huffman.tree file.
		final int remainingBytes = Utils.readRemainingBits();
		final HuffmanTreeNode rootNode = Utils.readSerializeTree();
		final String str = getBitsString(compressBytes, remainingBytes);
		return decodeMessage(str, rootNode);
	}

	/**
	 * Compresses the string using huffman algorithm.
	 * 
	 * @param sentence
	 *            The sentence to be serialized
	 * @return boolean true if compression done successfully, otherwise false.
	 * 
	 */
	public boolean compress(final String sentence) {
		boolean result = false;
		if (sentence == null || sentence.length() <= 0) {
			System.out.println("** Input Data is NULL or empty text.");
			return false;
		}
		System.out.println("** Input Data : " + sentence + " "
				+ sentence.length() + " bytes\n");
		// convert EOT character in string.
		final String tempExtra = String.valueOf(Character.toChars(3));
		// add new extra character at the end of message.
		final String input = sentence + tempExtra;
		final HashMap<Character, Integer> freuencyTable = prepareFreqTable(input);
		// print frequency table.
		printFreqTable(freuencyTable);
		// build huffman tree.
		final HuffmanTreeNode root = buildHuffmanTree(freuencyTable);
		// get prefix or huffman code for every character.
		final HashMap<Character, String> codeTable = getCodeTable(root);
		// print codeTable.
		printCodingTable(codeTable);
		// build string of original message text in binary format.
		final String encodedMessage = generateEncodedMessage(input, codeTable);
		if (encodedMessage != null && encodedMessage.length() > 0) {
			System.out.println("=> Here is the original data encoded : ");
			System.out.println("   " + encodedMessage);
			setEncodedBytes(convertInBits(encodedMessage));
			System.out.println("   Compressed data fits in "
					+ getEncodedBytes().length + " bytes");
			Utils.writeSerializeTree(root, mRemainingBits);
			result = true;
		}
		return result;
	}
}
