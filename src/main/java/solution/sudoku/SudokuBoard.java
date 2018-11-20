package solution.sudoku;

import solution.strategies.BacktrackingStrategy;
import solution.strategies.PotentialValueStrategy;
import solution.strategies.StrategyInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class SudokuBoard {
    private int size;
    private char possibleValues[];
    private char actualValues[][];
    private static final HashSet<Integer> validSizes = new HashSet<>(Arrays.asList(4, 9, 16, 25));

    public SudokuBoard(String fName) throws UnformattedBoardException, IOException {
        File file = new File(fName);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();
        int size = Integer.parseInt(line);
        this.setSize(size);

        line = br.readLine();
        String[] possibleValue = line.split(" ");
        char[] possibleValues = new char[size];
        if (size != possibleValue.length) {
            throw new UnformattedBoardException("Board size should match possible values size");
        }
        int counter = 0;
        for (String val : possibleValue) {
            char tempArray[] = val.toCharArray();
            if (tempArray.length == 1) {
                possibleValues[counter++] = tempArray[0];
            } else {
                throw new UnformattedBoardException("Invalid value");
            }
        }
        this.possibleValues = possibleValues;

        char actualValue[][] = new char[size][size];
        for (int row = 0; row < size; row++) {
            counter = 0;
            line = br.readLine();
            String boardValues[] = line.split(" ");
            for (String val : boardValues) {
                char tempArray[] = val.toCharArray();
                if (tempArray.length == 1) {
                    actualValue[row][counter++] = tempArray[0];
                } else {
                    throw new UnformattedBoardException("Invalid value");
                }
            }
        }
        this.actualValues = actualValue;
        this.validateActualValues();
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) throws UnformattedBoardException {
        System.out.println("Board size => " + size);
        if (validSizes.contains(size)) {
            this.size = size;
        } else {
            throw new UnformattedBoardException("Invalid Board Size");
        }
    }

    public char[] getPossibleValues() {
        return possibleValues;
    }


    public char[][] getActualValues() {
        return actualValues;
    }


    public String print() {
        StringBuilder sb = new StringBuilder();
        for (char n : this.possibleValues) {
            sb.append(n).append(" ");
            System.out.print(n + " ");
        }
        sb.append("/n");
        System.out.println();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                System.out.print(this.actualValues[row][col] + " ");
                sb.append(this.actualValues[row][col]).append(" ");
            }
            sb.append("/n");
            System.out.println();
        }
        return sb.toString();
    }

    public boolean isSolved() {

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (actualValues[row][col] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    private void validateValueInPossibleValues(char value) throws UnformattedBoardException {
        boolean isValid = false;
        for (int k = 0; k < size; k++) {
            if (value == possibleValues[k] || value == '-') {
                isValid = true;
                break;
            }
        }
        if (!isValid) {
            throw new UnformattedBoardException("Invalid character in the solution.sudoku board");
        }
    }

    private void validateActualValues() throws UnformattedBoardException {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                validateValueInPossibleValues(actualValues[i][j]);
            }
        }
    }

    public List<StrategyInfo> solve() {
        ArrayList<StrategyInfo> strategies = new ArrayList<>();
        PotentialValueStrategy pvs = new PotentialValueStrategy();
        BacktrackingStrategy bs = new BacktrackingStrategy();

        StrategyInfo potentialValueInfoStrategy = new StrategyInfo("Potential Value Strategy");
        StrategyInfo backTrackingStrategy = new StrategyInfo("Backtracking Strategy");

        strategies.add(potentialValueInfoStrategy);
        strategies.add(backTrackingStrategy);

        int counter = 0;
        long potentialValueTimer = 0;
        int potentialCounter = 0;
        long backtrackTimer = 0;
        int backtrackCounter = 0;

        while (!this.isSolved() && counter < 30) {
            potentialValueTimer += pvs.solve(this);
            potentialCounter++;
            backtrackTimer += bs.solve(this);
            backtrackCounter++;
            counter++;
        }
        if (!this.isSolved()) {
            System.out.println("Can't solve the solution.sudoku puzzle");
        }
        potentialValueInfoStrategy.setTime(potentialValueTimer);
        potentialValueInfoStrategy.setUses(potentialCounter);
        backTrackingStrategy.setTime(backtrackTimer);
        backTrackingStrategy.setUses(backtrackCounter);
        return strategies;
    }
}
