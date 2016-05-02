import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GrinDecoder {

	public void decode (String infile, String outfile) throws IOException {
		
		// create input and output
		BitInputStream input = new BitInputStream(infile);
		BitOutputStream output = new BitOutputStream(outfile);
		
		// parse header
		int magicNumber = input.readBits(32);
		int codes = input.readBits(32);
		
		// construct frequency map from header
		Map<Short, Integer> freqMap = new HashMap<>();
		Short codeVal;
		Integer occurrences;
		for (int i = 0; i < codes; i++) {
			codeVal = (short) input.readBits(16);
			occurrences = input.readBits(32);
			freqMap.put(codeVal, occurrences);
		}
		
		// construct a huffman tree from frequency map
		HuffmanTree hTree = new HuffmanTree(freqMap);
	}
}
