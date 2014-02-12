package dius.bowling;

public class IntegerFormatter {

	// This function formats the provided Integer into a String with 3 digits max 
	// and a single letter to signify the unit of measure.
	// Time = O(n), Space = O(n) as the code traversal is linear.
	// Assumes that provided Integer is valid and non-negative.
    public String format(Integer integer) {
        
    	// generate suffix based on length of integer
        String intStr = String.valueOf(integer);
        int length = intStr.length();
        String suffix = "B";
        if (length > 9) {
            suffix = "G";
        }
        else if (length > 6) {
            suffix = "M";
        }
        else if (length > 3) {
            suffix = "K";
        }
          
        // get the left most 3 digits
        int prefixLength = 3;
        boolean needDecimal = true;
        if (length < 3) {
        	prefixLength = length;
        	needDecimal = false;
        }
        
        // determine decimal position based on length of original integer
        String prefix = intStr.substring(0, prefixLength);
        StringBuilder result = new StringBuilder();
        int decimalPosition = length%prefixLength;
    	String trailing = prefix.substring(decimalPosition, prefixLength);
    	
    	// trim trailing zeroes
    	if (trailing.equals("0") || trailing.equals("00")) {
    		prefix = prefix.substring(0, decimalPosition);
    		needDecimal = false;
    	}
        
    	if (decimalPosition == 0) {
    		needDecimal = false;
    	}
    	
    	// format the integer into the final result
        if (needDecimal) {        	
	        result.append(prefix.substring(0, decimalPosition));
	        result.append(".");
	        result.append(trailing);
	        result.append(suffix);
        }
        else {
        	result.append(prefix);
	        result.append(suffix);
        }
        
        return result.toString();
        
    }
	
}
