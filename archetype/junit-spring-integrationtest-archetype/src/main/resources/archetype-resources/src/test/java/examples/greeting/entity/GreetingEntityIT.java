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
package examples.greeting.entity;

import examples.GreetingConfig;
import examples.greeting.repository.GreetingRepository;
import examples.greeting.repository.entity.GreetingEntity;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
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
 * <li>inject the class under test's real collaborating EntityManager instance
 * using {@link Real @Real} annotation</li>
 * <li>inject the real GreetingRepository instance using {@link Real @Real}
 * annotation for verification purpose</li>
 * </ul>
 *
 * @author saden
 */
@Module(GreetingConfig.class)
@RequiresResource(InMemoryHSQLResource.class)
@RunWith(SpringIntegrationTest.class)
public class GreetingEntityIT {

    @Real
    GreetingRepository greetingRepository;

    @Test
    public void givenNewGreetingSaveShouldCreateNewGreeting() {
        //Arrange
        String phrase = "hello";
        GreetingEntity entity = new GreetingEntity(phrase);

        //Act
        greetingRepository.save(entity);

        //Assert
        assertThat(entity).isNotNull();
        GreetingEntity result = greetingRepository.findOne(entity.getId());
        assertThat(result).isEqualTo(entity);
        assertThat(result.hashCode()).isEqualTo(entity.hashCode());
    }

    @Test
    public void givenExistingEntitySaveShouldUpdateExistingGreeting() {
        //Arrange
        UUID id = UUID.fromString("0d216415-1b8e-4ab9-8531-fcbd25d5966f");
        String phrase = "caio";
        GreetingEntity existingEntity = greetingRepository.findOne(id);
        GreetingEntity updatedEntity = new GreetingEntity(id, phrase);

        //Act
        greetingRepository.save(updatedEntity);

        //Assert
        assertThat(existingEntity).isNotEqualTo(updatedEntity);
        assertThat(existingEntity.hashCode()).isNotEqualTo(updatedEntity.hashCode());
    }

    @Test
    public void givenEqualEntitiesEqualsShouldReturnTrue() {
        //Arrange
        UUID id = UUID.fromString("0d216415-1b8e-4ab9-8531-fcbd25d5966f");

        //Act
        GreetingEntity entity1 = greetingRepository.findOne(id);
        GreetingEntity entity2 = greetingRepository.findOne(id);

        //Assert
        assertThat(entity1).isEqualTo(entity2);
    }

    @Test
    public void givenEqualEntityEqualsShouldReturnFalse() {
        //Arrange
        GreetingEntity helloEntity = new GreetingEntity("hello");
        GreetingEntity ciaoEntity = new GreetingEntity("ciao");

        greetingRepository.save(helloEntity);
        greetingRepository.save(ciaoEntity);

        //Act & Assert
        assertThat(helloEntity).isNotEqualTo(ciaoEntity);
    }

    @Test
    public void givenSameEntityEqualsShouldReturnTrue() {
        //Arrange
        UUID id = UUID.fromString("0d216415-1b8e-4ab9-8531-fcbd25d5966f");

        //Act
        GreetingEntity entity = greetingRepository.findOne(id);

        //Act & Assert
        assertThat(entity).isEqualTo(entity);
    }

    @Test
    public void givenNullEntityEqualsShouldReturnFalse() {
        //Arrange
        UUID id = UUID.fromString("0d216415-1b8e-4ab9-8531-fcbd25d5966f");

        //Act
        GreetingEntity entity = greetingRepository.findOne(id);

        //Act & Assert
        assertThat(entity).isNotEqualTo(null);
    }

    @Test
    public void givenDifferentyTypeEntityEqualsShouldReturnFalse() {
        //Arrange
        UUID id = UUID.fromString("0d216415-1b8e-4ab9-8531-fcbd25d5966f");

        //Act
        GreetingEntity entity = greetingRepository.findOne(id);

        //Act & Assert
        assertThat(entity).isNotEqualTo(new Object());
    }

    @Test
    public void callToStringShouldContainIdAndPhrase() {
        //Arrange
        UUID id = UUID.fromString("0d216415-1b8e-4ab9-8531-fcbd25d5966f");

        //Act
        GreetingEntity entity = greetingRepository.findOne(id);

        //Act & Assert
        assertThat(entity.toString()).contains(id.toString(), "hello");
    }

}
