/**
 * My Thoughtful player. Implements basic strategy for looking ahead.
 * First, he will win if he can.
 * Then, he will block if he needs to.
 * If he can't win or block, he'll just take a valuable spot (the naive implementation: center, then corner, then side)
 */
public class ThoughtfulPlayer extends Player{
    public ThoughtfulPlayer (Square.Mark mark) {
        super(mark);
    }
    @Override
    public Square makeMove(Game game) {
        if (winningMove(game, mySymbol) != null) {
            return winningMove(game, mySymbol);
        }
        Square.Mark opponentSymbol = mySymbol == Square.Mark.X ? Square.Mark.O : Square.Mark.X;
        if (winningMove(game, opponentSymbol) != null) {
            return winningMove(game, opponentSymbol);
        }
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

    public Square winningMove(Game game, Square.Mark mark) {
        for (int i = 0; i <= 2; i++) {
            if (winningMoveInColumn(game, i, mark) != null) {
                return winningMoveInColumn(game, i, mark);
            }
            if (winningMoveInRow(game, i, mark) != null) {
                return winningMoveInRow(game, i, mark);
            }
        }
        if (winningMoveInDiagonal(game, true, mark) != null) {
            return winningMoveInDiagonal(game, true, mark);
        }
        if (winningMoveInDiagonal(game, false, mark) != null) {
            return winningMoveInDiagonal(game, false, mark);
        }
        return null;
    }

    public Square winningMoveInColumn(Game game, int i, Square.Mark mark) {
        Square openSquare = null;
        int myPlaces = 0;
        for (int j = 0; j<= 2; j++) {
            Square piece = game.getAt(j,i);
            if (piece.mark == Square.Mark.BLANK) {
                if (openSquare == null) {
                    openSquare = piece;
                } else {
                    return null;//no winning move;
                }
            } else if (piece.mark == mark) {
                myPlaces++;//it's one of mine
            } else {
                return null;//enemy has one of these pieces, so it can't be a victory.
            }
        }
        if (myPlaces == 2 && openSquare != null) {
            return openSquare;
        } else {
            return null; //This should absolutely never happen.
        }
    }
    public Square winningMoveInRow(Game game, int i, Square.Mark mark) {
        Square openSquare = null;
        int myPlaces = 0;
        for (int j = 0; j<= 2; j++) {
            Square piece = game.getAt(i,j);
            if (piece.mark == Square.Mark.BLANK) {
                if (openSquare == null) {
                    openSquare = piece;
                } else {
                    return null;//no winning move;
                }
            } else if (piece.mark == mark) {
                myPlaces++;//it's one of mine
            } else {
                return null;//enemy has one of these pieces, so it can't be a victory.
            }
        }
        if (myPlaces == 2 && openSquare != null) {
            return openSquare;
        } else {
            return null; //This should absolutely never happen.
        }
    }

    public Square winningMoveInDiagonal(Game game, boolean topLeftToBottomRight, Square.Mark mark) {
        Square openSquare = null;
        int myPlaces = 0;
        for (int j = 0; j<= 2; j++) {
            Square piece;
            if (topLeftToBottomRight) {
                piece = game.getAt(j,j);
            } else {
                piece = game.getAt(j,2-j);
            }
            if (piece.mark == Square.Mark.BLANK) {
                if (openSquare != null) {
                    openSquare = piece;
                } else {
                    return null;//no winning move;
                }
            } else if (piece.mark == mark) {
                myPlaces++;//it's one of mine
            } else {
                return null;//enemy has one of these pieces, so it can't be a victory.
            }
        }
        if (myPlaces == 2 && openSquare != null) {
            return openSquare;
        } else {
            return null; //This should absolutely never happen.
        }
    }
}