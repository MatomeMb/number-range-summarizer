# Number Range Summarizer

A Java utility that produces comma-delimited lists of numbers, grouping sequential numbers into ranges.

## Overview

This project implements the `NumberRangeSummarizer` interface to convert comma-separated number strings into formatted ranges.

### Example
- **Input:** `"1,3,6,7,8,12,13,14,15,21,22,23,24,31"`
- **Output:** `"1, 3, 6-8, 12-15, 21-24, 31"`

## Requirements

- Java 8 or higher
- Maven 3.6 or higher

## Project Structure

```
number-range-summarizer/
├── src/
│   ├── main/java/com/impact/numberrangesummarizer/
│   │   ├── NumberRangeSummarizer.java          # Interface
│   │   └── NumberRangeSummarizerImpl.java      # Implementation
│   └── test/java/com/impact/numberrangesummarizer/
│       └── NumberRangeSummarizerImplTest.java  # Unit tests
├── pom.xml                                     # Maven configuration
└── README.md                                   # This file
```

## Building and Testing

### Build the project
```bash
mvn clean compile
```

### Run tests
```bash
mvn test
```

### Generate test coverage report
```bash
mvn jacoco:report
```

### Package the application
```bash
mvn clean package
```

## Usage

```java
NumberRangeSummarizer summarizer = new NumberRangeSummarizerImpl();

// Parse input
Collection<Integer> numbers = summarizer.collect("1,3,6,7,8,12,13,14,15");

// Generate summary
String result = summarizer.summarizeCollection(numbers);
System.out.println(result); // Output: "1, 3, 6-8, 12-15"
```

## Features

- **Input validation**: Handles null, empty, and malformed inputs gracefully
- **Duplicate handling**: Automatically removes duplicate numbers
- **Flexible parsing**: Handles whitespace and various input formats
- **Negative numbers**: Supports negative number ranges
- **Comprehensive testing**: 100% code coverage with edge case testing

## Design Decisions

1. **TreeSet for collection**: Automatically handles sorting and duplicate removal
2. **Stream API**: Leverages Java 8 streams for functional programming approach
3. **Comprehensive validation**: Robust error handling with meaningful messages
4. **Single Responsibility**: Each method has a clear, focused purpose
5. **Immutable approach**: Methods don't modify input parameters

## Author

Matome Mbowene - Impact.com Take Home Assessment
