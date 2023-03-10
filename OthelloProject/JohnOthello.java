import java.util.ArrayList;
import java.io.BufferedReader;
//imports used for logging times in log.txt such that an average time can be calculated
import java.io.File;
import java.io.FileReader;
import java.io.IOException; 
import java.io.FileWriter;
import java.util.Scanner;

public class JohnOthello implements IOthelloAI {

	// GameTree topOfGameTree;
	// boolean firstMove = false;
	public int searchDepth;

	public Position decideMove(GameState s) {
		GameState state = s;
		searchDepth = 3;	
		
		//increases the search depth according to total tokens on the board
		if(totalTokens(s) >= 20){
			searchDepth++;
			if(totalTokens(s)>= 30){
				searchDepth++;
				if(totalTokens(s) >= 40){
					if (totalTokens(s) >= 50){
						searchDepth+=2;
					}
					searchDepth+=3;
				}
			}
		}
		System.out.println("I N S T A L L :" + searchDepth);
		ArrayList<Position> moves = state.legalMoves();
		if (!moves.isEmpty()){
			return MinimaxSearch(s);
		}
			return new Position(-1, -1);
		
	}

	public void createTimeLog(Double time){
		try {
			String filePath = "TimeLog.txt"; // Replace with the path to your file
      		  File file = new File(filePath);
       		 if (file.exists()) {
            	writeTimelog(file, new FileWriter(file.getAbsolutePath(),true), time);
        	} else {
				File tLog = new File("TimeLog.txt");
				FileWriter logWriter = new FileWriter(tLog,true);
				writeTimelog(new File(filePath), logWriter, time);
       		}
			
		  } catch (IOException e) {
			System.out.println("An error occurred when creating log file");
			e.printStackTrace();
		  }
	}

	public void writeTimelog(File tLog, FileWriter logWriter, Double time){
		try{
			Scanner file = new Scanner(new File("TimeLog.txt"));
			logWriter.write(time.toString() + System.lineSeparator());
			
			logWriter.close();
			file.close();	
		} catch (IOException e) {
			System.out.println("An error occurred when writing log file");
			e.printStackTrace();
		  }
				
	}

	public int totalTokens(GameState s){
		return s.countTokens()[1]+s.countTokens()[0];
	}

	public Position MinimaxSearch(GameState s) {
		var startTime = System.nanoTime();
		var currentPlayer = s.getPlayerInTurn();
		double time;
		if (currentPlayer == 1) {
			//If Black Then Play as Max
			var x = MaxValue(s, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
			System.out.println(x.getPos() + " EVAL: " + x.getUtil());
			System.out.println("TIME: " + (System.nanoTime()-startTime)/1_000_000_000.0);
			time =((System.nanoTime()-startTime)/1_000_000_000.0);
			createTimeLog(time);
			return x.getPos();
		} else if (currentPlayer == 2) {
			//If White Then Play as Min
			var y = MinValue(s, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
			System.out.println(y.getPos() + " EVAL: " + y.getUtil());
			System.out.println("TIME: " + (System.nanoTime()-startTime)/1_000_000_000.0);
			time =((System.nanoTime()-startTime)/1_000_000_000.0);
			createTimeLog(time);
			return y.getPos();
		}
		//No Possible moves left
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
			//Pruning
			if (counter >= searchDepth) {
				if (beta.getUtil() > trueBeta)
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
			//Pruning
			if (counter >= searchDepth) {
				if (alpha.getUtil() < trueBeta)
					return alpha;
			}
		}

		return alpha;
	}

	
	//Returns the lesser of two integers
	private int Min(int trueBeta, Integer util) {
		if (util < trueBeta) {
			return util;
		} else
			return trueBeta;

	}

	//Returns the greater of two integers
	private int Max(Integer util, int trueAlpha) {
		if (util > trueAlpha) {
			return util;
		} else
			return trueAlpha;
	}

	//Calculates the utility of the gamestate
	public int calculateUtility(GameState s) {
		// Positive values are good for black, negative values are good for white
		var board = s.getBoard();
		int tokens1 = 0;
		int tokens2 = 0;
		var size = board.length;

		//on the final part of the game, prioritises total tokens over positioning
		if(searchDepth<=5){
		//Board position evaluation
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (board[i][j] == 1)
						if (((i == 0 && j == 0) || (i == 0 && j == size - 1) || (i == size - 1 && j == 0)
					|| (i == size - 1 && j == size - 1)) || (i==0 && (j!=1 || j!=size-2) || i==size-1 && (j!=1 || j!=size-2))) {
					//Corners
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
		}else{
			//pure token amount evaluation
			tokens2 = s.countTokens()[1];
			tokens1 = s.countTokens()[0];
		}
		
		int totalValue = tokens1 - tokens2;
		return totalValue;
	}

}
