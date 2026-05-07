import java.util.ArrayList;

public class EightPuzzle {
    public static void main(String[] args) {
        ArrayList<Integer> grid = GridCreator.createGrid();
        EightPuzzleSolver.solve(grid);
    }
}