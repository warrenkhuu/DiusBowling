package dius.bowling;

import static org.junit.Assert.*;

import org.junit.Test;

// Test for IntegerFormatter
public class IntegerFormatterTest {

	    @Test
	    public void testFormat() {
	        IntegerFormatter formatter = new IntegerFormatter();
	        assertEquals("3B",    	formatter.format(3));
	        assertEquals("34B",    	formatter.format(34));
	        assertEquals("341B",    formatter.format(341));
	        assertEquals("34.2K",   formatter.format(34200));
	        assertEquals("5.91M",   formatter.format(5910000));
	        assertEquals("5M",   	formatter.format(5000000));
	        assertEquals("50M",   	formatter.format(50000000));
	        assertEquals("500M",    formatter.format(500000000));
	        assertEquals("1G",      formatter.format(1000000000));	        
	    }
	
}
