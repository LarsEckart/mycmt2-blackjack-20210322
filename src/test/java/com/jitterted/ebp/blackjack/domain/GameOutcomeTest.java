package com.jitterted.ebp.blackjack.domain;

import com.jitterted.ebp.blackjack.domain.port.GameMonitor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class GameOutcomeTest {

  @Test
  public void playerGoesBustResultsInPlayerLoses() throws Exception {
    Deck playerHitsGoesBustDeck = new StubDeck(Rank.QUEEN, Rank.EIGHT,
                                               Rank.TEN, Rank.FOUR,
                                               Rank.THREE);
    Game game = new Game(playerHitsGoesBustDeck);

    game.initialDeal();

    game.playerHits();

    assertThat(game.determineOutcome())
        .isEqualByComparingTo(GameOutcome.PLAYER_BUSTED);
  }

  @Test
  public void playerBeatsDealer() throws Exception {
    Deck playerBeatsDealerDeck = new StubDeck(Rank.QUEEN, Rank.EIGHT,
                                              Rank.TEN, Rank.JACK);
    Game game = new Game(playerBeatsDealerDeck);
    game.initialDeal();

    game.playerStands();

    assertThat(game.determineOutcome())
        .isEqualByComparingTo(GameOutcome.PLAYER_BEATS_DEALER);
  }

  @Test
  public void dealerDrawsAdditionalCardAfterPlayerStands() throws Exception {
    Deck dealerDrawsAdditionalCardDeck = new StubDeck(Rank.TEN, Rank.QUEEN,
                                                      Rank.EIGHT, Rank.FIVE,
                                                      Rank.SIX);
    Game game = new Game(dealerDrawsAdditionalCardDeck);
    game.initialDeal();

    game.playerStands();

    assertThat(game.dealerHand().cards())
        .hasSize(3);
  }

  @Test
  void playerStandsCompletesGameSendsToMonitor() {
    // creates the spy based on the interface
    GameMonitor gameMonitorSpy = spy(GameMonitor.class);
    Deck playerHitsGoesBustDeck = new StubDeck(Rank.QUEEN, Rank.KING,
        Rank.NINE, Rank.TEN);
    Game game = new Game(playerHitsGoesBustDeck, gameMonitorSpy);

    game.initialDeal();
    game.playerStands();

    // verify that the roundCompleted method was called with any instance of a Game class
    verify(gameMonitorSpy).roundCompleted(any(Game.class));
  }

  @Test
  void playerHitsDoesNotGoBustNoCallToMonitor() {
    // creates the spy based on the interface
    GameMonitor gameMonitorSpy = spy(GameMonitor.class);
    Deck playerHitsGoesBustDeck = new StubDeck(Rank.QUEEN, Rank.KING,
        Rank.FIVE, Rank.EIGHT,
        Rank.FOUR);
    Game game = new Game(playerHitsGoesBustDeck, gameMonitorSpy);

    game.initialDeal();
    game.playerHits();

    // verify that the roundCompleted method was NOT called
    verify(gameMonitorSpy, never()).roundCompleted(any(Game.class));
  }

  @Test
  void playerHitsGoesBustCallToMonitorDealerTakesNoTurn() {
    // creates the spy based on the interface
    GameMonitor gameMonitorSpy = spy(GameMonitor.class);
    Deck playerHitsGoesBustDeck = new StubDeck(Rank.QUEEN, Rank.KING,
        Rank.FIVE, Rank.EIGHT,
        Rank.NINE);
    Game game = new Game(playerHitsGoesBustDeck, gameMonitorSpy);

    game.initialDeal();
    game.playerHits();

    // verify that the roundCompleted method was NOT called
    verify(gameMonitorSpy).roundCompleted(any(Game.class));
  }
}
