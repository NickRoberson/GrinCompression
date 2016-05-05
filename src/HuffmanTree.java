import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanTree {

    public Map<Short, Integer> huffMap;
    public Node root;

    public HuffmanTree(Map<Short, Integer> m) {
	huffMap = m;
	this.root = toHuff(m);
    }

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

    public void encode(BitInputStream in, BitOutputStream out) {
	while (in.hasBits()) {
	    Short ch = (short) in.readBits(8);
	    // find char's huffman code
	    // write huffman code to file
	}
    }

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

    public void decode(BitInputStream in, BitOutputStream out) {
	while (in.hasBits()) {
	    Short ch = decodeH(root, in);
	    out.writeBits(ch, 8);
	}
    }

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
