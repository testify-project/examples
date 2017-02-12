#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/*
 * Copyright 2016-2017 Sharmarke Aden.
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

import examples.database.transaction.PerTransactionImpl;
import examples.greeting.entity.GreetingEntity;
import java.util.UUID;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import static org.assertj.core.api.Assertions.assertThat;
import org.glassfish.hk2.extras.operation.OperationManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.mock;
import org.testifyproject.annotation.Cut;
import org.testifyproject.annotation.Fixture;
import org.testifyproject.annotation.RequiresResource;
import org.testifyproject.annotation.Virtual;
import org.testifyproject.junit.integration.HK2IntegrationTest;
import org.testifyproject.resource.hsql.InMemoryHSQLResource;

/**
 * An integration test that demonstrates the ability to:
 * <ul>
 * <li>substitute the production database with an in-memory HSQL database using
 * {@link RequiresResource @RequiresResource} annotation</li>
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
@RequiresResource(InMemoryHSQLResource.class)
@RunWith(HK2IntegrationTest.class)
public class RemoveGreetingIT {

    @Cut
    RemoveGreeting cut;

    @Virtual
    EntityManager entityManager;

    @Inject
    OperationManager operationManager;

    @Inject
    EntityManagerFactory entityManagerFactory;

    @Test(expected = IllegalArgumentException.class)
    public void givenNullIdRemoveGreetingShouldThrowException() {
        //Act
        cut.removeGreeting(null);
    }

    @Test(expected = EntityNotFoundException.class)
    public void givenNoneExistentIdRemoveGreetingShouldThrowException() {
        //Arrange
        UUID id = UUID.fromString("aa216415-1b8e-4ab9-8531-fcbd25d596aa");

        //Act
        cut.removeGreeting(id);
    }

    @Test
    public void givenExistingGreetingRemoveGreetingShouldNotRemoveGreeting() {
        //Arrange
        operationManager.createAndStartOperation(PerTransactionImpl.INSTANCE);
        GreetingEntity entity = mock(GreetingEntity.class);
        UUID id = UUID.fromString("0d216415-1b8e-4ab9-8531-fcbd25d5966f");
        given(entityManager.getReference(GreetingEntity.class, id)).willReturn(entity);
        willDoNothing().given(entityManager).remove(entity);

        //Act
        cut.removeGreeting(id);

        //Assert
        EntityManager testEntityManager = entityManagerFactory.createEntityManager();
        GreetingEntity result = testEntityManager.find(GreetingEntity.class, id);
        assertThat(result).isNotNull();
        testEntityManager.close();
    }

}
