/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.acme.myproject.moduleA;

// import static org.moduliths.test.assertj.PublishedEventAssertions.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.moduliths.test.PublishedEvents;
import org.moduliths.test.PublishedEvents.TypedPublishedEvents;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.acme.myproject.NonVerifyingModuleTest;
import com.acme.myproject.moduleB.ServiceComponentB;

/**
 * @author Oliver Drotbohm
 */
@NonVerifyingModuleTest
class ModuleATest {

	@Autowired ApplicationContext context;
	@Autowired PublishedEvents events;

	@Test
	void bootstrapsModuleAOnly() {

		context.getBean(ServiceComponentA.class);

		assertThatExceptionOfType(NoSuchBeanDefinitionException.class)
				.isThrownBy(() -> context.getBean(ServiceComponentB.class));
	}

	@Test // #17
	void assertEventsFired() {

		context.getBean(ServiceComponentA.class).fireEvent();

		TypedPublishedEvents<SomeEventA> matching = events.ofType(SomeEventA.class) //
				.matching(it -> it.getMessage().equals("Message"));

		assertThat(matching).hasSize(1);
	}

	@Test // #17
	void injectsPublishedEventsIntoMethod(PublishedEvents events) {
		assertThat(events).isNotNull();
	}
}
