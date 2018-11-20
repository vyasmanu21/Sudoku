package solution.strategies;

import solution.sudoku.Cell;
import solution.sudoku.SudokuBoard;

public class PotentialValueStrategy implements IStrategy {
    public void setSize(int size) {
        this.size = size;
    }

    private int size;

    public void setActualValues(char[][] actualValues) {
        this.actualValues = actualValues;
    }

    private char actualValues[][];

    @Override
    public long solve(SudokuBoard board) {
        long startTime = System.currentTimeMillis();
        this.size = board.getSize();
        this.actualValues = board.getActualValues();
        Cell[][] cellArray = initializeCells(board);
        findFeasibleValues(cellArray, board);
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    private Cell[][] initializeCells(SudokuBoard board) {
        char[][] actualValues = board.getActualValues();
        Cell[][] cellValues = new Cell[board.getSize()][board.getSize()];

        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                Cell cell = new Cell(actualValues[row][col]);
                cellValues[row][col] = cell;
            }
        }
        return cellValues;
    }

    private void findFeasibleValues(Cell[][] cellValues, SudokuBoard board) {
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if (cellValues[row][col].getValue() == '-') {
                    char[] possibleValues = board.getPossibleValues();

                    for (char n : possibleValues) {
                        if (!validateRow(n, row) && !validateColumn(n, col) && !validateBlock(n, row, col)) {
                            cellValues[row][col].appendFeasibleValue(n);
                        }
                    }
                }
                if (cellValues[row][col].getFeasibleValues().size() == 1) {
                    board.getActualValues()[row][col] = cellValues[row][col].getFeasibleValues().get(0);
                }
            }
        }
    }

    public boolean validateBlock(char value, int row, int col) {
        int rowStart = 0;
        int rowEnd = 0;
        int colStart = 0;
        int colEnd = 0;
        switch (size) {
            case 4:
                rowStart = row < 2 ? 0 : 2;
                rowEnd = row < 2 ? 1 : 3;
                colStart = col < 2 ? 0 : 2;
                colEnd = col < 2 ? 1 : 3;
                break;
            case 9:
                rowStart = row < 3 ? 0 : row < 6 ? 3 : 6;
                rowEnd = row < 3 ? 2 : row < 6 ? 5 : 8;
                colStart = col < 3 ? 0 : col < 6 ? 3 : 6;
                colEnd = col < 3 ? 2 : col < 6 ? 5 : 8;
                break;
            case 16:
                rowStart = row < 4 ? 0 : row < 8 ? 4 : row < 12 ? 8 : 12;
                rowEnd = row < 4 ? 3 : row < 8 ? 7 : row < 12 ? 11 : 15;
                colStart = col < 4 ? 0 : col < 8 ? 4 : col < 12 ? 8 : 12;
                colEnd = col < 4 ? 3 : col < 8 ? 7 : col < 12 ? 11 : 15;
                break;
            case 25:
                rowStart = row < 5 ? 0 : row < 10 ? 5 : row < 15 ? 10 : row < 20 ? 15 : 20;
                rowEnd = row < 5 ? 4 : row < 10 ? 9 : row < 15 ? 14 : row < 20 ? 19 : 24;
                colStart = col < 5 ? 0 : col < 10 ? 5 : col < 15 ? 10 : col < 20 ? 15 : 20;
                colEnd = col < 5 ? 4 : col < 10 ? 9 : col < 15 ? 14 : col < 20 ? 19 : 24;
                break;
        }
        for (int r = rowStart; r <= rowEnd; r++) {
            for (int c = colStart; c <= colEnd; c++) {
                if (value == actualValues[r][c]) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean validateRow(char value, int row) {
        if (row <= size) {
            for (int col = 0; col < size; col++) {
                if (value == actualValues[row][col]) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean validateColumn(char value, int col) {
        if (col <= size) {
            for (int row = 0; row < size; row++) {
                if (value == actualValues[row][col]) {
                    return true;
                }
            }
        }
        return false;
    }
}
