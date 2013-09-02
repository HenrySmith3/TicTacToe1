import java.util.ArrayList;

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
        if (game.centerPiece().mark == Square.Mark.BLANK) {
            return game.centerPiece();
        }
        ArrayList<Square> possibleChoices = new ArrayList<Square>();
        for (Square square : game.corners()) {
            if (square.mark == Square.Mark.BLANK) {
                possibleChoices.add(square);
            }
        }
        if (possibleChoices.size() >= 1) {
            return possibleChoices.get((int)(Math.random()*possibleChoices.size()));
        }
        for (Square square : game.sides()) {
            if (square.mark == Square.Mark.BLANK) {
                possibleChoices.add(square);
            }
        }
        if (possibleChoices.size() >= 1) {
            return possibleChoices.get((int)(Math.random()*possibleChoices.size()));
        }
        return new Square(-1,-1);//This should never happen.
    }
}
