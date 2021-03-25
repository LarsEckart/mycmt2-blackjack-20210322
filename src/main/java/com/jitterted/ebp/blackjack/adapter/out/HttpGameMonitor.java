package com.jitterted.ebp.blackjack.adapter.out;

import static org.slf4j.LoggerFactory.getLogger;

import com.jitterted.ebp.blackjack.domain.Game;
import com.jitterted.ebp.blackjack.domain.port.GameMonitor;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

public class HttpGameMonitor implements GameMonitor {
  private static final Logger log = getLogger(HttpGameMonitor.class);

  private HttpClient client;

  public HttpGameMonitor() {
    client =
        HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(2))
            .build();
  }

  @Override
  public void roundCompleted(Game game) {
    try {
      String body = """
        {
            \"playerName\": \"%s\",
            \"outcome\": \"%s\",
            \"playerHandValue\": \"%s\",
            \"dealerHandValue\": \"%s\"
        }""".formatted("Lars",
          game.determineOutcome().name(), game.playerHand().value(), game.dealerHand().value());
      var request = HttpRequest.newBuilder()
          .uri(URI.create("https://blackjack-game-monitor.herokuapp.com/api/gameresults"))
          .header("Content-Type", "application/json")
          .POST(BodyPublishers.ofString(body))
          .build();
      var response = client.send(request, BodyHandlers.ofString());
      log.info("response code: " + response.statusCode());
    } catch (InterruptedException | IOException e) {
      log.error(e.getMessage());
    }
  }
}
