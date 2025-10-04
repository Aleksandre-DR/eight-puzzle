import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class GridCreator {
    private static ArrayList<Integer> grid;
    private static Scanner scanner;

    {
        grid = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    GridCreator() {
        fillGridFirstly();

        while (!isGridValid()) {
            grid.clear();
            refillGrid();
        }
    }

    private static boolean isGridValid() {
        boolean isBlankEven;            // odd-evenness of grid's blank space
        int parity;                     // how many switches are needed to solve the puzzle
        boolean isParityEven;

        isBlankEven = grid.indexOf(9) % 2 == 0;
        parity = countParity();
        isParityEven = parity % 2 == 0;

        if ((!isBlankEven && !isParityEven) || (isBlankEven && isParityEven)) return true;
        return false;
    }

    private static int countParity() {
        int parity = 0;
        ArrayList<Integer> tempGrid = (ArrayList<Integer>) grid.clone();

        for (int i = 0; i < 9; i++) {
            if (tempGrid.get(i) != (i + 1)) {
                Collections.swap(tempGrid, i, tempGrid.indexOf(i + 1));
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
        for (int i = 0; i < 9; i++) {
            grid.add(scanner.nextInt());
        }
    }

    public ArrayList<Integer> getGrid() {
        return grid;
    }
}