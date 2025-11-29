package verifiers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import main.SudokuBoard;

public class ColumnVerifier implements Verifier {
    
    @Override
    public List<VerificationResult> verify(SudokuBoard board) {
        List<VerificationResult> errors = new ArrayList<>();

        for (int col = 0; col < 9; col++) {
            Set<Integer> seen = new HashSet<>();
            List<Integer> duplicatePositions = new ArrayList<>();
            int duplicateValue = -1;
            
            for (int row = 0; row < 9; row++) {
                int value = board.getValue(row, col);
                if (seen.contains(value)) {
                    if (duplicateValue == -1) duplicateValue = value;
                    duplicatePositions.add(row + 1); // 1-indexed
                } else {
                    seen.add(value);
                }
            }
            
            if (!duplicatePositions.isEmpty()) {
                errors.add(new VerificationResult("COL", col + 1, duplicateValue, duplicatePositions));
            }
        }
        return errors;
    }
}