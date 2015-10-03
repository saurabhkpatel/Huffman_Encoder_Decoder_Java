
public class HuffmanTreeNode implements Comparable<HuffmanTreeNode>{

	
	public char mLetter;
	public int mFrequency;
	public HuffmanTreeNode mRight = null;
	public HuffmanTreeNode mLeft = null;
	
	public HuffmanTreeNode(char letter, int frequency, HuffmanTreeNode left, HuffmanTreeNode right) {
		
		mLetter = letter;
		mFrequency = frequency;
		mRight = right;
		mLeft = left;
	}
	
	/** Compares by frequency. */
	public int compareTo(HuffmanTreeNode node)
	{
		if(this.mFrequency>node.mFrequency)
			return 1;
		else if(this.mFrequency<node.mFrequency)
			return -1;
		else
			return 0;
	}
	
}
