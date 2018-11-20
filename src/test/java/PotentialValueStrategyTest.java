import org.junit.Before;
import org.junit.Test;
import solution.strategies.PotentialValueStrategy;
import solution.sudoku.SudokuBoard;
import solution.sudoku.UnformattedBoardException;

import java.io.IOException;
import java.util.Objects;

import static org.junit.Assert.*;

public class PotentialValueStrategyTest {
    private ClassLoader classLoader;
    private PotentialValueStrategy pvs;

    @Before
    public void init() {
        classLoader = getClass().getClassLoader();
        pvs = new PotentialValueStrategy();
    }

    @Test
    public void validateFindPotentialValues() throws UnformattedBoardException, IOException {
        SudokuBoard board = new SudokuBoard(Objects.requireNonNull(classLoader.getResource("puzzles/Puzzle-4x4-0001.txt")).getFile());
        pvs.solve(board);
        assertEquals(board.getActualValues()[0][1], '4');
        assertEquals(board.getActualValues()[1][2], '2');
        assertEquals(board.getActualValues()[3][0], '4');
        assertEquals(board.getActualValues()[2][3], '2');
    }
}
