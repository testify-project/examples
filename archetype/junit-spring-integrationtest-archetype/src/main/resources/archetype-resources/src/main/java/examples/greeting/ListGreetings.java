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

import examples.greeting.repository.GreetingRepository;
import examples.greeting.repository.entity.GreetingEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * A service that retrieves all greetings.
 *
 * @author saden
 */
@Service
public class ListGreetings {

    private final GreetingRepository greetingRepository;

    @Autowired
    ListGreetings(GreetingRepository greetingRepository) {
        this.greetingRepository = greetingRepository;
    }

    /**
     * Get all greetings.
     *
     * @return a collection of all the greetings, empty collection otherwise
     */
    public Iterable<GreetingEntity> listGreetings() {
        return greetingRepository.findAll();
    }
}