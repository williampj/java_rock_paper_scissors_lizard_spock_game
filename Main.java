// The driver class for the game
class Main {
  // The main method instantiates the two other classes of the game and 
  // calls the playGame method containing the control flow of the game
  public static void main(String[] args) {
    Display display = new Display();
    RPSLSGame game = new RPSLSGame();
    game.playGame(display);
  }
}