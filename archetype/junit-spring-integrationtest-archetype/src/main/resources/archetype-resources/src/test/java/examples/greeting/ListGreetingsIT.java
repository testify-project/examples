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

import org.testify.annotation.Cut;
import org.testify.annotation.Fake;
import org.testify.annotation.Module;
import org.testify.annotation.RequiresResource;
import org.testify.resource.hsql.InMemoryHSQLResource;
import org.testify.junit.integration.SpringIntegrationTest;
import examples.GreetingConfig;
import examples.greeting.repository.GreetingRepository;
import examples.greeting.repository.entity.GreetingEntity;
import static java.util.Collections.EMPTY_LIST;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.given;

/**
 * An integration test that demonstrates the ability to:
 * <ul>
 * <li>load a module using {@link Module @Module} annotation</li>
 * <li>substitute the production database with an in-memory HSQL database using
 * {@link RequiresResource @RequiresResource} annotation</li>
 * <li>specify the the class under test using {@link Cut @Cut} annotation</li>
 * <li>replace the class under test's collaborating GreetingRepository instance
 * with a fake instance using {@link Fake @Fake} annotation</li>
 * </ul>
 *
 * @author saden
 */
@Module(GreetingConfig.class)
@RequiresResource(InMemoryHSQLResource.class)
@RunWith(SpringIntegrationTest.class)
public class ListGreetingsIT {

    @Cut
    ListGreetings cut;

    @Fake
    GreetingRepository greetingRepository;

    @Test
    public void callToListGreetingsShouldReturnEmptyListOfGreetings() {
        //Arrange
        List<GreetingEntity> entities = EMPTY_LIST;
        given(greetingRepository.findAll()).willReturn(entities);

        //Act
        Iterable<GreetingEntity> result = cut.listGreetings();

        //Assert
        assertThat(result).isEqualTo(entities);
    }

}
