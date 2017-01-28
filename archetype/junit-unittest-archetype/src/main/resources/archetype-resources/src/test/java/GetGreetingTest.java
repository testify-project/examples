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
package ${package};

import ${package}.model.GreetingModel;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import org.testify.annotation.CollaboratorProvider;
import org.testify.annotation.Cut;
import org.testify.annotation.Virtual;
import org.testify.junit.UnitTest;

/**
 * A basic unit tests that demonstrates the ability to specify a class under
 * test's collaborators by annotating a method in the test class with
 * {@link CollaboratorProvider}.
 *
 * @author saden
 */
@RunWith(UnitTest.class)
public class GetGreetingTest {

    @Cut
    GetGreeting cut;

    @Virtual
    Map<UUID, GreetingModel> store;

    @CollaboratorProvider
    Object[] collaborators() {
        return new Object[]{
            new HashMap<>()
        };
    }

    @Test
    public void givenMapStoreNewGetGreetingShouldNotDoWorkInConstructor() {
        //Act
        GetGreeting result = new GetGreeting(store);

        //Assert
        assertThat(result).isNotNull();
        verifyZeroInteractions(store);
    }

    @Test
    public void givenNullGetGreetingShouldReturnAnEmptyOptional() {
        //Arrange
        UUID id = null;

        //Act
        Optional<GreetingModel> result = cut.getGreeting(id);

        //Assert
        assertThat(result).isEmpty();
        verify(store).get(id);
    }

    @Test
    public void givenNoneExistentidGetGreetingShouldReturnAnEmptyOptional() {
        //Arrange
        UUID id = UUID.fromString("0d216415-1b8e-4ab9-8531-fcbd25d5966f");

        //Act
        Optional<GreetingModel> result = cut.getGreeting(id);

        //Assert
        assertThat(result).isEmpty();
        verify(store).get(id);
    }

    @Test
    public void givenExistentidGetGreetingShouldReturnOptionalWithAGreeting() {
        //Arrange
        UUID id = UUID.fromString("0d216415-1b8e-4ab9-8531-fcbd25d5966f");
        GreetingModel greeting = mock(GreetingModel.class);
        given(store.get(id)).willReturn(greeting);

        //Act
        Optional<GreetingModel> result = cut.getGreeting(id);

        //Assert
        assertThat(result).contains(greeting);
        verify(store).get(id);
    }

}
