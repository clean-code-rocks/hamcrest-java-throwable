package rocks.cleancode.hamcrest.throwable;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static rocks.cleancode.hamcrest.throwable.ExceptionMatcher.willThrow;

public class ExceptionMatcherTest {

    @Test
    public void should_match_exception_type() {
        assertThat(requireNonNull(null), willThrow(NullPointerException.class));
    }

    @Test
    public void should_fail_when_exception_type_does_not_match() {
        AssertionError assertionError = assertThrows(
                AssertionError.class,
                () -> assertThat(requireNonNull(null), willThrow(IllegalArgumentException.class))
        );

        String expectedMessage = expectedMessage(
                "throws java.lang.IllegalArgumentException",
                "was java.lang.NullPointerException"
        );

        assertThat(assertionError.getMessage(), is(equalTo(expectedMessage)));
    }

    @Test
    public void should_fail_when_no_exception_is_thrown() {
        AssertionError assertionError = assertThrows(
                AssertionError.class,
                () -> assertThat(requireNonNull("Dummy value"), willThrow(NullPointerException.class))
        );

        String expectedMessage = expectedMessage(
                "throws java.lang.NullPointerException",
                "nothing was thrown"
        );

        assertThat(assertionError.getMessage(), is(equalTo(expectedMessage)));
    }

    private Runnable requireNonNull(Object value) {
        return () -> Objects.requireNonNull(value);
    }

    private String expectedMessage(String expected, String actual) {
        return String.format(
                "%nExpected: %s%n     but: %s",
                expected,
                actual
        );
    }

}
