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

import org.testify.annotation.CollaboratorProvider;
import org.testify.annotation.Cut;
import org.testify.junit.UnitTest;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * <p>
 * A unit test that demonstrates the ability to explictly specify the
 * collaborators of the class under test. This is sometimes necessary due
 * collaborators being final classes (i.e. UUID) that can not be mocked due to
 * JVM restrictions.
 * </p>
 * <p>
 * Notice the {@link Collaborators} annotation on {@link #collaborators()}
 * method which test testify how to create an instance of the class under test
 * with the given collaborators. That the name of the method does not matter but
 * it must not take parameters and must return an array of objects.
 * </p>
 *
 * @author saden
 */
@RunWith(UnitTest.class)
public class GreetingEntityTest {

    @Cut
    GreetingEntity cut;

    @CollaboratorProvider
    Object[] collaborators() {
        return new Object[]{
            UUID.fromString("0d216415-1b8e-4ab9-8531-fcbd25d5966f"),
            "hello"
        };
    }

    @Test
    public void givenNothingNewGreetingEntityShouldCreateInstance() {
        //Act
        GreetingEntity entity = new GreetingEntity();

        //Assert
        assertThat(entity).isNotNull();
    }

    @Test
    public void givenPhraseNewGreetingEntityShouldCreateInstance() {
        //Arrange
        String phrase = "hello";

        //Act
        GreetingEntity entity = new GreetingEntity(phrase);

        //Assert
        assertThat(entity.getId()).isNull();
        assertThat(entity.getPhrase()).isEqualTo(phrase);
    }

    @Test
    public void callToGetidShouldReturnid() {
        //Arrange
        UUID id = UUID.fromString("0d216415-1b8e-4ab9-8531-fcbd25d5966f");

        //Act
        UUID result = cut.getId();

        //Assert
        assertThat(result).isEqualTo(id);
    }

    @Test
    public void callToGetPhraseShouldReturnPhrase() {
        //Arrange
        String phrase = "hello";

        //Act
        String result = cut.getPhrase();

        //Assert
        assertThat(result).isEqualTo(phrase);
    }

    @Test
    public void callToSetidShouldSetid() {
        //Arrange
        UUID id = UUID.fromString("aaa16415-1b8e-4ab9-8531-fcbd25d59aaa");

        //Act
        cut.setId(id);

        //Assert
        assertThat(cut.getId()).isEqualTo(id);
    }

    @Test
    public void callToSetPhraseShouldSetPhrase() {
        //Arrange
        String phrase = "hello";

        //Act
        cut.setPhrase(phrase);

        //Assert
        assertThat(cut.getPhrase()).isEqualTo(phrase);
    }

    @Test
    public void givenEqualGreetingsEqualsShouldReturnTrue() {
        //Arrange
        UUID id = UUID.fromString("0d216415-1b8e-4ab9-8531-fcbd25d5966f");
        String phrase = "hello";
        GreetingEntity entity = new GreetingEntity(phrase);
        entity.setId(id);
        //Act
        boolean result = cut.equals(entity);

        //Assert
        assertThat(result).isTrue();
    }

    @Test
    public void givenUnequalGreetingsEqualsShouldReturnTrue() {
        //Arrange
        String phrase = "ciao";
        GreetingEntity entity = new GreetingEntity(phrase);

        //Act
        boolean result = cut.equals(entity);

        //Assert
        assertThat(result).isFalse();
    }

    @Test
    public void givenNullGreetingEqualsShouldReturnFalse() {
        //Arrange
        GreetingEntity entity = null;

        //Act
        boolean result = cut.equals(entity);

        //Assert
        assertThat(result).isFalse();
    }

    @Test
    public void givenObjectEqualsShouldReturnFalse() {
        //Arrange
        Object object = new Object();

        //Act
        boolean result = cut.equals(object);

        //Assert
        assertThat(result).isFalse();
    }

    @Test
    public void givenSameObjectEqualsShouldReturnFalse() {
        //Act
        boolean result = cut.equals(cut);

        //Assert
        assertThat(result).isTrue();
    }

    @Test
    public void givenEqualGreetingsHashCodeShouldBeTheSame() {
        //Arrange
        UUID id = UUID.fromString("0d216415-1b8e-4ab9-8531-fcbd25d5966f");
        String phrase = "hello";
        GreetingEntity entity = new GreetingEntity(phrase);
        entity.setId(id);
        //Act
        int result = cut.hashCode();

        //Assert
        assertThat(result).isEqualTo(entity.hashCode());
    }

    @Test
    public void callToStringShouldContaineridAndPhrase() {
        //Arrange
        UUID id = UUID.fromString("0d216415-1b8e-4ab9-8531-fcbd25d5966f");
        String phrase = "hello";

        //Act
        String result = cut.toString();

        //Assert
        assertThat(result).contains(id.toString(), phrase);
    }
}
