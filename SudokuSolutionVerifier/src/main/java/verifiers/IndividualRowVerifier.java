/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package verifiers;

import java.util.List;
import main.SudokuBoard;

/**
 *
 * @author orignal store
 */
public class IndividualRowVerifier implements Verifier {
    private int rowNumber;

    public IndividualRowVerifier(int rownum) {
        this.rowNumber = rownum;
    }

    @Override
    public List<VerificationResult> verify(SudokuBoard board) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int getRowNumber() {
        return rowNumber;
    }

 
    
}
