package hangman;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordQuiz {

	protected String quizword;
	protected String coveredQuizword;
	protected boolean finish;
	protected char[] guessedLetters;
	protected int remainingAttemps;
	protected ConsoleReader inputReader;
	protected WordlistReader wordlistReader;
	
	public WordQuiz(int length, int attemps, ConsoleReader cr, WordlistReader wr) {
		guessedLetters = new char[length];
		remainingAttemps = attemps;
		inputReader = cr;
		wordlistReader = wr;
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
		coveredQuizword = word.toString();
		
	}
	
	protected void uncover(char letter, List<Integer> positions) {
		for(int pos : positions) {
			uncover(letter, pos);
		}
	}
	
	public void playGame() {
		
		System.out.println("Welcome to Hangman - you fool!\n"
				+ "\n"
				+ "To play the game you shall give me a number of the letters that shall be guessed.\n");
		System.out.print("Number: ");
		int number = 0;
		try {
			number = Integer.parseInt(inputReader.readLine());
			if(number<0) throw new IllegalArgumentException("Integer is less than 0");
		} catch(NumberFormatException nfe) {
			System.out.println("Number must be an Integer - "+nfe.getMessage());
		} catch(IOException ioe) {
			System.out.println("Error reading line - "+ioe.getMessage());
		} catch(IllegalArgumentException iae) {
			System.out.println(iae.getMessage());
		}
		
		List<String> wordsOfLength = wordlistReader.getWordsOfLength(number);
		int ranNum = new Random().nextInt(wordsOfLength.size());
		quizword = wordsOfLength.get(ranNum).toLowerCase();
		coveredQuizword = createCoveredQuizword(quizword.length());
		
		while(true) {
			printGameInfo();
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
				System.out.println("You win - well done, mate! Grab a beer and celebrate you victory!");
			} else if(remainingAttemps==0) {
				System.out.println("Game over - you los, mate! Pack your things and try again!");
				break;
			}
		}
	}
	
	private boolean checkWin() {
		if(quizword==coveredQuizword) return true;
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
