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
import org.testify.tools.category.ContainerTests;
import org.testify.tools.category.SystemTests;
import examples.GreetingApplication;
import examples.repository.entity.GreetingEntity;
import fixture.TestModule;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.CREATED;
import static org.assertj.core.api.Assertions.assertThat;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.testify.junit.system.SpringSystemTest;

/**
 *
 * @author saden
 */
@Application(GreetingApplication.class)
@Module(TestModule.class)
@RequiresContainer(value = "postgres", version = "9.4")
@Category({ContainerTests.class, SystemTests.class})
@RunWith(SpringSystemTest.class)
public class CreateGreetingResourceST {

    @Cut
    ClientInstance<WebTarget> cut;

    @ConfigHandler
    public void configureClient(ClientBuilder clientBuilder) {
        clientBuilder.register(JacksonFeature.class);
    }

    @Test
    public void callToGetGreetingShouldReturn() {
        //Arrange 
        GreetingEntity greetingEntity = new GreetingEntity("caio");
        Entity<GreetingEntity> entity = Entity.json(greetingEntity);

        //Act
        Response response = cut.getTarget()
                .path("greetings")
                .request()
                .post(entity);

        //Assert
        assertThat(response.getStatus()).isEqualTo(CREATED.getStatusCode());
        assertThat(response.getLocation()).isNotNull();
    }
}
