package rocks.cleancode.hamcrest.throwable;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class ThrowableMatcher extends TypeSafeDiagnosingMatcher<Runnable> {

    private final Class<? extends Throwable> throwableType;

    public ThrowableMatcher(Class<? extends Throwable> throwableType) {
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
