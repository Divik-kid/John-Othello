import java.util.ArrayList;

public class JohnOthello implements IOthelloAI {

	// GameTree topOfGameTree;
	// boolean firstMove = false;
	public int searchDepth;

	public Position decideMove(GameState s) {
		GameState state = s;
		searchDepth = 2;
		/*
		 * if (this.firstMove = false){
		 * this.topOfGameTree = new GameTree(s, null);
		 * topOfGameTree.GenerateGameTree();
		 * this.firstMove = true;
		 * }
		 */

		ArrayList<Position> moves = state.legalMoves();

		if (!moves.isEmpty())
			return MinimaxSearch(s);
		else
			return new Position(-1, -1);
	}

	public Position MinimaxSearch(GameState s) {
		var currentPlayer = s.getPlayerInTurn();
		if (currentPlayer == 1) {
			var x = MaxValue(s, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
			System.out.println("hewwo :3333");
			System.out.println("I was!");
			System.out.println(s.getPlayerInTurn());
			System.out.println(x.getPos());
			return x.getPos();
		} else if (currentPlayer == 2) {
			var y = MaxValue(s, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
			System.out.println("hewwo :3333");
			System.out.println("I was!");
			System.out.println(s.getPlayerInTurn());
			System.out.println(y.getPos());
			return y.getPos();
		}
		return new Position(-1, -1);
	}

	public UtilPos MaxValue(GameState s, int counter, int trueAlpha, int trueBeta) {
		UtilPos beta = new UtilPos(Integer.MIN_VALUE, null);

		if (s.isFinished() || counter > searchDepth) {
			return new UtilPos(calculateUtility(s), null);
		}

		for (Position p : s.legalMoves()) {

			GameState g = new GameState(s.getBoard(), s.getPlayerInTurn());
			g.insertToken(p);
			UtilPos up = MinValue(g, counter++, trueAlpha, trueBeta);
			if (up.getUtil() >= beta.getUtil()) {
				beta = up;
				beta.setPos(p);

				trueAlpha = Max(beta.getUtil(), trueAlpha);

			}
			if (counter > searchDepth) {
				System.out.println("OWO");
				if (beta.getUtil() >= trueBeta)
					return beta;
			}
		}

		return beta;
	}

	public UtilPos MinValue(GameState s, int counter, int trueAlpha, int trueBeta) {
		UtilPos alpha = new UtilPos(Integer.MAX_VALUE, null);
		if (s.isFinished() || counter > searchDepth) {
			return new UtilPos(calculateUtility(s), null);
		}

		for (Position p : s.legalMoves()) {

			GameState g = new GameState(s.getBoard(), s.getPlayerInTurn());
			g.insertToken(p);
			UtilPos up = MaxValue(g, counter++, trueAlpha, trueBeta);
			if (up.getUtil() <= alpha.getUtil()) {
				alpha = up;
				alpha.setPos(p);

				trueBeta = Min(trueBeta, alpha.getUtil());

			}
			if (counter > searchDepth) {
				System.out.println("OWO");
				if (alpha.getUtil() <= trueBeta)
					return alpha;
			}
		}

		return alpha;
	}

	private int Min(int trueBeta, Integer util) {
		if (util < trueBeta) {
			return util;
		} else
			return trueBeta;

	}

	private int Max(Integer util, int trueAlpha) {
		if (util > trueAlpha) {
			return util;
		} else
			return trueAlpha;
	}

	public int calculateUtility(GameState s) {
		// Positive values are good for black, negative values are good for white
		System.out.println("uwu");
		var board = s.getBoard();
		int tokens1 = 0;
		int tokens2 = 0;
		var size = board.length;

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {

				if (board[i][j] == 1)

					if (((i == 0 && j == 0) || (i == 0 && j == size - 1) || (i == size - 1 && j == 0)
							|| (i == size - 1 && j == size - 1)) || (i==0 && (j!=1 || j!=size-2) || i==size-1 && (j!=1 || j!=size-2))) {
							//corners
						tokens1 += 150;
					} else if ((i == 1) || (i == size - 2) || (j == 1) || (j == size - 2)) {
						//Danger zone
						tokens1 += 5;
							
					} else {
							//Center
						tokens1 += 10;
					}
					else if (board[i][j] == 2)
					if (((i == 0 && j == 0) || (i == 0 && j == size - 1) || (i == size - 1 && j == 0)
							|| (i == size - 1 && j == size - 1)) || (i==0 && (j!=1 || j!=size-2) || i==size-1 && (j!=1 || j!=size-2))) {
						tokens2 += 150;
					} else if ((i == 1) || (i == size - 2) || (j == 1) || (j == size - 2)) {
						tokens2 += 5;
					} else {
						tokens2 += 10;
					}

			}
		}
		int totalValue = tokens1 - tokens2;
		System.out.println(totalValue);
		return totalValue;
	}

}
