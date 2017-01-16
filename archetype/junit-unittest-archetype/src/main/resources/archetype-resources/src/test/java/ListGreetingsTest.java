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
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * A unit test that demonstrates the ability to verify class under test method
 * calls by setting {@link Cut${symbol_pound}value()} to true.
 *
 * @author saden
 */
@RunWith(UnitTest.class)
public class ListGreetingsTest {

    @Cut(true)
    ListGreetings cut;

    @Fake
    Map<UUID, GreetingEntity> store;

    @Test
    public void givenMapStoreNewListGreetingShouldNotDoWorkInConstructor() {
        //Act
        ListGreetings result = new ListGreetings(store);

        //Assert
        assertThat(result).isNotNull();
        verifyZeroInteractions(store);
    }

    @Test
    public void givenEmptyStoreListGreetingShouldReturnAnEmptyCollection() {
        //Act
        Collection<GreetingEntity> result = cut.listGreetings();

        //Assert
        assertThat(result).isEmpty();
        verify(store).values();

        //We can verify calls to the class under test
        verify(cut).listGreetings();
    }

    @Test
    public void givenStoreGreetingsListGreetingShouldReturnCollectionOfGreetings() {
        //Arrange
        @SuppressWarnings("unchecked")
        Collection<GreetingEntity> greetings = mock(Collection.class);
        given(store.values()).willReturn(greetings);

        //Act
        Collection<GreetingEntity> result = cut.listGreetings();

        //Assert
        assertThat(result).isSameAs(greetings);
        verify(store).values();

        //We can verify calls to the class under test
        verify(cut).listGreetings();
    }

}
