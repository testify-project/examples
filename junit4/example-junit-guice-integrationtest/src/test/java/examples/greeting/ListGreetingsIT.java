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

import static java.util.Collections.EMPTY_LIST;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.testifyproject.annotation.Fake;
import org.testifyproject.annotation.Fixture;
import org.testifyproject.annotation.LocalResource;
import org.testifyproject.annotation.Module;
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
 * <li>inject and replace the class under test's real collaborating EntityManager instance with
 * a fake instance using {@link Fake @Fake} annotation</li>
 * <li>inject a managed EntityManager instance using {@link Inject @Inject} and
 * {@link Fixture @Fixture} annotations for verification purpose</li>
 * </ul>
 *
 * @author saden
 */
@Module(GreetingsModule.class)
@LocalResource(InMemoryHSQLResource.class)
@RunWith(IntegrationTest.class)
public class ListGreetingsIT {

    @Sut
    ListGreetings sut;

    @Fake
    EntityManager entityManager;

    @Test
    public void callToListGreetingsShouldReturnEmptyListOfGreetings() {
        //Arrange
        Query query = mock(Query.class);
        List<GreetingEntity> entities = EMPTY_LIST;
        given(entityManager.createQuery(anyString())).willReturn(query);
        given(query.getResultList()).willReturn(entities);

        //Act
        Collection<GreetingEntity> result = sut.listGreetings();

        //Assert
        assertThat(result).isSameAs(entities);
    }

}
