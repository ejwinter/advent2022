package winter.advent.advent2022;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

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
                .map(logLine -> new Round(Move.fromCharacter(logLine.charAt(2)), Move.fromCharacter(logLine.charAt(0))))
                .collect(Collectors.toList());
        return new RockPaperScissorsGame(log);
    }

    public static RockPaperScissorsGame loadFromInputStream(InputStream inputStream){
        return loadFromLogLines(readAllLines(inputStream));
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


        public Round(Move playerMove, Move opponentMove) {
            this.playerMove = playerMove;
            this.opponentMove = opponentMove;
        }

        public static Round of(Move playerMove, Move opponentMove){
            return new Round(playerMove, opponentMove);
        }

        public int getScore(){
            return playerMove.scoreAgainst(opponentMove);
        }

        public int getOpponentScore(){
            return opponentMove.scoreAgainst(playerMove);
        }


    }

    private static List<String> readAllLines(InputStream inputStream) {
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            return bufferedReader.lines().collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
