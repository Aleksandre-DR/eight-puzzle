import java.util.*;

import static java.lang.System.exit;

public class DFS {
    private static final String goalState = "123456789";
    private ArrayDeque<String> toExploreStates;
    private HashMap<String, String> goalPathHierarchy;     // <state, parent> pairs
    private HashSet<String> openedStates;
    private long startTime;
    private ArrayList<Integer> grid;

    {
        toExploreStates = new ArrayDeque<>();
        goalPathHierarchy = new HashMap<>();
        openedStates = new HashSet<>();
        startTime = System.currentTimeMillis();
    }

    DFS(ArrayList<Integer> grid) {
        this.grid = grid;
    }

    public void goDFS() {
        String startingState = grid.stream().map(String::valueOf).reduce(String::concat).get();

        if (goalState.equals(startingState)) {
            System.out.println("the puzzle is already solved");
            return;
        }

        openedStates.add(startingState);
        toExploreStates.addLast(startingState);
        goalPathHierarchy.put(startingState, null);
        actualDFS();
    }

    private void actualDFS() {
        String exploringState;

        while (!toExploreStates.isEmpty()) {      // exploring unvisited nodes
            exploringState = toExploreStates.removeLast();
            int blankIndex = exploringState.indexOf("9");

            // making valid moves
            if (blankIndex > 2) move(exploringState, -3);           // move up
            if (blankIndex % 3 != 2) move(exploringState, 1);       // move right
            if (blankIndex < 6) move(exploringState, 3);            // move down
            if (blankIndex % 3 != 0) move(exploringState, -1);      // move left
        }
    }

    private void move(String exploringState, int moveToWhere) {
        int blankIndex = exploringState.indexOf("9");

        String stateBeforeMove = exploringState;
        String stateAfterMove = swap(exploringState, blankIndex, blankIndex + moveToWhere);

        if (goalState.equals(stateAfterMove)) {
            goalPathHierarchy.put(stateAfterMove, stateBeforeMove);
            whatWhenSolved();
        }

        if (!openedStates.contains(stateAfterMove)) {
            openedStates.add(stateAfterMove);
            toExploreStates.addLast(stateAfterMove);

            // if we comment above line and uncomment bottom one, algorithm will be informed
            // addStateToQueueInformly(blankIndex, stateAfterMove);

            goalPathHierarchy.put(stateAfterMove, stateBeforeMove);
        }
    }


    private void addStateToQueueInformly(int blankIndex, String state) {
        if (blankIndex + 1 == state.charAt(blankIndex) - '0') {
            toExploreStates.addLast(state);
        } else {
            toExploreStates.addFirst(state);
        }
    }

    private String swap(String str, int i, int j) {
        StringBuilder sb = new StringBuilder(str);
        sb.setCharAt(i, str.charAt(j));
        sb.setCharAt(j, str.charAt(i));
        return sb.toString();
    }


    private void whatWhenSolved() {
        ArrayList<String> statesFromStartToEnd = new ArrayList<>();
        String state = goalState;
        statesFromStartToEnd.add(state);

        while (true) {
            state = goalPathHierarchy.get(state);
            if (state == null) break;
            statesFromStartToEnd.add(state);
        }

        Collections.reverse(statesFromStartToEnd);
        printPathFromStartToGoal(statesFromStartToEnd);

        System.out.println((System.currentTimeMillis() - startTime) + " miliseconds for DFS");
        exit(1);
    }

    private void printPathFromStartToGoal(ArrayList<String> statesFromStartToEnd) {
        System.out.println();
        for (String st : statesFromStartToEnd) {
            printAsGrid(st);
        }
        System.out.println("goalPathHierarchy map size is " + goalPathHierarchy.size());
        System.out.println(statesFromStartToEnd.size() + " moves in total");
    }

    private void printAsGrid(String state) {
        for (int i = 0; i < 9; i++) {
            if (state.charAt(i) == '9') System.out.print((char) 0 + " ");
            else System.out.print(state.charAt(i) + " ");
            if (i % 3 == 2) System.out.println();
        }
        System.out.println();
    }
}
