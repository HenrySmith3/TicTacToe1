/**
 * Represents a single square. Strictly speaking this class isn't necessary, but it avoids a lot of
 * things like string checking and ugly passing of parameters.
 */
public class Square {
    public enum Mark {X, O,BLANK};
    public int x;
    public int y;
    public Mark mark;

    public Square(int x, int y) {
        this.x = x;
        this.y = y;
        this.mark = Mark.BLANK;
    }

    @Override
    public String toString() {
        if (mark == Mark.X) {
            return "X";
        } else if (mark == Mark.O) {
            return "O";
        } else {
            return " ";
        }
    }
}
