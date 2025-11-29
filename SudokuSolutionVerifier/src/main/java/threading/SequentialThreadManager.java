package threading;

import java.util.ArrayList;
import java.util.List;

import main.SudokuBoard;
import verifiers.VerificationResult;
import verifiers.Verifier;

public class SequentialThreadManager implements ThreadManager {
    
    @Override
    public List<VerificationResult> execute(List<Verifier> verifiers, SudokuBoard board) {
        List<VerificationResult> allErrors = new ArrayList<>();
        
        System.out.println("Mode 0: Running sequentially in main thread");
        System.out.println("Execution order: Row Verifier → Column Verifier → Box Verifier");
        
        for (int i = 0; i < verifiers.size(); i++) {
            Verifier verifier = verifiers.get(i);
            String verifierName = getVerifierName(verifier);
            
            System.out.println("Executing " + verifierName + "...");
            List<VerificationResult> results = verifier.verify(board);
            allErrors.addAll(results);
            
            System.out.println("Completed " + verifierName + " with " + results.size() + " errors");
        }
        
        System.out.println("Mode 0: Sequential execution completed. Total errors: " + allErrors.size());
        return allErrors;
    }
    
    private String getVerifierName(Verifier verifier) {
        if (verifier instanceof verifiers.RowVerifier) return "Row Verifier";
        if (verifier instanceof verifiers.ColumnVerifier) return "Column Verifier";
        if (verifier instanceof verifiers.BoxVerifier) return "Box Verifier";
        return "Unknown Verifier";
    }
}