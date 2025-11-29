package verifiers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.SudokuBoard;

public class RowVerifier implements Verifier {
    
    @Override
    public List<VerificationResult> verify(SudokuBoard board) {
        List<VerificationResult> errors = new ArrayList<>();

        for (int row = 0; row < 9; row++) {
            Map<Integer, List<Integer>> valuePositions = new HashMap<>();
            
            // Collect all positions for each value
            for (int col = 0; col < 9; col++) {
                int value = board.getValue(row, col);
                if (value != 0) { // Only track non-zero values
                    valuePositions.computeIfAbsent(value, k -> new ArrayList<>()).add(col + 1);
                }
            }
            
            // Report duplicates for values that appear more than once
            for (Map.Entry<Integer, List<Integer>> entry : valuePositions.entrySet()) {
                if (entry.getValue().size() > 1) {
                    errors.add(new VerificationResult("ROW", row + 1, entry.getKey(), entry.getValue()));
                }
            }
        }
        return errors;
    }
}