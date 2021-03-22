package com.jitterted.ebp.blackjack;

import org.approvaltests.Approvals;
import org.approvaltests.combinations.CombinationApprovals;
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

  @Test
  void all_combinations() {
    CombinationApprovals.verifyAllCombinations(this::displayCard, Suit.values(), Rank.values());
  }

  private String displayCard(Suit suit, Rank rank) {
    return new Card(suit, rank).display();
  }
}
