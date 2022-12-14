# Hamcrest - Throwable

[![Maven Central][Maven Central - badge]][Maven Central - link]
[![Javadoc][Javadoc - badge]][Javadoc - link]
[![Codecov][Codecov - badge]][Codecov - link]
[![License: GPL v3][Licence - badge]][Licence - link]
[![Fossa][Fossa - badge]][Fossa - link]

[Java Hamcrest] matchers for exceptions and errors.

## Requirement

Java 8+

## Installation

### Maven

```xml
<dependency>
    <groupId>rocks.cleancode</groupId>
    <artifactId>hamcrest-throwable</artifactId>
    <version>1.0.0</version>
    <scope>test</scope>
</dependency>
```

## Usage

Three matchers are provided for Throwable: `message(matcher)`, `cause(matcher)` and `willThrow(type)`.

### message(matcher)

This matcher matches the throwable message.

```java
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static rocks.cleancode.hamcrest.throwable.MessageMatcher.message;

Throwable throwable = new NullPointerException("Exception message");

assertThat(throwable, message(is(equalTo("Exception message"))));
```

### cause(matcher)

This matcher matches the throwable cause.

```java
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static rocks.cleancode.hamcrest.throwable.CauseMatcher.cause;

Throwable throwable = new RuntimeException(new NullPointerException("Exception message"));

assertThat(throwable, cause(is(instanceOf(NullPointerException.class))));
```

### willThrow(type)

This matcher matches the type of the exception thrown by a `java.lang.Runnable`.

```java
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static rocks.cleancode.hamcrest.throwable.WillThrowMatcher.willThrow;

assertThat(() -> Objects.requireNonNull(null), willThrow(NullPointerException.class));
```

It is possible to add matchers.

```java
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static rocks.cleancode.hamcrest.throwable.CauseMatcher.cause;
import static rocks.cleancode.hamcrest.throwable.MessageMatcher.message;
import static rocks.cleancode.hamcrest.throwable.WillThrowMatcher.willThrow;

assertThat(
        () -> {
            throw new RuntimeException("Exception message", new NullPointerException());
        },
        willThrow(RuntimeException.class)
            .and(message(is(equalTo("Exception message"))))
            .and(cause(is(instanceOf(NullPointerException.class))))
);
```

[Java Hamcrest]: https://hamcrest.org/JavaHamcrest/

[Maven Central - badge]: https://img.shields.io/maven-central/v/rocks.cleancode/hamcrest-throwable?color=brightgreen
[Maven Central - link]: https://search.maven.org/artifact/rocks.cleancode/hamcrest-throwable
[Javadoc - badge]: https://javadoc.io/badge2/rocks.cleancode/hamcrest-throwable/javadoc.svg
[Javadoc - link]: https://javadoc.io/doc/rocks.cleancode/hamcrest-throwable
[Codecov - badge]: https://codecov.io/gh/clean-code-rocks/hamcrest-java-throwable/branch/main/graph/badge.svg?token=X7OB8PWHSF
[Codecov - link]: https://codecov.io/gh/clean-code-rocks/hamcrest-java-throwable
[Licence - badge]: https://img.shields.io/badge/License-GPLv3-blue.svg
[Licence - link]: https://www.gnu.org/licenses/gpl-3.0
[Fossa - badge]: https://app.fossa.com/api/projects/git%2Bgithub.com%2Fclean-code-rocks%2Fhamcrest-java-throwable.svg?type=shield
[Fossa - link]: https://app.fossa.com/projects/git%2Bgithub.com%2Fclean-code-rocks%2Fhamcrest-java-throwable?ref=badge_shield
