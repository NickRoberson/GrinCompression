import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Grin {

    public static void main(String[] args) throws IOException {

//	if (args.length != 4) {
//	    System.out.println("Not enough arguments, program exiting.");
//	    System.exit(1);
//	}

	Map<Short, Integer> huff = new HashMap<Short, Integer>();

	//String option = args[1];
	
	//String infile = args[2];
	//String outfile = args[3];
	
	
	String infile = "src/plaintext.txt";
	String outfile = "src/encoded.grin";
	BitInputStream input = new BitInputStream(infile);
	BitOutputStream output = new BitOutputStream(outfile);

	HuffmanTree.addAllChars(huff, input);

	input = new BitInputStream(infile);
	HuffmanTree htree = new HuffmanTree(huff);
	
	htree.encode(input, output);
	input.close();
	output.close();
	
	input = new BitInputStream(outfile);
	output = new BitOutputStream("src/decoded.txt");
	
	htree.decode(input, output);
	input.close();
	output.close();
	
	
//	if (option.toLowerCase().equals("encode")) {
//	    htree.encode(input, output);
//	} else if (option.toLowerCase().equals("decode")) {
//	    htree.decode(input, output);
//	} else {
//	    throw new IOException("invalid encode/decode command");
//	}
    }
}
