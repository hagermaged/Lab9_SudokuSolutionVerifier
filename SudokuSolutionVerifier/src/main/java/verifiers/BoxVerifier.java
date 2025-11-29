package verifiers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.SudokuBoard;

public class BoxVerifier implements Verifier {

    @Override
    public List<VerificationResult> verify(SudokuBoard board) {
        List<VerificationResult> errors = new ArrayList<>();
        
        for (int boxIndex = 0; boxIndex < 9; boxIndex++) {
            Map<Integer, List<Integer>> valuePositions = new HashMap<>();
            
            int startRow = (boxIndex / 3) * 3;
            int startCol = (boxIndex % 3) * 3;
            
            // Collect all positions for each value in the box
            for (int i = startRow; i < startRow + 3; i++) {
                for (int j = startCol; j < startCol + 3; j++) {
                    int value = board.getValue(i, j);
                    if (value != 0) { // Only track non-zero values
                        int positionInBox = getPositionInBox(i, j, startRow, startCol);
                        valuePositions.computeIfAbsent(value, k -> new ArrayList<>()).add(positionInBox);
                    }
                }
            }
            
            // Report duplicates
            for (Map.Entry<Integer, List<Integer>> entry : valuePositions.entrySet()) {
                if (entry.getValue().size() > 1) {
                    errors.add(new VerificationResult("BOX", boxIndex + 1, entry.getKey(), entry.getValue()));
                }
            }
        }
        return errors;
    }
    
    private int getPositionInBox(int row, int col, int startRow, int startCol) {
        int boxRow = row - startRow;
        int boxCol = col - startCol;
        return boxRow * 3 + boxCol + 1; // 1-indexed position in box
    }
}