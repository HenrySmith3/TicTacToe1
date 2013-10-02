import java.util.ArrayList;

public class PlayerReasoning {
    public ArrayList<Reason> reasons;

    public PlayerReasoning() {
        reasons = new ArrayList<Reason>();
    }

    public void addReason(Reason reason) {
        reasons.add(reason);
    }

    public String printReasons() {
        String retVal = "";
        int turn = 1;
        for (Reason reason : reasons) {
            if (null == reason.move) {//The move is null for strategic knowledge.
                retVal += reason.justification + "\n";
            } else {
                retVal += "On my " + ThoughtfulPlayer.numberToOrdinal(turn) + " turn, " + reason.toString() + "\n";
                turn++;
            }
        }
        return retVal;
    }
}
