import java.util.ArrayList;
import java.util.List;

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

    public List<Reason> getReasonsForTurn(int desiredTurn) {
        List<Reason> retVal = new ArrayList<Reason>();
        int turn = 1;
        for (Reason reason : reasons) {
            if (turn != desiredTurn) {
                if (null != reason.move) {
                    turn++;//do nothing, just advance to next turn.
                }
            } else {
                //now we're on the right turn.
                retVal.add(reason);
                if (null != reason.move) {//the end of this turn's knowledge.
                    return retVal;
                }
            }
        }
        return retVal;
    }

}
