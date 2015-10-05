/**
 * HuffmanTreeNode.java This file is represents huffman tree node structure.
 * 
 * @author Saurabh Patel, skpatel@syr.edu
 * @version 1.0
 * @date 10/05/2015
 */
public class HuffmanTreeNode implements Comparable<HuffmanTreeNode> {

	public char mLetter; // Character
	public int mFrequency; // Character's frequency
	public HuffmanTreeNode mRight = null; // right node
	public HuffmanTreeNode mLeft = null; // left node

	/** Constructor of tree node class. */
	public HuffmanTreeNode(final char letter, final int frequency,
			final HuffmanTreeNode left, final HuffmanTreeNode right) {
		mLetter = letter;
		mFrequency = frequency;
		mRight = right;
		mLeft = left;
	}

	/** Compares by frequency. */
	public int compareTo(final HuffmanTreeNode node) {
		if (this.mFrequency > node.mFrequency)
			return 1;
		else if (this.mFrequency < node.mFrequency)
			return -1;
		else
			return 0;
	}
}
