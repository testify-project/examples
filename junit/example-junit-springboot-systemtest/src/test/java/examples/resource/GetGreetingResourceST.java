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

import examples.GreetingApplication;
import examples.resource.repository.entity.GreetingEntity;
import fixture.TestConfigHandler;
import fixture.TestModule;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.OK;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.testifyproject.ClientInstance;
import org.testifyproject.annotation.Application;
import org.testifyproject.annotation.ConfigHandler;
import org.testifyproject.annotation.Cut;
import org.testifyproject.annotation.Module;
import org.testifyproject.annotation.RequiresContainer;
import org.testifyproject.junit.system.SpringBootSystemTest;
import org.testifyproject.tools.category.ContainerTests;
import org.testifyproject.tools.category.SystemTests;

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
public class GetGreetingResourceST {

    @Cut
    ClientInstance<WebTarget> cut;

    @Test
    public void callToGetGreetingShouldReturnGreeting() {
        //Act
        Response response = cut.getTarget()
                .path("greetings")
                .path("0d216415-1b8e-4ab9-8531-fcbd25d5966f")
                .request()
                .get();

        //Assert
        assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
        GreetingEntity result = response.readEntity(GreetingEntity.class);
        assertThat(result).isNotNull();
    }
}
