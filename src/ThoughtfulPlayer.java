import java.util.ArrayList;
import java.util.List;

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
        List<StrategicKnowledge.MoveType> strategies = StrategicKnowledge.getStrategy(StrategicKnowledge.StrategyType.THOUGHTFUL);

        for (StrategicKnowledge.MoveType moveType : strategies) {
            reasoning.addReason(new Reason(null, "My strategic knowledge says that I should go for a " + moveType + " right now."));
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
            case WINNING_MOVE:
                return winningMove(game, mySymbol);
            case BLOCKING_MOVE:
                return winningMove(game, mySymbol == Square.Mark.X ? Square.Mark.O : Square.Mark.X);//opponent's symbol
            default:
                Square square = game.gameKnowledge.mostValuableEmptySquare();
                reasoning.addReason(new Reason(square, "I can't win this turn, and my opponent can't win next turn, so I'm just picking the most valuable square, which is this "
                        + square.type.toString() + " square."));
                return square;
        }
    }

    /**
     * Tests to see if there is a winning move for the given mark type.
     * Also adds the reasoning in.
     * @param game The game state.
     * @param mark Which player are we looking for a winnning move for?
     * @return null if no winning move exists, or a Square representing the winning move.
     */
    public Square winningMove(Game game, Square.Mark mark) {
        for (int i = 0; i <= game.gameKnowledge.boardWidth -1; i++) {
            if (winningMoveInColumn(game, i, mark) != null) {
                if (mark.equals(mySymbol)) {
                    reasoning.addReason(new Reason(winningMoveInColumn(game, i, mark), "there are already two of my pieces in the "
                        + numberToOrdinal(i) + " column so I can win by playing there."));
                } else {
                    reasoning.addReason(new Reason(winningMoveInColumn(game, i, mark), "there are already two of my opponent's pieces in the "
                            + numberToOrdinal(i) + " column so I have to play there to block him."));
                }
                return winningMoveInColumn(game, i, mark);
            }
        }
        for (int i = 0; i <= game.gameKnowledge.boardHeight -1; i++) {
            if (winningMoveInRow(game, i, mark) != null) {
                if (mark.equals(mySymbol)) {
                    reasoning.addReason(new Reason(winningMoveInRow(game, i, mark), "there are already two of my pieces in the "
                            + numberToOrdinal(i) + " row so I can win by playing there."));
                } else {
                    reasoning.addReason(new Reason(winningMoveInRow(game, i, mark), "there are already two of my opponent's pieces in the "
                            + numberToOrdinal(i) + " row so I have to play there to block him."));
                }
                return winningMoveInRow(game, i, mark);
            }
        }
        if (winningMoveInDiagonal(game, true, mark) != null) {
            if (mark.equals(mySymbol)) {
                reasoning.addReason(new Reason(winningMoveInDiagonal(game, true, mark), "there are already two of my pieces in the " +
                        "diagonal so I can win by playing there."));
            } else {
                reasoning.addReason(new Reason(winningMoveInDiagonal(game, true, mark), "there are already two of my opponent's pieces in the "
                        + "diagonal so I have to play there to block him."));
            }
            return winningMoveInDiagonal(game, true, mark);
        }
        if (winningMoveInDiagonal(game, false, mark) != null) {
            if (mark.equals(mySymbol)) {
                reasoning.addReason(new Reason(winningMoveInDiagonal(game, false, mark), "there are already two of my pieces in the " +
                        "diagonal so I can win by playing there."));
            } else {
                reasoning.addReason(new Reason(winningMoveInDiagonal(game, false, mark), "there are already two of my opponent's pieces in the "
                        + "diagonal so I have to play there to block him."));
            }
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
        if (myPlaces == game.gameKnowledge.piecesInARowOrColumnToWin - 1 && openSquare != null) {
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
        if (myPlaces == game.gameKnowledge.piecesInARowOrColumnToWin - 1 && openSquare != null) {
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
        if (myPlaces == game.gameKnowledge.piecesInARowOrColumnToWin - 1 && openSquare != null) {
            return openSquare;
        } else {
            return null; //This should absolutely never happen.
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
            if (reasons.size() == 6) {
                retVal += reasons.get(1).justification.replace(".", ", and ");
                retVal += reasons.get(3).justification.replace(".", ", but ");
                retVal += reasons.get(5).justification;
            } else if (reasons.size() == 4) {
                retVal += reasons.get(1).justification.replace(".", ", but ");
                retVal += reasons.get(3).justification;
            } else {
                retVal += reasons.get(reasons.size()-1).justification;
            }
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

    public static String numberToOrdinal(int i) {
        switch (i) {
            case 1:
                return "first";
            case 2:
                return "second";
            case 3:
                return "third";
            case 4:
                return "fourth";
            case 5:
                return "fifth";
            case 6:
                return "sixth";
            case 7:
                return "seventh";
            case 8:
                return "eighth";
            case 9:
                return "ninth";
            default:
                return "";
        }
    }
}
