import java.util.ArrayList;

public class GameKnowledge {

    public int boardWidth, boardHeight, piecesInARowOrColumnToWin;
    public int[][] positionValues;
    private Square[][] board;
    public GameKnowledge(Square[][] board, int piecesInARowOrColumnToWin) {
        this.board = board;
        this.piecesInARowOrColumnToWin = piecesInARowOrColumnToWin;
        this.boardHeight = board.length;
        this.boardWidth = board[0].length;
        positionValues = new int[boardWidth][boardHeight];
        for (int i = 0; i < boardWidth; i++) {
            for (int j = 0; j < boardWidth; j++) {
                positionValues[i][j] = 0; //default value
            }
        }
        //corners are more valuable, but all equal to each other
        positionValues[0][0] = 10;
        positionValues[boardWidth-1][0] = 10;
        positionValues[0][boardHeight-1] = 10;
        positionValues[boardWidth-1][boardHeight-1] = 10;

        //center is very valuable
        positionValues[boardWidth/2][boardHeight/2] = 20;
    }

    public Square mostValuableEmptySquare() {
        int highestValue = -10;
        Square bestSquare = null;
        ArrayList<Square> possibleChoices = new ArrayList<Square>();
        for (int i = 0; i < boardWidth; i++) {
            for (int j = 0; j < boardWidth; j++) {
                if (board[i][j].mark.equals(Square.Mark.BLANK)) {
                    if (positionValues[i][j] > highestValue) {
                        highestValue = positionValues[i][j];
                        bestSquare = board[i][j];
                        possibleChoices.clear();
                        possibleChoices.add(board[i][j]);
                    } else if (positionValues[i][j] == highestValue) {
                        possibleChoices.add(board[i][j]);
                    }
                }
            }
        }
        return bestSquare;
    }
}
