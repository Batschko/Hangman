package hangman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Mathis B, Lukas H
 * 
 * </br>created 18.04.2019
 */

public class WordQuiz {

	protected String quizword;
	protected int length;
	protected String coveredQuizword;
	protected boolean finish;
	protected char[] guessedLetters;
	protected int remainingAttemps;
	protected ConsoleReader inputReader;
	protected WordlistReader wordlistReader;
	/**
	 * Constructor for Class
	 * @param length Length of the word to guess
	 * @param attemps Number of attemps until lose
	 * @param cr ConsoleReader to read user input
	 * @param wr WordlistReader to read words to guess from file
	 */
	public WordQuiz(int length, int attemps, ConsoleReader cr, WordlistReader wr) {
		guessedLetters = new char[attemps];
		remainingAttemps = attemps;
		inputReader = cr;
		wordlistReader = wr;
		this.length = length;
	}
	
	/** 
	 * Main Method to run the game
	 * @param args Arguments from command line
	 */
	public static void main(String[] args) {
		ConsoleReader cr = new ConsoleReader(new BufferedReader(new InputStreamReader(System.in)));
		WordlistReader wr = null;
		try {
			wr = new WordlistReader("words.txt");
		} catch(IOException ioe) {
			System.out.println(ioe.getMessage());
			System.exit(0);
		}
		
		System.out.print("Choose a length of the word: ");
		int length = 0;
		try {
			length = Integer.parseInt(cr.readLine());
		} catch(IOException ioe) {
			System.out.println(ioe.getMessage());
		} catch(NumberFormatException nfe) {
			System.out.println(nfe.getMessage());
		}
		
		System.out.print("Choose a number of attempts: ");
		int attempts = 0;
		try {
			attempts = Integer.parseInt(cr.readLine());
		} catch(IOException ioe) {
			System.out.println(ioe.getMessage());
		} catch(NumberFormatException nfe) {
			System.out.println(nfe.getMessage());
		}
		
		WordQuiz wq = new WordQuiz(length, attempts, cr, wr);
		wq.playGame();
		
	}
	
	/**
	 * Compare the input witht the quizword
	 * @param input Letter to compare
	 * @return Number of appereances of input in the quizword
	 */
	protected int compareInput(char input) {
		int compare = 0;
		char[] charsOfQuizword = quizword.toCharArray();
		
		for(char c : charsOfQuizword) {
			if(c==input) compare++;
		}
		
		return compare;
	}
	
	/**
	 * Uncover the given letter on the given position
	 * @param letter Letter to uncover
	 * @param pos Position in String
	 */
	protected void uncover(char letter, int pos) {
	
		char[] word = coveredQuizword.toCharArray();
		word[pos*2] = letter;
		coveredQuizword = new String(word);
		
	}
	
	/**
	 * Uncover given letter on every position provided by the List
	 * @param letter Letter to uncover
	 * @param positions Positions in String
	 */
	protected void uncover(char letter, List<Integer> positions) {
		for(int pos : positions) {
			uncover(letter, pos);
		}
	}
	
	/**
	 * Method which provides to play the game
	 */
	public void playGame() {
		
		System.out.println("Welcome to Hangman - you fool!\n"
				+ "\n"
				+ "To play the game you shall give me a number of the letters that shall be guessed.\n"
				+ "So lets start mate!\n");
		
		List<String> wordsOfLength = wordlistReader.getWordsOfLength(length);
		int ranNum = new Random().nextInt(wordsOfLength.size());
		quizword = wordsOfLength.get(ranNum).toLowerCase();
		coveredQuizword = createCoveredQuizword(quizword.length());
		
		while(true) {
			printGameInfo();
			System.out.print("\nGuess a letter: ");
			char guess = ' ';
			try {
				guess = Character.toLowerCase(inputReader.readNextChar());
			} catch(IOException ioe) {
				System.out.println("Error reading letter - "+ioe.getMessage());
			}
			
			guessedLetters[guessedLetters.length-remainingAttemps] = guess;
			int appereances = compareInput(guess);
			if(appereances==0) {
				System.out.println("Wrong letter mate - try again!");
				remainingAttemps--;
			} else {
				List<Integer> positions = new ArrayList<Integer>();
				for(int i=0;i<quizword.length();i++) {
					if(guess==quizword.charAt(i)) {
						positions.add(i);
					}
				}
				uncover(guess, positions);
			}
			
			if(checkWin()) {
				System.out.println("\nYou win - "+quizword+" is the word! Well done, mate! Grab a beer and celebrate your victory!");
				break;
			} else if(remainingAttemps==0) {
				System.out.println("\nGame over - you los, mate! Pack your things and try again!");
				break;
			}
		}
	}
	
	/**
	 * Checks if the user won the game
	 * @return true if the user guessed all letters of the word
	 */
	private boolean checkWin() {
		String check=coveredQuizword.replaceAll(" ", "");
		check.trim();
		if(quizword.equals(check)) return true;
		return false;
	}
	
	/**
	 * Creates a cover word with _ for the given length
	 * @param length Length of the cover word
	 * @return String with covered word
	 */
	private String createCoveredQuizword(int length) {
		
		String coveredString = "";
		
		for(int i=0;i<length;i++) {
			coveredString = coveredString+"_ ";
		}
		
		return coveredString;
	}
	 /**
	  * Prints the current game info
	  */
	private void printGameInfo() {
		System.out.println();
		System.out.println("Remaining attemps: "+remainingAttemps);
		System.out.print("Guessed letters: ");
		for(char letter : guessedLetters) {
			System.out.print(letter+" ");
		}
		System.out.println();
		System.out.println("\nWord: "+coveredQuizword);
	}
	
}
