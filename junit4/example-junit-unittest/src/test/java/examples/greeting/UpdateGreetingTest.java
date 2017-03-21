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

import examples.greeting.model.GreetingModel;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.testifyproject.annotation.Cut;
import org.testifyproject.annotation.Virtual;
import org.testifyproject.junit4.UnitTest;

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
    Map<UUID, GreetingModel> store = new HashMap<>();

    @Test
    public void givenNonExistentIdUpdateShouldNotUpdateStore() {
        //Arrange
        UUID id = UUID.fromString("0d216415-1b8e-4ab9-8531-fcbd25d5966f");
        GreetingModel model = mock(GreetingModel.class);

        //Act
        cut.updateGreeting(id, model);

        //Assert
        assertThat(store).doesNotContainEntry(id, model);
    }

    @Test
    public void givenExistingGreetingUpdateGreetingShouldUpdateStore() {
        //Arrange
        UUID id = UUID.fromString("0d216415-1b8e-4ab9-8531-fcbd25d5966f");
        String phrase = "caio";
        GreetingModel existingEntity = new GreetingModel(phrase);
        store.put(id, existingEntity);

        GreetingModel model = mock(GreetingModel.class);

        //Act
        cut.updateGreeting(id, model);

        //Assert
        assertThat(store).containsEntry(id, model);
        verify(store).computeIfPresent(eq(id), any());
    }

}
