### HuffmanRevisited
**Text compression through variable-length coding**

HuffmanRevisited is a command-line app compressing text files into a proprietary .huf format, and decompressing them back again.

It uses Huffman coding, a famous technique invented by David A. Huffman. It relies on repeatedly gathering into a new subtree the lowest two members of a sorted list of subtrees whose leaves correspond to the unique characters of the string being compressed; the sorting being by accumulated frequency of occurrence of any character held in that subtree.

Find out more in my [blog post](https://drumchant.wordpress.com/2021/04/08/revisiting-a-classic/).

Kevin Higgins, 05/05/21