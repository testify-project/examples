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
import org.testify.junit.UnitTest;
import examples.greeting.entity.GreetingEntity;
import java.util.Map;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * A unit test that demonstrates discovery of collaborators based only on its
 * type. Notice that the name of the collaborator in this test is "datastore"
 * where as the name of the collaborator in {@link UpdateGreeting} is "store".
 *
 * @author saden
 */
@RunWith(UnitTest.class)
public class CreateGreetingTest {

    @Cut
    CreateGreeting cut;

    @Fake
    Map<UUID, GreetingEntity> datastore;

    @Test
    public void givenMapStoreNewGetGreetingShouldNotDoWorkInConstructor() {
        //Act
        CreateGreeting result = new CreateGreeting(datastore);

        //Assert
        assertThat(result).isNotNull();
        verifyZeroInteractions(datastore);
    }

    @Test
    public void givenMapStoreNewCreateGreetingShouldNotDoWorkInConstructor() {
        //Act
        CreateGreeting result = new CreateGreeting(datastore);

        //Assert
        assertThat(result).isNotNull();
        verifyZeroInteractions(datastore);
    }

    @Test(expected = NullPointerException.class)
    public void givenNullGreetingShouldThrowException() {
        GreetingEntity model = null;

        cut.createGreeting(model);
    }

    @Test
    public void givenExistingGreetingUpdateGreetingShouldUpdate() {
        //Arrange
        GreetingEntity model = mock(GreetingEntity.class);

        //Act
        cut.createGreeting(model);

        //Assert
        verify(datastore).put(any(UUID.class), eq(model));
        verify(model).setId(any(UUID.class));
    }

}
