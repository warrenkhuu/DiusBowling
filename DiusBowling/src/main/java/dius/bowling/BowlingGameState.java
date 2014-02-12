package dius.bowling;

/**
 * @author Warren Khuu
 * 
 * This class keeps track of the game state. ie.
 * the current frame, current roll, number of pins remaining
 */
public class BowlingGameState {

	private int pinsRemaining = BowlingGame.NUMBER_OF_PINS;
	private int frameCount    = 1;
	private int rollCount     = 1;

	/**
	 * Processes a roll and increments the frame/roll count accordingly. Performs basic validation 
	 * @param pinsKnocked
	 * @return true if roll was successful, false if validation failed
	 */
	public boolean roll(int pinsKnocked) {
		// fail if pinsKnocked is invalid
		if (pinsKnocked > pinsRemaining || pinsKnocked < 0) {
			return false;
		}
		
		// fail if game is over
		if (frameCount > BowlingGame.MAX_FRAME_COUNT) {
			return false;
		}
		
		// update game state
		pinsRemaining = pinsRemaining - pinsKnocked;
		rollCount++;
		
		// final round conditional updates
		if (BowlingGame.isFinalRound(frameCount)) {
			// failed to get extra roll
			if (rollCount > BowlingGame.MAX_ROLLS_NORMAL_FRAME && pinsRemaining > 0) {
				frameCount++;
			}
			// extra roll finished
			if (rollCount > BowlingGame.MAX_ROLLS_FINAL_FRAME) {
				frameCount++;
			}
			// reset the pins for spare/strike
			if (pinsRemaining == 0) {
				pinsRemaining = BowlingGame.NUMBER_OF_PINS;
			}			
		}
		
		// normal round update
		else {
			if (rollCount > BowlingGame.MAX_ROLLS_NORMAL_FRAME || pinsRemaining == 0) {
				frameCount++;
				rollCount = 1;
				pinsRemaining = BowlingGame.NUMBER_OF_PINS;
			}
		}
		
		return true;
	}
	
	public int getPins() {
		return pinsRemaining;
	}
	
	public int getFrameCount() {
		return frameCount;
	}
	
	public int getRollCount() {
		return rollCount;
	}
	
}
