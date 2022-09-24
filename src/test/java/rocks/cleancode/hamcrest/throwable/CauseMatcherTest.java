package rocks.cleancode.hamcrest.throwable;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static rocks.cleancode.hamcrest.throwable.CauseMatcher.cause;

public class CauseMatcherTest {

    @Test
    public void should_match_throwable_cause() {
        Throwable throwableWithoutCause = new NullPointerException("Exception message");
        Throwable throwableWithCause = new RuntimeException(throwableWithoutCause);

        assertThat(throwableWithoutCause, cause(is(nullValue())));
        assertThat(throwableWithCause, cause(is(instanceOf(NullPointerException.class))));
    }

    @Test
    public void should_fail_when_throwable_cause_does_not_match() {
        Throwable throwableWithoutCause = new NullPointerException("Exception message");
        Throwable throwableWithCause = new RuntimeException(throwableWithoutCause);

        AssertionError assertionError = assertThrows(
                AssertionError.class,
                () -> assertThat(throwableWithCause, cause(is(instanceOf(IllegalArgumentException.class))))
        );

        String expectedMessage = String.format(
                "%n%s%n%s",
                "Expected: cause is an instance of java.lang.IllegalArgumentException",
                "     but: cause <java.lang.NullPointerException: Exception message> is a java.lang.NullPointerException"
        );

        assertThat(assertionError.getMessage(), is(equalTo(expectedMessage)));
    }

}
