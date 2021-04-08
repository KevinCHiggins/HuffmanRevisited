/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package huffmanrevisited;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Kevin Higgins
 */
public class HuffmanTree {
    private HuffmanTreeNode root;
    public HuffmanTreeNode getRoot() {
	return root;
    }
    public void setRoot(HuffmanTreeNode n) {
	if (root == null) {
	    root = n;
	}
    }
    public HuffmanTree() {
	
    }
    // for use in decoding
    // can't be a constructor as name would clash with constructor taking Map<Character, Integer>
    public HuffmanTree rebuildFromCodings (Map<Character, String> codings) {
	root = new HuffmanTreeNode();
	// place each coded character properly in the tree
	for (char c : codings.keySet()) {
	    
	    HuffmanTreeNode curr = root; // start placement process at root of tree
	    String code = codings.get(c);
	    // take each char (either '1' or '0') from the code and use
	    // it to navigate the tree, creating any needed branches if they
	    // don't yet exist in it
	    char[] codeArr = code.toCharArray();
	    
	    for (int i = 0; i < codeArr.length; i++) {
		char currentBranching = codeArr[i];
		if (currentBranching == '0') {
		    if (curr.left == null) {
			curr.left = new HuffmanTreeNode();
		    }
		    curr = curr.left;
		}
		else if (currentBranching == '1') {
		    if (curr.right == null) {
			curr.right = new HuffmanTreeNode();
		    }
		    curr = curr.right;
		}
	    }
	    curr.c = c;
	    // NOTE THAT THE FREQUENCIES AREN'T KNOWN WHEN WE 
	    // BUILD THE TREE THIS WAY, BUT THEY AREN'T REQUIRED EITHER
	    // because it's the shape of the tree only that affords decoding
	    // THIS NEEDS TO BE THOUGHT THROUGH and ideally a design found that
	    // doesn't have an empty property depending on how the tree
	    // was built. Maybe scrap the frequency property after construction 
	    // via the Huffman algorithm (in the constructor)?
	    // curr.freq = ??
	}
	return this;
    }
    public HuffmanTree(Map<Character, Integer> frequencies) {
	// be good to handle 0 as well
	if (frequencies.size() == 0) {
	    root = null;
	}
	// having only one distinct character will confuse the normal algorithm which
	// takes pairs of subtrees (which are initially created one for each char).
	// So we handle it specially. It's silly though because we need to
	// have a pair of children from the root to make it a binary tree.
	else if (frequencies.size() == 1) {
	    HuffmanTreeNode n = new HuffmanTreeNode();
	    HuffmanTreeNode left = new HuffmanTreeNode();
	    HuffmanTreeNode right = new HuffmanTreeNode();
	    left.c = (char) frequencies.keySet().toArray()[0]; // pluck out the single character
	    n.left = left;
	    right.c = (char) frequencies.keySet().toArray()[0]; // encode the same one for want of anything else
	    n.right = right;
	    root = n;
	    
	}
	else {
	    List<HuffmanTreeNode> subtrees = new LinkedList<>();
	    for (Character k : frequencies.keySet()) {
		subtrees.add(new HuffmanTreeNode(k, frequencies.get(k)));
	    }
	    while (subtrees.size() > 1) {
		Collections.sort(subtrees); // I think this could be sped up by using a binary search + insert instead
		HuffmanTreeNode smallest = subtrees.remove(0);
		HuffmanTreeNode secondSmallest = subtrees.remove(0);
		//System.out.println("Combining the nodes containing chars " + smallest.c + " and " + secondSmallest.c);
		subtrees.add(new HuffmanTreeNode(smallest, secondSmallest));
	    }
	    root = subtrees.get(0);
	}
    }
    // differentiated from the recursive function which must take a parameter to function
    public HashMap<Character, String> getCodingsAsMap() {
	if (root != null) {
	    return getCodingsAsMap(root);
	}
	else return new HashMap<Character, String>();
    }
    private HashMap<Character, String> getCodingsAsMap(HuffmanTreeNode n) {
	if (n.left == null) {
	    HashMap<Character, String> m = new HashMap<>();
	    m.put(n.c, "");
	    return m;
	}
	HashMap<Character, String> leftCodings = getCodingsAsMap(n.left);
	HashMap<Character, String> rightCodings = getCodingsAsMap(n.right);
	
	for (char c : leftCodings.keySet()) {
	    leftCodings.put(c, 0 + leftCodings.get(c)); // better to use StringBuilder?
	}
	for (char c : rightCodings.keySet()) {
	    rightCodings.put(c, 1 + rightCodings.get(c)); // better to use StringBuilder?
	}
	leftCodings.putAll(rightCodings);
	return leftCodings;	
    }
    // a depth first traversal yielding nodes in pre-order
    public List<HuffmanTreeNode> asNodesInPreOrder() {
	return asNodesInPreOrder(root);
    }
    private List<HuffmanTreeNode> asNodesInPreOrder(HuffmanTreeNode node) {	
	List l = new LinkedList<HuffmanTreeNode>();
	l.add(node);
	if (node.left != null) l.addAll(asNodesInPreOrder(node.left));
	if (node.right != null) l.addAll(asNodesInPreOrder(node.right));
	return l;
    }
    public byte[] compact() {
	//StringBuilder sb = new StringBuilder(compactedSubtreeAsBinCsq(root));
	if (root != null) {
	    return BinaryConversions.binCsqToByteArray(compactedSubtreeAsBinCsq(root));
	}
	return new byte[0];
    }
    private CharSequence compactedSubtreeAsBinCsq(HuffmanTreeNode node) {
	StringBuilder sb = new StringBuilder();
	if (node.c != null) {
	    sb.append('1'); // an idea from Wikipedia article - 1 signifies a leaf
	    sb.append(BinaryConversions.charToBinCsq(node.c));
	    System.out.println("compactedSubtreeAsBinCsq: added CharSequence " + BinaryConversions.charToBinCsq(node.c));
	}
	else {
	    sb.append('0'); // 0 signifies a parent
	    sb.append(compactedSubtreeAsBinCsq(node.left));
	    sb.append(compactedSubtreeAsBinCsq(node.right));
	}
	System.out.println("compactedSubtreeAsBinCsq: StringBuilder: " + sb);
	return sb;
    }
    /*
    private class HuffmanEncodedChar {
	Character ch;
	String co;
	HuffmanEncodedChar (Character ch, String co) {
	    this.ch = ch;
	    this.co = co;
	}
    }

    // a test method
    private void printEncodings(List<HuffmanEncodedChar> l) {
	for (HuffmanEncodedChar c : l) {
	    System.out.println("Character " + c.ch + " has encoding " + c.co);
	}
    }
    private List<HuffmanEncoder.HuffmanEncodedChar> getEncodingsFromTree(HuffmanTreeNode n) {
	if (n.left == null) {
	    ArrayList<HuffmanEncoder.HuffmanEncodedChar> l = new ArrayList<>();
	    l.add(new HuffmanEncoder.HuffmanEncodedChar(n.c, ""));
	    return l;
	}
	List<HuffmanEncoder.HuffmanEncodedChar> leftEncodings = getEncodingsFromTree(n.left);
	List<HuffmanEncoder.HuffmanEncodedChar> rightEncodings = getEncodingsFromTree(n.right);
	for (HuffmanEncoder.HuffmanEncodedChar c : leftEncodings) {
	    c.co = "0" + c.co;
	}
	for (HuffmanEncoder.HuffmanEncodedChar c : rightEncodings) {
	    c.co = "1" + c.co;
	}
	leftEncodings.addAll(rightEncodings);
	return leftEncodings;
    }

    */
}
