package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileHandler {
    private String filePath;
    
    public FileHandler(String filePath) {
        // Remove quotes if present
        this.filePath = filePath.replace("\"", "").trim();
    }
    
    public int[][] loadFromCSV() {
        int[][] board = new int[9][9];
        BufferedReader br = null;
        
        try {
            br = new BufferedReader(new FileReader(this.filePath));
            String line;
            int row = 0;
            
            while ((line = br.readLine()) != null && row < 9) {
                String[] values = line.split(",");
                if (values.length != 9) {
                    throw new IllegalArgumentException("Invalid CSV format at row " + (row + 1) + 
                                                     ": expected 9 columns, got " + values.length);
                }
                
                for (int col = 0; col < 9; col++) {
                    String valueStr = values[col].trim();
                    try {
                        int value = Integer.parseInt(valueStr);
                        if (value < 1 || value > 9) {
                            throw new IllegalArgumentException("Invalid value " + value + 
                                                             " at [" + row + "," + col + "]. Must be 1-9.");
                        }
                        board[row][col] = value;
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Invalid number format at [" + row + "," + col + 
                                                         "]: '" + valueStr + "'");
                    }
                }
                row++;
            }
            
            if (row != 9) {
                throw new IllegalArgumentException("Invalid CSV: expected 9 rows, got " + row);
            }
            
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + this.filePath);
            System.err.println("Make sure the file exists and path is correct.");
            e.printStackTrace();
            // Initialize with zeros to avoid null pointer
            board = new int[9][9];
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return board;
    }
}