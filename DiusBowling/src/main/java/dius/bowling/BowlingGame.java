package dius.bowling;

/**
 * @author Warren Khuu
 *
 */
public class BowlingGame {

	public static int MAX_FRAME_COUNT        = 10;
	public static int MAX_ROLLS_NORMAL_FRAME = 2;
	public static int MAX_ROLLS_FINAL_FRAME  = 3;
	public static int NUMBER_OF_PINS         = 10;

	public static boolean isFinalRound(int frameCount) {
		return frameCount >= BowlingGame.MAX_FRAME_COUNT;
	}	
	
	private BowlingGameScore score;
	private BowlingGameState state;
	
	public BowlingGame() {
		score = new BowlingGameScore();
		state = new BowlingGameState();
	}
	
	/**
	 * Update the game state and score based on the roll
	 * Fails silently if the roll is invalid
	 */
	public void roll(int pinsKnocked) {
		int frameCount = state.getFrameCount();
		int rollCount  = state.getRollCount();
		if (state.roll(pinsKnocked)) {
			score.setScore(frameCount, rollCount, pinsKnocked);
		}
	}
	
	/**
	 * 
	 * @return the total score across all 10 frames
	 */
	public int score() {
		int total = 0;
		for (int frameCount=1; frameCount <= MAX_FRAME_COUNT; frameCount++) {
			total += score.getScoreForFrame(frameCount);
		}
		return total;
	}
	
}
