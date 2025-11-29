package verifiers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import main.SudokuBoard;

public class RowVerifier implements Verifier {

    @Override
    public List<VerificationResult> verify(SudokuBoard board) {
        List<VerificationResult> errors = new ArrayList<>();

        for (int row = 0; row < 9; row++) {
            Set<Integer> seen = new HashSet<>();
            List<Integer> duplicatePositions = new ArrayList<>();
            int duplicateValue = -1;
            
            for (int col = 0; col < 9; col++) {
                int value = board.getValue(row, col);
                if (seen.contains(value)) {
                    if (duplicateValue == -1) duplicateValue = value;
                    duplicatePositions.add(col + 1); 
                } else {
                    seen.add(value);
                }
            }
            
            if (!duplicatePositions.isEmpty()) {
                errors.add(new VerificationResult("ROW", row + 1, duplicateValue, duplicatePositions));
            }
        }
        return errors;
    }
}