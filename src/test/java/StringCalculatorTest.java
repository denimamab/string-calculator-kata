import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringCalculatorTest {

    private final StringCalculator stringCalculator;

    public StringCalculatorTest() {
        stringCalculator = new StringCalculator();
    }

    @Test
    void shouldReturnZeroWhenCallingAddGivenEmptyString() throws NegativeNumberException, NotMatchingPatternException {
        assertEquals(0, stringCalculator.add(""));
    }

    @Test
    void shouldReturnTheGivenNumberWhenCallingAddGivenStringWithOnlyOneNumber() throws NegativeNumberException, NotMatchingPatternException {
        assertEquals(1, stringCalculator.add("1"));
    }

    @Test
    void shouldReturnThreeWhenCallingAddGivenStringWithOneAndTwo() throws NegativeNumberException, NotMatchingPatternException {
        assertEquals(3, stringCalculator.add("1,2"));
    }

    @Test
    void shouldReturnSixWhenCallingAddGivenOneTwoThree() throws NegativeNumberException, NotMatchingPatternException {
        assertEquals(6, stringCalculator.add("1,2,3"));
    }

    @Test
    void shouldReturnSixWhenCallingAddGivenStringWithTwoDifferentDelimiters() throws NegativeNumberException, NotMatchingPatternException {
        assertEquals(6, stringCalculator.add("1\n2,3"));
    }

    @Test
    void shouldReturnThreeWhenCallingAddGivenStringWithDelimiterOnFirstLineAndNumbersOnSecondLine() throws NegativeNumberException, NotMatchingPatternException {
        assertEquals(3, stringCalculator.add("//;\n1;2"));
    }

    @Test
    void shouldThrowExceptionWhenCallingAddGivenStringWithNegativeNumber() {
        NegativeNumberException negativeNumberException = assertThrows(NegativeNumberException.class, () -> stringCalculator.add("-1"));

        assertEquals("negatives not allowed -1", negativeNumberException.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCallingAddGivenStringWithMultipleNegativeNumbers() {
        NegativeNumberException negativeNumberException = assertThrows(NegativeNumberException.class, () -> stringCalculator.add("0,1,2,-1,34,-2"));

        assertEquals("negatives not allowed -1, -2", negativeNumberException.getMessage());
    }

    @Test
    void shouldReturnZeroWhenCallingAddGivenDelimiterAndNoNumbers() throws NegativeNumberException, NotMatchingPatternException {
        assertEquals(0, stringCalculator.add("//;\n"));
    }
}