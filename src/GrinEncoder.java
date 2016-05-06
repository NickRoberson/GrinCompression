import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GrinEncoder {

    /**
     * 
     * @param file
     *            the file that we are reading from to get the values for our
     *            retMap
     * @return A Map of each individual character and its frequencies
     * 
     * @throws IOException
     */
    public Map<Short, Integer> createFrequencyMap(String file) throws IOException {
	Map<Short, Integer> retMap = new HashMap<Short, Integer>();
	BitInputStream input = new BitInputStream(file);
	HuffmanTree.addAllChars(retMap, input);
	return retMap;
    }

    /**
     * 
     * @param infile
     *            file we read from
     * @param outfile
     *            file we write to
     * @throws IOException
     * 
     *             writes encoding to outfile
     */
    public void encode(String infile, String outfile) throws IOException {
	Map<Short, Integer> freqMap = createFrequencyMap(infile);
	BitInputStream input = new BitInputStream(infile);
	BitOutputStream output = new BitOutputStream(outfile);

	writeHeader(output, 1846, freqMap.size(), freqMap);

	HuffmanTree hTree = new HuffmanTree(freqMap);
	hTree.encode(input, output);
	input.close();
	output.close();
    }

    /**
     * 
     * @param out
     *            file we write header to
     * @param magicNum
     *            magic number in header, default 1846
     * @param numCodes
     *            number of codes in header
     * @param freqMap
     *            frequency map constructed from tree
     * 
     *            writes the header to the outfile
     */
    public void writeHeader(BitOutputStream out, int magicNum, int numCodes, Map<Short, Integer> freqMap) {
	out.writeBits(magicNum, 32);
	out.writeBits(numCodes, 32);
	for (Short key : freqMap.keySet()) {
	    out.writeBits(key, 16);
	    out.writeBits(freqMap.get(key), 32);
	}
    }
}
