package rocks.cleancode.hamcrest.throwable;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class ExceptionMatcher extends TypeSafeDiagnosingMatcher<Runnable> {

    private final Class<? extends Exception> exceptionClass;

    public ExceptionMatcher(Class<? extends Exception> exceptionClass) {
        this.exceptionClass = exceptionClass;
    }

    @Override
    protected boolean matchesSafely(Runnable runnable, Description mismatchDescription) {
        try {
            runnable.run();
        } catch (Exception exception) {
            mismatchDescription
                    .appendText("was ")
                    .appendText(exception.getClass().getCanonicalName());

            return exception.getClass().equals(exceptionClass);
        }

        mismatchDescription.appendText("nothing was thrown");

        return false;
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendText("throws ")
                .appendText(exceptionClass.getCanonicalName());
    }

}
