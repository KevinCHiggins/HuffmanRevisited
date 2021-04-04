/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package huffmanrevisited;

/**
 *
 * @author Kevin Higgins
 */
// these generics complicated! If I understand right, in human words it's
// "
public class HuffmanTreeNode implements Comparable<HuffmanTreeNode> {
    protected HuffmanTreeNode left;
    protected HuffmanTreeNode right;
    Integer freq;
    Character c;
    public HuffmanTreeNode() {

    }
    public HuffmanTreeNode(Character c, Integer freq) {
	this.freq = freq;
	this.c = c;
    }
    public HuffmanTreeNode(HuffmanTreeNode left, HuffmanTreeNode right) {
	this.left = left;
	this.right = right;
	this.freq = left.freq + right.freq;
    }
    @Override
    public int compareTo(HuffmanTreeNode n) {
	return this.freq.compareTo(n.freq);
    }

}
