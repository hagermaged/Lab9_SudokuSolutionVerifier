/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package verifiers;
import java.util.List;
import main.SudokuBoard;
/**
 *
 * @author Hajer1
 */
public interface Verifier {
    
 List<VerificationResult>verify(SudokuBoard board);
}
