package com.jitterted.ebp.blackjack.adapter.out;

import com.jitterted.ebp.blackjack.domain.Game;
import org.junit.jupiter.api.Test;

class HttpGameMonitorTest {

  @Test
  void name() {
    HttpGameMonitor httpGameMonitor = new HttpGameMonitor();
    httpGameMonitor.roundCompleted(new Game());
  }
}
