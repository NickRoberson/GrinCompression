import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Grin {

	public static void main(String[] args) throws IOException {

		/**
		 * if (args.length != 4) { System.out.println(
		 * "Not enough arguments, program exiting."); System.exit(1); }
		 **/

		Map<Short, Integer> huff = new HashMap<Short, Integer>();

		String infile = "src/plaintext.txt";
		String outfile = "src/encoded.txt";
		BitInputStream input = new BitInputStream(infile);
		BitInputStream output = new BitInputStream(outfile);

		HuffmanTree.addAllChars(huff, input);
		HuffmanTree htree = new HuffmanTree(huff);

		/**
		 * String command = args[1]; String infile = args[2]; String outfile =
		 * args[3];
		 **/

	}
}
