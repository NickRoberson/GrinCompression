import java.io.IOException;
import java.util.Map;

public class GrinEncoder {

	public Map<Short, Integer> createFrequencyMap(String file) throws IOException {
		Map<Short, Integer> retMap = null;
		BitInputStream input = new BitInputStream(file);
		HuffmanTree.addAllChars(retMap, input);
		return retMap;
	}
	
	public void encode (String infile, String outfile) throws IOException {
		Map<Short, Integer> freqMap = createFrequencyMap(infile);
		BitInputStream input = new BitInputStream(infile);
		BitOutputStream output = new BitOutputStream(outfile);
		
	}
}
