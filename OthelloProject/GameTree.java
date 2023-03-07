import java.util.ArrayList;

public class GameTree {

    GameState currentState;
    ArrayList<GameState> childStates;
    ArrayList<GameTree> childTrees;
    GameTree parentTree;

    public GameTree(GameState state, GameTree parentTree) {
        this.currentState = state;
        this.childStates = new ArrayList<>();
        this.childTrees = new ArrayList<>();
        this.parentTree = parentTree;
    }

    public void GenerateGameTree() {

        ArrayList<Position> moves = currentState.legalMoves();
        if (!moves.isEmpty()) {
            for (Position position : moves) {
                // Create new gamestate
                GameState tempState = currentState;
                tempState.insertToken(position);
                // Save ChildStates
                childStates.add(tempState);
            }

            for (GameState child : childStates) {
                //Generate ChildTrees and save them in parent
                GameTree tempTree = new GameTree(child, this);
                childTrees.add(tempTree);
            }

            for (GameTree child : childTrees) {
                //Let the recursion go brrrrrrrrrrrrrrrrrr
                child.GenerateGameTree();
            }
        }
    }

}