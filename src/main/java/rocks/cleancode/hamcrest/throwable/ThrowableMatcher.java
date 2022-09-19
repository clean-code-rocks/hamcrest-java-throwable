package rocks.cleancode.hamcrest.throwable;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class ThrowableMatcher<T extends Throwable> extends TypeSafeDiagnosingMatcher<Runnable> {

    public static <T extends Throwable> Matcher<Runnable> willThrow(Class<T> throwableType) {
        return new ThrowableMatcher<>(throwableType);
    }

    private final Class<T> throwableType;

    private ThrowableMatcher(Class<T> throwableType) {
        this.throwableType = throwableType;
    }

    @Override
    protected boolean matchesSafely(Runnable runnable, Description mismatchDescription) {
        try {
            runnable.run();
        } catch (Throwable throwable) {
            mismatchDescription
                    .appendText("was ")
                    .appendText(throwable.getClass().getCanonicalName());

            return throwable.getClass().equals(throwableType);
        }

        mismatchDescription.appendText("nothing was thrown");

        return false;
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendText("throws ")
                .appendText(throwableType.getCanonicalName());
    }

}
