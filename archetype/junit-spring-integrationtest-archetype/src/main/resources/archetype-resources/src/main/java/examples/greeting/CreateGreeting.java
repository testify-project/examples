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
package examples.greeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import examples.greeting.repository.GreetingRepository;
import examples.greeting.repository.entity.GreetingEntity;

/**
 * A service that creates a new greeting.
 *
 * @author saden
 */
@Service
@Transactional
public class CreateGreeting {

    private final GreetingRepository greetingRepository;

    @Autowired
    CreateGreeting(GreetingRepository greetingRepository) {
        this.greetingRepository = greetingRepository;
    }

    /**
     * Create a new greeting.
     *
     * @param entity the greeting entity that will be created
     */
    public void createGreeting(GreetingEntity entity) {
        greetingRepository.save(entity);
    }
}
