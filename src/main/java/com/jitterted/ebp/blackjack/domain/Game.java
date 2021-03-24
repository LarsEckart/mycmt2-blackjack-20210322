package com.jitterted.ebp.blackjack.domain;

public class Game {

  private final Deck deck;

  private final Hand dealerHand = new Hand();
  private final Hand playerHand = new Hand();
  private boolean playerDone;

  public Game() {
    this(new Deck());
  }

  public Game(Deck deck) {
    this.deck = deck;
  }

  public void initialDeal() {
    dealRoundOfCards();
    dealRoundOfCards();
  }

  private void dealRoundOfCards() {
    // why: players first because this is the rule
    playerHand.drawFrom(deck);
    dealerHand.drawFrom(deck);
  }

  public GameOutcome determineOutcome() {
    if (playerHand.isBusted()) {
      return GameOutcome.YOU_BUSTED_SO_YOU_LOSE;
    } else if (dealerHand.isBusted()) {
      return GameOutcome.DEALER_WENT_BUST_PLAYER_WINS_YAY_FOR_YOU;
    } else if (playerHand.beats(dealerHand)) {
      return GameOutcome.YOU_BEAT_THE_DEALER;
    } else if (playerHand.pushes(dealerHand)) {
      return GameOutcome.PUSH_THE_HOUSE_WINS_YOU_LOSE;
    } else {
      return GameOutcome.YOU_LOST_TO_THE_DEALER;
    }
  }

  public void dealerTurn() {
    // Dealer makes its choice automatically based on a simple heuristic (<=16, hit, 17>stand)
    if (!playerHand.isBusted()) {
      while (dealerHand.dealerMustDrawCard()) {
        dealerHand.drawFrom(deck);
      }
    }
  }

  public Hand playerHand() {
    return playerHand;
  }

  public Hand dealerHand() {
    return dealerHand;
  }

  public void playerHits() {
    playerHand.drawFrom(deck);
    playerDone = playerHand.isBusted();
  }

  public void playerStands() {
    playerDone = true;
  }

  public boolean isPlayerDone() {
    return playerDone;
  }
}
