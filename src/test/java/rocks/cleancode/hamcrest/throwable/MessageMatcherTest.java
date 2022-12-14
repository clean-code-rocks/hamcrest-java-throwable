package rocks.cleancode.hamcrest.throwable;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static rocks.cleancode.hamcrest.throwable.MessageMatcher.message;

public class MessageMatcherTest {

    @Test
    public void should_match_throwable_message() {
        Throwable throwable = new NullPointerException("Exception message");

        assertThat(throwable, message(is(equalTo("Exception message"))));
    }

    @Test
    public void should_fail_when_throwable_message_does_not_match() {
        Throwable throwable = new NullPointerException("Exception message");

        AssertionError assertionError = assertThrows(
                AssertionError.class,
                () -> assertThat(throwable, message(is(equalTo("Other expected message"))))
        );

        String expectedMessage = String.format(
                "%n%s%n%s",
                "Expected: message is \"Other expected message\"",
                "     but: message was \"Exception message\""
        );

        assertThat(assertionError.getMessage(), is(equalTo(expectedMessage)));
    }

}
