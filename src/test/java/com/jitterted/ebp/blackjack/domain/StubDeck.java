package com.jitterted.ebp.blackjack.domain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StubDeck extends Deck {

  private List<Card> cards;

  public StubDeck(Card... card) {
    cards = Arrays.stream(card).collect(Collectors.toList());
  }

  @Override
  public Card draw() {
    return cards.remove(0);
  }
}
