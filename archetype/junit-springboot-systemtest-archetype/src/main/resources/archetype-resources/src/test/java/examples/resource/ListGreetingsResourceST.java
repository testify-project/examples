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
package examples.resource;

import org.testify.ClientInstance;
import org.testify.annotation.Application;
import org.testify.annotation.ConfigHandler;
import org.testify.annotation.Cut;
import org.testify.annotation.Module;
import org.testify.annotation.RequiresContainer;
import org.testify.junit.system.SpringBootSystemTest;
import org.testify.tools.category.ContainerTests;
import org.testify.tools.category.SystemTests;
import examples.GreetingApplication;
import examples.resource.repository.entity.GreetingEntity;
import fixture.TestConfigHandler;
import fixture.TestModule;
import java.util.List;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.OK;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

/**
 *
 * @author saden
 */
@Application(GreetingApplication.class)
@Module(TestModule.class)
@ConfigHandler(TestConfigHandler.class)
@RequiresContainer(value = "postgres", version = "9.4")
@Category({ContainerTests.class, SystemTests.class})
@RunWith(SpringBootSystemTest.class)
public class ListGreetingsResourceST {

    @Cut
    ClientInstance<WebTarget> cut;

    @Test
    public void callToListGreetingsShouldReturnGreetings() {
        //Act
        Response response = cut.getTarget()
                .path("greetings")
                .path("list")
                .request()
                .get();

        //Assert
        assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
        GenericType<List<GreetingEntity>> genericType = new GenericType<List<GreetingEntity>>() {
        };
        List<GreetingEntity> result = response.readEntity(genericType);
        assertThat(result).hasSize(1);
    }

}
