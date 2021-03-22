package com.jitterted.ebp.blackjack;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import java.util.List;

public class HandDisplayTest {
  @Test
  public void displayFirstCard() throws Exception {
    Hand hand = new Hand(List.of(new Card(Suit.HEARTS, Rank.ACE)));

    Approvals.verify(ConsoleHand.displayFirstCard(hand));
  }

  @Test
  public void displayTwoCard() throws Exception {
    Hand hand =
        new Hand(List.of(new Card(Suit.HEARTS, Rank.ACE), new Card(Suit.SPADES, Rank.THREE)));

    Approvals.verify(ConsoleHand.cardsAsString(hand.cards()));
  }
}
