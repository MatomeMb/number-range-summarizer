package com.impact.numberrangesummarizer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive unit tests for NumberRangeSummarizerImpl.
 * 
 * Tests cover:
 * - Happy path scenarios
 * - Edge cases
 * - Error conditions
 * - Input validation
 * 
 * @author Matome Mbowene
 */
@DisplayName("NumberRangeSummarizer Tests")
class NumberRangeSummarizerImplTest {
    
    private NumberRangeSummarizer summarizer;
    
    @BeforeEach
    void setUp() {
        summarizer = new NumberRangeSummarizerImpl();
    }
    
    @Nested
    @DisplayName("collect() method tests")
    class CollectTests {
        
        @Test
        @DisplayName("Should collect valid comma-separated numbers")
        void shouldCollectValidNumbers() {
            Collection<Integer> result = summarizer.collect("1,3,6,7,8");
            
            assertEquals(5, result.size());
            assertTrue(result.containsAll(Arrays.asList(1, 3, 6, 7, 8)));
        }
        
        @Test
        @DisplayName("Should handle single number")
        void shouldHandleSingleNumber() {
            Collection<Integer> result = summarizer.collect("42");
            
            assertEquals(1, result.size());
            assertTrue(result.contains(42));
        }
        
        @Test
        @DisplayName("Should handle numbers with spaces")
        void shouldHandleNumbersWithSpaces() {
            Collection<Integer> result = summarizer.collect(" 1 , 2 , 3 ");
            
            assertEquals(3, result.size());
            assertTrue(result.containsAll(Arrays.asList(1, 2, 3)));
        }
        
        @Test
        @DisplayName("Should handle duplicate numbers")
        void shouldHandleDuplicateNumbers() {
            Collection<Integer> result = summarizer.collect("1,2,2,3");
            
            assertEquals(3, result.size());
            assertTrue(result.containsAll(Arrays.asList(1, 2, 3)));
        }
        
        @Test
        @DisplayName("Should handle negative numbers")
        void shouldHandleNegativeNumbers() {
            Collection<Integer> result = summarizer.collect("-3,-2,-1,0,1");
            
            assertEquals(5, result.size());
            assertTrue(result.containsAll(Arrays.asList(-3, -2, -1, 0, 1)));
        }
        
        @Test
        @DisplayName("Should return empty collection for empty string")
        void shouldReturnEmptyCollectionForEmptyString() {
            Collection<Integer> result = summarizer.collect("");
            assertTrue(result.isEmpty());
        }
        
        @Test
        @DisplayName("Should return empty collection for whitespace string")
        void shouldReturnEmptyCollectionForWhitespace() {
            Collection<Integer> result = summarizer.collect("   ");
            assertTrue(result.isEmpty());
        }
        
        @Test
        @DisplayName("Should throw exception for null input")
        void shouldThrowExceptionForNullInput() {
            assertThrows(IllegalArgumentException.class, 
                () -> summarizer.collect(null));
        }
        
        @ParameterizedTest
        @ValueSource(strings = {"abc", "1,abc,3", "1.5,2", "1,,3"})
        @DisplayName("Should throw exception for invalid number formats")
        void shouldThrowExceptionForInvalidFormats(String input) {
            assertThrows(IllegalArgumentException.class, 
                () -> summarizer.collect(input));
        }
        
        @Test
        @DisplayName("Should maintain sorted order")
        void shouldMaintainSortedOrder() {
            Collection<Integer> result = summarizer.collect("3,1,4,1,5,9,2");
            
            List<Integer> resultList = new ArrayList<>(result);
            List<Integer> expectedSorted = Arrays.asList(1, 2, 3, 4, 5, 9);
            
            assertEquals(expectedSorted, resultList);
        }
    }
    
    @Nested
    @DisplayName("summarizeCollection() method tests")
    class SummarizeCollectionTests {
        
        @Test
        @DisplayName("Should summarize the example from requirements")
        void shouldSummarizeExampleFromRequirements() {
            Collection<Integer> input = summarizer.collect("1,3,6,7,8,12,13,14,15,21,22,23,24,31");
            String result = summarizer.summarizeCollection(input);
            
            assertEquals("1, 3, 6-8, 12-15, 21-24, 31", result);
        }
        
        @Test
        @DisplayName("Should handle single number")
        void shouldHandleSingleNumber() {
            Collection<Integer> input = Arrays.asList(5);
            String result = summarizer.summarizeCollection(input);
            
            assertEquals("5", result);
        }
        
        @Test
        @DisplayName("Should handle two consecutive numbers")
        void shouldHandleTwoConsecutiveNumbers() {
            Collection<Integer> input = Arrays.asList(5, 6);
            String result = summarizer.summarizeCollection(input);
            
            assertEquals("5-6", result);
        }
        
        @Test
        @DisplayName("Should handle non-consecutive numbers")
        void shouldHandleNonConsecutiveNumbers() {
            Collection<Integer> input = Arrays.asList(1, 3, 5);
            String result = summarizer.summarizeCollection(input);
            
            assertEquals("1, 3, 5", result);
        }
        
        @Test
        @DisplayName("Should handle negative number ranges")
        void shouldHandleNegativeRanges() {
            Collection<Integer> input = Arrays.asList(-3, -2, -1, 1, 2);
            String result = summarizer.summarizeCollection(input);
            
            assertEquals("-3--1, 1-2", result);
        }
        
        @Test
        @DisplayName("Should handle mixed ranges and singles")
        void shouldHandleMixedRangesAndSingles() {
            Collection<Integer> input = Arrays.asList(1, 3, 4, 5, 7, 9, 10);
            String result = summarizer.summarizeCollection(input);
            
            assertEquals("1, 3-5, 7, 9-10", result);
        }
        
        @Test
        @DisplayName("Should return empty string for null collection")
        void shouldReturnEmptyStringForNull() {
            String result = summarizer.summarizeCollection(null);
            assertEquals("", result);
        }
        
        @Test
        @DisplayName("Should return empty string for empty collection")
        void shouldReturnEmptyStringForEmptyCollection() {
            String result = summarizer.summarizeCollection(new ArrayList<>());
            assertEquals("", result);
        }
        
        @ParameterizedTest
        @CsvSource({
            "'1,2,3,4,5', '1-5'",
            "'1,3,5,7,9', '1, 3, 5, 7, 9'",
            "'1,2,4,5,7,8,9', '1-2, 4-5, 7-9'",
            "'10,11,12,20,21,30', '10-12, 20-21, 30'"
        })
        @DisplayName("Should correctly format various input patterns")
        void shouldFormatVariousPatterns(String input, String expected) {
            Collection<Integer> numbers = summarizer.collect(input);
            String result = summarizer.summarizeCollection(numbers);
            
            assertEquals(expected, result);
        }
    }
    
    @Nested
    @DisplayName("Integration tests")
    class IntegrationTests {
        
        @Test
        @DisplayName("Should work end-to-end with the original example")
        void shouldWorkEndToEndWithOriginalExample() {
            String input = "1,3,6,7,8,12,13,14,15,21,22,23,24,31";
            String expected = "1, 3, 6-8, 12-15, 21-24, 31";
            
            Collection<Integer> collected = summarizer.collect(input);
            String result = summarizer.summarizeCollection(collected);
            
            assertEquals(expected, result);
        }
        
        @Test
        @DisplayName("Should handle complex real-world scenario")
        void shouldHandleComplexRealWorldScenario() {
            String input = "1,2,3,5,6,8,9,10,12,15,16,17,18,22,25,27,28,29,31";
            String expected = "1-3, 5-6, 8-10, 12, 15-18, 22, 25, 27-29, 31";
            
            Collection<Integer> collected = summarizer.collect(input);
            String result = summarizer.summarizeCollection(collected);
            
            assertEquals(expected, result);
        }
        
        @Test
        @DisplayName("Should handle unordered input correctly")
        void shouldHandleUnorderedInput() {
            String input = "8,1,3,2,15,14,13,12";
            String expected = "1-3, 8, 12-15";
            
            Collection<Integer> collected = summarizer.collect(input);
            String result = summarizer.summarizeCollection(collected);
            
            assertEquals(expected, result);
        }
    }
}
