package hangman;

import java.io.BufferedReader;

import java.io.IOException;

/**
 * @author Mathis B, Lukas H
 * 
 * </br>created 18.04.2019
 */

public class ConsoleReader {

	private BufferedReader reader;
	
	/**
	 * Constructor for ConsoleReader
	 * @param reader Reader to read user input with
	 */
	public ConsoleReader(BufferedReader reader) {
		this.reader = reader;
	}
	
	/**
	 * Gives the 1st letter of a read line
	 * @return 1st letter of read line
	 * @throws IOException
	 */
	public char readNextChar() throws IOException {
		char nextChar = (char) -1;
		nextChar = reader.readLine().charAt(0);
		return nextChar;
	}
	
	/**
	 * Reads a line
	 * @return read line
	 * @throws IOException
	 */
	public String readLine() throws IOException {
		return reader.readLine();
	}
	
}
