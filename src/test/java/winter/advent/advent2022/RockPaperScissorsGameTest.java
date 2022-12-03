package winter.advent.advent2022;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static winter.advent.advent2022.RockPaperScissorsGame.Round.*;

class RockPaperScissorsGameTest {

    @Test
    void calculateYourScore_log() {
        RockPaperScissorsGame game = RockPaperScissorsGame.loadFromInputStream(getClass().getResourceAsStream("/d2/rps-strategy-guide.txt"));
        int actual = game.calculateYourScore();
        assertEquals(13924, actual);
    }

    @Test
    void calculateYourScore_single1(){
        RockPaperScissorsGame game = new RockPaperScissorsGame(List.of(
                of(RockPaperScissorsGame.Move.PAPER, RockPaperScissorsGame.Move.ROCK)
        ));

        int actual = game.calculateYourScore();
        assertEquals(8, actual);
    }

    @Test
    void calculateYourScore_small(){
        RockPaperScissorsGame game = new RockPaperScissorsGame(List.of(
                of(RockPaperScissorsGame.Move.ROCK, RockPaperScissorsGame.Move.PAPER),
                of(RockPaperScissorsGame.Move.PAPER, RockPaperScissorsGame.Move.ROCK),
                of(RockPaperScissorsGame.Move.SCISSORS, RockPaperScissorsGame.Move.SCISSORS)
        ));

        int actual = game.calculateYourScore();
        assertEquals(15, actual);
    }

}