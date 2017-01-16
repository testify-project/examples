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

import org.testify.annotation.Cut;
import org.testify.annotation.Fake;
import org.testify.junit.UnitTest;
import ${package}.entity.GreetingEntity;
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

/**
 * A basic unit tests that demonstrates the ability to specify a class under
 * test and create and assign a mock instance of its collaborators to it. Notice
 * that the class under test is annotated with {@link Cut} and that its
 * collaborator is annotated with {@link Fake}.
 *
 * @author saden
 */
@RunWith(UnitTest.class)
public class GetGreetingTest {

    @Cut
    GetGreeting cut;

    @Fake
    Map<UUID, GreetingEntity> store;

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
        Optional<GreetingEntity> result = cut.getGreeting(id);

        //Assert
        assertThat(result).isEmpty();
        verify(store).get(id);
    }

    @Test
    public void givenNoneExistentidGetGreetingShouldReturnAnEmptyOptional() {
        //Arrange
        UUID id = UUID.fromString("0d216415-1b8e-4ab9-8531-fcbd25d5966f");

        //Act
        Optional<GreetingEntity> result = cut.getGreeting(id);

        //Assert
        assertThat(result).isEmpty();
        verify(store).get(id);
    }

    @Test
    public void givenExistentidGetGreetingShouldReturnOptionalWithAGreeting() {
        //Arrange
        UUID id = UUID.fromString("0d216415-1b8e-4ab9-8531-fcbd25d5966f");
        GreetingEntity greeting = mock(GreetingEntity.class);
        given(store.get(id)).willReturn(greeting);

        //Act
        Optional<GreetingEntity> result = cut.getGreeting(id);

        //Assert
        assertThat(result).contains(greeting);
        verify(store).get(id);
    }

}
