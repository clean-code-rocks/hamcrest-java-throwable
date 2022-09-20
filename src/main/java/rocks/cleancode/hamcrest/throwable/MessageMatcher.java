package rocks.cleancode.hamcrest.throwable;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class MessageMatcher<T extends Throwable> extends TypeSafeDiagnosingMatcher<T> {

    public static <T extends Throwable> TypeSafeDiagnosingMatcher<T> message(Matcher<String> messageMatcher) {
        return new MessageMatcher<>(messageMatcher);
    }

    private final Matcher<String> messageMatcher;

    private MessageMatcher(Matcher<String> messageMatcher) {
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
