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

import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.testifyproject.annotation.Fixture;
import org.testifyproject.annotation.LocalResource;
import org.testifyproject.annotation.Module;
import org.testifyproject.annotation.Real;
import org.testifyproject.annotation.Sut;
import org.testifyproject.junit4.IntegrationTest;
import org.testifyproject.resource.hsql.InMemoryHSQLResource;

import examples.GreetingsModule;
import examples.greeting.entity.GreetingEntity;

/**
 * An integration test that demonstrates the ability to:
 * <ul>
 * <li>substitute the production database with an in-memory HSQL database using
 * {@link LocalResource @LocalResource} annotation</li>
 * <li>specify the the class under test using {@link Sut @Sut} annotation</li>
 * <li>inject the class under test's real collaborating EntityManager instance using
 * {@link Real @Real} annotation</li>
 * <li>inject a managed EntityManager instance using {@link Inject @Inject} and
 * {@link Fixture @Fixture} annotations for verification purpose</li>
 * </ul>
 *
 * @author saden
 */
@Module(GreetingsModule.class)
@LocalResource(InMemoryHSQLResource.class)
@RunWith(IntegrationTest.class)
public class GetGreetingIT {

    @Sut
    GetGreeting sut;

    @Real
    EntityManager entityManager;

    @Test(expected = IllegalArgumentException.class)
    public void givenNullGetGreetingShouldThrowException() {
        //Arrange
        UUID id = null;

        //Act
        sut.getGreeting(id);
    }

    @Test
    public void givenNoneExistentKeyGetGreetingShouldReturnAnEmptyOptional() {
        //Arrange
        UUID id = UUID.fromString("aa216415-1b8e-4ab9-8531-fcbd25d596aa");

        //Act
        Optional<GreetingEntity> result = sut.getGreeting(id);

        //Assert
        assertThat(result).isEmpty();
    }

    @Test
    public void givenExistentKeyGetGreetingShouldReturnOptionalWithAGreeting() {
        //Arrange
        UUID id = UUID.fromString("0d216415-1b8e-4ab9-8531-fcbd25d5966f");

        //Act
        Optional<GreetingEntity> result = sut.getGreeting(id);

        //Assert
        assertThat(result).isNotEmpty();
    }

}
