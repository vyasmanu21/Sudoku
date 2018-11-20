package solution.strategies;

import solution.sudoku.SudokuBoard;

public interface IStrategy {
    long solve(SudokuBoard board);
}
