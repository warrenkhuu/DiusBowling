package dius.bowling;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BowlingGameScoreTest {

	private static BowlingGameScore gameScore; 
	
	@Before
	public void init() {
		gameScore = new BowlingGameScore();
	}
	
	@Test
	public void testSetScoreBadValues() {
		// bad frames
		assertFalse(gameScore.setScore(0, 1, 10));
		assertFalse(gameScore.setScore(11, 1, 10));
		
		// bad rolls
		assertFalse(gameScore.setScore(1, -1, 10));
		assertFalse(gameScore.setScore(1, 3, 10));
		assertFalse(gameScore.setScore(10, 4, 10));
		
		// bad pins
		assertFalse(gameScore.setScore(1, 1, -10));
		assertFalse(gameScore.setScore(1, 1, 100));
		
		// too many pins in one frame
		gameScore.setScore(1, 1, 7);
		assertFalse(gameScore.setScore(1, 2, 5));
	}
	
	@Test
	public void testSetScore() {
		assertTrue(gameScore.setScore(1, 1, 4));
		assertTrue(gameScore.setScore(1, 2, 6));
		assertTrue(gameScore.setScore(2, 1, 10));
		assertTrue(gameScore.setScore(3, 1, 1));
	}

	@Test
	public void testIsSpare() {
		gameScore.setScore(2, 1, 5);
		gameScore.setScore(2, 2, 5);
		assertTrue(gameScore.isSpare(2));
		
		// final round
		gameScore.setScore(10, 1, 5);
		gameScore.setScore(10, 2, 5);
		assertTrue(gameScore.isSpare(2));
	}
	
	@Test
	public void testIsSpareFails() {
		gameScore.setScore(1, 1, 4);
		gameScore.setScore(1, 2, 5);
		assertFalse(gameScore.isSpare(1));
		
		// final round
		gameScore.setScore(10, 1, 5);
		gameScore.setScore(10, 2, 0);
		assertFalse(gameScore.isSpare(10));
	}	
	
	@Test
	public void testIsStrike() {
		gameScore.setScore(2, 1, 10);
		assertTrue(gameScore.isStrike(2));
		
		// final round
		gameScore.setScore(10, 1, 10);
		assertTrue(gameScore.isStrike(10));
	}
	
	@Test
	public void testIsStrikeFails() {
		gameScore.setScore(1, 1, 5);
		gameScore.setScore(1, 2, 5);
		assertFalse(gameScore.isStrike(1));
		
		// final round
		gameScore.setScore(10, 1, 0);
		assertFalse(gameScore.isStrike(10));
	}	
	
	@Test
	public void testGetScoreForFrameNormalRoll() {
		gameScore.setScore(1, 1, 0);
		gameScore.setScore(1, 2, 1);
		assertEquals(1, gameScore.getScoreForFrame(1));
		gameScore.setScore(2, 1, 1);
		gameScore.setScore(2, 2, 1);
		assertEquals(2, gameScore.getScoreForFrame(2));
	}

	@Test
	public void testGetScoreForFrameSplit() {
		gameScore.setScore(1, 1, 3);
		gameScore.setScore(1, 2, 7);
		assertEquals(10, gameScore.getScoreForFrame(1));
		gameScore.setScore(2, 1, 1);
		gameScore.setScore(2, 2, 9);
		assertEquals(11, gameScore.getScoreForFrame(1));
		assertEquals(10, gameScore.getScoreForFrame(2));
		gameScore.setScore(3, 1, 6);
		gameScore.setScore(3, 2, 4);
		assertEquals(16, gameScore.getScoreForFrame(2));
		assertEquals(10, gameScore.getScoreForFrame(3));
		gameScore.setScore(4, 1, 10);
		assertEquals(20, gameScore.getScoreForFrame(3));
		
		gameScore.setScore(10, 1, 1);
		gameScore.setScore(10, 2, 9);
		gameScore.setScore(10, 3, 5);
		assertEquals(15, gameScore.getScoreForFrame(10));
	}	
	
	@Test
	public void testGetScoreForFrameStrike() {
		gameScore.setScore(1, 1, 10);
		assertEquals(10, gameScore.getScoreForFrame(1));
		gameScore.setScore(2, 1, 10);
		assertEquals(20, gameScore.getScoreForFrame(1));
		assertEquals(10, gameScore.getScoreForFrame(2));
		gameScore.setScore(3, 1, 10);
		assertEquals(30, gameScore.getScoreForFrame(1));
		assertEquals(20, gameScore.getScoreForFrame(2));
		assertEquals(10, gameScore.getScoreForFrame(3));
		gameScore.setScore(4, 1, 2);		
		assertEquals(22, gameScore.getScoreForFrame(2));
		assertEquals(12, gameScore.getScoreForFrame(3));
		gameScore.setScore(4, 2, 3);		
		assertEquals(22, gameScore.getScoreForFrame(2));
		assertEquals(15, gameScore.getScoreForFrame(3));
		
		gameScore.setScore(9, 1, 10);
		gameScore.setScore(10, 1, 1);
		gameScore.setScore(10, 2, 4);
		assertEquals(15, gameScore.getScoreForFrame(9));
	}
}
