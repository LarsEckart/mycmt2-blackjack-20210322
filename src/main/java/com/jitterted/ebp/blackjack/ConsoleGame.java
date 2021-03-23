package com.jitterted.ebp.blackjack;

import static org.fusesource.jansi.Ansi.ansi;

import org.fusesource.jansi.Ansi;

public class ConsoleGame {

  private final Game game;

  public ConsoleGame(Game game) {
    this.game = game;
  }

  public static void main(String[] args) {
    Game game = new Game();
    ConsoleGame consoleGame = new ConsoleGame(game); // in general: Entities aren't directly passed in to Adapters
    consoleGame.start();
  }

  static void resetScreen() {
    System.out.println(ansi().reset());
  }

  static void displayWelcomeScreen() {
    System.out.println(ansi()
                           .bgBright(Ansi.Color.WHITE)
                           .eraseScreen()
                           .cursor(1, 1)
                           .fgGreen().a("Welcome to")
                           .fgRed().a(" Jitterted's")
                           .fgBlack().a(" BlackJack"));
  }

  public void start() {
    displayWelcomeScreen();

    game.initialDeal();

    playerPlays();

    game.dealerTurn();

    game.displayFinalGameState();

    game.determineOutcome();

    resetScreen();
  }

  public void playerPlays() {
    while (!game.isPlayerDone()) {
      game.displayGameState();
      String command = game.inputFromPlayer();
      handle(command);
    }
  }

  public void handle(String command) {
    if (command.toLowerCase().startsWith("h")) {
      game.playerHits();
    } else if (command.toLowerCase().startsWith("s")) {
      game.playerStands();
    }
  }

}
