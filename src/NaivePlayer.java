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
}
