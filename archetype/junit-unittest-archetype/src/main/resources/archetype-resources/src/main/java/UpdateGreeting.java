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
package ${package};

import ${package}.entity.GreetingEntity;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * A services that updates an existing greeting.
 *
 * @author saden
 */
public class UpdateGreeting {

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private final Map<UUID, GreetingEntity> store = new HashMap<>();

    /**
     * Update greeting with the given id.
     *
     * @param id the id
     * @param model the greeting model
     */
    public void updateGreeting(UUID id, GreetingEntity model) {
        GreetingEntity entity = store.get(id);

        if (entity == null) {
            throw new IllegalArgumentException("Entity not found.");
        }

        entity.setPhrase(model.getPhrase());
    }
}
