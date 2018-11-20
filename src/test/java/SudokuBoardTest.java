import org.junit.*;
import solution.strategies.PotentialValueStrategy;
import solution.strategies.StrategyInfo;
import solution.sudoku.SudokuBoard;
import solution.sudoku.UnformattedBoardException;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;

public class SudokuBoardTest {
    private ClassLoader classLoader;
    private PotentialValueStrategy pvs;

    @Before
    public void init() {
        classLoader = getClass().getClassLoader();
        pvs = new PotentialValueStrategy();
    }

    @Test
    public void loadInvalidBoard() {
        try {
            SudokuBoard board = new SudokuBoard(Objects.requireNonNull(classLoader.getResource("fail/invalidBoardSize.txt")).getFile());
            fail();
            assertNull(board);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnformattedBoardException e) {
            assertTrue(true);
        }
    }

    @Test
    public void loadInvalidPossibleValues() {
        try {
            SudokuBoard board = new SudokuBoard(Objects.requireNonNull(classLoader.getResource("fail/invalidPossibleValues.txt")).getFile());
            fail();
            assertNull(board);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnformattedBoardException e) {
            assertTrue(true);
        }
    }

    @Test
    public void tooManyPossibleValues() {
        try {
            SudokuBoard board = new SudokuBoard(Objects.requireNonNull(classLoader.getResource("fail/tooManyPossibleValues.txt")).getFile());
            fail();
            assertNull(board);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnformattedBoardException e) {
            assertTrue(true);
        }
    }

    @Test
    public void invalidValue() {
        try {
            SudokuBoard board = new SudokuBoard(Objects.requireNonNull(classLoader.getResource("fail/invalidValue.txt")).getFile());
            fail();
            assertNull(board);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnformattedBoardException e) {
            assertTrue(true);
        }
    }


    @Test
    public void validateCheckRow() {
        try {
            SudokuBoard board = new SudokuBoard(Objects.requireNonNull(classLoader.getResource("puzzles/Puzzle-4x4-0001.txt")).getFile());
            pvs.setActualValues(board.getActualValues());
            pvs.setSize(board.getSize());
            assertTrue(pvs.validateRow('2', 0));
            assertTrue(pvs.validateRow('3', 0));
            assertTrue(pvs.validateRow('1', 0));

            assertTrue(pvs.validateRow('1', 1));
            assertTrue(pvs.validateRow('3', 1));
            assertTrue(pvs.validateRow('4', 1));

            assertTrue(pvs.validateRow('3', 2));
            assertTrue(pvs.validateRow('1', 2));
            assertTrue(pvs.validateRow('4', 2));

            assertTrue(pvs.validateRow('2', 3));
            assertTrue(pvs.validateRow('1', 3));
            assertTrue(pvs.validateRow('3', 3));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnformattedBoardException e) {
            fail();
        }
    }

    @Test
    public void validateCheckCol() {
        try {
            SudokuBoard board = new SudokuBoard(Objects.requireNonNull(classLoader.getResource("puzzles/Puzzle-4x4-0001.txt")).getFile());
            pvs.setActualValues(board.getActualValues());
            pvs.setSize(board.getSize());
            assertTrue(pvs.validateColumn('2', 0));
            assertTrue(pvs.validateColumn('1', 0));
            assertTrue(pvs.validateColumn('3', 0));

            assertTrue(pvs.validateColumn('3', 1));
            assertTrue(pvs.validateColumn('1', 1));
            assertTrue(pvs.validateColumn('2', 1));

            assertTrue(pvs.validateColumn('3', 2));
            assertTrue(pvs.validateColumn('4', 2));
            assertTrue(pvs.validateColumn('1', 2));

            assertTrue(pvs.validateColumn('1', 3));
            assertTrue(pvs.validateColumn('4', 3));
            assertTrue(pvs.validateColumn('3', 3));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnformattedBoardException e) {
            fail();
        }
    }

    @Test
    public void validateCheckBlock9x9() {
        try {
            SudokuBoard board = new SudokuBoard(Objects.requireNonNull(classLoader.getResource("puzzles/Puzzle-9x9-0001.txt")).getFile());
            pvs.setActualValues(board.getActualValues());
            pvs.setSize(board.getSize());
            assertTrue(pvs.validateBlock('1', 1, 3));
            assertTrue(pvs.validateBlock('9', 1, 8));
            assertTrue(pvs.validateBlock('1', 8, 1));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnformattedBoardException e) {
            fail();
        }
    }

    @Test
    public void validateCheckBlock16x16() {
        try {
            SudokuBoard board = new SudokuBoard(Objects.requireNonNull(classLoader.getResource("puzzles/Puzzle-16x16-0001.txt")).getFile());
            pvs.setActualValues(board.getActualValues());
            pvs.setSize(board.getSize());

            assertTrue(pvs.validateBlock('7', 1, 1));
            assertTrue(pvs.validateBlock('3', 2, 2));
            assertTrue(pvs.validateBlock('E', 1, 10));
            assertTrue(pvs.validateBlock('8', 0, 15));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnformattedBoardException e) {
            fail();
        }
    }

    @Test
    public void validateCheckBlock25x25() {
        try {
            SudokuBoard board = new SudokuBoard(Objects.requireNonNull(classLoader.getResource("puzzles/Puzzle-25x25-0101.txt")).getFile());
            pvs.setActualValues(board.getActualValues());
            pvs.setSize(board.getSize());

            assertTrue(pvs.validateBlock('M', 1, 1));
            assertTrue(pvs.validateBlock('K', 0, 5));
            assertTrue(pvs.validateBlock('7', 0, 10));
            assertTrue(pvs.validateBlock('1', 24, 0));
            assertTrue(pvs.validateBlock('P', 24, 24));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnformattedBoardException e) {
            fail();
        }
    }

    @Test
    public void validateIsSolved() throws UnformattedBoardException, IOException {
        SudokuBoard board = new SudokuBoard(Objects.requireNonNull(classLoader.getResource("puzzles/Puzzle-4x4-0001.txt")).getFile());
        assertFalse(board.isSolved());
        board = new SudokuBoard(Objects.requireNonNull(classLoader.getResource("puzzles/solved.txt")).getFile());
        assertTrue(board.isSolved());
    }

    @Test
    public void validateSolve() throws UnformattedBoardException, IOException {
        HashSet<String> validStrategies = new HashSet<>(Arrays.asList("Potential Value Strategy", "Backtracking Strategy"));
        SudokuBoard board = new SudokuBoard(Objects.requireNonNull(classLoader.getResource("puzzles/Puzzle-9x9-0902.txt")).getFile());
        List<StrategyInfo> strategies = board.solve();
        assertEquals(strategies.size(), 2);
        strategies.forEach((strategy) -> {
            assertTrue(validStrategies.contains(strategy.getName()));
        });
        assertTrue(board.isSolved());
    }

    @Test
    public void validatePrint() throws UnformattedBoardException, IOException {
        SudokuBoard board = new SudokuBoard(Objects.requireNonNull(classLoader.getResource("puzzles/Puzzle-9x9-0902.txt")).getFile());
        String before = board.print();
        board.solve();
        String after = board.print();
        assertNotEquals(before, after);
    }
}
