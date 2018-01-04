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

import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import examples.resource.repository.GreetingRepository;
import examples.resource.repository.entity.GreetingEntity;

/**
 * A resource that retrieves an existing greeting.
 *
 * @author saden
 */
@RestController
public class GetGreetingResource {

    private final GreetingRepository greetingRepository;

    @Inject
    GetGreetingResource(GreetingRepository greetingRepository) {
        this.greetingRepository = greetingRepository;
    }

    @RequestMapping(
            path = "/greetings/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity getGreeting(@NotNull @PathVariable("id") UUID id) {
        Optional<GreetingEntity> result = greetingRepository.findById(id);

        return result.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
