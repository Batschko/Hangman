import hangman.*;
import java.io.*;

public class Main {
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
}
