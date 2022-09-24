package rocks.cleancode.hamcrest.throwable;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static rocks.cleancode.hamcrest.throwable.MessageMatcher.message;
import static rocks.cleancode.hamcrest.throwable.WillThrowMatcher.willThrow;

public class WillThrowMatcherTest {

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

        String expectedMessage = String.format(
                "%n%s%n%s",
                "Expected: throws java.lang.IllegalArgumentException",
                "     but: was java.lang.NullPointerException"
        );

        assertThat(assertionError.getMessage(), is(equalTo(expectedMessage)));
    }

    @Test
    public void should_fail_when_no_throwable_is_thrown() {
        AssertionError assertionError = assertThrows(
                AssertionError.class,
                () -> assertThat(requireNonNull("Dummy value"), willThrow(NullPointerException.class))
        );

        String expectedMessage = String.format(
                "%n%s%n%s",
                "Expected: throws java.lang.NullPointerException",
                "     but: nothing was thrown"
        );

        assertThat(assertionError.getMessage(), is(equalTo(expectedMessage)));
    }

    @Test
    public void should_match_throwable_type_and_message() {
        assertThat(
                requireNonNull(null, "My exception message"),
                willThrow(NullPointerException.class)
                        .and(message(is(equalTo("My exception message"))))
        );
    }

    @Test
    public void should_fail_when_message_does_not_match() {
        AssertionError assertionError = assertThrows(
                AssertionError.class,
                () -> assertThat(
                        requireNonNull(null, "My exception message"),
                        willThrow(NullPointerException.class)
                                .and(message(is(equalTo("Other expected message"))))
                )
        );

        String expectedMessage = String.format(
                "%n%s%n%s",
                "Expected: throws java.lang.NullPointerException and message is \"Other expected message\"",
                "     but: message was \"My exception message\""
        );

        assertThat(assertionError.getMessage(), is(equalTo(expectedMessage)));
    }

    private static Runnable requireNonNull(Object value, String message) {
        return () -> Objects.requireNonNull(value, message);
    }

    private static Runnable requireNonNull(Object value) {
        return requireNonNull(value, null);
    }

}
