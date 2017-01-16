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
import org.testify.annotation.Virtual;
import org.testify.junit.UnitTest;
import examples.greeting.entity.GreetingEntity;
import java.util.Map;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * A unit test that demonstrates discovery of initialized collaborators and
 * creating virtual instance of the real collaborator of the class under test.
 * This is useful for SPI implementations which are not allowed to have
 * parameterized constructors.
 *
 * @author saden
 */
@RunWith(UnitTest.class)
public class UpdateGreetingTest {

    @Cut
    UpdateGreeting cut;

    @Virtual
    Map<UUID, GreetingEntity> store;

    @Test(expected = IllegalArgumentException.class)
    public void givenNullUUIDUpdateGreetingShouldThrowException() {
        UUID id = null;
        GreetingEntity model = mock(GreetingEntity.class);

        cut.updateGreeting(id, model);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenNullGreetingShouldThrowException() {
        UUID id = UUID.fromString("0d216415-1b8e-4ab9-8531-fcbd25d5966f");
        GreetingEntity model = null;

        cut.updateGreeting(id, model);
    }

    @Test
    public void givenExistingGreetingUpdateGreetingShouldUpdate() {
        //Arrange
        UUID id = UUID.fromString("0d216415-1b8e-4ab9-8531-fcbd25d5966f");
        String phrase = "caio";
        GreetingEntity entity = mock(GreetingEntity.class);
        GreetingEntity model = mock(GreetingEntity.class);

        given(store.get(id)).willReturn(entity);
        given(model.getPhrase()).willReturn(phrase);

        //Act
        cut.updateGreeting(id, model);

        //Assert
        verify(store).get(id);
        verify(model).getPhrase();
        verify(entity).setPhrase(phrase);
    }

}
