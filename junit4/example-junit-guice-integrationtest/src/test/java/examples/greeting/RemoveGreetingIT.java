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

import examples.GreetingsModule;
import examples.greeting.entity.GreetingEntity;
import java.util.UUID;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import org.testifyproject.annotation.Cut;
import org.testifyproject.annotation.Fixture;
import org.testifyproject.annotation.Module;
import org.testifyproject.annotation.LocalResource;
import org.testifyproject.annotation.Virtual;
import org.testifyproject.junit4.integration.GuiceIntegrationTest;
import org.testifyproject.resource.hsql.InMemoryHSQLResource;

/**
 * An integration test that demonstrates the ability to:
 * <ul>
 * <li>substitute the production database with an in-memory HSQL database using
 * {@link LocalResource @LocalResource} annotation</li>
 * <li>specify the the class under test using {@link Cut @Cut} annotation</li>
 * <li>inject and replace the class under test's real collaborating
 * EntityManager instance with a virtual instance that delegates class to the
 * real instance using {@link Virtual @Virtual} annotation</li>
 * <li>inject a managed EntityManager instance using {@link Inject @Inject} and
 * {@link Fixture @Fixture} annotations for verification purpose</li>
 * </ul>
 *
 * @author saden
 */
@Module(GreetingsModule.class)
@LocalResource(InMemoryHSQLResource.class)
@RunWith(GuiceIntegrationTest.class)
public class RemoveGreetingIT {

    @Cut
    RemoveGreeting cut;

    @Virtual
    EntityManager entityManager;

    @Test(expected = IllegalArgumentException.class)
    public void givenNullKeyRemoveGreetingShouldThrowException() {
        //Act
        cut.removeGreeting(null);
    }

    @Test(expected = EntityNotFoundException.class)
    public void givenNoneExistentKeyRemoveGreetingShouldThrowException() {
        //Arrange
        UUID id = UUID.fromString("aa216415-1b8e-4ab9-8531-fcbd25d596aa");

        //Act
        cut.removeGreeting(id);
    }

    @Test
    public void givenExistingGreetingRemoveGreetingShouldNotRemoveGreeting() {
        //Arrange
        UUID id = UUID.fromString("0d216415-1b8e-4ab9-8531-fcbd25d5966f");
        GreetingEntity entity = new GreetingEntity(id, "hello");

        given(entityManager.getReference(GreetingEntity.class, id)).willReturn(entity);
        willDoNothing().given(entityManager).remove(entity);

        //Act
        cut.removeGreeting(id);

        //Assert: the entity was not removed
        GreetingEntity result = entityManager.getReference(GreetingEntity.class, entity.getId());
        assertThat(result).isNotNull();
    }

}
