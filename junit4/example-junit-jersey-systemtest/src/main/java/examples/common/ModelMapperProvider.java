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
package examples.common;

import org.glassfish.hk2.api.Factory;
import org.jvnet.hk2.annotations.Service;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NamingConventions;

/**
 * A factory class that provides a singleton {@link ModelMapper} instance.
 *
 * @author saden
 */
@Service
public class ModelMapperProvider implements Factory<ModelMapper> {

    @Override
    public ModelMapper provide() {
        ModelMapper mapper = new ModelMapper();

        Configuration configuration = mapper.getConfiguration();
        configuration.setMatchingStrategy(MatchingStrategies.STRICT);
        configuration.setFieldAccessLevel(Configuration.AccessLevel.PUBLIC);
        configuration.setMethodAccessLevel(Configuration.AccessLevel.PUBLIC);
        configuration.setAmbiguityIgnored(false);
        configuration.setDestinationNamingConvention(NamingConventions.JAVABEANS_MUTATOR);
        configuration.setSourceNamingConvention(NamingConventions.JAVABEANS_ACCESSOR);

        return mapper;

    }

    @Override
    public void dispose(ModelMapper instance) {
        //DO NOTHING
    }

}
