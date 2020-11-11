package org.springframework.test.context.junit.jupiter;

import java.util.stream.Stream;

import org.springframework.context.ApplicationEvent;

/**
 * {@code ApplicationEvents} encapsulates all {@linkplain ApplicationEvent application events} that were fired during
 * the execution of a single test method.
 *
 * @author Sam Brannen
 * @author Oliver Drotbohm
 * @since 5.3.1
 * @see org.springframework.context.ApplicationEvent
 * @see org.springframework.context.ApplicationListener
 */
public interface ApplicationEvents {
	/**
	 * Stream all application events that were fired during test execution.
	 *
	 * @return a stream of all application events
	 */
	Stream<ApplicationEvent> stream();

	/**
	 * Stream all application events or event payloads of the given type that were fired during test execution.
	 *
	 * @param <T> the event type
	 * @param type the type of events or payloads to stream; never {@code null}
	 * @return a stream of all application events or event payloads of the specified type
	 */
	<T> Stream<T> stream(Class<T> type);
}
