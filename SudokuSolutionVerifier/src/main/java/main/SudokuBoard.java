/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import utils.FileHandler;

/**
 *
 * @author Hajer1
 */
public class SudokuBoard {

        private int[][] board;
    
    public SudokuBoard(String filePath) {
        FileHandler fileHandler = new FileHandler(filePath);  
        this.board = fileHandler.loadFromCSV();              
    }
    
 
    public SudokuBoard(int[][] board) {
        this.board = board;
    }
     public int getValue(int row, int col) {
        return board[row][col];
    }
    
    public int[][] getBoard() {
        return board;
    }
    
   
    public void printBoard() {
        System.out.println("Sudoku Board:");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(board[i][j] + " ");
                if (j == 2 || j == 5) {
                    System.out.print("| ");  // Visual separator for boxes
                }
            }
            System.out.println();
            if (i == 2 || i == 5) {
                System.out.println("------+-------+------");  // Visual separator for boxes
            }
        }
    }
    
   
    public boolean isBoardLoaded() {
        return board != null && board.length == 9 && board[0].length == 9;
    }
    
}
