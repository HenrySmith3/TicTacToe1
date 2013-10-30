import javax.swing.*;

public class Main {

    public enum Question{WHY, WHICHACTION};

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
        game.turn = 1;
        while (!game.isFinished()) {
            System.out.println("Turn " + game.turn + ":");
            game.turn++;
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
        userQuestions(game, player1, player2);
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

    /**
     * Allows the user to ask questions to the players about why they made the decisions they did.
     * @param game The game that was being played.
     * @param p1 Player 1, the X player
     * @param p2 Player 2, the O player
     */
    public static void userQuestions(Game game, Player p1, Player p2) {
        while (true) {
            Player selectedPlayer = p2;
            if (selectWhichPlayer() == 'X') {
                selectedPlayer = p1;
            }
            int turn = selectWhichTurn(game, selectedPlayer);
            Question question = selectWhichQuestion(turn);
            if (!displayAnswer(selectedPlayer.answerQuestion(question, turn))) {
                break;
            }
        }

    }

    /**
     * Asks the user which player the question is about.
     * @return X if player1, O if player2
     */
    public static char selectWhichPlayer() {
        JRadioButton xButton = new JRadioButton("Player 1, X");
        JRadioButton yButton = new JRadioButton("Player 2, O");
        ButtonGroup group = new ButtonGroup();
        xButton.setSelected(true);
        group.add(xButton);
        group.add(yButton);
        final JComponent[] inputs = new JComponent[] {
                new JLabel("Which player is this question to?"),
                xButton, yButton
        };
        JOptionPane.showMessageDialog(null, inputs, "Select Player", JOptionPane.PLAIN_MESSAGE);
        if (xButton.isSelected()) {
            return 'X';
        } else {
            return 'Y';
        }
    }

    /**
     * Asks the user which turn the question is about.
     * @param game The game that was played. We only need to know the number of turns from it.
     * @param player the player that this is being asked to.
     * @return An int describing which turn this is about.
     */
    public static int selectWhichTurn(Game game, Player player) {
        JTextField inputField = new JTextField();
        int maxTurn = game.turn/2;
        if (player.getSymbol() == Square.Mark.O) {
            maxTurn--;//Poor design, but it works, so whatever.
        }
        final JComponent[] inputs = new JComponent[] {
                new JLabel("Which turn is this about? Please enter a number between 1 and " + maxTurn),
                inputField
        };
        JOptionPane.showMessageDialog(null, inputs, "Select Turn", JOptionPane.PLAIN_MESSAGE);
        try {
            return Integer.parseInt(inputField.getText());
        } catch (Exception e) {
            return selectWhichTurn(game, player);
        }
    }

    /**
     * Shows the user a list of questions they can ask the player.
     * @param turn Which turn this concerns. This lets us use ordinal numbers.
     * @return The Question that they are asking.
     */
    public static Question selectWhichQuestion(int turn) {
        JRadioButton whichButton = new JRadioButton("What move did you make on your " +
                ThoughtfulPlayer.numberToOrdinal(turn) + " turn?");
        JRadioButton whyButton = new JRadioButton("Why did you make that move on your " +
                ThoughtfulPlayer.numberToOrdinal(turn) + " turn?");
        ButtonGroup group = new ButtonGroup();
        whichButton.setSelected(true);
        group.add(whichButton);
        group.add(whyButton);
        final JComponent[] inputs = new JComponent[] {
                new JLabel("What question would you like to ask?"),
                whichButton, whyButton
        };
        JOptionPane.showMessageDialog(null, inputs, "Select Question", JOptionPane.PLAIN_MESSAGE);
        if (whichButton.isSelected()) {
            return Question.WHICHACTION;
        } else {
            return Question.WHY;
        }
    }

    /**
     * Displays the answer and asks if the user wants to ask another question.
     * @param answer The String that the player gave as their answer.
     * @return true if they want to ask again, false otherwise.
     */
    public static boolean displayAnswer(String answer) {
        JRadioButton yesButton = new JRadioButton("Yes");
        JRadioButton noButton = new JRadioButton("No");
        ButtonGroup group = new ButtonGroup();
        yesButton.setSelected(true);
        group.add(yesButton);
        group.add(noButton);
        final JComponent[] inputs = new JComponent[] {
                new JLabel("The player responded: \"" + answer + "\"."),
                new JLabel("Would you like to ask another question? "),
                yesButton, noButton
        };
        JOptionPane.showMessageDialog(null, inputs, "Ask Again?", JOptionPane.PLAIN_MESSAGE);
        if (yesButton.isSelected()) {
            return true;
        } else {
            return false;
        }
    }
}
