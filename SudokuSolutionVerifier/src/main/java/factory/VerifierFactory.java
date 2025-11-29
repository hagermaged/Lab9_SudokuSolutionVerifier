package factory;

import main.SudokuBoard;
import verifiers.*;
import utils.FileHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VerifierFactory {
    
    // Factory method for SudokuBoard
    public static SudokuBoard createSudokuBoard(String filePath) {
        FileHandler fileHandler = new FileHandler(filePath);
        return new SudokuBoard(filePath);
    }
    
    // Factory method for Scanner
    public static Scanner createScanner() {
        return new Scanner(System.in);
    }
    
    // Factory method for individual verifiers
    public static Verifier createRowVerifier() {
        return new RowVerifier();
    }
    
    public static Verifier createColumnVerifier() {
        return new ColumnVerifier();
    }
    
    public static Verifier createBoxVerifier() {
        return new BoxVerifier();
    }
    
    public static Verifier createIndividualRowVerifier(int rowNumber) {
        return new IndividualRowVerifier(rowNumber);
    }
    
    public static Verifier createIndividualColumnVerifier(int columnNumber) {
        return new IndividualColumnVerifier(columnNumber);
    }
    
    public static Verifier createIndividualBoxVerifier(int boxNumber) {
        return new IndividualBoxVerifier(boxNumber);
    }
    
    // Main factory method for creating verifiers based on mode
    public static List<Verifier> createVerifiersForMode(int mode) {
        List<Verifier> verifiers = new ArrayList<>();
        
        switch (mode) {
            case 0: // Sequential - 3 comprehensive verifiers
                verifiers.add(createRowVerifier());
                verifiers.add(createColumnVerifier());
                verifiers.add(createBoxVerifier());
                break;
                
            case 3: // 4 threads - 3 comprehensive verifiers
                verifiers.add(createRowVerifier());
                verifiers.add(createColumnVerifier());
                verifiers.add(createBoxVerifier());
                break;
                
            case 27: // 28 threads - 27 individual verifiers
                // 9 individual row verifiers
                for (int i = 0; i < 9; i++) {
                    verifiers.add(createIndividualRowVerifier(i));
                }
                // 9 individual column verifiers
                for (int i = 0; i < 9; i++) {
                    verifiers.add(createIndividualColumnVerifier(i));
                }
                // 9 individual box verifiers
                for (int i = 0; i < 9; i++) {
                    verifiers.add(createIndividualBoxVerifier(i));
                }
                break;
                
            default:
                throw new IllegalArgumentException("Invalid mode: " + mode);
        }
        
        return verifiers;
    }
    
    // Factory method for FileHandler
    public static FileHandler createFileHandler(String filePath) {
        return new FileHandler(filePath);
    }
    
    // Utility method to get description of created objects
    public static String getFactoryDescription(int mode) {
        switch (mode) {
            case 0: return "Factory: Sequential Mode (3 comprehensive verifiers)";
            case 3: return "Factory: 4-Thread Mode (3 comprehensive verifiers)";
            case 27: return "Factory: 28-Thread Mode (27 individual verifiers)";
            default: return "Factory: Unknown Mode";
        }
    }
}