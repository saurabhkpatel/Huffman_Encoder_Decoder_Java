import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.StringTokenizer;
/**
 * Utils.java : This class contains utility methods which supports this program.
 * All methods are static so these can be accessible from any class.
 * 
 * Mainly this class holds those methods which are responsible for serialize and deserialized tree.Utils
 * 
 * @author Saurabh Patel, skpatel@syr.edu
 * @version 1.0
 * @date 10/05/2015
 */
public class Utils {
	
	// Separator for tree nodes when we are doing serialize and deserialize..
	public final static String BELL_CHAR = String.valueOf(Character.toChars(7));
	
	/**
	 * serialize huffman tree in file.
	 * 
	 * @param node
	 *           root node of tree. 
	 * @return string
	 *           serialize tree using pre-order. 
	 * 			           
	 */
	private static String serialize(final HuffmanTreeNode root) {
		final StringBuilder sb = new StringBuilder();
		serialize(root, sb);
		return sb.toString();
	}

	/**
	 * serialize huffman tree in file.
	 * 
	 * @param node
	 *           root node of tree. 
	 * @param sb
	 *           out variable, used in recursion and build final string.
	 * 			           
	 */
	private static void serialize(final HuffmanTreeNode node, final StringBuilder sb) {
	
		if (node == null) {
			sb.append("?"+BELL_CHAR);
		} else {
			// pre-order.
			sb.append(node.mLetter + "~" + node.mFrequency + BELL_CHAR);
			serialize(node.mLeft, sb);
			serialize(node.mRight, sb);
		}
	}

	/**
	 * deserialize file and return huffman tree node.
	 * 
	 * @param s
	 *            string from file.
	 * @return HuffmanTreeNode
	 * 			root node of tree.            
	 */
	private static HuffmanTreeNode deserialize(final String s) {
		if (s == null || s.length() == 0)
			return null;
		final StringTokenizer st = new StringTokenizer(s, BELL_CHAR);
		return deserialize(st);
	}

	/**
	 * deserialize file and return huffman tree node.
	 * 
	 * @param st
	 *            StringTokenizer
	 * @return HuffmanTreeNode
	 * 			root node of tree.            
	 */
	private static HuffmanTreeNode deserialize(final StringTokenizer st) {
		if (!st.hasMoreTokens())
			return null;
		final String val = st.nextToken();
		if (val.equals("?"))
			return null;
		final String[] separated = val.split("~");
		if (separated.length == 2) {
			final HuffmanTreeNode root = new HuffmanTreeNode(separated[0].charAt(0),
					Integer.parseInt(separated[1]), null, null);
			root.mLeft = deserialize(st);
			root.mRight = deserialize(st);
			return root;
		} else {
			return null;
		}

	}

	/**
	 * Read remaining bits from file where we put serialize huffman tree.
	 * 
	 * @return bits
	 *            remaining bits in last byte of encoded byte array.
	 */
	public static int readRemainingBits() {
		int bits = 0;
		try {
			final String s = new String(Files.readAllBytes(Paths.get("huffman.tree")));
			bits = Integer.parseInt(s.substring(0, 1));
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return bits;
	}

	/**
	 * Read serialize huffman tree from file.
	 * 
	 * @return HuffmanTreeNode
	 *            huffman tree root node.
	 */
	public static HuffmanTreeNode readSerializeTree() {
		String s = "";
		try {
			s = new String(Files.readAllBytes(Paths.get("huffman.tree")));
			s = s.substring(1);
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return deserialize(s);

	}

	/**
	 * Write serialize huffman tree in file.
	 * 
	 * @param root
	 *            root node of huffman tree
	 * @param remaingBits
	 *            remaining bits in last byte of encoded byte array.
	 */
	public static void writeSerializeTree(final HuffmanTreeNode root, final int remaingBits) {
		// Add integer value infront of serialize tree string.
		String treewithbytes = String.valueOf(remaingBits);
		final String result = serialize(root);
		treewithbytes = treewithbytes + result;
		// write string in file.
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter("huffman.tree"));
			writer.write(treewithbytes);

		} catch (final IOException e) {
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (final IOException e) {
			}
		}
	}

}
