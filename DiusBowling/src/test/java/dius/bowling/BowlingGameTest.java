package dius.bowling;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BowlingGameTest {

	private BowlingGame game;
	
	@Before
	public void init() {
		game = new BowlingGame();
	}

	@Test
	public void testPerfectGame() {
		// normal rounds
		for(int i=0; i<9; i++) {
			game.roll(10);		
		}
		// final round
		game.roll(10);
		game.roll(10);
		game.roll(10);
		assertEquals(300, game.score());		
	}
	
	@Test
	public void testBadgame() {
		for(int i=0; i<10; i++) {
			game.roll(0);		
		}
		assertEquals(0, game.score());
	}
	
	@Test
	public void testNormal() {
		// round 1
		game.roll(6); 
		game.roll(0);
		assertEquals(6, game.score());
		
		// round 2
		game.roll(3); 
		game.roll(4);
		assertEquals(13, game.score());
	}
	
	@Test
	public void testSplit() {
		// round 1
		game.roll(5); 
		game.roll(5);
		assertEquals(10, game.score());
		
		// round 2
		game.roll(5);
		assertEquals(20, game.score());
		game.roll(5);
		assertEquals(25, game.score());
		
		// round 3
		game.roll(3);
		assertEquals(31, game.score());
		game.roll(7);
		assertEquals(38, game.score());
	}
	
	@Test
	public void testStrike() {
		// round 1
		game.roll(10); 
		assertEquals(10, game.score());
		
		// round 2
		game.roll(3); 
		game.roll(4);
		assertEquals(24, game.score());
		
		// round 3
		game.roll(10); 
		assertEquals(34, game.score());
		
		// round 4
		game.roll(10);
		
		// round 5
		game.roll(6);
		game.roll(4);
		assertEquals(80, game.score());
		
		// round 6
		game.roll(7);
		game.roll(1);
		assertEquals(95, game.score());
	}
	
	@Test
	public void testFinalRoundSplit() {
		for(int i=0; i<8; i++) {
			game.roll(10);		
		}
		assertEquals(210, game.score());
		
		// round 9
		game.roll(5);
		game.roll(5);
		assertEquals(235, game.score());
		
		// round 10
		game.roll(5);
		game.roll(5);
		game.roll(1);
		assertEquals(251, game.score());
	}
}


