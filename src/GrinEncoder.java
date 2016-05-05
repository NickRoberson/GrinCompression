import java.io.IOException;
import java.util.Map;

public class GrinEncoder {

    public Map<Short, Integer> createFrequencyMap(String file) throws IOException {
	Map<Short, Integer> retMap = null;
	BitInputStream input = new BitInputStream(file);
	HuffmanTree.addAllChars(retMap, input);
	return retMap;
    }

    public void encode(String infile, String outfile) throws IOException {
	Map<Short, Integer> freqMap = createFrequencyMap(infile);
	BitInputStream input = new BitInputStream(infile);
	BitOutputStream output = new BitOutputStream(outfile);

	writeHeader(output, 1848, freqMap.size(), freqMap);
	
	HuffmanTree hTree = new HuffmanTree(freqMap);
	hTree.encode(input, output);
    }
    
    public void writeHeader(BitOutputStream out, int magicNum,
	    		    int numCodes, Map<Short, Integer> freqMap) {
	out.writeBits(magicNum, 32);
	out.writeBits(numCodes, 32);
	for (Short key : freqMap.keySet()) {
	    out.writeBits(key, 16);
	    out.writeBits(freqMap.get(key), 32);
	}
    }
}
