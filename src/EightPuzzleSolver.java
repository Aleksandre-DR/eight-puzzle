import java.util.*;

public class EightPuzzleSolver {
    private static final String goalState = "123456789";
    private static ArrayDeque<String> toExploreStates;
    private static HashMap<String, String> goalPathHierarchy;     // <state, parent> pairs
    private static HashSet<String> visitedStates;
    private static long startTime, endTime;

    private EightPuzzleSolver() {
    }

    public static void solve(ArrayList<Integer> grid) {
        String startingState = gridToString(grid);

        if (goalState.equals(startingState)) {
            System.out.println("the puzzle is already solved");
            return;
        }

        initializeClassVariables();
        poluteClassVariablesBeforeStartSolving(startingState);

        try {
            solveWithDFS();
        } catch (RuntimeException e) {
            whatWhenSolved();
        }
    }

    private static void initializeClassVariables() {
        toExploreStates = new ArrayDeque<>();
        goalPathHierarchy = new HashMap<>();
        visitedStates = new HashSet<>();
    }

    private static void poluteClassVariablesBeforeStartSolving(String startingState) {
        startTime = System.currentTimeMillis();
        visitedStates.add(startingState);
        toExploreStates.addLast(startingState);
        goalPathHierarchy.put(startingState, null);
    }

    private static String gridToString(ArrayList<Integer> grid) {
        StringBuilder sb = new StringBuilder();
        for (int n : grid) sb.append(n);
        return sb.toString();
    }

    private static void solveWithDFS() {
        String exploringState;
        int blankBoxIndex;

        while (!toExploreStates.isEmpty()) {        // exploring unvisited states
            exploringState = toExploreStates.removeLast();
            blankBoxIndex = getStateBlankBoxIndex(exploringState);

            // making valid moves
            if (blankBoxIndex > 2) move(exploringState, -3);           // move up
            if (blankBoxIndex % 3 != 2) move(exploringState, 1);       // move right
            if (blankBoxIndex < 6) move(exploringState, 3);            // move down
            if (blankBoxIndex % 3 != 0) move(exploringState, -1);      // move left
        }
    }

    private static void move(String currentState, int moveToWhere) {
        int blankBoxIndex = getStateBlankBoxIndex(currentState);

        String stateBeforeMove = currentState;
        String stateAfterMove = swap(currentState, blankBoxIndex, blankBoxIndex + moveToWhere);

        checkIfSolvedAfterMove(stateBeforeMove, stateAfterMove);

        if (!visitedStates.contains(stateAfterMove)) {
            visitedStates.add(stateAfterMove);
            toExploreStates.addLast(stateAfterMove);        // addFirst() would be BFS

            // if we comment above line and uncomment bottom one, algorithm will be informed
            // addStateToQueueInformly(blankBoxIndex, stateAfterMove);

            goalPathHierarchy.put(stateAfterMove, stateBeforeMove);
        }
    }

    private static void checkIfSolvedAfterMove(String stateBeforeMove, String stateAfterMove) {
        if (goalState.equals(stateAfterMove)) {
            goalPathHierarchy.put(stateAfterMove, stateBeforeMove);
            endTime = System.currentTimeMillis();
            throw new RuntimeException();                   // if solved, stop DFS
        }
    }

    private static void addStateToQueueInformly(int index, String state) {
        int correctNumberOnIndex = index + 1;
        int actualNumberOnIndex = state.charAt(index) - '0';

        if (correctNumberOnIndex == actualNumberOnIndex) {
            toExploreStates.addLast(state);
        } else {
            toExploreStates.addFirst(state);
        }
    }

    private static int getStateBlankBoxIndex(String state) {
        return state.indexOf("9");
    }

    private static String swap(String str, int i, int j) {
        StringBuilder sb = new StringBuilder(str);
        sb.setCharAt(i, str.charAt(j));
        sb.setCharAt(j, str.charAt(i));
        return sb.toString();
    }

    private static void whatWhenSolved() {
        ArrayList<String> statesFromStartToGoal = new ArrayList<>();
        String state = goalState;
        statesFromStartToGoal.add(state);

        while (true) {
            state = goalPathHierarchy.get(state);
            if (state == null) break;                  // reached starting state
            statesFromStartToGoal.add(state);
        }

        Collections.reverse(statesFromStartToGoal);
        printPathFromStartToGoal(statesFromStartToGoal);

        System.out.println((endTime - startTime) + " miliseconds for DFS");
    }

    private static void printPathFromStartToGoal(ArrayList<String> statesFromStartToGoal) {
        System.out.println();
        for (String state : statesFromStartToGoal) {
            printStateAsGrid(state);
        }
        System.out.println("goalPathHierarchy map size is " + goalPathHierarchy.size());
        System.out.println(statesFromStartToGoal.size() + " moves in total");
    }

    private static void printStateAsGrid(String state) {
        int gridSize = state.length();
        for (int i = 0; i < gridSize; i++) {
            if (state.charAt(i) == '9') System.out.print("□ ");
            else System.out.print(state.charAt(i) + " ");
            if (i % 3 == 2) System.out.println();
        }
        System.out.println();
    }
}
