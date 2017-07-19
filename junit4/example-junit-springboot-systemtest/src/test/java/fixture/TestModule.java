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
package fixture;

import java.net.InetAddress;
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
     * @param inetAddress the container address.
     * @return the test data source
     */
    @Primary
    @Bean
    DataSource testDataSource(@Qualifier("resource:/postgres:9.4/resource") InetAddress inetAddress) {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setServerName(inetAddress.getHostAddress());
        dataSource.setPortNumber(5432);

        //Default postgres image database name, user and postword
        dataSource.setDatabaseName("postgres");
        dataSource.setUser("postgres");
        dataSource.setPassword("mysecretpassword");

        return dataSource;
    }

    /**
     * Create and configure a test entity manager bean factory.
     *
     * @param builder the entity manager builder
     * @param dataSource the test data source
     * @param applicationContext the application context
     * @return an entity manager bean factory
     */
    @Primary
    @Bean
    LocalContainerEntityManagerFactoryBean testEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            DataSource dataSource,
            ApplicationContext applicationContext) {
        Map<String, Object> properties = new HashMap<>();
        properties.put(DATASOURCE, dataSource);
        properties.put("hibernate.ejb.entitymanager_factory_name", applicationContext.getId());

        return builder.dataSource(dataSource)
                .persistenceUnit("test.example.greeter")
                .properties(properties)
                .build();
    }

}
