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
public class RowVerifier implements Verifier {

    @Override
    public List<VerificationResult> verify(SudokuBoard board) {
        List<VerificationResult> errors = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            Set<Integer> seen;
            seen = new HashSet<>();
            for (int j = 0; j < 9; j++) {
                int value = board.getValue(i, j);
                if (value >= 1 && value <= 9) {
                    if (seen.contains(value)) {
                        errors.add(new VerificationResult("row", i, value, i, j));
                    } else {
                        seen.add(value);
                    }

                }
            }

        }
        return errors;
    }

}
