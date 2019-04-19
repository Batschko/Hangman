package hangman;

import java.util.ArrayList;
import java.util.List;
import java.io.*;

/**
 * @author Mathis B, Lukas H
 * 
 * </br>created 18.04.2019
 */

public class WordlistReader {

	private List<String> wordlist;
	
	/**
	 * Constructor for WordlistReader
	 * @param liste Path to the file where the words are saved
	 * @throws IOException
	 */
	public WordlistReader(String liste) throws IOException {
		wordlist = new ArrayList<String>();
		if(!readListFromFile(liste)) {
			throw new IOException("Cant read file - "+new File(liste).getAbsolutePath());
		}
	}
	
	/**
	 * Returns a list with all the words from the file
	 * @return list with all words
	 */
	public List<String> getAllWords() {
		return wordlist;
	}
	
	/**
	 * Returns a list of words for the given length
	 * @param length Length of words contained in the list
	 * @return list with words for the given length
	 */
	public List<String> getWordsOfLength(int length) {
		List<String> words = new ArrayList<String>();
		
		for(String word : wordlist) {
			if(word.length()==length) {
				words.add(word);
			}
		}
		
		return words;
	}
	
	/**
	 * Reads all words from the given file
	 * @param file Path to the file
	 * @return true if words could be read
	 * @throws FileNotFoundException
	 */
	protected boolean readListFromFile(String file) throws FileNotFoundException {
		
		File f = new File(file);
		if(!f.exists()) {
			return false;
		}
		BufferedReader fr = new BufferedReader(new FileReader(f));
		try {
		wordlist.add(fr.readLine());
		} catch(IOException ioe) {
			return false;
		}
		
		return true;
	}
	
}
