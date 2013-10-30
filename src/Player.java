/**
 * The abstract class to represent a player.
 * Subclasses will implement makeMove to show their behavior.
 */
public abstract class Player {
    public PlayerReasoning reasoning = new PlayerReasoning();
    public final Square.Mark mySymbol;
    public Player(){this.mySymbol = Square.Mark.BLANK;}//unused
    public Player(Square.Mark mySymbol) {
        this.mySymbol = mySymbol;
    }

    public Square.Mark getSymbol() {
        return mySymbol;
    }
    public abstract Square makeMove(Game game);

    public abstract String answerQuestion(Main.Question question, int turn, Game game);
}
