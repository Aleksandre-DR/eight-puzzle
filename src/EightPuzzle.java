
public class EightPuzzle {

    public static void main(String[] args) {
        GridCreator ourGrid = new GridCreator();
        DFS dfs = new DFS(ourGrid.getGrid());
        dfs.goDFS();
    }
}
