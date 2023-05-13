import java.util.ArrayList;

// The Display class holds all the display logic of the game
// The class does not contain attributes; therefore an explicit constructor is unnecessary
public class Display {
  
  // Welcome message and rules of the game at the beginning of each game
  public void welcomeMessage() {
    System.out.println("""
                       
                       Welcome to Rock, Paper, Scissors, Lizard, Spock
                       
                       The rules are as follows:
                       Each hand defeats two other hands and loses to the remaining two hands.
                       
                       Rock crushes scissors
                       Rock crushes lizard
                       
                       Paper covers rock
                       Paper disproves Spock
                       
                       Scissors cuts paper
                       Scissors decapitates lizard
                       
                       Lizard eats paper
                       Lizard poisons Spock
                       
                       Spock vaporizes rock
                       Spock smashes scissors
                       """);
  }

  // A prompt to the user to select between the three different game lengths
  public void promptBestOf() {
    System.out.println("""
                       Would you like to play a best of 3, 5 or 7?
                       Type "3" for best of 3
                       Type "5" for best of 5
                       Type "7" for best of 7
                       """);
  }

  // Print statement for when the user does not select a valid game length
  public void invalidBestOfMessage() {
    System.out.println("Invalid choice. Valid choices are 3, 5 or 7\n");
  }

  // Confirmation statement of the user's choice of game length
  public void bestOf(int bestOfNum) {
    System.out.println(String.format("\nThe game will be played best of %s\n", bestOfNum));
  }

  // A prompt to the user at the beginning of each hand to chose one of the five options
  public void promptChoice() {
    System.out.println("""
                       Please select a number between 1-5 representing the following hands:
                       1: Rock
                       2: Paper
                       3: Scissors
                       4: Lizard
                       5: Spock
                       """);
  } 

  // Print statement for when the user does not select a valid hand option
  public void invalidPlayerChoiceMessage() {
      System.out.println("Invalid choice. Valid choices are between 1 and 5\n");
  }

  // Displays the two player's choices to the user
  public void choices(String playerChoice, String computerChoice) {
    System.out.println(String.format("You selected %s while the computer selected %s.\n", playerChoice, computerChoice));
  }

  // Displays a tie outcome 
  public void tie(String choice) {
    System.out.println("This round was a tie and will start over.\n");
  }

  // Displays the outcome of each round
  public void handOutcome(String winner, String winnerHand, String verb, String loserHand) {
    String winConjugation = winner.equals("You") ? "win" : "wins";
    
    System.out.println(String.format("%s %s the round!\n%s %s %s\n", winner, winConjugation, winnerHand, verb, loserHand));
  }

  // Displays the current score of the game
  // The space in the string is to line up the user's and computer's win tally horizontally
  public void score(int playerWins, int computerWins) {
    System.out.println(String.format("The current score is:\n      you: %s wins\n computer: %s wins\n", playerWins, computerWins));
  }

  // New round announcement at the beginning of each round
  public void announceNextRound(int roundNumber) {
    System.out.println("------------ Round " + roundNumber + " ------------\n");
  }
  // Displays the winner of the game along with a history of each round
  public void finalResult(String winner, int winnerWins, int loserWins, ArrayList<ArrayList<String>> history) { 
    System.out.println("============ End of Game ============\n");
    System.out.println(String.format("%s won the game by a score of %s to %s\n", winner, winnerWins, loserWins));
    System.out.println("History of the game:\n");

    // iteration in order to print the outcome of each round in the game
    for (int i = 0; i < history.size(); i++) {
      String roundWinner = history.get(i).get(0);
      String winnerHand = history.get(i).get(1);
      String verb = history.get(i).get(2);
      String loserHand = history.get(i).get(3);
      
      System.out.println("Round " + (i+1) + ": " + roundWinner + " won - " + winnerHand + " " + verb + " " + loserHand);
    }
    System.out.println();
  }

  // Prompts the user of whether it would like to play another game
  public void promptPlayAgain() {
    System.out.println("""
      Would you like to play again? 
        (Enter "y" for yes)
        (Enter any other key to quit)
      """);
  }

  // Print statement in case the user entered an invalid response to the play again prompt
  public void invalidPlayAgainChoice() {
    System.out.println("Invalid choice.\n");
  }

  // New game print statement for when the user opts to play again
  public void newGame() {
    System.out.println("\n============ New Game ============");
  }

  // Final message upon exiting the game (with a tribute to the recently departed Jerry Springer)
  public void goodbyeMessage() {
    System.out.println("\nThank you for playing Rock, Paper, Scissors, Lizard, Spock\n\nTill next time, take care of yourselves and each other!\n");
  }
}