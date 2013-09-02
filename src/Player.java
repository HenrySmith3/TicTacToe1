/**
 * Created with IntelliJ IDEA.
 * User: Henry
 * Date: 9/2/13
 * Time: 3:31 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Player {
    public final Square.Mark mySymbol;
    public Player(){this.mySymbol = Square.Mark.BLANK;}//unused
    public Player(Square.Mark mySymbol) {
        this.mySymbol = mySymbol;
    }

    public Square.Mark getSymbol() {
        return mySymbol;
    }
    public abstract Square makeMove(Game game);
}
