package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileHandler {
    private String filePath;
    
    // Constructor that takes filePath
    public FileHandler(String filePath) {
        this.filePath = filePath;
    }
    
    // Instance method that uses the stored filePath
    public int[][] loadFromCSV() {
        int[][] board = new int[9][9];
        BufferedReader br = null;
        
        try {
            br = new BufferedReader(new FileReader(this.filePath));  
            String line;
            int row = 0;
            while ((line = br.readLine()) != null && row < 9) {
                String[] values = line.split(",");
                for (int col = 0; col < 9; col++) {
                    board[row][col] = Integer.parseInt(values[col].trim());
                }
                row++;
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + this.filePath);
            e.printStackTrace();
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