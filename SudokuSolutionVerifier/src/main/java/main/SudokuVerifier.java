/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.util.List;
import java.util.Scanner;
import verifiers.*;

/**
 *
 * @author Hajer1
 */
public class SudokuVerifier {
 public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    
    // Get file path from user
    System.out.print("Enter Sudoku CSV file path: ");
    String filePath = scanner.nextLine();
    
    // Get mode from user
    System.out.print("Enter mode (0, 3, or 27): ");
    int mode = scanner.nextInt();
    
    scanner.close();
    
    if (mode == 0) {
        runSequential(filePath);
    } else if (mode == 3) {
        System.out.println("Mode 3 (4 threads) - To be implemented");
        runSequential(filePath); // Fallback for now
    } else if (mode == 27) {
        System.out.println("Mode 27 (28 threads) - To be implemented");
        runSequential(filePath); // Fallback for now
    } else {
        System.out.println("Invalid mode. Use 0, 3, or 27.");
    }
}
    private static void runSequential(String filePath) {
        SudokuBoard board = new SudokuBoard(filePath);
        
        RowVerifier rowVerifier = new RowVerifier();
        ColumnVerifier colVerifier = new ColumnVerifier();
        BoxVerifier boxVerifier = new BoxVerifier();
        
        List<VerificationResult> rowErrors = rowVerifier.verify(board);
        List<VerificationResult> colErrors = colVerifier.verify(board);
        List<VerificationResult> boxErrors = boxVerifier.verify(board);
        
        if (rowErrors.isEmpty() && colErrors.isEmpty() && boxErrors.isEmpty()) {
            System.out.println("VALID");
        } else {
            System.out.println("INVALID\n");
            
            // Print row errors
            for (VerificationResult error : rowErrors) {
                System.out.println(error);
            }
            
            // Print column errors  
            for (VerificationResult error : colErrors) {
                System.out.println(error);
            }
            
            // Print box errors
            for (VerificationResult error : boxErrors) {
                System.out.println(error);
            }
        }
    }
}
