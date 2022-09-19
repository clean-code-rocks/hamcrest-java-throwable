package rocks.cleancode.hamcrest.throwable;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static rocks.cleancode.hamcrest.throwable.MessageMatcher.message;

public class MessageMatcherTest {

    @Test
    public void should_match_exception_message() {
        Exception exception = new NullPointerException("My exception message");

        assertThat(exception, message(is(equalTo("My exception message"))));
    }

    @Test
    public void should_fail_when_testing_exception_message() {
        Exception exception = new NullPointerException("My exception message");

        AssertionError assertionError = assertThrows(
                AssertionError.class,
                () -> assertThat(exception, message(is(equalTo("Other expected message"))))
        );

        String expectedMessage = String.format(
                "%n%s%n%s",
                "Expected: exception message is \"Other expected message\"",
                "     but: was \"My exception message\""
        );

        assertThat(assertionError.getMessage(), is(equalTo(expectedMessage)));
    }

}
