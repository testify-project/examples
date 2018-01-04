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
package examples.resource;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import examples.resource.entity.GreetingEntity;
import examples.resource.model.GreetingModel;

/**
 * A resource that updates an existing greeting.
 *
 * @author saden
 */
@Path("greetings")
@Transactional
public class UpdateGreetingResource {

    private final EntityManager entityManager;

    @Inject
    UpdateGreetingResource(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PUT
    @Path("{id}")
    @Consumes({APPLICATION_JSON, APPLICATION_FORM_URLENCODED})
    public Response updateGreeting(@NotNull @PathParam("id") UUID id, @Valid GreetingModel model) {
        GreetingEntity entity = entityManager.find(GreetingEntity.class, id);

        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        entity.setPhrase(model.getPhrase());
        entityManager.persist(entity);

        return Response.accepted().build();
    }
}
