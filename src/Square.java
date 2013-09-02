/**
 * Created with IntelliJ IDEA.
 * User: Henry
 * Date: 9/2/13
 * Time: 3:20 PM
 * To change this template use File | Settings | File Templates.
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
