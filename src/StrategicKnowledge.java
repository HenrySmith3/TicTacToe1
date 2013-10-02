import java.util.Arrays;
import java.util.List;

public class StrategicKnowledge {
    public enum StrategyType{THOUGHTFUL, NAIVE};

    public enum MoveType{
        WINNING_MOVE("winning move"),
        BLOCKING_MOVE("blocking move"),
        VALUABLE_MOVE("most valuable move");

        private String value;

        MoveType(String value) {
            this.value = value;
        }

        public String toString() {
            return this.value;
        }};
    public StrategicKnowledge.StrategyType strategyType;
    List<MoveType> moves;

    public static List<MoveType> getStrategy(StrategicKnowledge.StrategyType strategyType) {
        if (strategyType == StrategicKnowledge.StrategyType.THOUGHTFUL) {
            return Arrays.asList(MoveType.WINNING_MOVE, MoveType.BLOCKING_MOVE, MoveType.VALUABLE_MOVE);
        } else {
            return Arrays.asList(MoveType.VALUABLE_MOVE);
        }
    }
}
