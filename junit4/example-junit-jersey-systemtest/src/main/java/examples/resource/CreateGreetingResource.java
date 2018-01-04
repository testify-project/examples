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

import java.net.URI;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.modelmapper.ModelMapper;

import examples.resource.entity.GreetingEntity;
import examples.resource.model.GreetingModel;

/**
 * A resource that creates a new greeting.
 *
 * @author saden
 */
@Path("greetings")
@Transactional
public class CreateGreetingResource {

    private final EntityManager entityManager;
    private final UriInfo uriInfo;
    private final ModelMapper modelMapper;

    @Inject
    CreateGreetingResource(EntityManager entityManager, UriInfo uriInfo, ModelMapper modelMapper) {
        this.entityManager = entityManager;
        this.uriInfo = uriInfo;
        this.modelMapper = modelMapper;
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    public Response create(@Valid GreetingModel model) {
        GreetingEntity entity = modelMapper.map(model, GreetingEntity.class);

        entityManager.persist(entity);

        URI location = uriInfo.getAbsolutePathBuilder()
                .path(entity.getId().toString())
                .build();

        return Response.created(location).build();
    }

}
