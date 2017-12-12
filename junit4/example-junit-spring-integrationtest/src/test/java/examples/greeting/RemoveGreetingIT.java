/*
 * Copyright 2016-2017 Testify Project.
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
package examples.greeting;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.willDoNothing;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.testifyproject.annotation.LocalResource;
import org.testifyproject.annotation.Module;
import org.testifyproject.annotation.Sut;
import org.testifyproject.annotation.Virtual;
import org.testifyproject.junit4.IntegrationTest;
import org.testifyproject.resource.hsql.InMemoryHSQLResource;

import examples.GreetingConfig;
import examples.greeting.repository.GreetingRepository;

/**
 * An integration test that demonstrates the ability to:
 * <ul>
 * <li>load a module using {@link Module @Module} annotation</li>
 * <li>substitute the production database with an in-memory HSQL database using
 * {@link LocalResource @LocalResource} annotation</li>
 * <li>specify the the class under test using {@link Sut @Sut} annotation</li>
 * <li>replace the class under test's collaborating GreetingRepository instance with a virtual
 * instance that delegates to the real instance using {@link Virtual @Virtual} annotation</li>
 * </ul>
 *
 * @author saden
 */
@Module(GreetingConfig.class)
@LocalResource(InMemoryHSQLResource.class)
@RunWith(IntegrationTest.class)
public class RemoveGreetingIT {

    @Sut
    RemoveGreeting sut;

    @Virtual
    GreetingRepository greetingRepository;

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void givenNullKeyRemoveGreetingShouldThrowException() {
        //Act
        sut.removeGreeting(null);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void givenNoneExistentKeyRemoveGreetingShouldThrowException() {
        //Arrange
        UUID id = UUID.fromString("aa216415-1b8e-4ab9-8531-fcbd25d5966f");

        //Act
        sut.removeGreeting(id);
    }

    @Test
    public void givenExistingGreetingRemoveGreetingShouldNotRemoveGreeting() {
        //Arrange
        UUID id = UUID.fromString("0d216415-1b8e-4ab9-8531-fcbd25d5966f");
        willDoNothing().given(greetingRepository).delete(id);

        //Act
        sut.removeGreeting(id);

        //Assert
        assertThat(greetingRepository.findOne(id)).isNotNull();
    }

}
