package rocks.cleancode.hamcrest.throwable;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class WillThrowMatcher<T extends Throwable> extends TypeSafeDiagnosingMatcher<Runnable> {

    public static <T extends Throwable> Matcher<Runnable> willThrow(Class<T> throwableClass) {
        return new WillThrowMatcher<>(throwableClass);
    }

    private final Class<T> throwableClass;

    private WillThrowMatcher(Class<T> throwableClass) {
        this.throwableClass = throwableClass;
    }

    @Override
    protected boolean matchesSafely(Runnable runnable, Description mismatchDescription) {
        try {
            runnable.run();
        } catch (Throwable throwable) {
            mismatchDescription
                    .appendText("was ")
                    .appendText(throwable.getClass().getCanonicalName());

            return throwableClass.equals(throwable.getClass());
        }

        mismatchDescription.appendText("nothing was thrown");

        return false;
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendText("throws ")
                .appendText(throwableClass.getCanonicalName());
    }

}
