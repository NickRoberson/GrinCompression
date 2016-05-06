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

    private Map<Short, String> buildHuffMap() {
	String huffCode = "";
	Map<Short, String> huffMap = new HashMap<>();
	buildHuffMapH(root, huffMap, huffCode);
	return huffMap;
    }

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
	Short ch = 0;
	while (in.hasBits()) {
	    ch = decodeH(root, in);
	    // if (ch == 256) {
	    // return;
	    // }
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
