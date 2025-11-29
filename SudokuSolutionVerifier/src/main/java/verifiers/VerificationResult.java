package verifiers;

import java.util.List;

public class VerificationResult {
    private final String type;
    private final int index;
    private final int duplicatedValue;
    private final List<Integer> positions;

    public VerificationResult(String type, int index, int duplicatedValue, List<Integer> positions) {
        this.type = type;
        this.index = index;
        this.duplicatedValue = duplicatedValue;
        this.positions = positions;
    }

    public String getType() {
        return type;
    }

    public int getIndex() {
        return index;
    }

    public int getDuplicatedValue() {
        return duplicatedValue;
    }

    public List<Integer> getPositions() {
        return positions;
    }

    @Override
    public String toString() {
        return String.format("%s %d, #%d, %s", 
            type, index, duplicatedValue, positions.toString());
    }
}