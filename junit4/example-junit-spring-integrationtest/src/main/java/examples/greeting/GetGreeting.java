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
package examples.greeting;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import examples.greeting.repository.GreetingRepository;
import examples.greeting.repository.entity.GreetingEntity;

/**
 * A service that retrieve an existing greeting.
 *
 * @author saden
 */
@Service
public class GetGreeting {

    private final GreetingRepository greetingRepository;

    @Autowired
    GetGreeting(GreetingRepository greetingRepository) {
        this.greetingRepository = greetingRepository;
    }

    /**
     * Get a greeting with the given id.
     *
     * @param id the greeting id
     * @return the optional containing the greeting, empty otherwise
     */
    public Optional<GreetingEntity> getGreeting(UUID id) {
        return greetingRepository.findById(id);
    }

}
