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
// these generics are complicated! Also, it's silly, it's only ever going
// to be a Float or a Double!
public class HuffmanTreeNodeGeneric<E extends Comparable<E>> implements Comparable<HuffmanTreeNodeGeneric<E>> {
    protected HuffmanTreeNodeGeneric<E> left;
    protected HuffmanTreeNodeGeneric<E> right;
    E e;
    public HuffmanTreeNodeGeneric(E e) {
	this.e = e;
    }
    public HuffmanTreeNodeGeneric(HuffmanTreeNodeGeneric<E> l, HuffmanTreeNodeGeneric<E> r) {
	
    }
    @Override
    public int compareTo(HuffmanTreeNodeGeneric<E> n) {
	return this.e.compareTo(n.e);
    }

}
