public class Main {

    public static void main(String[] args) throws Exception{
        for (String arg : args) {System.out.println(arg);}
        if (args.length != 2 || args[0].length() != 1 || args[1].length() != 1) {
            args = new String[]{"N", "T"};
        }
        Player player1 = makePlayer(args[0], Square.Mark.X);
        Player player2 = makePlayer(args[1], Square.Mark.O);
        playGame(player1, player2);
    }

    /**
     * This is abstracted to another method so that I can use main to run long tests.
     * Like seeing how often Thoughful beats Naive over 10,000 plays or something.
     * @return The mark of the winning player.
     * @throws Exception
     */
    public static Square.Mark playGame(Player player1, Player player2) throws Exception {
        Game game = new Game();
        System.out.print(game);
        Player currentPlayer = player1;
        int turn = 1;
        while (!game.isFinished()) {
            System.out.println("Turn " + turn + ":");
            turn++;
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
        return game.getWinner();
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
