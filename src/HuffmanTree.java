import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanTree {

	public Node root;

	public HuffmanTree(Map<Short, Integer> m) {
		this.root = toHuff(m);
	}

	public static Node toHuff(Map<Short, Integer> m) {

		for (Map.Entry<Short, Integer> entry : m.entrySet()) {
			System.out.println(entry.getKey() + "/" + entry.getValue());
		}

		PriorityQueue<Node> huff = new PriorityQueue<Node>();
		for (Map.Entry<Short, Integer> entry : m.entrySet()) {
			huff.add(new Node(entry.getKey(), entry.getValue(), null, null));
		}
		huff.add(new Node((short) 256, 1, null, null));

		Node temp1, temp2, addNode;
		while (huff.size() >= 2) {
			temp1 = huff.poll();
			temp2 = huff.poll();
			addNode = new Node(null, temp1.freq + temp2.freq, temp1, temp2);
			huff.add(addNode);
		}
		return huff.poll();
	}

	void encode(BitInputStream in, BitOutputStream out) {

	}

	public static void addAllChars(Map<Short, Integer> hash, BitInputStream reader) {
		short bitVal = 0;
		// while loop to read from the text file
		while (reader.hasBits()) {
			bitVal = (short) reader.readBits(8);
			System.out.println(bitVal);
			// check if hashmap already contains key, if so increment, if not add with value 1 
			if (!hash.containsKey(bitVal)) {
				hash.put(bitVal, 1);
			} else {
				hash.put(bitVal, hash.get(bitVal) + 1);
			}
		}
	}

	void decode(BitInputStream in, BitOutputStream out) {

	}

	public static void main(String[] args) throws IOException {
		Map<Short, Integer> huff = new HashMap<Short, Integer>();
		String infile = "src/text.txt";
		BitInputStream input = new BitInputStream(infile);
		addAllChars(huff,input);

		HuffmanTree h = new HuffmanTree(huff);
		System.out.println(h.root.freq);

	}
}
