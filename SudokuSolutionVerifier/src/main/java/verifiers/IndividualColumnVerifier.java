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
public class IndividualColumnVerifier implements Verifier{
     private int columnNumber;

    public IndividualColumnVerifier(int colnum) {
        this.columnNumber = colnum;
    }

    @Override
    public List<VerificationResult> verify(SudokuBoard board) {
        List<VerificationResult> errors = new ArrayList<>();
        Set<Integer> seen = new HashSet<>();
        List<Integer> duplicatePositions = new ArrayList<>();
        int duplicateValue = -1;
        
        for (int row = 0; row < 9; row++) {
            int value = board.getValue(row, columnNumber);
            if (seen.contains(value)) {
                if (duplicateValue == -1) duplicateValue = value;
                duplicatePositions.add(row + 1);
            } else {
                seen.add(value);
            }
        }
        
        if (!duplicatePositions.isEmpty()) {
            errors.add(new VerificationResult("COL", columnNumber + 1, duplicateValue, duplicatePositions));
        }
        
        return errors;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    

    
}
