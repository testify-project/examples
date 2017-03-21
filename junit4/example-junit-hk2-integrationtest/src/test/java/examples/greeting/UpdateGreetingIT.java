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

import examples.greeting.entity.GreetingEntity;
import fixture.TestModule;
import java.util.UUID;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.mock;
import org.testifyproject.annotation.Cut;
import org.testifyproject.annotation.Fixture;
import org.testifyproject.annotation.Module;
import org.testifyproject.annotation.Real;
import org.testifyproject.annotation.RequiresContainer;
import org.testifyproject.junit4.integration.HK2IntegrationTest;

/**
 * An integration test that demonstrates the ability to:
 * <ul>
 * <li>load a module using {@link Module @Module} annotation</li>
 * <li>substitute the production database with a container based PostgreSQL
 * database using {@link RequiresContainer @RequiresContainer} annotation</li>
 * <li>specify the the class under test using {@link Cut @Cut} annotation</li>
 * <li>inject the class under test's real collaborating EntityManager instance
 * using {@link Real @Real} annotation</li>
 * <li>inject a managed EntityManager instance using {@link Inject @Inject} and
 * {@link Fixture @Fixture} annotations for verification purpose</li>
 * </ul>
 *
 * @author saden
 */
@Module(TestModule.class)
@RequiresContainer(value = "postgres", version = "9.4")
@RunWith(HK2IntegrationTest.class)
public class UpdateGreetingIT {

    @Cut
    UpdateGreeting cut;

    @Real
    EntityManager entityManager;

    @Inject
    EntityManagerFactory entityManagerFactory;

    @Test(expected = IllegalArgumentException.class)
    public void givenNullIdUpdateGreetingShouldThrowException() {
        //Arrange
        UUID id = null;
        GreetingEntity entity = mock(GreetingEntity.class);

        //Act
        cut.updateGreeting(id, entity);
    }

    @Test(expected = NullPointerException.class)
    public void givenNullGreetingUpdateGreetingShouldThrowException() {
        //Arrange
        UUID id = UUID.fromString("0d216415-1b8e-4ab9-8531-fcbd25d5966f");
        GreetingEntity entity = null;

        //Act
        cut.updateGreeting(id, entity);
    }

    @Test
    public void givenExistingGreetingUpdateGreetingShouldUpdateGreeting() {
        //Arrange
        UUID id = UUID.fromString("0d216415-1b8e-4ab9-8531-fcbd25d5966f");

        //Act
        String phrase = "ciao";
        GreetingEntity entity = new GreetingEntity(phrase);
        cut.updateGreeting(id, entity);

        //Assert
        EntityManager testEntityManager = entityManagerFactory.createEntityManager();
        GreetingEntity result = testEntityManager.find(GreetingEntity.class, id);
        assertThat(result.getPhrase()).isEqualTo(entity.getPhrase());
        testEntityManager.close();
    }
}