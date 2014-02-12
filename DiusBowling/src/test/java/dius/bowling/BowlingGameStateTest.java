package dius.bowling;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BowlingGameStateTest {

	private static BowlingGameState gameState;
	
	@Before
	public void init() {
		gameState = new BowlingGameState();
	}
	
	@Test
	public void testInitGamestate() {
		assertEquals(10, gameState.getPins());
		assertEquals(1, gameState.getFrameCount());
		assertEquals(1, gameState.getRollCount());		
	}
	
	@Test
	public void testRoll() {
		assertTrue(gameState.roll(1));
		assertTrue(gameState.roll(2));
		assertTrue(gameState.roll(4));
		assertTrue(gameState.roll(6));
		assertTrue(gameState.roll(10));
	}
	
	@Test
	public void testInvalidRollFails() {
		// bad numbers
		assertFalse(gameState.roll(-1));
		assertFalse(gameState.roll(11));
		
		// not enough remaining pins
		gameState.roll(9);
		assertFalse(gameState.roll(9));
		gameState.roll(1);
	}
	
	@Test
	public void testRollCountIncremented() {
		// normal roll
		gameState.roll(1);
		assertEquals(2, gameState.getRollCount());
		gameState.roll(1);
		assertEquals(1, gameState.getRollCount());
		
		// split
		gameState.roll(3);
		assertEquals(2, gameState.getRollCount());
		gameState.roll(7);
		assertEquals(1, gameState.getRollCount());
		
		// strike
		gameState.roll(10);
		assertEquals(1, gameState.getRollCount());
	}
	
	@Test
	public void testFrameCountIncremented() {
		// normal rolls
		gameState.roll(1);
		assertEquals(1, gameState.getFrameCount());
		gameState.roll(1);
		assertEquals(2, gameState.getFrameCount());
		
		// split
		gameState.roll(4);
		assertEquals(2, gameState.getFrameCount());
		gameState.roll(6);
		assertEquals(3, gameState.getFrameCount());
		
		// strike
		gameState.roll(10);
		assertEquals(4, gameState.getFrameCount());
		gameState.roll(1);
		assertEquals(4, gameState.getFrameCount());
		gameState.roll(1);
		assertEquals(5, gameState.getFrameCount());
	}

	@Test
	public void testPinCount() {
		// normal roll
		gameState.roll(1);
		assertEquals(9, gameState.getPins());
		gameState.roll(3);
		assertEquals(10, gameState.getPins());
		
		// split
		gameState.roll(5);
		assertEquals(5, gameState.getPins());
		gameState.roll(5);
		assertEquals(10, gameState.getPins());
		
		// strike
		gameState.roll(10);
		assertEquals(10, gameState.getPins());
	}
	
	@Test
	public void testFinalRoundNoBonusRoll() {
		for (int i=0; i<9; i++) {
			gameState.roll(10);
		}
		assertEquals(10, gameState.getFrameCount());
		gameState.roll(1);
		gameState.roll(1);
		
		// assert game end condition
		assertFalse(gameState.roll(1));
	}
	
	@Test
	public void testFinalRoundStrike() {
		for (int i=0; i<9; i++) {
			gameState.roll(10);
		}
		
		// roll 1
		gameState.roll(10);
		assertEquals(10, gameState.getFrameCount());
		assertEquals(10, gameState.getPins());
		assertEquals(2, gameState.getRollCount());
		
		// roll 2
		gameState.roll(10);
		assertEquals(10, gameState.getFrameCount());
		assertEquals(10, gameState.getPins());
		assertEquals(3, gameState.getRollCount());
		
		// roll 3 - assert game end condition
		gameState.roll(1);
		assertFalse(gameState.roll(1));
	}
	
	@Test
	public void testFinalRoundSpare() {
		for (int i=0; i<9; i++) {
			gameState.roll(10);
		}

		// roll 1
		gameState.roll(4);
		assertEquals(10, gameState.getFrameCount());
		assertEquals(6, gameState.getPins());
		assertEquals(2, gameState.getRollCount());
		
		// roll 2
		gameState.roll(6);
		assertEquals(10, gameState.getFrameCount());
		assertEquals(10, gameState.getPins());
		assertEquals(3, gameState.getRollCount());
		
		// roll 3 - assert game end condition
		gameState.roll(1);
		assertFalse(gameState.roll(1));
	}	
}
