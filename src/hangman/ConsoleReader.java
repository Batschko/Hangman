package hangman;

import java.io.BufferedReader;
import java.io.IOException;

public class ConsoleReader {

	private BufferedReader reader;
	
	public ConsoleReader(BufferedReader reader) {
		this.reader = reader;
	}
	
	public char readNextChar() throws IOException {
		char nextChar = (char) -1;
		nextChar = (char)reader.read();
		return nextChar;
	}
	
	public String readLine() throws IOException {
		return reader.readLine();
	}
	
}
