#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
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
package ${package};

import ${package}.common.RandomUuidSupplier;
import ${package}.model.GreetingModel;
import java.util.Map;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import org.testifyproject.annotation.Cut;
import org.testifyproject.annotation.Fake;
import org.testifyproject.junit4.UnitTest;

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
    Map<UUID, GreetingModel> datastore;

    @Fake
    RandomUuidSupplier randomUuidSupplier;

    @Test
    public void givenMapStoreConstructorShouldNotDoWork() {
        //Act
        CreateGreeting result = new CreateGreeting(datastore, randomUuidSupplier);

        //Assert
        assertThat(result).isNotNull();
        verifyZeroInteractions(datastore, randomUuidSupplier);
    }

    @Test
    public void givenNullCreateGreetingShouldReturn() {
        //Arrange
        GreetingModel model = null;

        //Act
        //Note that since we are using a fake Map to store values an NPE will
        //not be thrown but some Map implementations do not allow null values
        cut.createGreeting(model);
    }

    @Test
    public void givenExistingGreetingUpdateGreetingShouldUpdate() {
        //Arrange
        GreetingModel model = mock(GreetingModel.class);
        UUID id = UUID.fromString("aa216415-1b8e-4ab9-8531-fcbd25d5966f");
        given(randomUuidSupplier.get()).willReturn(id);

        //Act
        cut.createGreeting(model);

        //Assert
        verify(randomUuidSupplier).get();
        verify(datastore).put(id, model);
    }

}
