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

import examples.GreetingConfig;
import examples.greeting.repository.GreetingRepository;
import examples.greeting.repository.entity.GreetingEntity;
import java.util.Optional;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.testifyproject.annotation.Cut;
import org.testifyproject.annotation.Module;
import org.testifyproject.annotation.Real;
import org.testifyproject.annotation.RequiresResource;
import org.testifyproject.junit.integration.SpringIntegrationTest;
import org.testifyproject.resource.hsql.InMemoryHSQLResource;

/**
 * An integration test that demonstrates the ability to:
 * <ul>
 * <li>load a module using {@link Module @Module} annotation</li>
 * <li>substitute the production database with an in-memory HSQL database using
 * {@link RequiresResource @RequiresResource} annotation</li>
 * <li>specify the the class under test using {@link Cut @Cut} annotation</li>
 * <li>inject the class under test's real collaborating GreetingRepository
 * instance using {@link Real @Real} annotation</li>
 * </ul>
 *
 * @author saden
 */
@Module(GreetingConfig.class)
@RequiresResource(InMemoryHSQLResource.class)
@RunWith(SpringIntegrationTest.class)
public class GetGreetingIT {

    @Cut
    GetGreeting cut;

    @Real
    GreetingRepository greetingRepository;

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void givenNullGetGreetingShouldThrowException() {
        //Arrange
        UUID id = null;

        //Act
        cut.getGreeting(id);
    }

    @Test
    public void givenNoneExistentKeyGetGreetingShouldReturnAnEmptyOptional() {
        //Arrange
        UUID id = UUID.fromString("aa216415-1b8e-4ab9-8531-fcbd25d5966f");

        //Act
        Optional<GreetingEntity> result = cut.getGreeting(id);

        //Assert
        assertThat(result).isEmpty();
    }

    @Test
    public void givenExistentKeyGetGreetingShouldReturnGreetingEntity() {
        //Arrange
        UUID id = UUID.fromString("0d216415-1b8e-4ab9-8531-fcbd25d5966f");

        //Act
        Optional<GreetingEntity> result = cut.getGreeting(id);

        //Assert
        assertThat(result).isPresent();
    }

}
