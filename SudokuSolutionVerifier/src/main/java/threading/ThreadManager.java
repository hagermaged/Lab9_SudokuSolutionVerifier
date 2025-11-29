package threading;

import java.util.List;

import main.SudokuBoard;
import verifiers.VerificationResult;
import verifiers.Verifier;

public interface ThreadManager {
    List<VerificationResult> execute(List<Verifier> verifiers, SudokuBoard board);
}