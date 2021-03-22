package com.jitterted.ebp.blackjack;

import static org.fusesource.jansi.Ansi.ansi;

import java.util.List;
import java.util.stream.Collectors;

public class ConsoleHand {

  static String displayFirstCard(Hand hand) {
    return ConsoleCard.display(hand.cards().get(0));
  }

  static String cardsAsString(List<Card> cards) {
    return cards.stream()
        .map(ConsoleCard::display)
        .collect(Collectors.joining(ansi().cursorUp(6).cursorRight(1).toString()));
  }
}
