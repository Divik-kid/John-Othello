import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
/**
 * A simple OthelloAI-implementation. The method to decide the next move just
 * returns the first legal move that it finds. 
 * @author Mai Ajspur
 * @version 9.2.2018
 */
public class MonkeAI implements IOthelloAI{

	/**
	 * Returns Random legal move
	 */
	public Position decideMove(GameState s){
		ArrayList<Position> moves = s.legalMoves();
        var monke = ThreadLocalRandom.current().nextInt(0, moves.size());
		if ( !moves.isEmpty() )
            
			return moves.get(monke);
		else
			return new Position(-1,-1);
	}
	
}
