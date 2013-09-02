/**
 * My naive player simply wants to take the center piece first. Then, it tries to take the corners,
 * and then it will take the sides.
 * The idea behind this is that it does not plan ahead at all, it simply takes what are considered
 * to be the most valuable places
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
        for (Square square : game.corners()) {
            if (square.mark == Square.Mark.BLANK) {
                return square;
            }
        }
        for (Square square : game.sides()) {
            if (square.mark == Square.Mark.BLANK) {
                return square;
            }
        }
        return new Square(-1,-1);//This should never happen.
    }
}
