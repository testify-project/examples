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
package examples;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.ConstrainedTo;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;

import org.glassfish.hk2.api.DynamicConfiguration;
import org.glassfish.hk2.api.DynamicConfigurationService;
import org.glassfish.hk2.api.Populator;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.extras.ExtrasUtilities;
import org.glassfish.hk2.utilities.ClasspathDescriptorFileFinder;

/**
 * Discovers service descriptor files and configure and populates the service locator.
 *
 * @author saden
 */
@ConstrainedTo(RuntimeType.SERVER)
public class GreetingsFeature implements Feature {

    private final ServiceLocator serviceLocator;
    private final DynamicConfigurationService dcs;

    @Inject
    GreetingsFeature(ServiceLocator serviceLocator, DynamicConfigurationService dcs) {
        this.serviceLocator = serviceLocator;
        this.dcs = dcs;
    }

    @Override
    public boolean configure(FeatureContext context) {
        //Enable HK2 Features
        ExtrasUtilities.enableOperations(serviceLocator);

        try {
            //populate service locator
            DynamicConfiguration dc = dcs.createDynamicConfiguration();
            Populator populator = dcs.getPopulator();
            populator.populate(new ClasspathDescriptorFileFinder());
            dc.commit();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        return true;
    }

}
