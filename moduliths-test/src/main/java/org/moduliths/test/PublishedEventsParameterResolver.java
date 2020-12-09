/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.moduliths.test;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.ApplicationEvents;
import org.springframework.test.context.junit.jupiter.ApplicationEventsExtension;

/**
 * Provides instances of {@link PublishedEvents} as test method parameters.
 *
 * @author Oliver Drotbohm
 */
class PublishedEventsParameterResolver extends ApplicationEventsExtension {

	PublishedEventsParameterResolver() {
		super();
	}

	PublishedEventsParameterResolver(Function<ExtensionContext, ApplicationContext> context) {
		super(context);
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.test.context.junit.jupiter.ApplicationEventsExtension#supportsParameter(org.junit.jupiter.api.extension.ParameterContext, org.junit.jupiter.api.extension.ExtensionContext)
	 */
	@Override
	public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
		return Stream.of(ApplicationEvents.class, PublishedEvents.class)
				.anyMatch(it -> it.isAssignableFrom(parameterContext.getParameter().getType()));
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.test.context.junit.jupiter.ApplicationEventsExtension#resolveParameter(org.junit.jupiter.api.extension.ParameterContext, org.junit.jupiter.api.extension.ExtensionContext)
	 */
	@Override
	public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {

		ApplicationEvents events = createApplicationEvents(parameterContext, extensionContext);

		return parameterContext.getParameter().getType().equals(PublishedEvents.class)
				? PublishedEvents.of(events.stream().collect(Collectors.toList()))
				: events;
	}
}
