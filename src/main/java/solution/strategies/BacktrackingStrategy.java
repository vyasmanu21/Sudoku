package solution.strategies;

import solution.sudoku.SudokuBoard;

public class BacktrackingStrategy implements IStrategy {
    private static final char NO_VALUE = '-';
    private int size;

    @Override
    public long solve(SudokuBoard board) {
        long startTime = System.currentTimeMillis();
        this.size = board.getSize();
        char[][] actualValues = board.getActualValues();
        char[] possibleValues = board.getPossibleValues();
        backtrack(actualValues, possibleValues);
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    private boolean backtrack(char[][] actualValues, char[] possibleValues) {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {

                if (actualValues[row][col] == NO_VALUE) {

                    for (char n : possibleValues) {
                        if (isValid(actualValues, row, col, n)) {

                            actualValues[row][col] = n;

                            if (backtrack(actualValues, possibleValues)) {
                                return true;
                            } else {
                                actualValues[row][col] = NO_VALUE;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean validateRow(char[][] actualValues, int row, char number) {
        for (int i = 0; i < this.size; i++) {
            if (actualValues[row][i] == number) {
                return true;
            }
        }
        return false;
    }

    private boolean validateColumn(char[][] actualValues, int col, char number) {
        for (int i = 0; i < this.size; i++) {
            if (actualValues[i][col] == number) {
                return true;

            }
        }

        return false;
    }

    private boolean validateBlock(char[][] actualValues, int row, int col, char number) {
        int r = row - row % 3;
        int c = col - col % 3;

        for (int i = r; i < r + 3; i++) {
            for (int j = c; j < c + 3; j++) {
                if (actualValues[i][j] == number) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isValid(char[][] actualValues, int row, int col, char number) {
        return !validateRow(actualValues, row, number) && !validateColumn(actualValues, col, number) && !validateBlock(actualValues, row, col, number);
    }

}
