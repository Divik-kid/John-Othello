import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class MonkeAI implements IOthelloAI{

	/**
	 * Returns Random legal move
	 */
	public Position decideMove(GameState s){
		ArrayList<Position> moves = s.legalMoves();
        var monkeMove = ThreadLocalRandom.current().nextInt(0, moves.size());
		if ( !moves.isEmpty() ){
			return moves.get(monkeMove);
        }else
			return new Position(-1,-1);
	}
	
}
