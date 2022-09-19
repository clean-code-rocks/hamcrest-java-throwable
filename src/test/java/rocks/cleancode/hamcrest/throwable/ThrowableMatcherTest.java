package rocks.cleancode.hamcrest.throwable;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static rocks.cleancode.hamcrest.throwable.MessageMatcher.message;
import static rocks.cleancode.hamcrest.throwable.ThrowableMatcher.exception;
import static rocks.cleancode.hamcrest.throwable.ThrowableMatcher.willThrow;

public class ThrowableMatcherTest {

    @Test
    public void should_match_throwable_type() {
        assertThat(requireNonNull(null), willThrow(NullPointerException.class));
    }

    @Test
    public void should_fail_when_throwable_type_does_not_match() {
        AssertionError assertionError = assertThrows(
                AssertionError.class,
                () -> assertThat(requireNonNull(null), willThrow(IllegalArgumentException.class))
        );

        String expectedMessage = expectedMessage(
                "throwable is an instance of java.lang.IllegalArgumentException",
                "was java.lang.NullPointerException"
        );

        assertThat(assertionError.getMessage(), is(equalTo(expectedMessage)));
    }

    @Test
    public void should_fail_when_no_throwable_is_thrown() {
        AssertionError assertionError = assertThrows(
                AssertionError.class,
                () -> assertThat(requireNonNull("Dummy value"), willThrow(NullPointerException.class))
        );

        String expectedMessage = expectedMessage(
                "throwable is an instance of java.lang.NullPointerException",
                "nothing was thrown"
        );

        assertThat(assertionError.getMessage(), is(equalTo(expectedMessage)));
    }

    @Test
    public void should_match_exception() {
        assertThat(requireNonNull(null), exception(message(is(equalTo("Value cannot be null")))));
    }

    private Runnable requireNonNull(Object value) {
        return () -> Objects.requireNonNull(value, "Value cannot be null");
    }

    private String expectedMessage(String expected, String actual) {
        return String.format(
                "%nExpected: %s%n     but: %s",
                expected,
                actual
        );
    }

}
