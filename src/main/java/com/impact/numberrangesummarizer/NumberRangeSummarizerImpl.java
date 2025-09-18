package com.impact.numberrangesummarizer;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of NumberRangeSummarizer interface.
 * 
 * This class provides functionality to:
 * 1. Parse comma-delimited number strings
 * 2. Group sequential numbers into ranges
 * 3. Format the output as specified
 * 
 * @author Matome Mbowene
 */
public class NumberRangeSummarizerImpl implements NumberRangeSummarizer {
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Integer> collect(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        
        if (input.trim().isEmpty()) {
            return new TreeSet<>();
        }
        
        return Arrays.stream(input.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(this::parseInteger)
                .collect(Collectors.toCollection(TreeSet::new));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String summarizeCollection(Collection<Integer> input) {
        if (input == null || input.isEmpty()) {
            return "";
        }
        
        List<Integer> sortedNumbers = input.stream()
                .sorted()
                .collect(Collectors.toList());
        
        List<String> ranges = new ArrayList<>();
        int start = 0;
        
        while (start < sortedNumbers.size()) {
            int end = findEndOfRange(sortedNumbers, start);
            ranges.add(formatRange(sortedNumbers, start, end));
            start = end + 1;
        }
        
        return String.join(", ", ranges);
    }
    
    /**
     * Parse a string to integer with proper error handling.
     * 
     * @param numberString string to parse
     * @return parsed integer
     * @throws IllegalArgumentException if string cannot be parsed
     */
    private Integer parseInteger(String numberString) {
        try {
            return Integer.parseInt(numberString);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                "Invalid number format: '" + numberString + "'", e);
        }
    }
    
    /**
     * Find the end index of a sequential range starting at the given index.
     * 
     * @param numbers sorted list of numbers
     * @param start starting index
     * @return end index of the range (inclusive)
     */
    private int findEndOfRange(List<Integer> numbers, int start) {
        int current = start;
        
        while (current < numbers.size() - 1 && 
               numbers.get(current + 1) == numbers.get(current) + 1) {
            current++;
        }
        
        return current;
    }
    
    /**
     * Format a range of numbers as either a single number or "start-end" format.
     * 
     * @param numbers sorted list of numbers
     * @param start starting index (inclusive)
     * @param end ending index (inclusive)
     * @return formatted range string
     */
    private String formatRange(List<Integer> numbers, int start, int end) {
        if (start == end) {
            return numbers.get(start).toString();
        } else {
            return numbers.get(start) + "-" + numbers.get(end);
        }
    }
}
