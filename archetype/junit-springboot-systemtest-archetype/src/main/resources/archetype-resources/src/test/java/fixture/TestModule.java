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
package fixture;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import static org.hibernate.cfg.AvailableSettings.DATASOURCE;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.testify.ContainerInstance;

/**
 * Test fixture module that defines the datasource of a postgreSQL running
 * inside of a container.
 *
 * @author saden
 */
@Configuration
public class TestModule {

    /**
     * Create a datasource that takes precedence (@Primary) over the production
     * datasource that points to the postgres in the container resource.
     *
     * @param instance the container instance.
     * @return the test data source
     */
    @Primary
    @Bean
    DataSource testDataSource(@Qualifier("postgres") ContainerInstance instance) {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setServerName(instance.getHost());
        dataSource.setPortNumber(instance.findFirstPort().get());

        //Default postgres image database name, user and postword
        dataSource.setDatabaseName("postgres");
        dataSource.setUser("postgres");
        dataSource.setPassword("mysecretpassword");

        return dataSource;
    }

    @Primary
    @Bean
    LocalContainerEntityManagerFactoryBean testEntityManagerFactory(
            EntityManagerFactoryBuilder builder, DataSource dataSource, ApplicationContext applicationContext) {
        Map<String, Object> properties = new HashMap<>();
        properties.put(DATASOURCE, dataSource);
        properties.put("hibernate.ejb.entitymanager_factory_name", applicationContext.getId());

        return builder.dataSource(dataSource)
                .persistenceUnit("test.example.greeter")
                .properties(properties)
                .build();
    }

}
