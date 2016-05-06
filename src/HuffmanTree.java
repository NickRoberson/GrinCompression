import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanTree {

    public Map<Short, Integer> huffMap;
    public Node root;

    public HuffmanTree(Map<Short, Integer> m) {
	huffMap = m;
	this.root = toHuff(m);
    }

    /**
     * @param m
     *            map of shorts and integers that we are making the huffman tree
     *            out of.
     * @return returns the root of the huffman tree.
     * 
     *         This function creates a huffman tree out of a Priority queue that
     *         is made from a Map. When there are more than two nodes left we
     *         poll two and make a new node out of them with their combined
     *         frequencies, which we then put back into the priority queue.
     */
    public static Node toHuff(Map<Short, Integer> m) {

	PriorityQueue<Node> huff = new PriorityQueue<Node>();
	for (Map.Entry<Short, Integer> entry : m.entrySet()) {
	    huff.add(new Node(entry.getKey(), entry.getValue(), null, null));
	}
	huff.add(new Node((short) 256, 1, null, null));

	for (Node node : huff) {
	    System.out.print(node.ch + " ");
	    System.out.println(node.freq);
	}

	Node temp1, temp2, addNode;
	while (huff.size() >= 2) {
	    temp1 = huff.poll();
	    temp2 = huff.poll();
	    addNode = new Node(null, temp1.freq + temp2.freq, temp1, temp2);
	    huff.add(addNode);
	}

	return huff.poll();
    }

    /**
     * This function reads chars and looks them up in our huffman tree. It then
     * writes either a 1 or a 0 to the outfile based on each individual char in
     * the huffcode
     */
    public void encode(BitInputStream in, BitOutputStream out) {
	Map<Short, String> huffMap = buildHuffMap();
	Short ch;
	String huffCode;
	while (in.hasBits()) {
	    // store ch and according huffCode
	    ch = (short) in.readBits(8);
	    huffCode = huffMap.get(ch);
	    System.out.print(ch);
	    // write chars huffCode to outfile
	    for (char c : huffCode.toCharArray()) {
		System.out.print(" " + c);
		if (c == '0') {
		    out.writeBit(0);
		} else {
		    out.writeBit(1);
		}
	    }
	    System.out.println();
	}
    }

    /**
     * Builds huffman map of shorts and strings using a helper function.
     * 
     * @return returns a huffman map with the chars and their corresponding
     *         huffman codes.
     */
    private Map<Short, String> buildHuffMap() {
	String huffCode = "";
	Map<Short, String> huffMap = new HashMap<>();
	buildHuffMapH(root, huffMap, huffCode);
	return huffMap;
    }

    /**
     * @param cur
     *            current node we are considering
     * @param huffMap
     *            current huffman map we are adding nodes to
     * @param huffCode
     *            the huffman code as of the current node we are at.
     * 
     *            This function traverses the huffman tree and adds a new entry
     *            to the huffman map every time it reaches a node. The entry
     *            consists of the short value for a char and its huffman code in
     *            the form of a string.
     */
    private void buildHuffMapH(Node cur, Map<Short, String> huffMap, String huffCode) {
	if (cur.ch != null) {
	    huffMap.put(cur.ch, huffCode);
	} else {
	    // create corresponding left and right huffcodes
	    String leftHuffCode = huffCode + "0";
	    String rightHuffCode = huffCode + "1";

	    // traverse each subtree
	    buildHuffMapH(cur.left, huffMap, leftHuffCode);
	    buildHuffMapH(cur.right, huffMap, rightHuffCode);
	}
    }

    /**
     * @param hash
     *            given a hashmap that this function will add all the individual
     *            chars to and their frequencies.
     * @param reader
     *            a bit input stream that we will read from and add each char to
     *            our hash map.
     * 
     *            Adds all chars and their frequencies to the hashmap hash.
     */

    public static void addAllChars(Map<Short, Integer> hash, BitInputStream reader) {
	short bitVal = 0;
	// while loop to read from the text file
	while (reader.hasBits()) {
	    bitVal = (short) reader.readBits(8);
	    // check if hashmap already contains key, if so increment, if not
	    // add with value 1
	    if (!hash.containsKey(bitVal)) {
		hash.put(bitVal, 1);
	    } else {
		hash.put(bitVal, hash.get(bitVal) + 1);
	    }
	}
    }

    /**
     * 
     * @param in
     *            file we are reding from
     * @param out
     *            file we are writing to
     * 
     *            This function uses a helper to decode the encoded file by
     *            reading bits and traversing our huffman tree
     */

    public void decode(BitInputStream in, BitOutputStream out) {
	Short ch = 0;
	while (in.hasBits()) {
	    ch = decodeH(root, in);
	    // if (ch == 256) {
	    // return;
	    // }
	    out.writeBits(ch, 8);
	}

    }

    /**
     * 
     * @param cur
     *            used to iterate though the tree
     * @param in
     *            encoded file that we are reading bits from which determines
     *            how we traverse the tree
     * @return returns a short that will be cast to a char
     * 
     *         This function accepts a node and reads a bit, depending on this
     *         bit we will either traverse the tree to the right or the the
     *         left. Whe we encounter a leaf we return its ch field.
     */
    private Short decodeH(Node cur, BitInputStream in) {
	if (cur.ch != null) {
	    return cur.ch;
	} else {
	    int bit = in.readBit();
	    if (bit == 0) {
		return decodeH(cur.left, in);
	    } else {
		return decodeH(cur.right, in);
	    }
	}
    }

}
