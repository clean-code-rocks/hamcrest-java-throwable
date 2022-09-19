package rocks.cleancode.hamcrest.throwable;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class ThrowableMessageMatcher<T extends Throwable> extends ThrowablePropertyMatcher<T> {

    private final Matcher<String> messageMatcher;

    public ThrowableMessageMatcher(Matcher<String> messageMatcher) {
        this.messageMatcher = messageMatcher;
    }

    @Override
    protected boolean matchesSafely(T throwable, Description mismatchDescription) {
        messageMatcher.describeMismatch(throwable.getMessage(), mismatchDescription);

        return messageMatcher.matches(throwable.getMessage());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("message ");

        messageMatcher.describeTo(description);
    }

}
