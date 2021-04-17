/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package huffmanrevisited;

/**
 *  A node of a binary tree, holding two data members: the frequency of all
 * characters stored at or lower than this node, and the character stored at
 * this node. In use, all nodes will have a frequency
 * but only leaf nodes will have a character.
 * 
 * @author Kevin Higgins
 */
public class HuffmanTreeNode implements Comparable<HuffmanTreeNode> {
    protected HuffmanTreeNode left;
    protected HuffmanTreeNode right;
    Integer freq;   // boxed type because I call on its compareTo method
    Character c;
    
    /**
     * Creates a Huffman tree node with none of its members initialised.
     */
    public HuffmanTreeNode() {
    }
    

    /**
     * Creates a node with the given character and frequency, that is to say,
     * a leaf node.
     *
     * @param c
     * @param freq
     */
    public HuffmanTreeNode(Character c, Integer freq) {
	this.freq = freq;
	this.c = c;
    }

    /**
     * Creates a parent node with the given pair of children, summing their
     * frequencies to arrive at its own.
     *
     * @param left
     * @param right
     */
    public HuffmanTreeNode(HuffmanTreeNode left, HuffmanTreeNode right) {
	this.left = left;
	this.right = right;
	this.freq = left.freq + right.freq;
    }
    /**
     * Compares this Huffman tree node with another by evaluating their frequencies.
     * Satisfies the Comparable interface.
     * 
     * @param n a marker of whether the frequency of this Huffman tree node is
     * greater (1), equal (0) or lesser (-1) than that of the one it is being 
     * compared to.
     * @return 
     */    
    @Override
    public int compareTo(HuffmanTreeNode n) {
	return this.freq.compareTo(n.freq);
    }

}
