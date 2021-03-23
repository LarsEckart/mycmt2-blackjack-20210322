package com.jitterted.ebp.blackjack;

import static org.fusesource.jansi.Ansi.ansi;

import org.fusesource.jansi.Ansi;

import java.util.Scanner;

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

  static void displayBackOfCard() {
    System.out.print(
        ansi()
            .cursorUp(7)
            .cursorRight(12)
            .a("┌─────────┐").cursorDown(1).cursorLeft(11)
            .a("│░░░░░░░░░│").cursorDown(1).cursorLeft(11)
            .a("│░ J I T ░│").cursorDown(1).cursorLeft(11)
            .a("│░ T E R ░│").cursorDown(1).cursorLeft(11)
            .a("│░ T E D ░│").cursorDown(1).cursorLeft(11)
            .a("│░░░░░░░░░│").cursorDown(1).cursorLeft(11)
            .a("└─────────┘"));
  }

  public void determineOutcome() {
    if (game.playerHand().isBusted()) {
      System.out.println("You Busted, so you lose.  💸");
    } else if (game.dealerHand().isBusted()) {
      System.out.println("Dealer went BUST, Player wins! Yay for you!! 💵");
    } else if (game.playerHand().beats(game.dealerHand())) {
      System.out.println("You beat the Dealer! 💵");
    } else if (game.playerHand().pushes(game.dealerHand())) {
      System.out.println("Push: The house wins, you Lose. 💸");
    } else {
      System.out.println("You lost to the Dealer. 💸");
    }
  }

  public void displayGameState() {
    System.out.print(ansi().eraseScreen().cursor(1, 1));
    System.out.println("Dealer has: ");
    System.out.println(ConsoleHand.displayFirstCard(game.dealerHand())); // first card is Face Up

    // second card is the hole card, which is hidden
    displayBackOfCard();

    System.out.println();
    System.out.println("Player has: ");
    System.out.println(ConsoleHand.cardsAsString(game.playerHand()));
    System.out.println(" (" + game.playerHand().value() + ")");
  }

  public void displayFinalGameState() {
    System.out.print(ansi().eraseScreen().cursor(1, 1));
    System.out.println("Dealer has: ");
    System.out.println(ConsoleHand.cardsAsString(game.dealerHand()));
    System.out.println(" (" + game.dealerHand().value() + ")");

    System.out.println();
    System.out.println("Player has: ");
    System.out.println(ConsoleHand.cardsAsString(game.playerHand()));
    System.out.println(" (" + game.playerHand().value() + ")");
  }

  public void start() {
    displayWelcomeScreen();

    game.initialDeal();

    playerPlays();

    game.dealerTurn();

    displayFinalGameState();

    determineOutcome();

    resetScreen();
  }

  public void playerPlays() {
    while (!game.isPlayerDone()) {
      displayGameState();
      String command = inputFromPlayer();
      handle(command);
    }
  }

  public String inputFromPlayer() {
    System.out.println("[H]it or [S]tand?");
    Scanner scanner = new Scanner(System.in);
    return scanner.nextLine();
  }

  public void handle(String command) {
    if (command.toLowerCase().startsWith("h")) {
      game.playerHits();
    } else if (command.toLowerCase().startsWith("s")) {
      game.playerStands();
    }
  }

}
