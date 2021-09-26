import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringCalculator {

    public static final String DEFAULT_DELIMITER = "[,\\n]";
    public static final String REGEX_DELIMITER_NUMBERS = "(//(.*)\\n)?(.*)";

    public int add(String s) throws NegativeNumberException, NotMatchingPatternException {
        if (StringUtils.isBlank(s))
            return 0;

        List<Integer> listOfNumbers = getNumbers(s);
        var listOfNegativeNumbers = listOfNumbers.stream().filter(i -> i < 0).collect(Collectors.toList());

        if (!listOfNegativeNumbers.isEmpty())
            throw new NegativeNumberException("negatives not allowed " + listOfNegativeNumbers.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", ")));

        return listOfNumbers.stream().reduce(0, Integer::sum);
    }

    /**
     * Gets the list of numbers from a string
     *
     * @param s string with first line contains delimiter(optional, default delimiter is , or \n),
     *          second line contains the list of numbers separated with delimiter
     * @return List of numbers
     * @throws NotMatchingPatternException
     */
    private List<Integer> getNumbers(String s) throws NotMatchingPatternException {
        var matcher = Pattern.compile(REGEX_DELIMITER_NUMBERS, Pattern.DOTALL).matcher(s);
        if (!matcher.find())
            throw new NotMatchingPatternException();

        var delimiter = Optional.ofNullable(matcher.group(2)).orElse(DEFAULT_DELIMITER);
        var numbers = Optional.ofNullable(matcher.group(3)).orElse("");

        return Arrays.stream(numbers.split(delimiter))
                .filter(StringUtils::isNotBlank)
                .map(Integer::valueOf)
                .collect(Collectors.toList());
    }
}
