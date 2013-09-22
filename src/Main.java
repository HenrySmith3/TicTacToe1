public class Main {

    /**
     * Uncomment the code in this method to enable playing many games to get statistics on how well an agent does.
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{
        //for (String arg : args) {System.out.println(arg);}
        if (args.length != 2 || args[0].length() != 1 || args[1].length() != 1) {
            args = new String[]{"N", "T"};
        }
        Player player1 = makePlayer(args[0], Square.Mark.X);
        Player player2 = makePlayer(args[1], Square.Mark.O);
        playGame(player1, player2);
//        int xWins = 0;
//        int oWins = 0;
//        int tries = 1000000;
//        for (int i = 0; i < tries; i++) {
//            Square.Mark winner = playGame(player1, player2);
//            if (winner == Square.Mark.O) {
//                oWins++;
//            } else if (winner == Square.Mark.X) {
//                xWins++;
//            }
//        }
//        System.out.println("X won: " + xWins);
//        System.out.println("O won: " + oWins);
//        System.out.println("Stalemates: " + (tries - xWins - oWins));
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
        System.out.println("\n" + player1.mySymbol + "'s reasoning:\n");
        System.out.println(player1.reasoning.printReasons());
        System.out.println("\n" + player2.mySymbol + "'s reasoning:\n");
        System.out.println(player2.reasoning.printReasons());
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
