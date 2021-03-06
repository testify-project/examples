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
package examples.greeting.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.testifyproject.annotation.LocalResource;
import org.testifyproject.annotation.Module;
import org.testifyproject.annotation.Real;
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
 * <li>inject the class under test's real collaborating EntityManager instance using
 * {@link Real @Real} annotation</li>
 * <li>inject the real GreetingRepository instance using {@link Real @Real} annotation for
 * verification purpose</li>
 * </ul>
 *
 * @author saden
 */
@Module(GreetingConfig.class)
@LocalResource(InMemoryHSQLResource.class)
@RunWith(IntegrationTest.class)
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
        Optional<GreetingEntity> result = greetingRepository.findById(entity.getId());
        assertThat(result).contains(entity);
    }

    @Test
    public void givenExistingEntitySaveShouldUpdateExistingGreeting() {
        //Arrange
        UUID id = UUID.fromString("0d216415-1b8e-4ab9-8531-fcbd25d5966f");
        String phrase = "caio";
        Optional<GreetingEntity> existingEntity = greetingRepository.findById(id);
        GreetingEntity updatedEntity = new GreetingEntity(id, phrase);

        //Act
        greetingRepository.save(updatedEntity);

        //Assert
        assertThat(existingEntity).isPresent();
        assertThat(existingEntity.get()).isNotEqualTo(updatedEntity);
    }

    @Test
    public void givenEqualEntitiesEqualsShouldReturnTrue() {
        //Arrange
        UUID id = UUID.fromString("0d216415-1b8e-4ab9-8531-fcbd25d5966f");

        //Act
        Optional<GreetingEntity> entity1 = greetingRepository.findById(id);
        Optional<GreetingEntity> entity2 = greetingRepository.findById(id);

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
        Optional<GreetingEntity> entity = greetingRepository.findById(id);

        //Act & Assert
        assertThat(entity).isNotEmpty();
        assertThat(entity).isEqualTo(entity);
    }

    @Test
    public void givenNullEntityEqualsShouldReturnFalse() {
        //Arrange
        UUID id = UUID.fromString("0d216415-1b8e-4ab9-8531-fcbd25d5966f");

        //Act
        Optional<GreetingEntity> entity = greetingRepository.findById(id);

        //Act & Assert
        assertThat(entity).isNotEmpty();
        assertThat(entity.get()).isNotEqualTo(null);
    }

    @Test
    public void givenDifferentyTypeEntityEqualsShouldReturnFalse() {
        //Arrange
        UUID id = UUID.fromString("0d216415-1b8e-4ab9-8531-fcbd25d5966f");

        //Act
        Optional<GreetingEntity> entity = greetingRepository.findById(id);

        //Act & Assert
        assertThat(entity).isNotEmpty();
        assertThat(entity.get()).isNotEqualTo(new Object());
    }

    @Test
    public void callToStringShouldContainIdAndPhrase() {
        //Arrange
        UUID id = UUID.fromString("0d216415-1b8e-4ab9-8531-fcbd25d5966f");

        //Act
        Optional<GreetingEntity> entity = greetingRepository.findById(id);

        //Act & Assert
        assertThat(entity).isNotEmpty();
        assertThat(entity.get().toString()).contains(id.toString(), "hello");
    }

}
