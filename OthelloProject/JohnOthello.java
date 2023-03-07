import java.util.ArrayList;
import java.util.HashMap;

import javax.management.NotificationEmitter;

public class JohnOthello implements IOthelloAI {

	//GameTree topOfGameTree;
	//boolean firstMove = false;

	public Position decideMove(GameState s) {
		GameState state = s;
		/*if (this.firstMove = false){
		this.topOfGameTree = new GameTree(s, null);
		topOfGameTree.GenerateGameTree();
		this.firstMove = true;
		}*/
		
		ArrayList<Position> moves = state.legalMoves();
		
		if (!moves.isEmpty())
			return MinimaxSearch(s);
		else
			return new Position(-1, -1);
	}

	public Position MinimaxSearch(GameState s) {
		var currentPlayer = s.getPlayerInTurn();
		if(currentPlayer == 1){
		var x = MaxValue(s);
		return x.getPos();
		} else {
		var y = MinValue(s);
		return y.getPos();
		}
		
	}

	public UtilPos MaxValue(GameState s) {
		if (s.isFinished()) {
			return new UtilPos(calculateUtility(s), null);
		}
		UtilPos beta = new UtilPos(Integer.MIN_VALUE, null);

		for(Position p : s.legalMoves()) {
			GameState g = s;
			g.insertToken(p);
			UtilPos up = MinValue(g);
			if (up.getUtil() > beta.getUtil()) {
				beta = up;
			}
		}

		return beta;
	}

	public UtilPos MinValue(GameState s) {
		if (s.isFinished()) {
			return new UtilPos(calculateUtility(s), null);
		}
		UtilPos alpha = new UtilPos(Integer.MAX_VALUE, null);
			for(Position p : s.legalMoves()) {
			GameState g = s;
			g.insertToken(p);
			UtilPos up = MaxValue(g);
			if (up.getUtil() < alpha.getUtil()) {
				alpha = up;
			}
		}

		return alpha;
	}

	public int calculateUtility(GameState s){
		//Positive values are good for black, negative values are good for white
		int totalValue = s.countTokens()[0] - s.countTokens()[1];
		return totalValue;
	}

}
