package winter.advent.advent2022;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static winter.advent.advent2022.RockPaperScissorsGame.Round.*;

class RockPaperScissorsGameTest {

    @Test
    void calculateYourScore_log() {
        RockPaperScissorsGame game = RockPaperScissorsGame.loadFromInputStream(getClass().getResourceAsStream("/inputfiles/rps-strategy-guide.txt"));
        int actual = game.calculateYourScore();
        assertEquals(13448, actual);
    }

    @Test
    void calculateYourScore_single1(){
        RockPaperScissorsGame game = new RockPaperScissorsGame(List.of(
                of(RockPaperScissorsGame.Move.ROCK, RockPaperScissorsGame.Outcome.DRAW)
        ));

        int actual = game.calculateYourScore();
        assertEquals(4, actual);
    }

    @Test
    void calculateYourScore_small(){
        RockPaperScissorsGame game = new RockPaperScissorsGame(List.of(
                of(RockPaperScissorsGame.Move.ROCK, RockPaperScissorsGame.Outcome.DRAW),
                of(RockPaperScissorsGame.Move.PAPER, RockPaperScissorsGame.Outcome.LOSE),
                of(RockPaperScissorsGame.Move.SCISSORS, RockPaperScissorsGame.Outcome.WIN)
        ));

        int actual = game.calculateYourScore();
        assertEquals(12, actual);
    }

}