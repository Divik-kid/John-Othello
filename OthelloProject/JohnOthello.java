import java.util.ArrayList;

public class JohnOthello implements IOthelloAI{

	public Position decideMove(GameState s){
		ArrayList<Position> moves = s.legalMoves();
		if ( !moves.isEmpty() )
			return moves.get(moves.size()-1);
		else
			return new Position(-1,-1);
	}
	
}
