package rocks.cleancode.hamcrest.throwable;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class CauseMatcher<T extends Throwable> extends TypeSafeDiagnosingMatcher<T> {

    public static <T extends Throwable> Matcher<T> cause(Matcher<Object> causeMatcher) {
        return new CauseMatcher<>(causeMatcher);
    }

    private final Matcher<Object> causeMatcher;

    private CauseMatcher(Matcher<Object> causeMatcher) {
        this.causeMatcher = causeMatcher;
    }

    @Override
    protected boolean matchesSafely(T throwable, Description mismatchDescription) {
        mismatchDescription.appendText("cause ");
        causeMatcher.describeMismatch(throwable.getCause(), mismatchDescription);

        return causeMatcher.matches(throwable.getCause());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("cause ");

        causeMatcher.describeTo(description);
    }

}
