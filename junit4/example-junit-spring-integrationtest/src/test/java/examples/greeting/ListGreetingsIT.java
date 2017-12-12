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
import static org.mockito.BDDMockito.given;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.testifyproject.annotation.Fake;
import org.testifyproject.annotation.LocalResource;
import org.testifyproject.annotation.Module;
import org.testifyproject.annotation.Sut;
import org.testifyproject.junit4.IntegrationTest;
import org.testifyproject.resource.hsql.InMemoryHSQLResource;

import examples.GreetingConfig;
import examples.greeting.repository.GreetingRepository;
import examples.greeting.repository.entity.GreetingEntity;

/**
 * An integration test that demonstrates the ability to:
 * <ul>
 * <li>load a module using {@link Module @Module} annotation</li>
 * <li>substitute the production database with an in-memory HSQL database using
 * {@link LocalResource @LocalResource} annotation</li>
 * <li>specify the the class under test using {@link Sut @Sut} annotation</li>
 * <li>replace the class under test's collaborating GreetingRepository instance with a fake
 * instance using {@link Fake @Fake} annotation</li>
 * </ul>
 *
 * @author saden
 */
@Module(GreetingConfig.class)
@LocalResource(InMemoryHSQLResource.class)
@RunWith(IntegrationTest.class)
public class ListGreetingsIT {

    @Sut
    ListGreetings sut;

    @Fake
    GreetingRepository greetingRepository;

    @Test
    public void callToListGreetingsShouldReturnEmptyListOfGreetings() {
        //Arrange
        List<GreetingEntity> entities = EMPTY_LIST;
        given(greetingRepository.findAll()).willReturn(entities);

        //Act
        Iterable<GreetingEntity> result = sut.listGreetings();

        //Assert
        assertThat(result).isEqualTo(entities);
    }

}
