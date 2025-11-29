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
 * @author orignal store
 */
public class IndividualBoxVerifier implements Verifier {
     private int boxNumber;

    public IndividualBoxVerifier(int boxnum) {
        this.boxNumber = boxnum;
    }

    @Override
    public List<VerificationResult> verify(SudokuBoard board) {
        List<VerificationResult> errors = new ArrayList<>();
        Set<Integer> seen = new HashSet<>();
        List<Integer> positions = new ArrayList<>();
        
        int startRow = (boxNumber / 3) * 3;
        int startCol = (boxNumber % 3) * 3;
        int duplicateValue = -1;
        
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                int value = board.getValue(i, j);
                if (seen.contains(value)) {
                    if (duplicateValue == -1) duplicateValue = value;
                    positions.add(getPositionInBox(i, j, startRow, startCol));
                } else {
                    seen.add(value);
                }
            }
        }
        
        if (!positions.isEmpty()) {
            errors.add(new VerificationResult("BOX", boxNumber + 1, duplicateValue, positions));
        }
        
        return errors;
    }

    private int getPositionInBox(int row, int col, int startRow, int startCol) {
        int boxRow = row - startRow;
        int boxCol = col - startCol;
        return boxRow * 3 + boxCol + 1; // 1-indexed position in box
    }   

    public int getBoxNumber() {
        return boxNumber;
    }

 
}
