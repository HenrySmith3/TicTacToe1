import java.util.ArrayList;

/**
 * Represents the state of the game.
 */
public class Game {
    //on the board, [0,0] would be the top left.
    private Square[][] board;
    public int turn;
    public GameKnowledge gameKnowledge;
    public Game() {
        board = new Square[3][3];
        for (int x = 0; x <= 2; x++) {
            for (int y = 0; y <= 2; y++) {
                board[x][y] = new Square(x,y);
                if ((x == 0 || x == 2) && (y == 0 || y == 2)) {
                    board[x][y].type = Square.Type.CORNER;
                } else if (x == 1 && y == 1) {
                    board[x][y].type = Square.Type.CENTER;
                } else {
                    board[x][y].type = Square.Type.SIDE;
                }
            }
        }
        gameKnowledge = new GameKnowledge(board, 3);
    }

    public void makeMove(Square square, Square.Mark mark) {
        board[square.x][square.y].mark = mark;
    }

    public boolean isValidMove (Square square) {
        return board[square.x][square.y].mark == Square.Mark.BLANK;
    }

    public Square getAt(int x, int y) {
        return board[x][y];
    }

    public ArrayList<Square> squaresWithMark(Square.Mark mark) {
        ArrayList<Square> squares = new ArrayList<Square>();
        for (int x = 0; x <= 2; x++) {
            for (int y = 0; y <= 2; y++) {
                if (board[x][y].mark == mark) {
                    squares.add(board[x][y]);
                }
            }
        }
        return squares;
    }

    @Override
    public String toString() {
        //Print out the game state
        String retVal = "\n";
        for (int y = 0; y <= 2; y++) {
            for (int x = 0; x <= 2; x++) {
                retVal += board[x][y].toString() + "|";
            }
            if (y != 2) {//don't need this row for the last part.
                retVal += "\n-----\n";
            }
        }
        retVal += "\n";
        return retVal;
    }

    /**
     * returns true if the game is done, false otherwise.
     */
    public boolean isFinished() {
        if (getWinner() != Square.Mark.BLANK) {
            return true; //no three in a row.
        }
        for (int x = 0; x <= 2; x++) {
            for (int y = 0; y <= 2; y++) {
                if (board[x][y].mark == Square.Mark.BLANK) {
                    return false;//still a valid move to make.
                }
            }
        }
        return true;
    }

    /*
    returns the mark of the winning player, or blank if neither player has won.
     */
    public Square.Mark getWinner() {
        for (int i = 0; i <=2; i++) {
            if (checkColumn(i) != Square.Mark.BLANK) {
                return checkColumn(i);
            }
            if (checkRow(i) != Square.Mark.BLANK) {
                return checkRow(i);
            }
        }
        if (checkDiagonal(true) != Square.Mark.BLANK) {
            return checkDiagonal(true);
        }
        if (checkDiagonal(false) != Square.Mark.BLANK) {
            return checkDiagonal(false);
        }
        return Square.Mark.BLANK;
    }

    /*
    returns the mark if all three in the column are the same, or blank if no winner.
     */
    private Square.Mark checkColumn(int i) {
        Square.Mark goal;
        if (board[0][i].mark == Square.Mark.BLANK) {
            return Square.Mark.BLANK;
        }
        goal = board[0][i].mark;
        if (board[1][i].mark == goal && board[2][i].mark == goal) {
            return goal;
        } else {
            return Square.Mark.BLANK;
        }
    }

    /*
    returns the mark if all three in the row are the same, or blank if no winner.
     */
    private Square.Mark checkRow(int i) {
        Square.Mark goal;
        if (board[i][0].mark == Square.Mark.BLANK) {
            return Square.Mark.BLANK;
        }
        goal = board[i][0].mark;
        if (board[i][1].mark == goal && board[i][2].mark == goal) {
            return goal;
        } else {
            return Square.Mark.BLANK;
        }
    }


    /*
    returns the mark if all three in the diagonal are the same, or blank if no winner.
    True is for top left to bottom right.
     */
    private Square.Mark checkDiagonal(boolean topLeftToBottomRight) {
        Square.Mark goal;
        if (centerPiece().mark == Square.Mark.BLANK) {
            return Square.Mark.BLANK;
        }
        goal = centerPiece().mark;
        if (topLeftToBottomRight) {
            if (board[0][0].mark == goal && board[2][2].mark == goal) {
                return goal;
            }
        } else {
            if (board[0][2].mark == goal && board[2][0].mark == goal) {
                return goal;
            }
        }
        return Square.Mark.BLANK;
    }

    /**
     * Tests if a given square is in the winning play.
     */
    public boolean inWinningCombo(Square square) {
        if (!isFinished()) {
            return false;
        }
        int x = square.x;
        int y = square.y;
        ArrayList<Square> squares = squaresWithMark(square.mark);
        //no matter the type, it could be a horizontal or vertical win.
        if (board[x][0].mark == square.mark && board[x][1].mark == square.mark && board[x][2].mark == square.mark) {
            return true;
        }
        if (board[0][y].mark == square.mark && board[1][y].mark == square.mark && board[2][y].mark == square.mark) {
            return true;
        }
        //for center or corner, have to check diagonals.
        if (square.type == Square.Type.CENTER || (square.x == 0 && square.y == 0) || (square.x == 2 && square.y == 2)) {
            if (checkDiagonal(true) == square.mark) {
                return true;
            }
        }
        if (square.type == Square.Type.CENTER || (square.x == 2 && square.y == 0) || (square.x == 0 && square.y == 2)) {
            if (checkDiagonal(false) == square.mark) {
                return true;
            }
        }
        return false;
    }

    public Square centerPiece() {
        return board[1][1];
    }

    public Square[] corners() {
        return new Square[]{board[0][0], board[0][2], board[2][0], board[2][2]};
    }

    public Square[] sides() {
        return new Square[]{board[1][2], board[2][1], board[0][1], board[1][0]};
    }
}
