
public class Node implements Comparable<Node> {

    // val can be either the frequency of the character or the combined
    // frequencies
    // of the characters below it.
    public Short ch;
    public Integer freq;
    public Node left;
    public Node right;

    public Node(Short ch, int freq, Node l, Node r) {
	this.ch = ch;
	this.freq = freq;
	this.left = l;
	this.right = r;
    }

    public int compareTo(Node o) {
	if (o.freq > this.freq) {
	    return -1;
	} else if (o.freq < this.freq) {
	    return 1;
	}
	return 0;
    }

}
