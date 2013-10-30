import javax.swing.*;

/**
 * This exists solely to test, and is very easy to break the game with.
 */
public class HumanPlayer extends Player{
    public HumanPlayer (Square.Mark mark) {
        super(mark);
    }
    @Override
    public Square makeMove(Game game) {
        JTextField xField = new JTextField();
        JTextField yField = new JTextField();
        final JComponent[] inputs = new JComponent[] {
                new JLabel("Please select a space. Ranges are from 0 to 2"),
                new JLabel("X: "),
                xField,
                new JLabel("Y: "),
                yField
        };
        JOptionPane.showMessageDialog(null, inputs, "Select Move", JOptionPane.PLAIN_MESSAGE);
        return new Square(Integer.parseInt(xField.getText()),
                Integer.parseInt(yField.getText()));
    }

    @Override
    public String answerQuestion(Main.Question question, int turn) {
        return "Ask the human who made the turn.";
    }
}
