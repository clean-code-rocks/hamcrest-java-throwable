package rocks.cleancode.hamcrest.throwable;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * Matches the throwable message.
 *
 * @param <T> Type of the throwable
 * @since 1.0.0
 */
public class MessageMatcher<T extends Throwable> extends TypeSafeDiagnosingMatcher<T> {

    /**
     * Create a matcher for a throwable message.
     *
     * @param messageMatcher Matcher for the message
     * @return Throwable message matcher
     * @param <T> Type of the throwable
     * @since 1.0.0
     */
    public static <T extends Throwable> Matcher<T> message(Matcher<String> messageMatcher) {
        return new MessageMatcher<>(messageMatcher);
    }

    private final Matcher<String> messageMatcher;

    private MessageMatcher(Matcher<String> messageMatcher) {
        this.messageMatcher = messageMatcher;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean matchesSafely(T throwable, Description mismatchDescription) {
        mismatchDescription.appendText("message ");
        messageMatcher.describeMismatch(throwable.getMessage(), mismatchDescription);

        return messageMatcher.matches(throwable.getMessage());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void describeTo(Description description) {
        description.appendText("message ");

        messageMatcher.describeTo(description);
    }

}
