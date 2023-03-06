import java.util.ArrayList;

public class IAmuD implements IOthelloAI{

	/**
	 * Returns Last legal move
	 */
	public Position decideMove(GameState s){
		ArrayList<Position> moves = s.legalMoves();
		if ( !moves.isEmpty() )
			return moves.get(moves.size()-1);
		else
			return new Position(-1,-1);
	}
	
}
