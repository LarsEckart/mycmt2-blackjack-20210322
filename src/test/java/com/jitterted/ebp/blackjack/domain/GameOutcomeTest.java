package com.jitterted.ebp.blackjack.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class GameOutcomeTest {

  @Test
  public void player_beats_dealer() {
    Deck stubDeck = new StubDeck(
        new Card(Suit.HEARTS, Rank.KING),
        new Card(Suit.HEARTS, Rank.JACK),
        new Card(Suit.HEARTS, Rank.QUEEN),
        new Card(Suit.HEARTS, Rank.NINE)
        );
    Game game = new Game(stubDeck);
    game.initialDeal();

    String outcome = game.determineOutcome();

    assertThat(outcome)
        .isEqualTo("You beat the Dealer! 💵");
  }

  @Test
  public void player_goes_bust() {
    Deck stubDeck = new StubDeck(
        new Card(Suit.HEARTS, Rank.KING),
        new Card(Suit.HEARTS, Rank.JACK),
        new Card(Suit.HEARTS, Rank.QUEEN),
        new Card(Suit.HEARTS, Rank.TEN),
        new Card(Suit.HEARTS, Rank.EIGHT)
    );
    Game game = new Game(stubDeck);
    game.initialDeal();
    game.playerHits();

    String outcome = game.determineOutcome();

    assertThat(outcome)
        .isEqualTo("You Busted, so you lose.  💸");
  }

}
