import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

// The RPSLSGame class holds the game logic for Rock, Paper, Scissors, Lizard, Spock
class RPSLSGame {
  // handOutcomes stores a nested hash map of 
  //    player choice (outer key)
  //       computer choice (middle layer key)
  //          winner + verb (inner keys)
  private HashMap<String, HashMap> handOutcomes = new HashMap<>();
  // Used to convert user and computer random choices (1-5) to string 
  private String[] numericChoiceConversion = {"rock", "paper", "scissors", "lizard", "spock"};
  // Holds user input throughout the program
  private Scanner input = new Scanner(System.in);

  public RPSLSGame() {
    // Constructor calls the function that generates the HandOutcomes hash map
    setUpHandOutcomes();
  }
  
  // public void setUpHandOutcomes() {
  private void setUpHandOutcomes() {
    // Adding player choices to handOutcomes hash map
    handOutcomes.put("rock", new HashMap<>());
    handOutcomes.put("paper", new HashMap<>());
    handOutcomes.put("scissors", new HashMap<>());
    handOutcomes.put("lizard", new HashMap<>());
    handOutcomes.put("spock", new HashMap<>());

    // All possible winner/loser outcomes
    // Each array consists of player choice, computer choice, hand winner and verb
    String[][] outcomes = {
      // Computer choices and outcomes for when player choses rock
      {"rock", "paper", "computer", "covers"},
      {"rock", "scissors", "player", "crushes"},
      {"rock", "lizard", "player", "crushes"},
      {"rock", "spock", "computer", "vaporizes"},
      // Computer choices and outcomes for when player choses paper 
      {"paper", "rock", "player", "covers"},
      {"paper", "scissors", "computer", "cuts"},
      {"paper", "lizard", "computer", "eats"},
      {"paper", "spock", "player", "disproves"},
      // Computer choices and outcomes for when player choses scissors 
      {"scissors", "rock", "computer", "crushes"},
      {"scissors", "paper", "player", "cuts"},
      {"scissors", "lizard", "player", "decapitates"},
      {"scissors", "spock", "computer", "smashes"},
      // Computer choices and outcomes for when player choses lizard 
      {"lizard", "rock", "computer", "crushes"},
      {"lizard", "paper", "player", "eats"},
      {"lizard", "scissors", "computer", "decapitates"},
      {"lizard", "spock", "player", "poisons"},
      // Computer choices and outcomes for when player choses spock 
      {"spock", "rock", "player", "vaporizes"},
      {"spock", "paper", "computer", "disproves"},
      {"spock", "scissors", "player", "smashes"},
      {"spock", "lizard", "computer", "poisons"},
    };

    // Iterates all possible outcomes and adds each to the handOutcomes hash map via addHandOutcome method
    for(String[] outcome: outcomes) {
      addHandOutcome(outcome[0], outcome[1], outcome[2], outcome[3]);
    }
  };

  // public void addHandOutcome(String playerChoice, String computerChoice, String winner, String verb) {
  private void addHandOutcome(String playerChoice, String computerChoice, String winner, String verb) {
    // creates the lowest level hash map in the handOutcomes nested hash map
    HashMap<String, String> handOutcome = new HashMap<>();
    handOutcome.put("winner", winner);
    handOutcome.put("verb", verb);
    // Adds outcome given the user's and computer's choices
    handOutcomes.get(playerChoice).put(computerChoice, handOutcome);
  }

  // Processes the outcome of the given round
  private HashMap<String, String> processHandOutcome(String playerChoice, String computerChoice) {
    // Creates and returns a simple hash map with a single "winner"=>"tie" key-value pair if user and computer choices are identical
    if (playerChoice.equals(computerChoice)) {
      HashMap<String, String> tieOutcome = new HashMap<>();
      tieOutcome.put("winner", "tie");
      return tieOutcome;
    }
    // Hash map of possible outcomes given user's choice
    HashMap<String, HashMap> computerChoices = handOutcomes.get(playerChoice);
    // Hash map of the outcome of the round given the computer's and player's choice
    HashMap<String, String> handOutcome = computerChoices.get(computerChoice);
    
    return handOutcome;
  }

  // Adds the outcome of each round to the history data structure to be printed after the end of the game
  private void addToHistory(ArrayList<ArrayList<String>> history, String winner, String winningHand, String verb, String losingHand) {
    // creates a flat 4-element handOutcome array to add to the nested history array
    ArrayList<String> handOutcome = new ArrayList<>();
    handOutcome.add(winner);
    handOutcome.add(winningHand);
    handOutcome.add(verb);
    handOutcome.add(losingHand);
    
    history.add(handOutcome);
  }

  // The method directing the game
  public void playGame(Display display) {
    // sets up all variables used in the method
    int bestOf = 0;
    int numHandsToWin = 0;
    int numGamesPlayed = 0;
    // user's input choosing between 1-5 for the five options
    int playerNumericChoice = 0;
    // used to generate the computer's choices
    Random rand = new Random();
    String playerChoice = "";
    String computerChoice = "";
    int playerWins = 0;
    int computerWins = 0;
    int winnerWins = 0;
    int loserWins = 0;
    HashMap<String, String> handOutcome = new HashMap<>();
    String winnerOfHand = "";
    boolean hasGameWinner = false;
    String gameWinner = "";
    ArrayList<ArrayList<String>> history = new ArrayList<>();
    String playAgainResponse = "";
    boolean playAgain = false;
    
    display.welcomeMessage();

    // User decides between playing a best of 3, 5 or 7
    boolean validBestOf = false;
    // Loops exits when user has given a valid response (3, 5 or 7)
    while (!validBestOf) {
      // Prints available options to the user
      display.promptBestOf();
      try {
        bestOf = input.nextInt();
        if (bestOf != 3 && bestOf != 5 && bestOf != 7) {
          display.invalidBestOfMessage();
        } 
        else {
          validBestOf = true;
          // print statement that confirms the user's choice
          display.bestOf(bestOf);
        }
      } 
      catch (InputMismatchException ex) {
        display.invalidBestOfMessage();
      }
      // clears the input of \n 
      input.nextLine();
    }

    // if best of 3, for example, the first to win two hands wins the game
    numHandsToWin = (bestOf / 2) + 1;

    // Each loop is a round. The loop exits when a game winer has been identified. 
    round:
    while (!hasGameWinner) {
      // The current score is displayed before each round
      display.score(playerWins, computerWins);
      // The round number is displayed for each round
      display.announceNextRound(history.size() + 1);
      boolean validChoice = false;      
      // Loop that exits when the user has made a valid selection (integers between 1 and 5)
      while (!validChoice) {
        display.promptChoice();
        try {
          playerNumericChoice = input.nextInt();
          if (playerNumericChoice < 1 || playerNumericChoice > 5) {
            display.invalidPlayerChoiceMessage();
          } 
          else {
            validChoice = true;
          }
        } 
        catch (InputMismatchException ex) {
          display.invalidPlayerChoiceMessage();
        }
        // clears input of /n character
        input.nextLine();
      }

      // user's and computer's choices are converted from an integer to one of the five strings: 
      // "Rock", "Paper", "Scissors", "Lizard", or "Spock"
      playerChoice = numericChoiceConversion[playerNumericChoice - 1];
      computerChoice = numericChoiceConversion[rand.nextInt(5)];
      // Each player's hand is displayed to the user
      display.choices(playerChoice, computerChoice);
      // The winner of the hand is determined from processHandOutcome method
      handOutcome = processHandOutcome(playerChoice, computerChoice);
      winnerOfHand = handOutcome.get("winner");

      // The round is restarted in case of a tie (user and computer choosing same option)
      if (winnerOfHand.equals("tie")) {
        display.tie(playerChoice);
        continue round; 
      }
      if (winnerOfHand.equals("player")) {
        // Hand outcome and the logic of why the user won is displayed
        display.handOutcome("You", playerChoice, handOutcome.get("verb"), computerChoice);
        // The result is stored in the history array
        addToHistory(history, "You", playerChoice, handOutcome.get("verb"), computerChoice);
        playerWins++;
        // if the user has won the game
        if (playerWins == numHandsToWin) {
          gameWinner = "You";
          winnerWins = playerWins;
          loserWins = computerWins;
          hasGameWinner = true;
        } 
      }
      else {
        // Hand outcome and the logic of why the computer won is displayed
        display.handOutcome("Computer", computerChoice, handOutcome.get("verb"), playerChoice);  
        // The result is stored in the history array
        addToHistory(history, "Computer", computerChoice, handOutcome.get("verb"), playerChoice);
        computerWins++;
        // if the computer has won the game
        if (computerWins == numHandsToWin) {
          gameWinner = "Computer";
          winnerWins = computerWins;
          loserWins = playerWins;
          hasGameWinner = true;
        } 
      }
    }
    // Prints the final result and history of the game 
    display.finalResult(gameWinner, winnerWins, loserWins, history);

    boolean validPlayAgainResponse = false;
    // User is queried about playing another game. Any character other than "Y/y" is interpreted as a no. 
    while (!validPlayAgainResponse) {
      try {
        display.promptPlayAgain();
        // chained method call to ensure first char in playAgainResponse will be a "y" if the user wishes to play again
        playAgainResponse = input.nextLine().trim().toLowerCase();
        playAgain = playAgainResponse.charAt(0) == 'y' ? true : false;
        validPlayAgainResponse = true;
      }
      catch (InputMismatchException ex) {
        display.invalidPlayAgainChoice();
      }
    }

    if (playAgain) {
      // prints a new game line separater
      display.newGame();
      // recursively calls the current method to play another game
      playGame(display);
    } 
    else {
      // final print message
      display.goodbyeMessage();
    }
  }
}