package dius.bowling;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Warren Khuu
 * 
 * This class keeps track of the game score
 * Each roll is stored separately and aggregated when the score() method is called 
 */
public class BowlingGameScore {

	private List<List<Integer>> scoreChart;
	
	/**
	 * Init the scoreChart ArrayList with empty values to be populated by user 
	 */
	public BowlingGameScore() {
		scoreChart = new ArrayList<List<Integer>>();
		// rounds 1-9 have 2 roll score slots
		for (int i=0; i < BowlingGame.MAX_FRAME_COUNT-1; i++) {
			List<Integer> rollChart = new ArrayList<Integer>();
			for (int j=0; j < BowlingGame.MAX_ROLLS_NORMAL_FRAME; j++) {
				rollChart.add(null);
			}
			scoreChart.add(rollChart);
		}
		// final round has 3 roll score slots
		List<Integer> rollChart = new ArrayList<Integer>();
		for (int j=0; j <= BowlingGame.MAX_ROLLS_FINAL_FRAME; j++) {
			rollChart.add(null);
		}
		scoreChart.add(rollChart);
		
	}
	
	/**
	 * Add score for a given frame and roll count. Performs some basic validation on the parameters
	 * @param frameCount
	 * @param rollCount
	 * @param pins
	 * @return true when score is added successfully, false if basic validation fails
	 */
	public boolean setScore(int frameCount, int rollCount, int pins) {
		// parameter checking
		if (frameCount > BowlingGame.MAX_FRAME_COUNT || frameCount < 1) {
			return false;
		}
		if (BowlingGame.isFinalRound(frameCount)) {
			if (rollCount > BowlingGame.MAX_ROLLS_FINAL_FRAME || rollCount < 1) {
				return false;
			}
		}
		else {
			if (rollCount > BowlingGame.MAX_ROLLS_NORMAL_FRAME || rollCount < 1) {
				return false;
			}
		}
		if (pins < 0 || pins > BowlingGame.NUMBER_OF_PINS) {
			return false;
		}
		
		// cant roll more than 10 pins in a normal frame
		if (!BowlingGame.isFinalRound(frameCount)) {
			int otherRollIndex = BowlingGame.MAX_ROLLS_NORMAL_FRAME - rollCount;
			int total = pins;
			Integer otherRoll = scoreChart.get(frameCount-1).get(otherRollIndex); 
			if (otherRoll != null) {
				total += otherRoll;
			}
			if (total > BowlingGame.NUMBER_OF_PINS) {
				return false;
			}
		}
		
		scoreChart.get(frameCount-1).set(rollCount-1, pins);		
		return true;
	}
	
	/**
	 * Checks first two rolls of given frame, return true if they total 10
	 * @param frameCount
	 * @return true if frame counts as a spare
	 */
	protected boolean isSpare(int frameCount) {
		List<Integer> rollChart = scoreChart.get(frameCount-1);
		int total = 0;
		// only look at first two (in case this is final frame)
		for (int i=0; i<2; i++) {
			Integer value = rollChart.get(i);
			if (value != null) {
				// it's actually a strike
				if (value == BowlingGame.NUMBER_OF_PINS) {
					return false;
				}
				total += value;
			}
		}
		return (total == BowlingGame.NUMBER_OF_PINS);
	}
	
	/**
	 * @param frameCount
	 * @return true if the frame is a strike
	 */
	protected boolean isStrike(int frameCount) {
		Integer value = scoreChart.get(frameCount-1).get(0);
		return (value != null && value == BowlingGame.NUMBER_OF_PINS);
	}
	
	/**
	 * Returns the score for a given frame. If the frame is a split/strike it attempts to
	 * look ahead to add the rolls from later frames
	 * @return score for the given frame
	 */
	public int getScoreForFrame(int frameCount) {		
		List<Integer> rollChart = scoreChart.get(frameCount-1);
		int total = 0;				
		
		// add all scores for the current frame
		for (Integer value : rollChart) {
			if (value != null) {
				total += value;
			}
		}
		
		// look ahead into future frames for bonus scores
		if (!BowlingGame.isFinalRound(frameCount)) {
			
			if (isSpare(frameCount) || isStrike(frameCount)) {
				// first roll of frame+1
				Integer value1 = scoreChart.get(frameCount).get(0);
				if (value1 != null) {
					total += value1;
				}
			}
			
			if (isStrike(frameCount)) {				
				// next roll is a strike too, look ahead frame+2
				if (isStrike(frameCount+1) && !BowlingGame.isFinalRound(frameCount+1)) {
					Integer value2 = scoreChart.get(frameCount+1).get(0);
					if (value2 != null) {
						total += value2;
					}
				}
				// add the second roll in frame+1
				else {
					Integer value2 = scoreChart.get(frameCount).get(1);
					if (value2 != null) {
						total += value2;
					}
				}
			}
			
		}
		
		return total;
	}
	
}
