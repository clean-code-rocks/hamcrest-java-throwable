package rocks.cleancode.hamcrest.throwable;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WillThrowMatcher<T extends Throwable> extends TypeSafeDiagnosingMatcher<Runnable> {

    public static <T extends Throwable> WillThrowMatcher<T> willThrow(Class<T> throwableClass) {
        return new WillThrowMatcher<>(throwableClass);
    }

    private final Class<T> throwableClass;

    private final List<Matcher<T>> throwableMatchers;

    private WillThrowMatcher(Class<T> throwableClass) {
        this.throwableClass = throwableClass;
        this.throwableMatchers = new ArrayList<>();
    }

    @Override
    protected boolean matchesSafely(Runnable runnable, Description mismatchDescription) {
        try {
            runnable.run();
        } catch (Throwable throwable) {
            boolean typeMatches = throwableClass.equals(throwable.getClass());

            if (!typeMatches) {
                mismatchDescription
                        .appendText("was ")
                        .appendText(throwable.getClass().getCanonicalName());

                return false;
            }

            Optional<Matcher<T>> throwableMatcher = throwableMatchers.stream()
                    .filter(matcher -> !matcher.matches(throwable))
                    .findFirst();

            throwableMatcher.ifPresent(matcher -> matcher.describeMismatch(throwable, mismatchDescription));

            return !throwableMatcher.isPresent();
        }

        mismatchDescription.appendText("nothing was thrown");

        return false;
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendText("throws ")
                .appendText(throwableClass.getCanonicalName());

        throwableMatchers
                .forEach(throwableMatcher -> {
                    description.appendText(" and ");

                    throwableMatcher.describeTo(description);
                });
    }

    public WillThrowMatcher<T> and(Matcher<T> throwableMather) {
        throwableMatchers.add(throwableMather);

        return this;
    }

}
