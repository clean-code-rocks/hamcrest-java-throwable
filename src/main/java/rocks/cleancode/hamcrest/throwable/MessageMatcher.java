package rocks.cleancode.hamcrest.throwable;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class MessageMatcher<T extends Throwable> extends TypeSafeDiagnosingMatcher<T> {

    public static <T extends Throwable> Matcher<T> message(Matcher<String> exceptionMessageMatcher) {
        return new MessageMatcher<>(exceptionMessageMatcher);
    }

    private final Matcher<String> exceptionMessageMatcher;

    private MessageMatcher(Matcher<String> exceptionMessageMatcher) {
        this.exceptionMessageMatcher = exceptionMessageMatcher;
    }

    @Override
    protected boolean matchesSafely(T throwable, Description mismatchDescription) {
        exceptionMessageMatcher.describeMismatch(throwable.getMessage(), mismatchDescription);

        return exceptionMessageMatcher.matches(throwable.getMessage());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("exception message ");

        exceptionMessageMatcher.describeTo(description);
    }

}
