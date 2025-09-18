package com.impact.numberrangesummarizer;

import java.util.Collection;

/**
 * Interface for producing comma-delimited lists of numbers,
 * grouping sequential numbers into ranges.
 * 
 * Example:
 * Input: "1,3,6,7,8,12,13,14,15,21,22,23,24,31"
 * Output: "1, 3, 6-8, 12-15, 21-24, 31"
 * 
 * @author Werner
 */
public interface NumberRangeSummarizer {
    
    /**
     * Collect and parse the input string into a collection of integers.
     * 
     * @param input comma-delimited string of numbers
     * @return collection of integers parsed from input
     * @throws IllegalArgumentException if input is invalid
     */
    Collection<Integer> collect(String input);
    
    /**
     * Summarize the collection of integers into a formatted string
     * with ranges for sequential numbers.
     * 
     * @param input collection of integers to summarize
     * @return formatted string with ranges
     */
    String summarizeCollection(Collection<Integer> input);
}
