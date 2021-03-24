package com.jitterted.ebp.blackjack.domain;

public enum GameOutcome {
  YOU_BUSTED_SO_YOU_LOSE("You Busted, so you lose.  💸"), DEALER_WENT_BUST_PLAYER_WINS_YAY_FOR_YOU(
      "Dealer went BUST, Player wins! Yay for you!! 💵"), YOU_BEAT_THE_DEALER(
      "You beat the Dealer! 💵"), PUSH_THE_HOUSE_WINS_YOU_LOSE(
      "Push: The house wins, you Lose. 💸"), YOU_LOST_TO_THE_DEALER("You lost to the Dealer. 💸");
  private String value;

  public String getValue() {
    return value;
  }

  GameOutcome(String value) {
    this.value = value;
  }
}
