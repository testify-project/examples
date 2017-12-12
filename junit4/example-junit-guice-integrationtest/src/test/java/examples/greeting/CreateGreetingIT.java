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

import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.testifyproject.annotation.Fixture;
import org.testifyproject.annotation.Module;
import org.testifyproject.annotation.Real;
import org.testifyproject.annotation.Sut;
import org.testifyproject.annotation.VirtualResource;
import org.testifyproject.junit4.IntegrationTest;

import examples.GreetingsModule;
import examples.greeting.entity.GreetingEntity;
import fixture.TestModule;

/**
 * An integration test that demonstrates the ability to:
 * <ul>
 * <li>load a module using {@link Module @Module} annotation</li>
 * <li>substitute the production database with a container based PostgreSQL database using
 * {@link VirtualResource @VirtualResource} annotation</li>
 * <li>specify the the class under test using {@link Sut @Sut} annotation</li>
 * <li>inject the class under test's real collaborating EntityManager instance using
 * {@link Real @Real} annotation</li>
 * <li>inject a managed EntityManager instance using {@link Inject @Inject} and
 * {@link Fixture @Fixture} annotations for verification purpose</li>
 * </ul>
 *
 * @author saden
 */
@Module(value = TestModule.class, test = true)
@Module(GreetingsModule.class)
@VirtualResource(value = "postgres", version = "9.4")
@RunWith(IntegrationTest.class)
public class CreateGreetingIT {

    @Sut
    CreateGreeting sut;

    @Real
    EntityManager entityManager;

    //@Test(expected = IllegalArgumentException.class)
    public void givenNullGreetingSaveGreetingShouldThrowException() {
        sut.createGreeting(null);
    }

    @Test
    public void givenNewGreetingCreateGreetingShouldCreateGreeting() {
        //Arrange
        String phrase = "Hello";
        GreetingEntity entity = new GreetingEntity(phrase);

        //Act
        sut.createGreeting(entity);

        //Assert
        UUID id = entity.getId();
        assertThat(id).isNotNull();
    }

}
