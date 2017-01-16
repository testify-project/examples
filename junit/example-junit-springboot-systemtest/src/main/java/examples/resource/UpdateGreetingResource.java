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

import examples.resource.repository.GreetingRepository;
import examples.resource.repository.entity.GreetingEntity;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * A resource that updates an existing greeting.
 *
 * @author saden
 */
@RestController
public class UpdateGreetingResource {

    private final GreetingRepository greetingRepository;

    @Autowired
    UpdateGreetingResource(GreetingRepository greetingRepository) {
        this.greetingRepository = greetingRepository;
    }

    @RequestMapping(
            path = "/greetings/{id}",
            method = RequestMethod.PUT,
            consumes = {
                MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE
            })
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity updateGreeting(@NotNull @PathVariable("id") UUID id,
            @Valid @RequestBody GreetingEntity model) {

        model.setId(id);
        GreetingEntity result = greetingRepository.save(model);

        return ResponseEntity.accepted().build();
    }
}
