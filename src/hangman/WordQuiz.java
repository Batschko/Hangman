package hangman;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordQuiz {

	protected String quizword;
	protected int length;
	protected String coveredQuizword;
	protected boolean finish;
	protected char[] guessedLetters;
	protected int remainingAttemps;
	protected ConsoleReader inputReader;
	protected WordlistReader wordlistReader;
	
	public WordQuiz(int length, int attemps, ConsoleReader cr, WordlistReader wr) {
		guessedLetters = new char[attemps];
		remainingAttemps = attemps;
		inputReader = cr;
		wordlistReader = wr;
		this.length = length;
	}
	
	protected int compareInput(char input) {
		int compare = 0;
		char[] charsOfQuizword = quizword.toCharArray();
		
		for(char c : charsOfQuizword) {
			if(c==input) compare++;
		}
		
		return compare;
	}
	
	protected void uncover(char letter, int pos) {
	
		char[] word = coveredQuizword.toCharArray();
		word[pos] = letter;
		coveredQuizword = new String(word);
		
	}
	
	protected void uncover(char letter, List<Integer> positions) {
		for(int pos : positions) {
			uncover(letter, pos);
		}
	}
	
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
			remainingAttemps--;
			if(appereances==0) {
				System.out.println("Wrong letter mate - try again!");
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
				System.out.println("You win - well done, mate! Grab a beer and celebrate your victory!");
				break;
			} else if(remainingAttemps==0) {
				System.out.println("Game over - you los, mate! Pack your things and try again!");
				break;
			}
		}
	}
	
	private boolean checkWin() {
		if(quizword.equals(coveredQuizword)) return true;
		return false;
	}
	
	private String createCoveredQuizword(int length) {
		
		String coveredString = "";
		
		for(int i=0;i<length;i++) {
			coveredString = coveredString+"_";
		}
		
		return coveredString;
	}
	
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
