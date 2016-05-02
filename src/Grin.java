import java.io.IOException;
import java.util.HashMap;

public class Grin {

	public static void main(String[] args) throws IOException {

		if (args.length != 4) {
			System.out.println("Not enough arguments, program exiting.");
			System.exit(1);
		}

		String command = args[1];
		String infile = args[2];
		String outfile = args[3];
		HashMap<Short, Integer> hash = new HashMap<Short, Integer>();
		BitInputStream input = new BitInputStream(infile);
		BitOutputStream output = new BitOutputStream(outfile);

	}
}
