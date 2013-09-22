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
        Square square = game.gameKnowledge.mostValuableEmptySquare();
        reasoning.addReason(new Reason(square, "I'm too naive to know what the best strategy is, so I'm just picking the most valuable square, which is this "
            + square.type.toString() + " square"));
        return square;
//        if (game.centerPiece().mark == Square.Mark.BLANK) {
//            reasoning.addReason(new Reason(game.centerPiece(), "the center piece is the most valuable and it isn't taken yet."));
//            return game.centerPiece();
//        }
//        ArrayList<Square> possibleChoices = new ArrayList<Square>();
//        for (Square square : game.corners()) {
//            if (square.mark == Square.Mark.BLANK) {
//                possibleChoices.add(square);
//            }
//        }
//        if (possibleChoices.size() >= 1) {
//            Square chosenSquare = possibleChoices.get((int)(Math.random()*possibleChoices.size()));
//            reasoning.addReason(new Reason(chosenSquare, "the center piece is taken, but there's a corner open and the corners are valuable."));
//            return chosenSquare;
//        }
//        for (Square square : game.sides()) {
//            if (square.mark == Square.Mark.BLANK) {
//                possibleChoices.add(square);
//            }
//        }
//        if (possibleChoices.size() >= 1) {
//            Square chosenSquare = possibleChoices.get((int)(Math.random()*possibleChoices.size()));
//            reasoning.addReason(new Reason(chosenSquare, "the corners and center square are all taken, so I have to take a side piece instead"));
//            return chosenSquare;
//        }
        //return new Square(-1,-1);//This should never happen.
    }
}
