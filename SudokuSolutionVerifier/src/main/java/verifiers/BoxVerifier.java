/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package verifiers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import main.SudokuBoard;

/**
 *
 * @author Hajer1
 */
public class BoxVerifier implements Verifier {

      @Override
    public List<VerificationResult> verify(SudokuBoard board) {
        List<VerificationResult> errors = new ArrayList<>();
        
        for (int boxIndex = 0; boxIndex < 9; boxIndex++) {
            Set<Integer> seen = new HashSet<>();
            List<Integer> positions = new ArrayList<>();
            
            int startRow = (boxIndex / 3) * 3;
            int startCol = (boxIndex % 3) * 3;
            
            // Collect all positions where duplicates appear
            for (int i = startRow; i < startRow + 3; i++) {
                for (int j = startCol; j < startCol + 3; j++) {
                    int value = board.getValue(i, j);
                    if (seen.contains(value)) {
                        positions.add(getPositionInBox(i, j, startRow, startCol));
                    } else {
                        seen.add(value);
                    }
                }
            }
            
            // If duplicates found, add error with all positions
            if (!positions.isEmpty()) {
                int duplicateValue = findDuplicateValueInBox(board, startRow, startCol);
                errors.add(new VerificationResult("BOX", boxIndex + 1, duplicateValue, positions));
            }
        }
        return errors;
    }
    
    private int getPositionInBox(int row, int col, int startRow, int startCol) {
        int boxRow = row - startRow;
        int boxCol = col - startCol;
        return boxRow * 3 + boxCol + 1; // 1-indexed position in box
    }
    
    private int findDuplicateValueInBox(SudokuBoard board, int startRow, int startCol) {
        Set<Integer> seen = new HashSet<>();
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                int value = board.getValue(i, j);
                if (seen.contains(value)) {
                    return value;
                }
                seen.add(value);
            }
        }
        return -1;
    }


}
