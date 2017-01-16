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
import org.testify.annotation.Module;
import org.testify.annotation.Real;
import org.testify.annotation.RequiresContainer;
import org.testify.junit.integration.SpringIntegrationTest;
import org.testify.tools.category.ContainerTests;
import org.testify.tools.category.IntegrationTests;
import examples.GreetingConfig;
import examples.greeting.repository.GreetingRepository;
import examples.greeting.repository.entity.GreetingEntity;
import fixture.TestModule;
import java.util.UUID;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

/**
 * An integration test that demonstrates the ability to:
 * <ul>
 * <li>load a module using {@link Module @Module} annotation</li>
 * <li>substitute the production database with a container based PostgreSQL
 * database using {@link RequiresContainer @RequiresContainer} annotation</li>
 * <li>specify the the class under test using {@link Cut @Cut} annotation</li>
 * <li>inject the class under test's real collaborating EntityManager instance
 * using {@link Real @Real} annotation</li>
 * </ul>
 *
 * @author saden
 */
@Module(GreetingConfig.class)
@Module(TestModule.class)
@RequiresContainer(value = "postgres", version = "9.4")
@Category({ContainerTests.class, IntegrationTests.class})
@RunWith(SpringIntegrationTest.class)
public class UpdateGreetingIT {

    @Cut
    UpdateGreeting cut;

    @Real
    GreetingRepository greetingRepository;

    @Test(expected = IllegalArgumentException.class)
    public void givenNullIdUpdateGreetingShouldThrowException() {
        //Arrange
        UUID id = null;
        String phrase = "caio";
        GreetingEntity entity = new GreetingEntity(id, phrase);

        //Act
        cut.updateGreeting(entity);
    }

    @Test
    public void givenNonExistentGreetingUpdateShouldThrowException() {
        //Arrange
        UUID id = UUID.fromString("aa216415-1b8e-4ab9-8531-fcbd25d5966f");
        String phrase = "caio";
        GreetingEntity entity = new GreetingEntity(id, phrase);

        //Act
        cut.updateGreeting(entity);
    }

    @Test
    public void givenExistingGreetingUpdateGreetingShouldUpdateGreeting() {
        //Arrange
        UUID id = UUID.fromString("0d216415-1b8e-4ab9-8531-fcbd25d5966f");
        String phrase = "caio";
        GreetingEntity entity = new GreetingEntity(id, phrase);

        //Act
        cut.updateGreeting(entity);
    }
}
