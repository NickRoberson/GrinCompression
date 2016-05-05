import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GrinDecoder {

    public void decode(String infile, String outfile) throws IOException {

	// create input and output
	BitInputStream in = new BitInputStream(infile);
	BitOutputStream out = new BitOutputStream(outfile);

	// parse header
	int magicNumber = in.readBits(32);
	if (magicNumber != 1846) {
	    throw new IllegalArgumentException("Not a valid .grin file");
	}
	int codes = in.readBits(32);

	// construct frequency map from header
	Map<Short, Integer> freqMap = new HashMap<>();
	Short codeVal;
	Integer occurrences;
	for (int i = 0; i < codes; i++) {
	    codeVal = (short) in.readBits(16);
	    occurrences = in.readBits(32);
	    freqMap.put(codeVal, occurrences);
	}

	// construct a huffman tree from frequency map
	HuffmanTree hTree = new HuffmanTree(freqMap);
	hTree.decode(in, out);
    }

}
