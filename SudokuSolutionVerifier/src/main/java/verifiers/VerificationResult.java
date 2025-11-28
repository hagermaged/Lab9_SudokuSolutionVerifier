/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package verifiers;

/**
 *
 * @author KimoStore
 */
class VerificationResult {

    private final String type;
    private final int index;
    private final int duplicatedValue;
    private final int row;
    private final int col;

    public VerificationResult(String type, int index, int duplicatedValue, int row, int col) {
        this.type = type;
        this.index = index;
        this.duplicatedValue = duplicatedValue;
        this.row = row;
        this.col = col;
    }

    public String getType() {
        return type;
    }

    public int getIndex() {
        return index;
    }

    public int getDuplicatedValue() {
        return duplicatedValue;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public String toString()
    {
        return String.format("%s%d,value %d at (%d,%d)",type,index+1,duplicatedValue,row,col);
    }
}
