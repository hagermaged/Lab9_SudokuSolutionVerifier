/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import factory.VerifierFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import verifiers.*;

/**
 *
 * @author Hajer1
 */
public class SudokuVerifier {
 public static void main(String[] args) {
       Scanner scanner = VerifierFactory.createScanner();
        
        try {
            // Get file path from user
            System.out.print("Enter Sudoku CSV file path: ");
            String filePath = scanner.nextLine();
            
            // Get mode from user
            System.out.print("Enter mode (0, 3, or 27): ");
            int mode = scanner.nextInt();
            
            // Validate mode
            if (mode != 0 && mode != 3 && mode != 27) {
                System.out.println("Invalid mode. Use 0, 3, or 27.");
                return;
            }
            
            // Execute verification using Factory-created objects
            executeVerification(filePath, mode);
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close the factory-created scanner
            scanner.close();
        }
}
 
   private static void executeVerification(String filePath, int mode) {
        // Use Factory to create SudokuBoard
        SudokuBoard board = VerifierFactory.createSudokuBoard(filePath);
        
        // Show factory configuration
        System.out.println("\n" + VerifierFactory.getFactoryDescription(mode));
        
        switch (mode) {
            case 0:
                runSequential(board, mode);
                break;
            case 3:
                System.out.println("Mode 3 (4 threads) - Running in sequential fallback mode");
                runSequential(board, mode);
                break;
            case 27:
                System.out.println("Mode 27 (28 threads) - Running in sequential fallback mode");
                runSequential(board, mode);
                break;
        }
    }
    
    private static void runSequential(SudokuBoard board, int mode) {
        // Use Factory to create all verifiers for the specified mode
        List<Verifier> verifiers = VerifierFactory.createVerifiersForMode(mode);
        
        // Display what was created
        System.out.println("Created " + verifiers.size() + " verifier(s):");
        for (int i = 0; i < verifiers.size(); i++) {
            Verifier verifier = verifiers.get(i);
            System.out.println("  " + (i+1) + ". " + getVerifierDescription(verifier));
        }
        System.out.println();
        
        // Execute all verifiers sequentially
        List<VerificationResult> allErrors = new ArrayList<>();
        for (Verifier verifier : verifiers) {
            List<VerificationResult> errors = verifier.verify(board);
            allErrors.addAll(errors);
        }
        
        printResults(allErrors);
    }
        private static String getVerifierDescription(Verifier verifier) {
        if (verifier instanceof verifiers.RowVerifier) return "Comprehensive Row Verifier";
        if (verifier instanceof verifiers.ColumnVerifier) return "Comprehensive Column Verifier";
        if (verifier instanceof verifiers.BoxVerifier) return "Comprehensive Box Verifier";
        if (verifier instanceof verifiers.IndividualRowVerifier) {
            int rowNum = ((verifiers.IndividualRowVerifier) verifier).getRowNumber() + 1;
            return "Individual Row Verifier (Row " + rowNum + ")";
        }
        if (verifier instanceof verifiers.IndividualColumnVerifier) {
            int colNum = ((verifiers.IndividualColumnVerifier) verifier).getColumnNumber() + 1;
            return "Individual Column Verifier (Column " + colNum + ")";
        }
        if (verifier instanceof verifiers.IndividualBoxVerifier) {
            int boxNum = ((verifiers.IndividualBoxVerifier) verifier).getBoxNumber() + 1;
            return "Individual Box Verifier (Box " + boxNum + ")";
        }
        return "Unknown Verifier Type";
    }
            private static void printResults(List<VerificationResult> allErrors) {
        if (allErrors.isEmpty()) {
            System.out.println("VALID");
        } else {
            System.out.println("INVALID\n");
            
            // Separate errors by type for better formatting
            List<VerificationResult> rowErrors = new ArrayList<>();
            List<VerificationResult> colErrors = new ArrayList<>();
            List<VerificationResult> boxErrors = new ArrayList<>();
            
            for (VerificationResult error : allErrors) {
                if (error.getType().startsWith("ROW")) rowErrors.add(error);
                else if (error.getType().startsWith("COL")) colErrors.add(error);
                else if (error.getType().startsWith("BOX")) boxErrors.add(error);
            }
            
            // Print errors with proper sections
            if (!rowErrors.isEmpty()) {
                for (VerificationResult error : rowErrors) {
                    System.out.println(error);
                }
            }
            
            if (!colErrors.isEmpty()) {
                if (!rowErrors.isEmpty()) System.out.println("------------------------------------------");
                for (VerificationResult error : colErrors) {
                    System.out.println(error);
                }
            }
            
            if (!boxErrors.isEmpty()) {
                if (!rowErrors.isEmpty() || !colErrors.isEmpty()) System.out.println("------------------------------------------");
                for (VerificationResult error : boxErrors) {
                    System.out.println(error);
                }
            }
        }
    }
}
