
public class Reason {
    public Square move;
    public String justification;

    public Reason(Square move, String justification) {
        this.move = move;
        this.justification = justification;
    }

    @Override
    public String toString() {
        return "I moved to square (" + move.x  + "," + move.y + ") because " + justification + "\n";
    }
}
