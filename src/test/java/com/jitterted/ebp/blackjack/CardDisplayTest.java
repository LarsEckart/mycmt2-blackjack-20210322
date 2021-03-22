package com.jitterted.ebp.blackjack;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

class CardDisplayTest {

  @Test
  void displayTenAsString() {
    Card card = new Card(Suit.HEARTS, Rank.TEN);

    Approvals.verify(card.display());
  }

  @Test
  void displayNonTenAsString() {
    Card card = new Card(Suit.CLUBS, Rank.SEVEN);

    Approvals.verify(card.display());
  }
}
