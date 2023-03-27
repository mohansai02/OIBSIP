import java.util.Random;
import javax.swing.JOptionPane;

public class task2 {

  public static void main(String[] args) {
    int rangeMin = 1;
    int rangeMax = 100;
    int numOfAttempts = 10;
    int score = 0;
    int round = 1;
    
    JOptionPane.showMessageDialog(null, "Welcome to Guess the Number! You have " + numOfAttempts + " attempts to guess the number between " + rangeMin + " and " + rangeMax + ".");
    
    do {
      Random rand = new Random();
      int targetNum = rand.nextInt(rangeMax - rangeMin + 1) + rangeMin;
      
      int attemptNum = 0;
      while (attemptNum < numOfAttempts) {
        String input = JOptionPane.showInputDialog("Round " + round + ": Guess the number between " + rangeMin + " and " + rangeMax + ". You have " + (numOfAttempts - attemptNum) + " attempts left.");
        int guess = Integer.parseInt(input);
        attemptNum++;
        
        if (guess == targetNum) {
          JOptionPane.showMessageDialog(null, "Congratulations, you guessed the number " + targetNum + " correctly in " + attemptNum + " attempt(s)!");
          score += (numOfAttempts - attemptNum + 1);
          break;
        } else if (guess < targetNum) {
          JOptionPane.showMessageDialog(null, "Your guess of " + guess + " is too low. Try again.");
        } else {
          JOptionPane.showMessageDialog(null, "Your guess of " + guess + " is too high. Try again.");
        }
      }
      
      round++;
      if (attemptNum == numOfAttempts) {
        JOptionPane.showMessageDialog(null, "Sorry, you did not guess the number " + targetNum + ". The correct number was " + targetNum + ".");
      }
      
      String playAgain = JOptionPane.showInputDialog("Your score is " + score + ". Would you like to play again? Enter 'yes' or 'no'.");
      if (playAgain.equalsIgnoreCase("no")) {
        break;
      }
      
    } while (true);
    
    JOptionPane.showMessageDialog(null, "Thanks for playing Guess the Number! Your final score is " + score + ".");
  }

}