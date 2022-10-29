package rocks.cleancode.hamcrest.throwable;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * Matches the throwable cause.
 *
 * @param <T> Type of the throwable
 *
 * @since 1.0.0
 */
public class CauseMatcher<T extends Throwable> extends TypeSafeDiagnosingMatcher<T> {

    /**
     * Create a matcher for a throwable cause.
     *
     * @param causeMatcher Matcher for the cause
     * @return Throwable cause matcher
     * @param <T> Type of the throwable
     *
     * @since 1.0.0
     */
    public static <T extends Throwable> Matcher<T> cause(Matcher<Object> causeMatcher) {
        return new CauseMatcher<>(causeMatcher);
    }

    private final Matcher<Object> causeMatcher;

    private CauseMatcher(Matcher<Object> causeMatcher) {
        this.causeMatcher = causeMatcher;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean matchesSafely(T throwable, Description mismatchDescription) {
        mismatchDescription.appendText("cause ");
        causeMatcher.describeMismatch(throwable.getCause(), mismatchDescription);

        return causeMatcher.matches(throwable.getCause());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void describeTo(Description description) {
        description.appendText("cause ");

        causeMatcher.describeTo(description);
    }

}
