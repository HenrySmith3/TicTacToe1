public class Main {

    public static void main(String[] args) throws Exception{
	    Game game = new Game();
        for (String arg : args) {System.out.println(arg);}
        if (args.length != 2 || args[0].length() != 1 || args[1].length() != 1) {
            args = new String[]{"H", "T"};
        }
        System.out.print(game);
        Player player1 = makePlayer(args[0], Square.Mark.X);
        Player player2 = makePlayer(args[1], Square.Mark.O);
        Player currentPlayer = player1;
        while (!game.isFinished()) {
            Square move = currentPlayer.makeMove(game);
            if (!game.isValidMove(move)) {
                throw new Exception("Not a valid move: " + move);
            }
            game.makeMove(move, currentPlayer.getSymbol());
            System.out.println(game);
            currentPlayer = currentPlayer.equals(player1) ? player2 : player1;
        }
        if (game.getWinner() == Square.Mark.BLANK) {
            System.out.println("Stalemate, nobody wins");
        } else {
            System.out.println("Winner: " + game.getWinner());
        }
    }

    public static Player makePlayer(String type, Square.Mark mark) {
        if (type.equalsIgnoreCase("H")) {
            return new HumanPlayer(mark);
        } else if (type.equalsIgnoreCase("N")) {
            return new NaivePlayer(mark);
        } else {
            return new ThoughtfulPlayer(mark);
        }
    }
}
