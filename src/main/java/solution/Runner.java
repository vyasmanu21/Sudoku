package solution;

import solution.strategies.StrategyInfo;
import solution.sudoku.SudokuBoard;
import solution.sudoku.UnformattedBoardException;

import java.io.PrintStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Runner {

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("You must provide a text file");
            System.exit(1);
        }
        SudokuBoard board = null;
        try {
            board = new SudokuBoard(args[0]);
            if (args.length == 1) {
                if (args[0].equals("-h")) {
                    System.out.println("Provide input file and/or output file: <input file name> <output file name>");
                }
            }
            if (args.length == 2) {
                PrintStream o = new PrintStream(args[1]);
                System.setOut(o);
            }
            System.out.println(board.getSize());
            board.print();


            long startTime = System.currentTimeMillis();
            List<StrategyInfo> strategies = board.solve();
            long endTime = System.currentTimeMillis();

            board.isSolved();

            System.out.println("Solution:");
            board.print();
            System.out.println();
            System.out.println();

            String title = String.format("%-24s %-10s %-10s", "Strategy", "Uses", "Time");

            System.out.println(title);

            strategies.forEach((strategy) -> {
                String solvers = String.format("%-24s %2d   %02d:%02d:%02d.%d",
                        strategy.getName(),
                        strategy.getUses(),
                        TimeUnit.MILLISECONDS.toHours(strategy.getTime()),
                        TimeUnit.MILLISECONDS.toMinutes(strategy.getTime()) -
                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(strategy.getTime())),
                        TimeUnit.MILLISECONDS.toSeconds(strategy.getTime()) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(strategy.getTime())),
                        strategy.getTime());
                System.out.println(solvers);
            });

            long millis = endTime - startTime;

            String hms = String.format("%-17s %02d:%02d:%02d.%d",
                    " ",
                    TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)),
                    millis);
            System.out.println("Total Time: " + hms);
        } catch (UnformattedBoardException e) {
            board.print();
            System.out.println("Invalid: not formatted corrected Board: " + e);
            e.printStackTrace();
        }
    }
}
