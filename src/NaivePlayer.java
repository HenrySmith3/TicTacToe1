import java.util.ArrayList;
import java.util.List;

/**
 * My naive player simply wants to take the center piece first. Then, it tries to take the corners,
 * and then it will take the sides.
 * The idea behind this is that it does not plan ahead at all, it simply takes what are considered
 * to be the most valuable places
 * In order to make things more interesting, it chooses randomly between each corner and side.
 * Otherwise, the game would have the same outcome each time.
 */
public class NaivePlayer extends Player {
    public NaivePlayer (Square.Mark mark) {
        super(mark);
    }
    @Override
    public Square makeMove(Game game) {
        List<StrategicKnowledge.MoveType> strategies = StrategicKnowledge.getStrategy(StrategicKnowledge.StrategyType.NAIVE);

        for (StrategicKnowledge.MoveType moveType : strategies) {
            reasoning.addReason(new Reason(null, "My limited strategic knowledge says that I should go for a " + moveType + " right now."));
            Square chosenMove = resolveMove(moveType, game);
            if (chosenMove != null) {
                return chosenMove;
            } else {
                reasoning.addReason(new Reason(null, "I couldn't find a valid " + moveType + "."));
            }
        }
        return null;
    }

    private Square resolveMove(StrategicKnowledge.MoveType moveType, Game game) {
        switch (moveType) {
            case VALUABLE_MOVE:
                Square square = game.gameKnowledge.mostValuableEmptySquare();
                reasoning.addReason(new Reason(square, "I'm too naive to know what the best strategy is, so I'm just picking the most valuable square, which is this "
                        + square.type.toString() + " square"));
                return square;
            default:
                return null;
        }
    }


    @Override
    public String answerQuestion(Main.Question question, int turn, Game game) {
        List<Reason> reasons = reasoning.getReasonsForTurn(turn);
        if (question.equals(Main.Question.WHICHACTION)) {
            Square move = reasons.get(reasons.size()-1).move;
            if (move.type.equals(Square.Type.CENTER)) {
                return "I moved to the center piece on the " + ThoughtfulPlayer.numberToOrdinal(turn) + " turn";
            } else if (move.type.equals(Square.Type.CORNER)) {
                return "I moved to a corner piece at (" + move.x + "," + move.y + ") on the " +
                        ThoughtfulPlayer.numberToOrdinal(turn) + " turn";
            } else {
                return "I moved to an edge piece at (" + move.x + "," + move.y + ") on the " +
                        ThoughtfulPlayer.numberToOrdinal(turn) + " turn";
            }
        } else if (question.equals(Main.Question.WHY)) {
            String retVal = "";
            retVal += reasons.get(reasons.size()-2).justification;
            retVal = retVal.replace("says", "said");
            retVal = retVal.replace(" right now.", ". \n");
            retVal += reasons.get(reasons.size()-1).justification;
            return retVal;
        } else if (question.equals(Main.Question.GOODMOVE)) {
            Square move = reasons.get(reasons.size()-1).move;
            if (game.getWinner() == mySymbol) {
                if (game.inWinningCombo(move)) {
                    return "Well, I won the game and that move was in the winning set of three, so yes, I think it was a good move.";
                } else {
                    return "That move wasn't actually in the winning combination, but I still won the game, so I still feel good about it.";
                }
            } else {
                if (turn ==1) {
                    return "Well, I lost the game, but this was my first move, so I can't really attribute my loss to it.";
                }
                return "I lost the game, so I can't really feel too good about any of my moves except for maybe the first move.";
            }
        }
        throw new RuntimeException();
    }
}
