package rocks.cleancode.hamcrest.throwable;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

public class ThrowableMatcher<T extends Throwable> extends TypeSafeDiagnosingMatcher<Runnable> {

    public static <T extends Throwable> Matcher<Runnable> willThrow(Class<T> throwableType) {
        return new ThrowableMatcher<>(is(instanceOf(throwableType)));
    }

    private final Matcher<T> throwableMatcher;

    private ThrowableMatcher(Matcher<T> throwableMatcher) {
        this.throwableMatcher = throwableMatcher;
    }

    @Override
    protected boolean matchesSafely(Runnable runnable, Description mismatchDescription) {
        try {
            runnable.run();
        } catch (Throwable throwable) {
            mismatchDescription
                    .appendText("was ")
                    .appendText(throwable.getClass().getCanonicalName());

            return throwableMatcher.matches(throwable);
        }

        mismatchDescription.appendText("nothing was thrown");

        return false;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("throwable ");

        throwableMatcher.describeTo(description);
    }

}
