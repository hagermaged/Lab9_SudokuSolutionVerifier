package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import factory.VerifierFactory;
import threading.Mode27ThreadManager;
import threading.Mode3ThreadManager;
import threading.SequentialThreadManager;
import threading.ThreadManager;
import verifiers.VerificationResult;
import verifiers.Verifier;

/**
 *
 * @author Hajer1
 */
public class SudokuVerifier {
    
    public static void main(String[] args) {
        if (args.length == 2) {
            // JAR mode: java -jar app.jar filepath mode
            runJarMode(args);
        } else {
            // Interactive mode
            runInteractiveMode();
        }
    }
    
    private static void runJarMode(String[] args) {
        try {
            String filePath = args[0];
            int mode = Integer.parseInt(args[1]);
            
            // Validate mode
            if (mode != 0 && mode != 3 && mode != 27) {
                System.out.println("Invalid mode. Use 0, 3, or 27.");
                return;
            }
            
            // Execute verification
            executeVerification(filePath, mode);
            
        } catch (NumberFormatException e) {
            System.out.println("Error: Mode must be a number (0, 3, or 27)");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void runInteractiveMode() {
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
        try {
            // Use Factory to create SudokuBoard
            SudokuBoard board = VerifierFactory.createSudokuBoard(filePath);
            
            // Verify board was loaded successfully
            if (!board.isBoardLoaded()) {
                System.out.println("Error: Failed to load Sudoku board from " + filePath);
                return;
            }
            
            // Show factory configuration
            System.out.println("\n" + VerifierFactory.getFactoryDescription(mode));
            
            // Display the board
            board.printBoard();
            System.out.println();
            
            // Use Factory to create all verifiers for the specified mode
            List<Verifier> verifiers = VerifierFactory.createVerifiersForMode(mode);
            
            // Create appropriate thread manager and execute
            ThreadManager threadManager = createThreadManager(mode);
            List<VerificationResult> allErrors = threadManager.execute(verifiers, board);
            
            printResults(allErrors);
            
        } catch (Exception e) {
            System.out.println("Error during verification: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static ThreadManager createThreadManager(int mode) {
        switch (mode) {
            case 0:
                return new SequentialThreadManager();
            case 3:
                return new Mode3ThreadManager();
            case 27:
                return new Mode27ThreadManager();
            default:
                throw new IllegalArgumentException("Invalid mode: " + mode);
        }
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
                System.out.println("ROW ERRORS:");
                for (VerificationResult error : rowErrors) {
                    System.out.println("  " + error);
                }
            }
            
            if (!colErrors.isEmpty()) {
                if (!rowErrors.isEmpty()) System.out.println();
                System.out.println("COLUMN ERRORS:");
                for (VerificationResult error : colErrors) {
                    System.out.println("  " + error);
                }
            }
            
            if (!boxErrors.isEmpty()) {
                if (!rowErrors.isEmpty() || !colErrors.isEmpty()) System.out.println();
                System.out.println("BOX ERRORS:");
                for (VerificationResult error : boxErrors) {
                    System.out.println("  " + error);
                }
            }
            
            System.out.println("\nTotal errors found: " + allErrors.size());
        }
    }
}