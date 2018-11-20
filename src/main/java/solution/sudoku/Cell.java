package solution.sudoku;

import java.util.ArrayList;

public class Cell {
    private char value;
    private ArrayList<Character> feasibleValuesList;

    public Cell(char v) {
        this.value = v;
        feasibleValuesList = new ArrayList<>();
    }

    public char getValue() {
        return this.value;
    }

    public ArrayList<Character> getFeasibleValues() {
        return this.feasibleValuesList;
    }

    public void appendFeasibleValue(char v) {
        this.feasibleValuesList.add(v);
    }
}
