import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class GridCreator {
    private static final int BLANK_BOX = 9;      // number 9 represents blank box in grid
    private static final int gridSize = 9;
    private static ArrayList<Integer> grid;

    private GridCreator() {
    }

    public static ArrayList<Integer> createGrid(){
        grid = new ArrayList<>();
        fillGridFirstly();

        while (!isGridSolvable()) {
            grid.clear();
            refillGrid();
        }
        return grid;
    }

    // parity is the number of number switches needed to solve the puzzle.
    // grid is solvable if blank box's odd-evenness is the same as parity's odd-evenness.
    private static boolean isGridSolvable() {
        boolean isBlankEven = grid.indexOf(BLANK_BOX) % 2 == 0;
        int parity = countParity();
        boolean isParityEven = parity % 2 == 0;

        return isBlankEven == isParityEven;            // solvable if even-even or odd-odd
    }

    private static int countParity() {
        int correctNumberAtIndex, parity = 0;
        ArrayList<Integer> tempGrid = (ArrayList<Integer>) grid.clone();

        for (int i = 0; i < gridSize; i++) {
            correctNumberAtIndex = i + 1;           // number i+1 should be at index i
            if (tempGrid.get(i) != correctNumberAtIndex) {
                Collections.swap(tempGrid, i, tempGrid.indexOf(correctNumberAtIndex));
                parity++;
            }
        }
        return parity;
    }

    private static void fillGridFirstly() {
        System.out.println("enter 9 numbers from 1 to 9:");
        fillGrid();
    }

    private static void refillGrid() {
        System.out.println("that kind of puzzle is unsolvable, try again!");
        fillGrid();
    }

    private static void fillGrid() {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < gridSize; i++) {
            grid.add(scanner.nextInt());
        }
    }
}