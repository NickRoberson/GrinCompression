import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Grin {

    public static void main(String[] args) throws IOException {

	if (args.length != 3) {
	    System.out.println("Not enough arguments, program exiting.");
	    System.exit(1);
	}
	String option = args[0];
	String infile = args[1];
	String outfile = args[2];

	GrinEncoder encoder = new GrinEncoder();
	GrinDecoder decoder = new GrinDecoder();

	// Determine whether or not we want to encode or decode.
	if (option.toLowerCase().equals("encode")) {
	    encoder.encode(infile, outfile);
	} else if (option.toLowerCase().equals("decode")) {
	    decoder.decode(infile, outfile);
	} else {
	    throw new IOException("invalid encode/decode command");
	}

    }
}
