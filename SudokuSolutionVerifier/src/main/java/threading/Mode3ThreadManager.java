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

public class Mode3ThreadManager implements ThreadManager {
    
    @Override
    public List<VerificationResult> execute(List<Verifier> verifiers, SudokuBoard board) {
        List<VerificationResult> allErrors = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(4);
        List<Future<List<VerificationResult>>> futures = new ArrayList<>();
        
        System.out.println("Mode 3: Starting 4 threads (main + 3 verifier threads)");
        System.out.println("Thread Pool: 3 comprehensive verifiers running in parallel");
        
        try {
            for (int i = 0; i < verifiers.size(); i++) {
                Verifier verifier = verifiers.get(i);
                Callable<List<VerificationResult>> task = () -> {
                    String threadName = Thread.currentThread().getName();
                    System.out.println("Thread " + threadName + " executing " + getVerifierName(verifier));
                    List<VerificationResult> results = verifier.verify(board);
                    System.out.println("Thread " + threadName + " completed " + getVerifierName(verifier) + 
                                     " with " + results.size() + " errors");
                    return results;
                };
                futures.add(executor.submit(task));
            }
            
            System.out.println("\nCollecting results from all threads...");
            for (int i = 0; i < futures.size(); i++) {
                try {
                    Future<List<VerificationResult>> future = futures.get(i);
                    List<VerificationResult> results = future.get();
                    allErrors.addAll(results);
                    
                    Verifier verifier = verifiers.get(i);
                    System.out.println("Collected results from " + getVerifierName(verifier) + 
                                     ": " + results.size() + " errors");
                    
                } catch (InterruptedException e) {
                    System.err.println("Thread was interrupted while waiting for: " + 
                                     getVerifierName(verifiers.get(i)));
                    Thread.currentThread().interrupt();
                } catch (ExecutionException e) {
                    System.err.println("Error executing " + getVerifierName(verifiers.get(i)) + 
                                     ": " + e.getCause().getMessage());
                }
            }
            
        } finally {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                    System.out.println("Forcing shutdown of unfinished threads...");
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
        
        System.out.println("Mode 3: All threads completed. Total errors found: " + allErrors.size());
        return allErrors;
    }
    
    private String getVerifierName(Verifier verifier) {
        if (verifier instanceof verifiers.RowVerifier) return "Row Verifier";
        if (verifier instanceof verifiers.ColumnVerifier) return "Column Verifier";
        if (verifier instanceof verifiers.BoxVerifier) return "Box Verifier";
        return "Unknown Verifier";
    }
}