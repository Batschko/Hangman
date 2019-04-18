package hangman;

import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class WordlistReader {

	private List<String> wordlist;
	
	public WordlistReader(String liste) throws IOException {
		if(!readListFromFile(liste)) {
			throw new IOException("Cant read file");
		}
	}
	
	public List<String> getAllWords() {
		return wordlist;
	}
	
	public List<String> getWordsOfLength(int length) {
		List<String> words = new ArrayList<String>();
		
		for(String word : wordlist) {
			if(word.length()==length) {
				words.add(word);
			}
		}
		
		return words;
	}
	
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
