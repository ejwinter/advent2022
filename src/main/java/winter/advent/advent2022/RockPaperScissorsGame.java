package winter.advent.advent2022;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import static winter.advent.advent2022.IOUtils.listAllLines;

@Getter
public class RockPaperScissorsGame {

    private final List<Round> rounds;

    public RockPaperScissorsGame(List<Round> rounds) {
        this.rounds = rounds;
    }

    public int calculateYourScore(){
        return rounds.stream()
                .mapToInt(Round::getScore)
                .sum();
    }

    public int calculateOpponentScore(){
        return rounds.stream()
                .mapToInt(Round::getOpponentScore)
                .sum();
    }

    public static RockPaperScissorsGame loadFromLogLines(List<String> logLines){
        List<Round> log = logLines.stream()
                .map(logLine -> new Round(Move.fromCharacter(logLine.charAt(0)), Outcome.fromCharacter(logLine.charAt(2))))
                .collect(Collectors.toList());
        return new RockPaperScissorsGame(log);
    }

    public static RockPaperScissorsGame loadFromInputStream(InputStream inputStream){
        return loadFromLogLines(listAllLines(inputStream));
    }

    public enum Outcome {
        WIN('Z')  , LOSE('X'), DRAW('Y');

        private final char character;

        Outcome(char character) {
            this.character = character;
        }

        public static Outcome fromCharacter(char character){
            for (Outcome outcome : values()) {
                if(outcome.character == character){
                    return outcome;
                }
            }
            throw new IllegalArgumentException("Unknown character: " + character);
        }
    }

    public enum Move {
        ROCK(1, 3, 2),
        PAPER(2, 1, 3),
        SCISSORS(3, 2, 1);

        public final int moveValue;

        public final int beatsValue;
        public final int losesToValue;

        Move(int moveValue, int beatsValue, int losesToValue) {
            this.moveValue = moveValue;
            this.beatsValue = beatsValue;
            this.losesToValue = losesToValue;
        }

        public static Move fromCharacter(char character){
            switch (character){
                case 'A': case 'X':
                    return ROCK;
                case 'B': case 'Y':
                    return PAPER;
                case 'C': case 'Z':
                    return SCISSORS;
                default:
                    throw new IllegalArgumentException("Invalid character: " + character);
            }
        }

        public static Move fromValue(int value) {
            for (Move move : values()) {
                if(move.moveValue == value){
                    return move;
                }
            }
            throw new IllegalArgumentException("Invalid value: " + value);
        }

        public int scoreAgainst(Move opponentMove){
            int score = moveValue;
            if(this.beatsValue == opponentMove.moveValue){
                score += 6;
            }else if(this == opponentMove){
                score += 3;
            }
            return score;
        }
    }

    @Getter
    public static class Round {
        private final Move playerMove;
        private final Move opponentMove;

        private final Outcome outcome;


        public Round(Move opponentMove, Outcome outcome) {
            this.opponentMove = opponentMove;
            this.outcome = outcome;
            this.playerMove = calculatePlayerMove(opponentMove, outcome);
        }

        private Move calculatePlayerMove(Move opponentMove, Outcome outcome) {
            switch (outcome){
                case WIN:
                    return Move.fromValue(opponentMove.losesToValue);
                case LOSE:
                    return Move.fromValue(opponentMove.beatsValue);
                case DRAW:
                    return opponentMove;
                default:
                    throw new IllegalArgumentException("Unknown outcome: " + outcome);
            }
        }

        public static Round of(Move opponentMove, Outcome outcome){
            return new Round(opponentMove, outcome);
        }

        public int getScore(){
            return playerMove.scoreAgainst(opponentMove);
        }

        public int getOpponentScore(){
            return opponentMove.scoreAgainst(playerMove);
        }


    }
}
