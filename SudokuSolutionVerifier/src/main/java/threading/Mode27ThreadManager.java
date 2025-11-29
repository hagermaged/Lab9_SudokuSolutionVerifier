package threading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import main.SudokuBoard;
import verifiers.VerificationResult;
import verifiers.Verifier;

public class Mode27ThreadManager implements ThreadManager {
    
    @Override
    public List<VerificationResult> execute(List<Verifier> verifiers, SudokuBoard board) {
        List<VerificationResult> allErrors = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(28);
        List<Future<List<VerificationResult>>> futures = new ArrayList<>();
        
        System.out.println("Mode 27: Starting 28 threads (main + 27 individual verifier threads)");
        System.out.println("Thread Pool: 9 row verifiers + 9 column verifiers + 9 box verifiers");
        
        try {
            for (int i = 0; i < verifiers.size(); i++) {
                final int taskIndex = i;
                Verifier verifier = verifiers.get(i);
                
                Callable<List<VerificationResult>> task = () -> {
                    String threadName = Thread.currentThread().getName();
                    String verifierInfo = getIndividualVerifierInfo(verifier, taskIndex);
                    System.out.println("Thread " + threadName + " executing " + verifierInfo);
                    
                    List<VerificationResult> results = verifier.verify(board);
                    
                    System.out.println("Thread " + threadName + " completed " + verifierInfo + 
                                     " with " + results.size() + " errors");
                    return results;
                };
                
                futures.add(executor.submit(task));
            }
            
            System.out.println("\nCollecting results from 27 individual verifier threads...");
            int completedTasks = 0;
            
            for (int i = 0; i < futures.size(); i++) {
                try {
                    Future<List<VerificationResult>> future = futures.get(i);
                    List<VerificationResult> results = future.get();
                    allErrors.addAll(results);
                    
                    completedTasks++;
                    Verifier verifier = verifiers.get(i);
                    String verifierInfo = getIndividualVerifierInfo(verifier, i);
                    
                    System.out.println("[" + completedTasks + "/27] Collected from " + verifierInfo + 
                                     ": " + results.size() + " errors");
                    
                } catch (InterruptedException e) {
                    System.err.println("Thread was interrupted while waiting for task " + i);
                    Thread.currentThread().interrupt();
                } catch (ExecutionException e) {
                    System.err.println("Error executing task " + i + ": " + e.getCause().getMessage());
                }
            }
            
        } finally {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                    System.out.println("Forcing shutdown of unfinished threads...");
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
        
        System.out.println("Mode 27: All 27 threads completed. Total errors found: " + allErrors.size());
        return allErrors;
    }
    
    private String getIndividualVerifierInfo(Verifier verifier, int index) {
        if (verifier instanceof verifiers.IndividualRowVerifier) {
            int rowNum = ((verifiers.IndividualRowVerifier) verifier).getRowNumber() + 1;
            return "Row " + rowNum + " Verifier";
        }
        if (verifier instanceof verifiers.IndividualColumnVerifier) {
            int colNum = ((verifiers.IndividualColumnVerifier) verifier).getColumnNumber() + 1;
            return "Column " + colNum + " Verifier";
        }
        if (verifier instanceof verifiers.IndividualBoxVerifier) {
            int boxNum = ((verifiers.IndividualBoxVerifier) verifier).getBoxNumber() + 1;
            return "Box " + boxNum + " Verifier";
        }
        return "Unknown Individual Verifier";
    }
}